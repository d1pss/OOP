package uno.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import uno.gameEngine.GameCommands;
import uno.output.OutputCommand;

/**
 * Support class for the UNO project.
 *
 * <p>This class illustrates how to read a script file line by line, ignore
 * empty lines and full-line comments, and extract the textual components of
 * each command.</p>
 *
 * <p>In this simplified version, the extracted information is printed to the
 * terminal. In the actual UNO project, students should replace those print
 * instructions by the creation of the internal objects required by their own
 * solution, storing the corresponding information in memory.</p>
 *
 * <p>Inline comments are not supported in script lines.</p>
 */
public class NormalScriptParser extends AbstractParser implements AutoCloseable,ScriptParser {

    /**
     * The buffered reader used to read the script file line by line.
     */
    private final BufferedReader reader;

    /**
     * Creates a parser that reads commands from the given reader.
     *
     * @param reader reader associated with the script file
     */
    public NormalScriptParser(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    /**
     * Reads the script file line by line, ignores empty lines and full-line
     * comments, extracts the command components, and prints them to the terminal.
     *
     * <p>In the actual UNO project, the print instructions should be replaced by
     * the creation and storage of the command objects required by the student's
     * own implementation.</p>
     *
     * @throws IOException if an I/O error occurs while reading the file
     */
    public void nextCommand(GameCommands commands, OutputCommand output) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String cleaned = cleanLine(line);
            if (cleaned == null) {
                continue;
            }

            String[] parts = cleaned.split("\\s+");
            if (parts.length < 3 || !"PLAYER".equals(parts[0])) {
                throw new IllegalArgumentException("Invalid script line: " + line);
            }

            int playerId = Integer.parseInt(parts[1]);
            String cmd = parts[2];

            if ("PLAY".equalsIgnoreCase(cmd)) {
                if (parts.length != 4) {
                    throw new IllegalArgumentException("PLAY requires index: " + line);
                }
                int idx = Integer.parseInt(parts[3]);
                
                output.outputCommand(cleaned);
                if(commands.commandPlayCard(playerId, idx)){
                    return;
                }

            } else if ("DRAW".equalsIgnoreCase(cmd)) {
                if (parts.length != 3) {
                    throw new IllegalArgumentException("DRAW has no arguments: " + line);
                }

                output.outputCommand(cleaned);
                if(commands.commandDrawCard(playerId)){
                    return;
                }

            } else if ("COLOR".equalsIgnoreCase(cmd)) {
                if (parts.length != 4) {
                    throw new IllegalArgumentException("COLOR requires color code: " + line);
                }
                String colorCode = parts[3];

                output.outputCommand(cleaned);
                if(commands.commandSetColorAfterWildCard(playerId, colorCode)){
                    return;
                }

            } else {
                throw new IllegalArgumentException("Unknown command: " + cmd + " in line " + line);
            }
        }
    }

    

    /**
     * Closes the underlying reader.
     *
     * @throws IOException if an I/O error occurs while closing the reader
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }

}
