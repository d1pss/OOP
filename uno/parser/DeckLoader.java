package uno.parser;

import java.io.BufferedReader;
import java.io.FileReader;
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
public class DeckLoader extends AbstractParser {

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

            drawPile.add(cardFactory.createCard(color.toCharArray()[0], rank));
        }
        return drawPile;
    }

    /**
     * Small standalone test program that reads a deck file and prints the
     * extracted color/rank pairs to the terminal.
     *
     * <p>This is only intended to illustrate how the support class works.
     * In the actual UNO project, students are expected to integrate this logic
     * into their own solution and create the appropriate in-memory objects.</p>
     *
     * @param args command-line arguments; {@code args[0]} must be the deck file
     */
    /*public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java exampledeck.DeckLoader <deckFile>");
            System.exit(1);
        }

        String deckFile = args[0];

        try (Reader reader = new FileReader(deckFile)) {
            DeckLoader loader = new DeckLoader();
            loader.loadDeck(reader);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }*/
}
