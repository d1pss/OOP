package uno.cards;

/**
 * Represents a standard UNO card used in the classic version of the game.
 * This concrete class implements the specific rules for identifying standard numeric cards,
 * action cards (Skip, Reverse, Draw Two), and wild cards based on their color and type.
 */
public class ClassicCard extends AbstractCard {

    /**
     * Constructs a new classic UNO card.
     *
     * @param color The color of the card (e.g., "R", "G", "B", "Y", or "W" for Wild).
     * @param type  The rank or action type of the card (e.g., "0"-"9", "SKIP", "REVERSE", "DRAW_TWO", "WILD").
     */
    public ClassicCard(String color, String type) {
        super(color, type);
    }
    
    @Override
    public boolean isNumberCard() {
        // If the card is a wild card, it is not a number card
        if (isWildCard()) {
            return false;
        }
        
        // If the card is a special action card, it is not a number card
        String cardType = this.getCardType();
        if (cardType.equals("SKIP") || cardType.equals("REVERSE") || cardType.equals("DRAW_TWO")) {
            return false;
        }

        // 3. If it is neither wild nor an action card, it must be a number card
        return true;
    }

    @Override
    public boolean isWildCard() {
        // If the card color is 'W', it is a wild card
        return this.getCardColor().equals("W");
    }

}