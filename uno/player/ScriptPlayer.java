package uno.player;

import uno.cards.Card;

public class ScriptPlayer extends AbstractPlayer {

    public ScriptPlayer(int id){
        super(id);
    }
    
    @Override
    public void placeCard(int index){
        hand.remove(index);
    }

    @Override
    public void drawCard(Card cardDrawn){
        hand.add(cardDrawn);
    }
}
