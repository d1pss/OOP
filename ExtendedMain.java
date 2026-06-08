import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import uno.cardEffect.CardEffect;
import uno.cardEffect.DrawCardsEffect;
import uno.cardEffect.ReverseEffect;
import uno.cardEffect.SkipEffect;
import uno.cardEffect.WildEffect;
import uno.gameEngine.NormalGameEngine;
import uno.output.ClassicOutput;
import uno.parser.NormalDeckLoader;
import uno.parser.NormalScriptParserFactory;
import uno.players.ScriptPlayerFactory;
import uno.rules.ClassicRule;
import uno.cards.CardFactory;
import uno.cards.ClassicCardFactory;
import uno.rules.Rule;
import unoExtended.rules.ExtendedRule;

import unoExtended.cards.ExtendedCardFactory;

/**
 * The main entry point for the Extended (Crazy/DRAW_THREE) version of the UNO game.
 * <p>This class handles the initialization of the game engine and configures
 * the specific extension requested via command-line arguments.</p>
 */
public class ExtendedMain {
    
    /**
     * The main method that configures and runs the extended game.
     *
     * @param argv Command-line arguments required to run the game.
     */
    public static void main(String[] argv) {
        
        int offset = 0;
        String extensionName = "";
        String extensionType = "";
        
        if (argv.length >= 3 && argv[0].equals("-p2")) {
            offset = 3; 
            extensionType = argv[1];
            extensionName = argv[2];
        }
        
        // Validate command-line arguments using the offset
        if (argv.length < offset + 3) {
            System.err.println("Usage: java -cp project-v2.jar:project-v1.jar ExtendedMain -p2 <type> <name> <deckFile> <scriptFile> <playerCount> [<cardsPerPlayer>]");
            return;
        }

        String deckPath = argv[offset];
        String scriptPath = argv[offset + 1];
        int playerCount = Integer.parseInt(argv[offset + 2]);
        int cardsPerPlayer = 7; // Default value
        
        if (argv.length == offset + 4) {
            cardsPerPlayer = Integer.parseInt(argv[offset + 3]);
            if (cardsPerPlayer < 1) {
                System.err.println("Error: The number of cards per player must be at least 1.");
                return;
            }
        } 

        // Classic card effects mapping
        Map<String, CardEffect> cardEffects = new HashMap<>();
        cardEffects.put("REVERSE", new ReverseEffect());
        cardEffects.put("WILD", new WildEffect());
        cardEffects.put("WILD_DRAW_FOUR", new DrawCardsEffect(4)); 

        CardFactory cardFactory = null;

        // Apply extension modifications to the card effects based on the provided extension type and name
        if ("DRAW_THREE".equals(extensionName) && "card".equals(extensionType)) {
            // if the extension is DRAW_THREE, we add a new card type with its corresponding effect
            cardEffects.put("DRAW_THREE", new DrawCardsEffect(3)); 
            cardFactory = new ExtendedCardFactory(); // Use the ExtendedCardFactory to create the new card type
        }else{
            cardFactory = new ClassicCardFactory(); // Use the standard CardFactory for classic cards
        }

        Rule rule = null;
        if ("CrazyRuleset".equals(extensionName) && "ruleset".equals(extensionType)) {
            // if the extension is CrazyRuleset, we replace the classic effects with more aggressive versions
            cardEffects.put("SKIP", new SkipEffect(2)); 
            cardEffects.put("DRAW_TWO", new DrawCardsEffect(3)); 
            rule = new ExtendedRule(cardEffects); // Use the ExtendedRule to apply the new ruleset
        }else{
            // for any other extension, we keep the classic effects
            cardEffects.put("SKIP", new SkipEffect(1)); 
            cardEffects.put("DRAW_TWO", new DrawCardsEffect(2));
            rule = new ClassicRule(cardEffects); // Use the ClassicRule for standard rules
        }

        // Instantiate the Singleton Game Engine with the Extended dependencies
        NormalGameEngine uno = NormalGameEngine.getInstance(
            playerCount, 
            cardsPerPlayer,
            cardFactory, 
            new ScriptPlayerFactory(), 
            rule, 
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