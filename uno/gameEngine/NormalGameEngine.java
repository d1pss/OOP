package uno.gameEngine;

// Java default packages
import java.io.Reader;
import java.util.Collections;
import java.util.List;

// Player
import uno.players.Player;
import uno.players.PlayerFactory;
import uno.cardEffect.CardEffect;
// Cards
import uno.cards.Card;
import uno.cards.CardFactory;
import uno.output.OutputCommand;
// Rules
import uno.rules.Rule;
// Parser
import uno.parser.*;


/**
 * The concrete implementation of the UNO game engine for the standard ruleset.
 * This class applies the Singleton design pattern to ensure only one active game 
 * instance exists at any given time. It manages the physical execution of game mechanics, 
 * including pile management, turn rotation, and command processing.
 */
public class NormalGameEngine extends AbstractGameEngine {

    /**
     * The single, globally accessible instance of the game engine (Singleton Pattern).
     */
    private static NormalGameEngine instance = null;

    /**
     * Retrieves the active instance of the NormalGameEngine, creating it if it doesn't exist yet.
     *
     * @param numberOfPlayers     The total number of players joining the game.
     * @param cardsPerPlayer      The initial number of cards dealt to each player.
     * @param cardFactory         The factory to create the game cards.
     * @param playerFactory       The factory to create the players.
     * @param ruleSet             The specific ruleset to be enforced during the game.
     * @param out                 The output handler for game logs.
     * @param deckLoader          The loader to read the deck file.
     * @param scriptParserFactory The factory to build the script parser.
     * @return The Singleton instance of the game engine.
     */
    public static NormalGameEngine getInstance(int numberOfPlayers, int cardsPerPlayer, CardFactory cardFactory, 
                                               PlayerFactory playerFactory, Rule ruleSet, OutputCommand out, 
                                               DeckLoader deckLoader, ScriptParserFactory scriptParserFactory) {
        if (instance == null) {
            instance = new NormalGameEngine(numberOfPlayers, cardsPerPlayer, cardFactory, playerFactory, 
                                            ruleSet, out, deckLoader, scriptParserFactory);
        }
        return instance;
    }

    /**
     * Private constructor to prevent direct instantiation from outside the class.
     */
    private NormalGameEngine(int numberOfPlayers, int cardsPerPlayer, CardFactory cardFactory, 
                             PlayerFactory playerFactory, Rule ruleSet, OutputCommand out, 
                             DeckLoader deckLoader, ScriptParserFactory scriptParserFactory) {
        super(numberOfPlayers, cardsPerPlayer, cardFactory, playerFactory, ruleSet, out, deckLoader, scriptParserFactory);
    }

    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Effects Context -----------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public void drawCards(Player player, int numberCardsToDraw) {
        Card cardDrawn = null;
        for (int i = 0; i < numberCardsToDraw; i++) {
            try {
                cardDrawn = drawCardFromPile();
            } catch (Exception e) {
                out.outputGameEndNoWinners();
                return;
            }
            player.drawCard(cardDrawn);
        }
    }

    @Override
    public void reverseWay() {
        isGameMovingClockwise = !isGameMovingClockwise;
    }

    @Override
    public void skipPlayers(int numberSkips) {
        for (int i = 0; i < numberSkips; i++) {
            nextTurn();
        }
    }

    @Override
    public List<Player> getReadOnlyPlayersList() {
        return Collections.unmodifiableList(this.players);
    }

