package uno.output;

import java.util.List;

import uno.players.Player;
import uno.cards.Card;

/**
 * Defines the contract for the game output and logging system.
 * This interface abstracts the presentation layer, allowing the game engine 
 * to remain completely decoupled from how events are displayed (e.g., terminal, 
 * graphical user interface, or a log file).
 */
public interface OutputCommand {
    
    /**
     * Outputs the initial state of the game, including the starting top card 
     * and the initial hands of all players.
     *
     * @param players The list of players participating in the game.
     * @param topCard The first card placed on the discard pile to start the game.
     */
    public void outputGameStart(List<Player> players, Card topCard);

    /**
     * Logs the raw command string that was read by the parser.
     *
     * @param command The exact string line of the command.
     */
    public void outputCommand(String command);

    /**
     * Announces that a player successfully played a card from their hand.
     *
     * @param playerId   The unique identifier of the player making the move.
     * @param cardPlayed The specific card that was placed on the discard pile.
     */
    public void outputPlayCard(int playerId, Card cardPlayed);

    /**
     * Announces that a player intentionally drew a card from the draw pile on their turn.
     *
     * @param playerId  The unique identifier of the player drawing the card.
     * @param cardDrawn The specific card that was drawn.
     */
    public void outputDrawCardFromPile(int playerId, Card cardDrawn);

    /**
     * Announces that a player was forced to draw cards due to a penalty or card effect 
     * (e.g., DRAW_TWO) and forfeit their turn.
     *
     * @param nextPlayerId        The unique identifier of the target player receiving the penalty.
     * @param amountOfCardsToDraw The number of cards the player was forced to draw.
     */
    public void outputDrawCard(int nextPlayerId, int amountOfCardsToDraw);

    /**
     * Announces the new active color chosen by a player after playing a Wild card.
     *
     * @param playerId The unique identifier of the player who made the choice.
     * @param color    The string representation of the chosen color (e.g., "R", "G", "B", "Y").
     */
    public void outputChoseColor(int playerId, String color);

    /**
     * Announces the start of a new turn.
     *
     * @param nextPlayerId The unique identifier of the player whose turn is starting.
     */
    public void outputTurnAdvance(int nextPlayerId);

    /**
     * Logs the specific output segment indicating that a Wild card was played 
     * and the game is waiting for a color to be chosen.
     */
    public void outputWild();

    /**
     * Announces the end of the game resulting in a definitive winner.
     *
     * @param winnerPlayerId The unique identifier of the winning player.
     */
    public void outputGameEndPlayerWin(int winnerPlayerId);

    /**
     * Announces the end of the game resulting in a draw or no winners 
     * (e.g., when the draw pile runs out of cards and no valid plays can be made).
     */
    public void outputGameEndNoWinners();

    /**
     * Logs an error message to the output stream when an invalid move or critical failure occurs.
     *
     * @param errorMessage The descriptive text of the error.
     */
    public void outputError(String errorMessage);

    /**
     * Outputs a newline character to terminate the current event log properly.
     */
    public void newLine();

}