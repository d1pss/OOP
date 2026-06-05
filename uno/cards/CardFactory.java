package uno.cards;

/**
 * A factory interface responsible for creating {@link Card} objects.
 * This interface abstracts the instantiation process, allowing the game engine
 * to create different types of cards (e.g., Classic cards or Extended cards)
 * without being coupled to their specific concrete classes.
 */
public interface CardFactory {

    /**
     * Creates and returns a new Card instance based on the specified attributes.
     *
     * @param color The color of the card to be created.
     * @param type  The type, number, or action type of the card.
     * @return A newly instantiated {@link Card} object matching the requested color and type.
     */
    public Card createCard(String color, String type);
    
}