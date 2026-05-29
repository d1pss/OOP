
import java.util.Map;

import uno.cardEffect.CardEffect;
import uno.cards.ClassicCardFactory;
import uno.gameEngine.NormalGameEngine;
import uno.output.ClassicOutput;
import uno.players.ScriptPlayerFactory;
import uno.rules.ClassicRule;

public class Main {
    public static void main(String[] argv){
        Map<String, CardEffect> cardEffects;


        NormalGameEngine uno = uno.getInstance(4, new ClassicCardFactory(), new ScriptPlayerFactory(), new ClassicRule(), new ClassicOutput())
    }
}
