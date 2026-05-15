package uno.player;

import uno.cards.Card;

public interface Player {

    public int getId();

    public void placeCard(int index);

    public void drawCard(Card cardDrawn);

}
