package uno.cards;

/**
 * Represents a playing card in the UNO game.
 * Defines the core attributes and behaviors expected from any card, 
 * including its color, type (rank), and string representation. 
 * This contract allows the game engine to handle numeric, action, 
 * and wild cards uniformly, while supporting future extensions.
 */
public interface Card {
    
    /**
     * Gets the formatted string representation of the card.
     * Typically formatted as "Color-Type".
     *
     * @return The formatted string identifier of the card.
     */
    public String getCardString();

    /**
     * Retrieves the color of the card.
     * 
     * @return The string representing the card's color.
     */
    public String getCardColor();
    
    /**
     * Retrieves the type (or rank) of the card.
     * 
     * @return The string representing the card's specific type or action.
     */
    public String getCardType();

    /**
     * Checks whether this card is a standard numeric card.
     *
     * @return {@code true} if the card is a number card (0-9) or other if extended; {@code false} otherwise.
     */
    public boolean isNumberCard();

    /**
     * Checks whether this card is a wild card.
     * Wild cards typically allow the player to change the active game color.
     *
     * @return {@code true} if the card is a wild card; {@code false} otherwise.
     */
    public boolean isWildCard();
}