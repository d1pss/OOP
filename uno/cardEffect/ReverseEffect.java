package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;


public class ReverseEffect implements CardEffect {
    @Override
    public void execute(EffectContext context, OutputCommand out, int PlayerId, Card cardUsed) {
        context.reverseWay();
        out.newLine();
    }
}
