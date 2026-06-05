package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

/**
 * Represents the default behavior for standard numeric UNO cards.
 * <p>This effect performs no special actions and simply allows the game to proceed normally.</p>
 */
public class NormalEffect implements CardEffect {

    @Override
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed) {
        // A normal card does not alter the game state. 
        out.newLine();
    }
}