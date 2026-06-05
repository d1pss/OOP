package uno.cardEffect;

import uno.gameEngine.EffectContext;
import uno.output.OutputCommand;
import uno.cards.Card;

/**
 * Represents the specific behavior or action associated with a card in the UNO game.
 * Implementing classes define how the game state should change when a particular
 * card (e.g., SKIP, DRAW_TWO, or custom extensions) is played.
 */
public interface CardEffect {

    /**
     * Executes the specific rules and state changes associated with this effect.
     * This method is triggered by the game engine immediately after a card is successfully played.
     *
     * @param context  The interface providing secure, restricted access to manipulate the game state.
     * @param out      The output handler used to log or print the events of this effect to the terminal.
     * @param playerId The unique identifier of the player who played the card.
     * @param cardUsed The specific {@link Card} instance that triggered this effect.
     */
    public void execute(EffectContext context, OutputCommand out, int playerId, Card cardUsed);
    
}