package uno.players;

/**
 * A concrete implementation of the {@link PlayerFactory} interface.
 * <p>This factory is specifically dedicated to instantiating {@link ScriptPlayer} objects,
 * which are utilized when the game is driven by an automated script rather than human input.</p>
 */
public class ScriptPlayerFactory implements PlayerFactory {
    
    /**
     * Constructs a new factory for creating script-controlled players.
     */
    public ScriptPlayerFactory() {
        // Default constructor
    }

    @Override
    public Player createPlayer(int id) {
        // Instantiates and returns a new ScriptPlayer with the provided ID
        return new ScriptPlayer(id);
    }

}