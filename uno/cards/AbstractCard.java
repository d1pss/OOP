package uno.cards;

public abstract class AbstractCard implements Card{
    private String color;
    private String type;
    
    public AbstractCard(String color, String type){
        this.color = color;
        this.type = type;
    }

    public String getCardString(){
        return color + "-" + type;
    }

    public String getCardColor(){
        return color;
    }

    public String getCardType(){
        return type;
    }
    

    public abstract boolean isNumberCard();

    public abstract boolean isWildCard();

}
