package uno.parser;

import java.io.IOException;

import uno.gameEngine.GameCommands;

public interface ScriptParser {

    public void nextCommand(GameCommands commands) throws IOException;
    
}