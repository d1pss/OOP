package uno.parser;

import java.io.IOException;

import uno.gameEngine.GameCommands;
import uno.output.OutputCommand;

/**
 * Defines the contract for parsing and executing the automated game script.
 * <p>Implementing classes are responsible for reading sequential commands from a data source 
 * (like a text file) and translating them into concrete actions within the game engine.</p>
 */
public interface ScriptParser {

    /**
     * Reads, parses, and executes the next command (or sequence of commands) from the script.
     * This method acts as the driver for the game loop when running in automated mode.
     *
     * @param commands The interface provided by the game engine to execute validated game actions 
     * (such as playing a card, drawing, or changing the active color).
     * @param output   The output handler used to log parser activities, errors, and game events.
     * @throws IOException If a critical error occurs while reading from the script's data source.
     */
    public void nextCommand(GameCommands commands, OutputCommand output) throws IOException;
    
}