package uno.player;

import uno.cards.Card;

public interface Player {

    public int getId();

    public Card placeCard(int index);

    public void drawCard(Card cardDrawn);

}
