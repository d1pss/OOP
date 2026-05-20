package uno.parser;

public class AbstractParser {
    
    /**
     * Cleans one line of the script file.
     *
     * <p>This method trims the line, ignores empty lines and full-line comments
     * starting with {@code #}, and rejects inline comments.</p>
     *
     * @param line original line read from the file
     * @return cleaned line, or {@code null} if the line should be ignored
     */
    protected String cleanLine(String line) {
        String trimmed = line.trim();

        if (trimmed.isEmpty() || trimmed.startsWith("#")) {
            return null;
        }

        if (trimmed.indexOf('#') >= 0) {
            throw new IllegalArgumentException(
                "Inline comments are not allowed in script lines: " + line
            );
        }

        return trimmed;
    }
}
