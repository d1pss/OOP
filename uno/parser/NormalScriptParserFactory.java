package uno.parser;

import java.io.Reader;

/**
 * A concrete implementation of the {@link ScriptParserFactory} interface.
 * This factory is specifically responsible for instantiating the {@link NormalScriptParser} 
 * used in the standard ruleset of the UNO game. By using this factory, the game engine 
 * is completely decoupled from the concrete parser implementation.
 */
public class NormalScriptParserFactory implements ScriptParserFactory {

    /**
     * Constructs a new factory dedicated to creating standard script parsers.
     */
    public NormalScriptParserFactory() {
        // Default constructor
    }

    @Override
    public ScriptParser createScriptParser(Reader reader) {
        // Instantiate and return a standard script parser hooked to the provided reader
        return new NormalScriptParser(reader);
    }
    
}