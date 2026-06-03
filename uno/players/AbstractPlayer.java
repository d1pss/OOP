package uno.players;

import java.util.ArrayList;
import java.util.List;
import uno.cards.Card;


public abstract class AbstractPlayer implements Player {
    protected List<Card> hand;
    //private int id;
    private final int id; // Alterei por ser um id fixo

    public AbstractPlayer(int id){
        hand = new ArrayList<>();
        this.id = id;
    }

    @Override
    public int getId(){
        return id;
    }

    @Override
    public List<Card> getHand(){
        return hand;
    }

    @Override
    public abstract Card placeCard(int index);

    @Override
    public abstract void drawCard(Card cardDrawn);
}
