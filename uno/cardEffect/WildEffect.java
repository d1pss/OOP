package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

/**
 * Represents the behavior of a standard Wild card in the UNO game.
 * While the primary function of a Wild card is to allow the player to choose 
 * a new active color, this specific effect handles the event registration 
 * and specific terminal output associated with playing a wild card.
 */
public class WildEffect implements CardEffect {

    @Override
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed) {
        // Register the Wild card play event in the output terminal
        out.outputWild();
    }
    
}