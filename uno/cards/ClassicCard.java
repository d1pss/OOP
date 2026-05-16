package uno.cards;

public class ClassicCard extends AbstractCard{

    public ClassicCard(char color, String type){
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
        if(this.getCardColor() == 'W') return true;
        return false;
    }

}
