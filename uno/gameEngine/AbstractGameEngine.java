package uno.gameEngine;

//Java default packages
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import uno.cards.Card;
import uno.cards.CardFactory;
import uno.output.OutputCommand;
import uno.parser.*;
import uno.players.Player;
import uno.players.PlayerFactory;
import uno.rules.Rule;


public abstract class AbstractGameEngine implements EffectContext, GameCommands{
    //Players
    protected final PlayerFactory playerFactory;

    protected ArrayList<Player> players;
    protected final int numberOfPlayers;

    //Cards
    protected final CardFactory cardFactory;

    protected List<Card> drawPile;
    protected List<Card> discardPile;

    //Rule
    protected final Rule ruleSet;

    //Parsers
    protected final DeckLoader deckLoader;
    protected ScriptParser scriptParser;
    protected final ScriptParserFactory scriptParserFactory;

    //Game Variables
    protected int idPlayerInCurrTurn;
    protected boolean isGameMovingClockwise;

    protected String afterWlidCardColor;
    protected int lastWildCardPlayerId;

    //Output
    protected final OutputCommand out;

    

    protected AbstractGameEngine(int numberOfPlayers, CardFactory cardFactory, PlayerFactory playerFactory, Rule ruleSet, OutputCommand out, DeckLoader deckLoader, ScriptParserFactory scriptParserFactory){
        //Players
        this.playerFactory = playerFactory;
        this.numberOfPlayers = numberOfPlayers;
        players = new ArrayList<>(numberOfPlayers);

        //Cards
        this.cardFactory = cardFactory;
        discardPile = new ArrayList<>();
        drawPile = null;

        //Rules
        this.ruleSet = ruleSet;
        
        //Parser
        this.deckLoader = deckLoader;
        scriptParser = null;
        this.scriptParserFactory = scriptParserFactory;

        this.out = out;
        isGameMovingClockwise = true;
    }

    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Efects Context ------------------------------------*/
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
    /*-------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------------------*/
    /*----------------------------------- Command Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    @Override
    public abstract boolean commandPlayCard(int playerId, int cardIndex);

    @Override
    public abstract boolean commandDrawCard(int playerId);

    @Override
    public abstract boolean commandSetColorAfterWildCard(int playerId, String color);

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Main code -----------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    public abstract void startGame(Reader deck, Reader script);

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

}
