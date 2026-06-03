package uno.cards;

public abstract class AbstractCard implements Card{
    private final String color;
    private final String type;
    
    public AbstractCard(String color, String type){
        this.color = color;
        this.type = type;
    }

    @Override
    public String getCardString(){
        return color + "-" + type;
    }

    @Override
    public String getCardColor(){
        return color;
    }

    @Override
    public String getCardType(){
        return type;
    }
    

    @Override
    public abstract boolean isNumberCard();

    @Override
    public abstract boolean isWildCard();

}
