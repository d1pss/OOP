import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map; // Java libs
import uno.cardEffect.CardEffect;
import uno.cardEffect.DrawCardsEffect;
import uno.cardEffect.ReverseEffect;
import uno.cardEffect.SkipEffect;
import uno.cardEffect.WildEffect;
import uno.cards.ClassicCardFactory;
import uno.gameEngine.NormalGameEngine;
import uno.output.ClassicOutput;
import uno.parser.NormalDeckLoader;
import uno.parser.NormalScriptParserFactory;
import uno.players.ScriptPlayerFactory;
import uno.rules.ClassicRule; // Game libs


/**
 * The main entry point for the standard (Classic) version of the UNO game.
 * This class handles the initialization of the game engine by wiring together 
 * the standard base components: classic rules, classic cards, and default card effects.
 */
public class Main {
    
    /**
     * The main method that bootstraps, configures, and runs the classic game.
     *
     * @param argv Command-line arguments required to run the game: 
     * {@code <deckFile> <scriptFile> <playerCount> [<cardsPerPlayer>]}
     */
    public static void main(String[] argv) {
        
        // Validate command-line arguments
        if (argv.length < 3) {
            System.err.println("Usage: java -jar project-v1.jar <deckFile> <scriptFile> <playerCount> [<cardsPerPlayer>]");
            return;
        }

        String deckPath = argv[0];
        String scriptPath = argv[1];
        int playerCount = Integer.parseInt(argv[2]); 
        int cardsPerPlayer = 7; // Default value
        
        if (argv.length == 4) {
            cardsPerPlayer = Integer.parseInt(argv[3]);
            if (cardsPerPlayer < 1) {
                System.err.println("Error: The number of cards per player must be at least 1.");
                return;
            }
        } 

        // Declaration of the classic card effects
        Map<String, CardEffect> cardEffects = new HashMap<>();
        cardEffects.put("SKIP", new SkipEffect(1));
        cardEffects.put("REVERSE", new ReverseEffect());
        cardEffects.put("DRAW_TWO", new DrawCardsEffect(2));
        cardEffects.put("WILD", new WildEffect());
        cardEffects.put("WILD_DRAW_FOUR", new DrawCardsEffect(4)); 

        // Instantiate the Singleton Game Engine with the standard Classic dependencies
        NormalGameEngine uno = NormalGameEngine.getInstance(
            playerCount, 
            cardsPerPlayer,
            new ClassicCardFactory(), 
            new ScriptPlayerFactory(), 
            new ClassicRule(cardEffects), 
            new ClassicOutput(), 
            new NormalDeckLoader(), 
            new NormalScriptParserFactory()
        );

        // Load files and start the game using try-with-resources to prevent resource leaks
        try (
            Reader deckReader = new FileReader(deckPath);
            Reader scriptReader = new FileReader(scriptPath)
        ) {
            uno.startGame(deckReader, scriptReader);

        } catch (FileNotFoundException e) {
            System.err.println("Error opening files: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
        
    }
}