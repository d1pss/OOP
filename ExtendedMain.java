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
//import uno.cards.ClassicCardFactory; // Modified to use the ExtendedCardFactory instead of the ClassicCardFactory
import uno.gameEngine.NormalGameEngine;
import uno.output.ClassicOutput;
import uno.parser.NormalDeckLoader;
import uno.parser.NormalScriptParserFactory;
import uno.players.ScriptPlayerFactory;
import uno.rules.ClassicRule;

import unoExtended.cards.ExtendedCardFactory;

public class ExtendedMain {
    public static void main(String[] argv) {
        
        if (argv.length < 3) {
            System.err.println("Uso: java -jar project-v1.jar <deckFile> <scriptFile> <playerCount> [<cardsPerPlayer>]");
            return;
        }

        String deckPath = argv[0];
        String scriptPath = argv[1];
        int playerCount = Integer.parseInt(argv[2]); 

        //declaration of the card effects
        Map<String, CardEffect> cardEffects = new HashMap<>();
        cardEffects.put("SKIP", new SkipEffect(2)); // Modified to skip two players instead of one to folow the crazy ruleset
        cardEffects.put("REVERSE", new ReverseEffect());
        cardEffects.put("DRAW_TWO", new DrawCardsEffect(3)); // Modified to draw three cards instead of two to folow the crazy ruleset
        cardEffects.put("DRAW_THREE", new DrawCardsEffect(3)); // New effect for drawing three cards
        cardEffects.put("WILD_DRAW_FOUR", new DrawCardsEffect(4)); 


        NormalGameEngine uno = NormalGameEngine.getInstance(
            playerCount, 
            new ExtendedCardFactory(), 
            new ScriptPlayerFactory(), 
            new ClassicRule(cardEffects), 
            new ClassicOutput(), 
            new NormalDeckLoader(), 
            new NormalScriptParserFactory()
        );

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
