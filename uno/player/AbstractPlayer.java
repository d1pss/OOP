package uno.player;

import java.util.ArrayList;
import java.util.List;

import uno.cards.Card;


public abstract class AbstractPlayer implements Player {
    protected List<Card> hand;
    private int id;
    

    public AbstractPlayer(int id){
        hand = new ArrayList<>();
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public abstract Card placeCard(int index);

    public abstract void drawCard(Card cardDrawn);
}
