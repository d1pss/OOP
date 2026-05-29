package unoExtended.cards;

import uno.cards.AbstractCard;

public class ExtendedCard extends AbstractCard{

     public ExtendedCard(String color, String type){
        super(color, type);
    }
    
    @Override
    public boolean isNumberCard(){
        //if the card is a wild card, it is not a number card
        if(isWildCard()) return false;
        
        //if the card is a special card, it is not a number card
        String cardtype = this.getCardType();
        if(cardtype.equals("SKIP") || cardtype.equals("REVERSE") || cardtype.equals("DRAW_TWO") || cardtype.equals("DRAW_THREE")) return false;

        return true;
    }

    @Override
    public boolean isWildCard(){
        //if the card color is 'W', it is a wild card
        if(this.getCardColor().equals("W")) return true;
        return false;
    }
}
