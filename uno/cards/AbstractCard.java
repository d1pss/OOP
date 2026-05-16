package uno.cards;

public abstract class AbstractCard implements Card{
    private char color;
    private String type;
    
    public AbstractCard(char color, String type){
        this.color = color;
        this.type = type;
    }

    public String getCardString(){
        return color + "-" + type;
    }

    public char getCardColor(){
        return color;
    }

    public String getCardType(){
        return type;
    }

    public abstract boolean isNumberCard();

    public abstract boolean isWildCard();

}