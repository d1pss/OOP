package uno.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import uno.cards.Card;
import uno.cards.CardFactory;

public interface DeckLoader {
    
    public List<Card> createDeck(Reader reader, CardFactory cardFactory) throws IOException;

}
