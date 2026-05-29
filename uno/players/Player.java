package uno.players;

import java.util.List;

import uno.cards.Card;

public interface Player {

    public int getId();

    public List<Card> getHand();

    public Card placeCard(int index);

    public void drawCard(Card cardDrawn);

}
