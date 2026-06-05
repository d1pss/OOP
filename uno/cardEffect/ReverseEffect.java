package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

/**
 * Represents an effect that reverses the current direction of play.
 * <p>When executed, the turn order switches.</p>
 */
public class ReverseEffect implements CardEffect {

    @Override
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed) {

        if(context.getReadOnlyPlayersList().size() == 2) {
            // In a 2-player game, a Reverse card acts like a Skip card, so we skip the next player's turn
            context.skipPlayers(1);
        }

        // Reverse the turn order direction in the game engine
        context.reverseWay();
        
        out.newLine();
    }
}