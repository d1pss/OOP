package uno.gameEngine;

/**
 * Defines the core actions the script can execute during the UNO game.
 * This interface acts as the primary API for the game engine, receiving commands from the 
 * script parser, processing them, and returning a status flag to control the execution loop.
 */
public interface GameCommands {
    
    /**
     * Attempts to play a card from the specified player's hand.
     *
     * @param playerId  The unique identifier of the player attempting to make the move.
     * @param cardIndex The zero-based index of the card within the player's hand.
     * @return {@code false} if the card was successfully played and the game should continue; 
     * {@code true} if an error occurred (e.g., invalid move, wrong turn, or the draw pile is empty) 
     * signaling the parser to halt execution.
     */
    public boolean commandPlayCard(int playerId, int cardIndex);

    /**
     * Commands the game engine to force the specified player to draw a card from the draw pile.
     *
     * @param playerId The unique identifier of the player drawing the card.
     * @return {@code false} if the draw action was successfully executed and the game continues; 
     * {@code true} if an error occurred (e.g., the draw pile ran out of cards).
     */
    public boolean commandDrawCard(int playerId);

    /**
     * Sets the new active game color after a Wild card has been played.
     *
     * @param playerId The unique identifier of the player who played the Wild card.
     * @param color    The string representation of the chosen color (e.g., "R", "G", "B", "Y").
     * @return {@code false} if the active color was successfully updated; 
     * {@code true} if the action was invalid (e.g., the previous card was not a Wild card, 
     * or the wrong player attempted to set the color).
     */
    public boolean commandSetColorAfterWildCard(int playerId, String color);
    
}