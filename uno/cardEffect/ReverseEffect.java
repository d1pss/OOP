package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

/**
 * Represents an effect that reverses the current direction of play.
 * When executed, the turn order switches.
 */
public class ReverseEffect implements CardEffect {

    @Override
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed) {
        // Reverse the turn order direction in the game engine
        context.reverseWay();
        
        out.newLine();
    }
}