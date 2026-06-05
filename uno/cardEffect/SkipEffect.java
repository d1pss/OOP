package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

/**
 * Represents an effect that skips the turn of one or more upcoming players in the game.
 */
public class SkipEffect implements CardEffect {
    
    /**
     * The number of subsequent players whose turns will be skipped when this effect is executed.
     */
    private int numberOfSkips;

    /**
     * Constructs a new skip effect with the specified amount of skips.
     *
     * @param numberOfSkips The number of players to skip when this effect is triggered.
     */
    public SkipEffect(int numberOfSkips) {
        this.numberOfSkips = numberOfSkips;
    }

    @Override
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed) {
        // Instruct the game engine to skip the specified number of players
        context.skipPlayers(numberOfSkips);
        
        out.newLine();
    }
}