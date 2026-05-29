package uno.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import uno.cards.*;

/**
 * Support class for the UNO project.
 *
 * <p>This class illustrates how to read a deck file line by line, remove
 * comments and empty lines, and split each valid line into its textual
 * components.</p>
 *
 * <p>In this simplified version, the extracted information is printed to the
 * terminal. In the actual UNO project, students should replace those print
 * instructions by the creation of the internal objects required by their own
 * solution, storing the corresponding information in memory.</p>
 * 
 * <p>Inline comments are supported in deck lines.</p>
 */
public class NormalDeckLoader extends AbstractParser implements DeckLoader {

    /**
     * Reads a deck file through the given {@link Reader}, removes comments and
     * empty lines, splits each valid card line into color and rank, and prints
     * the result.
     *
     * <p>In the actual UNO project, the print instruction should be replaced by
     * the creation and storage of the internal objects required by the
     * student's own implementation.</p>
     *
     * @param reader reader associated with the deck file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<Card> createDeck(Reader reader, CardFactory cardFactory) throws IOException {
        List<Card> drawPile = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);
        String line;

        while ((line = br.readLine()) != null) {
            String cleaned = cleanLine(line);
            if (cleaned == null) {
                continue;
            }

            String[] parts = cleaned.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid deck line: " + line);
            }

            String color = parts[0];
            String rank = parts[1];

            drawPile.add(cardFactory.createCard(color, rank));
        }
        return drawPile;
    }

}
