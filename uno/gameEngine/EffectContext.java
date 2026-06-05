package uno.gameEngine;

import java.util.List;

import uno.players.Player;

/**
 * Provides a restricted execution environment for card effects.
 * <p>This interface acts as a secure bridge between the game engine and the card effects,
 * exposing only the safe and necessary operations allowed to modify the game state.</p>
 */
public interface EffectContext {

    /**
     * Forces a specific player to draw a designated number of cards from the draw pile.
     *
     * @param player            The target player who will receive the cards.
     * @param numberCardsToDraw The exact amount of cards to be drawn.
     */
    public void drawCards(Player player, int numberCardsToDraw);

    /**
     * Reverses the current direction of play.
     * For example, if the game is moving clockwise, it will switch to counter-clockwise, and vice versa.
     */
    public void reverseWay();

    /**
     * Skips the turns of a specified number of upcoming players in the current play direction.
     *
     * @param numberSkips The number of players to skip (e.g., 1 for a standard Skip card).
     */
    public void skipPlayers(int numberSkips);

    /**
     * Calculates and retrieves the identifier of the next player in the turn order,
     * taking into account the current direction of play and any active skips.
     *
     * @return The unique ID of the next player.
     */
    public int nextPlayerId();

    /**
     * Retrieves a secure, unmodifiable view of the players currently in the game.
     * Effects can use this to inspect player states (e.g., hand size) without 
     * being able to add or remove players from the actual game list.
     *
     * @return A read-only list containing all the {@link Player} objects.
     */
    public List<Player> getReadOnlyPlayersList();
    
}