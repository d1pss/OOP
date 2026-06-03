package uno.parser;

import java.io.IOException;

import uno.gameEngine.GameCommands;
import uno.output.OutputCommand;

public interface ScriptParser {

    public void nextCommand(GameCommands commands, OutputCommand output) throws IOException;
    
}