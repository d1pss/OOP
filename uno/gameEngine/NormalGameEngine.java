package uno.gameEngine;

//Java default packages
import java.io.Reader;
import java.util.Collections;
import java.util.List;

//Player
import uno.players.Player;
import uno.players.PlayerFactory;
import uno.cardEffect.CardEffect;
//Cards
import uno.cards.Card;
import uno.cards.CardFactory;
import uno.output.OutputCommand;
//Rules
import uno.rules.Rule;

//Parser
import uno.parser.*;

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
                // TODO: handle exception
            }
            
            player.drawCard(cardDrwan);
        }
    }

    public void reverseWay(){
        if(isGameMovingClockwise){
            isGameMovingClockwise = false;
        }else{
            isGameMovingClockwise = true;
        }
    }

    public void skipPlayers(int numberSkips){
        for(int i = 0; i < numberSkips; i++){
            nextTurn();
        }
    }

    public List<Player> getReadOnlyPlayersList(){
        return Collections.unmodifiableList(this.players);
    }


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
        return drawPile.removeLast();
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------*/
    /*----------------------------------- Command Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    public void commandPlayCard(int playerId, int cardIndex){
        Card cardToBePlayed = players.get(playerId).placeCard(cardIndex);

        Card topCard = null;
        try {
            topCard = getTopCard();
        } catch (Exception e) {
            // TODO: handle exception
        }

        if(!ruleSet.isPlayable(topCard, cardToBePlayed, afterWlidCardColor)){
            // TODO invalid play exit
        }

        if(cardToBePlayed.isWildCard()) lastWildCardPlayerId = playerId;

        out.outputPlayCard(playerId, cardToBePlayed);

        CardEffect effect = ruleSet.getEffectOf(cardToBePlayed);

        effect.execute(this, out, playerId, cardToBePlayed);

        discardCardToPile(cardToBePlayed);
        
        if(!cardToBePlayed.isWildCard()){
            nextTurn();
            out.outputTurnAdvance(playerId);
        }
        
    }

    public void commandDrawCard(int playerId){
        Card drawnCard = null;
        try {
            drawnCard = drawCardFromPile();
        } catch (Exception e) {
            // TODO: handle exception
        }

        players.get(playerId).drawCard(drawnCard);

        out.outputDrawCardFromPile(playerId, drawnCard);

        nextTurn();
        out.outputTurnAdvance(playerId);
    }

    public void commandSetColorAfterWildCard(int playerId, String color){
       Card topCard = null;
        try {
            topCard = getTopCard();
        } catch (Exception e) {
            // TODO: handle exception
        }

        if(!topCard.isWildCard() || playerId != lastWildCardPlayerId){
            //TODO canot set color after normal or special card or anhother player setting the color
        }

        afterWlidCardColor = color;

        nextTurn();
        out.outputTurnAdvance(playerId);
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
            // TODO exit in this case
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
            scriptParser.nextCommand(this);
        } catch (Exception e) {
            // TODO: handle exception
        }
        

    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/
}
