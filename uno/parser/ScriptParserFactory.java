package uno.parser;

import java.io.Reader;

public interface ScriptParserFactory {

    public ScriptParser creatScriptParser(Reader reader);

}
