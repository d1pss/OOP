package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

public interface CardEffect {

    public void execute(EffectContext context, OutputCommand out, int PlayerId, Card cardUsed);
    
}
