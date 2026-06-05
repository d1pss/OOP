package uno.cards;

/**
 * Represents a standard UNO card used in the classic version of the game.
 * <p>This concrete class implements the specific rules for identifying standard numeric cards,
 * action cards (Skip, Reverse, Draw Two), and wild cards based on their color and type.</p>
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
    public boolean isNumberCard(){
        //if the card is a wild card, it is not a number card
        if(isWildCard()) return false;
        
        //if the card is a special card, it is not a number card
        String cardtype = this.getCardType();
        if(cardtype.equals("SKIP") || cardtype.equals("REVERSE") || cardtype.equals("DRAW_TWO")) return false;

        return true;
    }

    @Override
    public boolean isWildCard(){
        //if the card color is 'W', it is a wild card
        if(this.getCardColor().equals("W")) return true;
        return false;
    }

}