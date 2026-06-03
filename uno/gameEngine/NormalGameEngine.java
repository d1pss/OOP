package uno.gameEngine;

//Java default packages
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import uno.cardEffect.CardEffect;
import uno.cards.Card;
import uno.cards.CardFactory;
import uno.output.OutputCommand;
import uno.parser.*;
import uno.players.Player;
import uno.players.PlayerFactory;
import uno.rules.Rule;

public class NormalGameEngine extends AbstractGameEngine {

    // Used Singleton Patern
    private static NormalGameEngine instance = null;

    public static NormalGameEngine getInstance(int numberOfPlayers, CardFactory cardFactory, PlayerFactory playerFactory, Rule ruleSet, OutputCommand out, DeckLoader deckLoader, ScriptParserFactory scriptParserFactory){
        if(instance == null){
            instance = new NormalGameEngine(numberOfPlayers, cardFactory, playerFactory, ruleSet, out, deckLoader, scriptParserFactory);
        }
        return instance;
    }

    private NormalGameEngine(int numberOfPlayers, CardFactory cardFactory, PlayerFactory playerFactory, Rule ruleSet, OutputCommand out, DeckLoader deckLoader, ScriptParserFactory scriptParserFactory){
        super(numberOfPlayers, cardFactory, playerFactory, ruleSet, out, deckLoader, scriptParserFactory);
    }


    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Efects Context ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    
    public void drawCards(Player player, int numberCardsToDraw){
        Card cardDrwan = null;
        for(int i = 0; i < numberCardsToDraw; i++){
            try {
                cardDrwan = drawCardFromPile();
            } catch (Exception e) {
                out.outputGameEndNoWinners();
                return;
            }
            
            player.drawCard(cardDrwan);
        }
    }

    @Override
    public void reverseWay(){
        isGameMovingClockwise = !isGameMovingClockwise;
    }

    @Override
    public void skipPlayers(int numberSkips){
        for(int i = 0; i < numberSkips; i++){
            nextTurn();
        }
    }

    @Override
    public List<Player> getReadOnlyPlayersList(){
        return Collections.unmodifiableList(this.players);
    }


    @Override
    public int nextPlayerId(){
        if(isGameMovingClockwise){
            //if the game is moving clockwise
            //if the current player is the last player, the next player is the first player (id 0)
            if(idPlayerInCurrTurn == numberOfPlayers - 1){
                return 0;
            }else{
                return idPlayerInCurrTurn + 1;
            } 
        }else{
            //if the game is moving counterclockwise
            //if the current player is the first player, the next player is the last player (id numberOfPlayers - 1)
            if(idPlayerInCurrTurn == 0){
                return numberOfPlayers - 1;
            }else{
                return idPlayerInCurrTurn - 1;
            }
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------*/
    /*------------------------------------- Piles Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    private Card getTopCard() throws Exception{
        if(discardPile.isEmpty()){
            throw new Exception("Discard pile is empty");
        }
        return discardPile.getLast();
    }

    private void discardCardToPile(Card card){
        discardPile.add(card);
    }
    
    private Card drawCardFromPile() throws Exception{
        if(drawPile.isEmpty()){
            throw new Exception("Draw pile is empty");
        }
        return drawPile.removeFirst();
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------*/
    /*----------------------------------- Command Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    public boolean commandPlayCard(int playerId, int cardIndex){
        if(playerId != idPlayerInCurrTurn){
            out.outputError("Not player  " + playerId + " turn\n");
            return true;
        }
        if(players.get(playerId).getHand().size() <= cardIndex || cardIndex < 0){
            //out.outputError("Player " + playerId + " does not have a card in index " + cardIndex + "\n");
            return true;
        }

        Card cardToBePlayed = players.get(playerId).placeCard(cardIndex);

        Card topCard = null;
        try {
            topCard = getTopCard();
        } catch (Exception e) {
            out.outputError("No top card available\n");
            return true;
        }

        if(!ruleSet.isPlayable(topCard, cardToBePlayed, afterWlidCardColor)){
            out.outputError("Card " + cardToBePlayed.getCardString() + " is not playable\n");
            return true;
        }

        if(cardToBePlayed.isWildCard()) lastWildCardPlayerId = playerId;

        out.outputPlayCard(playerId, cardToBePlayed);

        CardEffect effect = ruleSet.getEffectOf(cardToBePlayed);

        effect.execute(this, out, playerId, cardToBePlayed);

        if(drawPile.isEmpty()) return true;

        discardCardToPile(cardToBePlayed);

        if(players.get(playerId).getHand().isEmpty()){
            out.outputGameEndPlayerWin(playerId);
        }
        
        if(!cardToBePlayed.isWildCard()){
            nextTurn();
            out.outputTurnAdvance(idPlayerInCurrTurn);
        }
        return false;
    }

    public boolean commandDrawCard(int playerId){
        Card drawnCard = null;
        try {
            drawnCard = drawCardFromPile();
        } catch (Exception e) {
            out.outputGameEndNoWinners();
            return true;
        }

        players.get(playerId).drawCard(drawnCard);

        out.outputDrawCardFromPile(playerId, drawnCard);

        nextTurn();
        out.outputTurnAdvance(idPlayerInCurrTurn);
        return false;
    }

    public boolean commandSetColorAfterWildCard(int playerId, String color){
       Card topCard = null;
        try {
            topCard = getTopCard();
        } catch (Exception e) {
            out.outputError("No top card available\n");
            return true;
        }

        if(!topCard.isWildCard()){
            out.outputError("Can only change color if last card was a Wild card\n");
            return true;
        }

        if(playerId != lastWildCardPlayerId){
            out.outputError("Only player " + lastWildCardPlayerId + " can choose the color\n");
            return true;
        }

        afterWlidCardColor = color;

        out.outputChoseColor(playerId, color);

        nextTurn();
        out.outputTurnAdvance(idPlayerInCurrTurn);
        return false;
    }

    private void nextTurn(){
        idPlayerInCurrTurn = nextPlayerId();
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Main code -----------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    public void startGame(Reader deck, Reader script){
        //check if the number of players is valid for the rule set
        if(!ruleSet.isNumberOfPlayersValid(numberOfPlayers)){
            out.outputError("Number of Players is not valid\n");
            return;
        }

        //init thescript parser and load the deck
        scriptParser = new NormalScriptParser(script);
        try {
            drawPile = deckLoader.createDeck(deck, cardFactory);
        } catch (Exception e) {
            // TODO: handle exception
        }

        //draw the first card of the deck
        Card topCard = null;
        try {
            topCard = drawCardFromPile();
        } catch (Exception e) {
            // TODO: handle exception
        }

        if(topCard.isWildCard()){
            // TODO error first top card can not be wild card
        }

        //discard the first card of the deck
        discardCardToPile(topCard);

        //create the players
        for(int j = 0; j < numberOfPlayers; j++){
            players.add(playerFactory.createPlayer(j));
        }

        //deal 7 cards to each player
        Card nextCard = null;
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < numberOfPlayers; j++){

                try {
                    nextCard = drawCardFromPile();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                
                players.get(j).drawCard(nextCard);
            }
        }

        out.outputGameStart(players, topCard);

        try {
            scriptParser.nextCommand(this, out);
        } catch (Exception e) {
            // TODO: handle exception
        }
        

    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/
}
