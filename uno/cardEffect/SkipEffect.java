package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

public class SkipEffect implements CardEffect {
    private int numberOfSkips;

    public SkipEffect(int numberOfSkips) {
        this.numberOfSkips = numberOfSkips;
    }

    @Override
    public void execute(EffectContext context, OutputCommand out, int PlayerId, Card cardUsed) {
        
        context.skipPlayers(numberOfSkips);
        out.newLine();

    }
}
