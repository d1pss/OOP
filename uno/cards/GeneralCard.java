package uno.cards;

public class GeneralCard implements Card{
    private char color;
    private String type;
    
    public GeneralCard(char color, String type){
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

}