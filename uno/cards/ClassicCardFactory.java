package uno.cards;

/**
 * A concrete implementation of the {@link CardFactory} interface.
 * <p>This factory is specifically responsible for instantiating standard 
 * {@link ClassicCard} objects used in the base version of the UNO game.
 * It strictly follows the Factory design pattern to encapsulate card creation 
 * and decouple the game engine from concrete card classes.</p>
 */
public class ClassicCardFactory implements CardFactory {

    /**
     * Constructs a new factory dedicated to creating classic UNO cards.
     */
    public ClassicCardFactory() {
        // Default constructor
    }

    @Override
    public Card createCard(String color, String type) {
        // Instantiate and return a standard classic UNO card
        return new ClassicCard(color, type);
    }

}