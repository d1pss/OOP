package uno.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import uno.cards.Card;
import uno.cards.CardFactory;

/**
 * Defines the contract for loading and constructing a deck of cards from a data source.
 * <p>Implementing classes are responsible for reading the deck configuration (e.g., from a file)
 * and utilizing a {@link CardFactory} to instantiate the specific card objects safely.</p>
 */
public interface DeckLoader {
    
    /**
     * Reads the deck configuration from the provided reader and constructs the initial draw pile.
     *
     * @param reader      The character stream reader containing the raw deck configuration data.
     * @param cardFactory The factory used to instantiate the individual card objects securely.
     * @return A list of {@link Card} objects representing the fully loaded deck, ready for play.
     * @throws IOException If an error occurs while reading from the data source, or if the 
     *                     data format is corrupted or invalid.
     */
    public List<Card> createDeck(Reader reader, CardFactory cardFactory) throws IOException;

}