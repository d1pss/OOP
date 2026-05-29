package uno.parser;

import java.io.Reader;

public class NormalScriptParserFactory implements ScriptParserFactory{

    @Override
    public ScriptParser creatScriptParser(Reader reader){
        return new NormalScriptParser(reader);
    }
    
}
