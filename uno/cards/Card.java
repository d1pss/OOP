package uno.cards;

public interface Card {

    /**
     * Returns a string representation of the card, in the format "color-type"
     * @return a string representation of the card
     */
    public String getCardString();

    /**
     * Returns the color of the card, represented as a character ('R', 'G', 'B', 'Y', 'W') or other if extended
     * @return the color of the card
     */
    public char getCardColor();
    
    /**
     * Returns the type of the card, represented as a string ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "SKIP", "REVERSE", "DRAW_TWO", "WILD", "WILD_DRAW_FOUR") or other if extended
     * @return the type of the card
     */
    public String getCardType();

    /**
     * Returns true if the card is a number card (0-9), false otherwise
     * @return true if the card is a number card, false otherwise
     */
    public boolean isNumberCard();

    /**
     * Returns true if the card is a wild card (WILD or WILD_DRAW_FOUR), false otherwise
     * @return true if the card is a wild card, false otherwise
     */
    public boolean isWildCard();

}