    @Override
    public int nextPlayerId() {
        if (isGameMovingClockwise) {
            // If the game is moving clockwise:
            // If the current player is the last player, loop back to the first player (id 0)
            if (idPlayerInCurrTurn == numberOfPlayers - 1) {
                return 0;
            } else {
                return idPlayerInCurrTurn + 1;
            } 
        } else {
            // If the game is moving counter-clockwise:
            // If the current player is the first player, loop back to the last player
            if (idPlayerInCurrTurn == 0) {
                return numberOfPlayers - 1;
            } else {
                return idPlayerInCurrTurn - 1;
            }
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------------------*/
    /*------------------------------------- Piles Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    /**
     * Retrieves the top card from the discard pile without removing it.
     *
     * @return The card currently on top of the discard pile.
     * @throws Exception If the discard pile is empty.
     */
    private Card getTopCard() throws Exception {
        if (discardPile.isEmpty()) {
            throw new Exception("Discard pile is empty");
        }
        return discardPile.getLast();
    }

    /**
     * Adds a played card to the top of the discard pile.
     *
     * @param card The card to be discarded.
     */
    private void discardCardToPile(Card card) {
        discardPile.add(card);
    }
    
    /**
     * Removes and returns the top card from the draw pile.
     *
     * @return The card drawn from the top of the pile.
     * @throws Exception If the draw pile is completely empty.
     */
    private Card drawCardFromPile() throws Exception {
        if (drawPile.isEmpty()) {
            throw new Exception("Draw pile is empty");
        }
        return drawPile.removeFirst();
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------------------*/
    /*----------------------------------- Command Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public boolean commandPlayCard(int playerId, int cardIndex) {
        if (playerId != idPlayerInCurrTurn) {
            out.outputError("Not player " + playerId + " turn\n");
            return true;
        }
        
        if (players.get(playerId).getHand().size() <= cardIndex || cardIndex < 0) {
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

        if (!ruleSet.isPlayable(topCard, cardToBePlayed, afterWildCardColor)) {
            out.outputError("Card " + cardToBePlayed.getCardString() + " is not playable\n");
            return true;
        }

        if (cardToBePlayed.isWildCard()) {
            lastWildCardPlayerId = playerId;
        }

        out.outputPlayCard(playerId, cardToBePlayed);

        CardEffect effect = ruleSet.getEffectOf(cardToBePlayed);
        effect.execute(this, out, playerId, cardToBePlayed);

        if (drawPile.isEmpty()) {
            return true;
        }

        discardCardToPile(cardToBePlayed);

        if (players.get(playerId).getHand().isEmpty()) {
            out.outputGameEndPlayerWin(playerId);
        }
        
        if (!cardToBePlayed.isWildCard()) {
            nextTurn();
            out.outputTurnAdvance(idPlayerInCurrTurn);
        }
        
        return false;
    }

    @Override
    public boolean commandDrawCard(int playerId) {
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

    @Override
    public boolean commandSetColorAfterWildCard(int playerId, String color) {
        Card topCard = null;
        try {
            topCard = getTopCard();
        } catch (Exception e) {
            out.outputError("No top card available\n");
            return true;
        }

        if (!topCard.isWildCard()) {
            out.outputError("Can only change color if last card was a Wild card\n");
            return true;
        }

        if (playerId != lastWildCardPlayerId) {
            out.outputError("Only player " + lastWildCardPlayerId + " can choose the color\n");
            return true;
        }

        afterWildCardColor = color;
        out.outputChoseColor(playerId, color);

        nextTurn();
        out.outputTurnAdvance(idPlayerInCurrTurn);
        
        return false;
    }

    /**
     * Advances the game state to the next player's turn.
     */
    private void nextTurn() {
        idPlayerInCurrTurn = nextPlayerId();
    }

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Main code -----------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public void startGame(Reader deck, Reader script) {
        if (!ruleSet.isNumberOfPlayersValid(numberOfPlayers)) {
            out.outputError("Number of Players is not valid\n");
            return;
        }

        scriptParser = scriptParserFactory.createScriptParser(script);
        
        try {
            drawPile = deckLoader.createDeck(deck, cardFactory);
        } catch (Exception e) {
            out.outputError("Error loading the deck: " + e.getMessage() + "\n");
            return; 
        }

        Card topCard = null;
        try {
            topCard = drawCardFromPile();
        } catch (Exception e) {
            out.outputError("The deck is empty. Cannot draw the first card.\n");
            return; 
        }

        if (topCard.isWildCard()) {
            out.outputError("The first card of the game cannot be a Wild card.\n");
            return; 
        }

        discardCardToPile(topCard);

        for (int j = 0; j < numberOfPlayers; j++) {
            players.add(playerFactory.createPlayer(j));
        }

        Card nextCard = null;
        for (int i = 0; i < cardsPerPlayer; i++) {
            for (int j = 0; j < numberOfPlayers; j++) {
                try {
                    nextCard = drawCardFromPile();
                } catch (Exception e) {
                    out.outputError("Not enough cards in the deck to deal starting hands.\n");
                    return; 
                }
                players.get(j).drawCard(nextCard);
            }
        }

        out.outputGameStart(players, topCard);

        try {
            scriptParser.nextCommand(this, out);
        } catch (Exception e) {
            out.outputError("Error while executing the script: " + e.getMessage() + "\n");
        }
    }
    
    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/
}