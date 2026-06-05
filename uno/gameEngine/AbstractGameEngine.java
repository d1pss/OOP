package uno.gameEngine;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import uno.players.Player;
import uno.players.PlayerFactory;
import uno.cards.Card;
import uno.cards.CardFactory;
import uno.output.OutputCommand;
import uno.rules.Rule;
import uno.parser.*;

/**
 * Provides a foundational implementation for the UNO game engine.
 * This abstract class holds the central game state (players, piles, turn direction) 
 * and orchestrates the interaction between cards, rules, parsers, and the output system.
 * It implements {@link EffectContext} to allow card effects to manipulate the game state safely,
 * and {@link GameCommands} to receive and process instructions from the script parser.
 */
public abstract class AbstractGameEngine implements EffectContext, GameCommands {

    /*-------------------------------------------------------------------------------------------*/
    // PLAYER STATE                                         
    /*-------------------------------------------------------------------------------------------*/

    /**
     * The factory used to instantiate players for the game.
     */
    protected final PlayerFactory playerFactory;

    /**
     * The list of players currently participating in the game.
     */
    protected ArrayList<Player> players;

    /**
     * The total number of players in the current game session.
     */
    protected final int numberOfPlayers;

    /**
     * The number of cards each player starts with at the beginning of the game.
     */
    protected final int cardsPerPlayer; 

    /*-------------------------------------------------------------------------------------------*/
    // CARD STATE                                          
    /*-------------------------------------------------------------------------------------------*/

    /**
     * The factory used to instantiate standard or extended cards.
     */
    protected final CardFactory cardFactory;

    /**
     * The draw pile containing the cards waiting to be drawn by players.
     */
    protected List<Card> drawPile;

    /**
     * The discard pile where players place their cards during their turn.
     */
    protected List<Card> discardPile;

    /*-------------------------------------------------------------------------------------------*/
    // RULES AND PARSERS                                       
    /*-------------------------------------------------------------------------------------------*/

    /**
     * The rule set applied to validate plays and game mechanics.
     */
    protected final Rule ruleSet;

    /**
     * The loader responsible for reading and creating the initial draw pile from a file.
     */
    protected final DeckLoader deckLoader;

    /**
     * The parser responsible for reading the game commands from the script.
     */
    protected ScriptParser scriptParser;

    /**
     * The factory used to instantiate the script parser.
     */
    protected final ScriptParserFactory scriptParserFactory;

    /*-------------------------------------------------------------------------------------------*/
    // GAME FLOW                                           
    /*-------------------------------------------------------------------------------------------*/

    /**
     * The ID of the player whose turn it currently is.
     */
    protected int idPlayerInCurrTurn;

    /**
     * A flag indicating the direction of play. 
     * {@code true} for clockwise, {@code false} for counter-clockwise.
     */
    protected boolean isGameMovingClockwise;

    /**
     * Stores the color chosen by a player after playing a Wild card.
     */
    protected String afterWildCardColor;

    /**
     * The ID of the player who last played a Wild card.
     */
    protected int lastWildCardPlayerId;

    /**
     * The output command interface to log game events to the terminal.
     */
    protected final OutputCommand out;

    /**
     * Initializes the core components of the game engine using dependency injection.
     *
     * @param numberOfPlayers     The total number of players joining the game.
     * @param cardFactory         The factory to create the game cards.
     * @param playerFactory       The factory to create the players.
     * @param ruleSet             The specific ruleset to be enforced during the game.
     * @param out                 The output handler for game logs.
     * @param deckLoader          The loader to read the deck file.
     * @param scriptParserFactory The factory to build the script parser.
     */
    protected AbstractGameEngine(int numberOfPlayers, int cardsPerPlayer, CardFactory cardFactory, PlayerFactory playerFactory, 
                                 Rule ruleSet, OutputCommand out, DeckLoader deckLoader, 
                                 ScriptParserFactory scriptParserFactory) {
        // Players
        this.playerFactory = playerFactory;
        this.numberOfPlayers = numberOfPlayers;
        this.cardsPerPlayer = cardsPerPlayer;
        this.players = new ArrayList<>(numberOfPlayers);

        // Cards
        this.cardFactory = cardFactory;
        this.discardPile = new ArrayList<>();
        this.drawPile = null;

        // Rules
        this.ruleSet = ruleSet;
        
        // Parsers
        this.deckLoader = deckLoader;
        this.scriptParser = null;
        this.scriptParserFactory = scriptParserFactory;

        // Output and State
        this.out = out;
        this.isGameMovingClockwise = true;
    }

    /*-------------------------------------------------------------------------------------------*/
    // EFFECT CONTEXT                                          
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public abstract void drawCards(Player player, int numberCardsToDraw);

    @Override
    public abstract void reverseWay();

    @Override
    public abstract void skipPlayers(int numberSkips);

    @Override
    public abstract List<Player> getReadOnlyPlayersList();

    @Override
    public abstract int nextPlayerId();

    /*-------------------------------------------------------------------------------------------*/
    // COMMAND OPERATIONS                                       
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public abstract boolean commandPlayCard(int playerId, int cardIndex);

    @Override
    public abstract boolean commandDrawCard(int playerId);

    @Override
    public abstract boolean commandSetColorAfterWildCard(int playerId, String color);

    /*-------------------------------------------------------------------------------------------*/
    // MAIN EXECUTION                                      
    /*-------------------------------------------------------------------------------------------*/

    /**
     * Initiates the game sequence by loading the deck and parsing the script commands.
     *
     * @param deck   The reader containing the deck configuration file.
     * @param script The reader containing the script commands file.
     */
    public abstract void startGame(Reader deck, Reader script);

}