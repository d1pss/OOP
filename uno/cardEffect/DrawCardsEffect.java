package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.players.Player;
import uno.cards.Card;

/**
 * Represents an effect that forces the next player in the turn order to draw 
 * a specific number of cards and forfeit their turn.
 */
public class DrawCardsEffect implements CardEffect {
    
    /**
     * The number of cards the target player must draw when this effect is executed.
     */
    private int amount;

    /**
     * Constructs a new effect that requires the next player to draw cards.
     *
     * @param amount The number of cards to be drawn by the targeted player.
     */
    public DrawCardsEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed) {
        // Identify the target (the next player in the current direction of play)
        Player target = context.getReadOnlyPlayersList().get(context.nextPlayerId());

        // Force the target to draw the specified amount of cards
        context.drawCards(target, amount);
        
        // Skip the target player's turn
        context.skipPlayers(1);

        // Output the event to the terminal (logging the action)
        out.outputDrawCard(context.nextPlayerId(), amount);
    }
}