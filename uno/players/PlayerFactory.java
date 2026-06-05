package uno.players;

/**
 * A factory interface responsible for creating {@link Player} instances.
 * <p>By implementing this interface, the game engine can dynamically instantiate 
 * different types of players without being tightly coupled to their concrete classes.</p>
 */
public interface PlayerFactory {
    
    /**
     * Creates and returns a new {@link Player} instance with the specified identifier.
     *
     * @param id The unique numerical identifier to be assigned to the newly created player.
     * @return A newly instantiated {@link Player} ready to join the game.
     */
    public Player createPlayer(int id);
    
}