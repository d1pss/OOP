
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


public class Main {
   public static void main(String[] argv) {
        
        // At least 3  arguments are necessary
        if (argv.length < 3) {
            System.err.println("Uso: java -jar project-v1.jar <deckFile> <scriptFile> <playerCount> [<cardsPerPlayer>]");
            return;
        }

        String deckPath = argv[0];
        String scriptPath = argv[1];
        int playerCount = Integer.parseInt(argv[2]); 

        //declaration of the card effects
        Map<String, CardEffect> cardEffects = new HashMap<>(); // Correlates the key (inside the string) into the object that is CardEffect, containing its atributes and methods (declared directly by a fit contructor)
        cardEffects.put("SKIP", new SkipEffect(1));
        cardEffects.put("REVERSE", new ReverseEffect());
        cardEffects.put("DRAW_TWO", new DrawCardsEffect(2));
        cardEffects.put("WILD", new WildEffect());
        cardEffects.put("WILD_DRAW_FOUR", new DrawCardsEffect(4)); 


        NormalGameEngine uno = NormalGameEngine.getInstance(
            playerCount, 
            new ClassicCardFactory(), 
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
