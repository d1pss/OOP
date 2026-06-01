package unoExtended.cards;

import uno.cards.Card;
import uno.cards.CardFactory;

public class ExtendedCardFactory implements CardFactory {

    @Override
    public Card createCard(String color, String type) {
        return new ExtendedCard(color, type);
    }
    
}
