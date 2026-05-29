package uno.gameEngine;

//Java default packages
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

//Player
import uno.players.Player;
import uno.players.PlayerFactory;
//Cards
import uno.cards.Card;
import uno.cards.CardFactory;
import uno.output.OutputCommand;
//Rules
import uno.rules.Rule;

//Parser
import uno.parser.*;


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
    protected DeckLoader deckLoader;
    protected ScriptParser scriptParser;

    //Game Variables
    protected int idPlayerInCurrTurn;
    protected boolean isGameMovingClockwise;

    protected String afterWlidCardColor;
    protected int lastWildCardPlayerId;

    //Output
    protected final OutputCommand out;

    

    protected AbstractGameEngine(int numberOfPlayers, CardFactory cardFactory, PlayerFactory playerFactory, Rule ruleSet, OutputCommand out){
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
        deckLoader = new DeckLoader();
        scriptParser = null;

        this.out = out;
    }

    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Efects Context ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    
    public abstract void drawCards(Player player, int numberCardsToDraw);

    public abstract void reverseWay();

    public abstract void skipPlayers(int numberSkips);

    public abstract List<Player> getReadOnlyPlayersList();

    public abstract int nextPlayerId();

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------------------*/
    /*----------------------------------- Command Operations ------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    public abstract void commandPlayCard(int playerId, int cardIndex);

    public abstract void commandDrawCard(int playerId);

    public abstract void commandSetColorAfterWildCard(int playerId, String color);

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------------*/
    /*--------------------------------------- Main code -----------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

    public abstract void startGame(Reader deck, Reader script);

    /*-------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------*/

}
