package uno.cards;

/**
 * Provides a abstract implementation of the {@link Card} interface to minimize 
 * the effort required to implement it.
 * <p>This abstract class handles the common state (color and type) and the standard 
 * string representation for all cards in the game. Subclasses only need to provide 
 * the specific logic for determining if they are numeric or wild cards.</p>
 */
public abstract class AbstractCard implements Card {
    
    /**
     * The color identifier of the card (e.g., "R", "G", "B", "Y", "W", or custom extensions).
     */
    private String color;
    
    /**
     * The type or rank identifier of the card (e.g., "0"-"9", "SKIP", "WILD", or custom extensions).
     */
    private String type;
    
    /**
     * Constructs a new card with the specified color and type.
     *
     * @param color The string representing the color of the card.
     * @param type  The string representing the type or rank of the card.
     */
    public AbstractCard(String color, String type) {
        this.color = color;
        this.type = type;
    }

    @Override
    public String getCardString() {
        return color + "-" + type;
    }

    @Override
    public String getCardColor() {
        return color;
    }

    @Override
    public String getCardType() {
        return type;
    }

    @Override
    public abstract boolean isNumberCard();
    
    @Override
    public abstract boolean isWildCard();

}