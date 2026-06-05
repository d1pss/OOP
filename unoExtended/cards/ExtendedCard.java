package unoExtended.cards;

import uno.cards.AbstractCard;

/**
 * Represents an extended playing card in the UNO game.
 * <p>This class builds upon the base card functionality by introducing new special 
 * card types (such as "DRAW_THREE") into the classification logic.</p>
 */
public class ExtendedCard extends AbstractCard {

    /**
     * Constructs a new extended UNO card with the specified color and type.
     *
     * @param color The string representation of the card color.
     * @param type  The string representation of the card type.
     */
     public ExtendedCard(String color, String type) {
        super(color, type);
    }
    
    @Override
    public boolean isNumberCard() {
        // If the card is a wild card, it cannot be a number card
        if (isWildCard()) {
            return false;
        }
        
        // If the card is one of the designated special action cards, it is not a number card
        String cardType = this.getCardType();
        if (cardType.equals("SKIP") || cardType.equals("REVERSE") || 
            cardType.equals("DRAW_TWO") || cardType.equals("DRAW_THREE")) {
            return false;
        }

        // Otherwise, it is a standard number card
        return true;
    }

    @Override
    public boolean isWildCard() {
        // Evaluates to true if the card's color code represents a Wild ('W')
        return this.getCardColor().equals("W");
    }
}