package uno.players;

import uno.cards.Card;

public class ScriptPlayer extends AbstractPlayer {

    public ScriptPlayer(int id){
        super(id);
    }
    
    @Override
    public Card placeCard(int index){
        return hand.remove(index);
    }

    @Override
    public void drawCard(Card cardDrawn){
        hand.add(cardDrawn);
    }
}
