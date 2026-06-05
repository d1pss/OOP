package uno.parser;

import java.io.Reader;

/**
 * A factory interface responsible for instantiating {@link ScriptParser} objects.
 * Implementing this interface allows the game engine to dynamically load different 
 * script parsing strategies without being tightly coupled to their concrete implementations.
 */
public interface ScriptParserFactory {

    /**
     * Creates and returns a new {@link ScriptParser} instance configured to read 
     * from the provided data source.
     *
     * @param reader The character stream reader containing the script commands to be parsed.
     * @return A newly instantiated {@link ScriptParser} ready to process the script.
     */
    public ScriptParser createScriptParser(Reader reader);

}