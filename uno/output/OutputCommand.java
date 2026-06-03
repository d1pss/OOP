package uno.output;

import java.util.List;

import uno.players.Player;
import uno.cards.Card;

public interface OutputCommand {
    public void outputGameStart(List<Player> players, Card topCard);

    public void outputCommand(String command);

    public void outputPlayCard(int playerId, Card cardplayed);

    public void outputDrawCardFromPile(int playerId, Card cardDrawn);

    public void outputDrawCard(int nextPlayerId, int amountOfCardsToDraw);

    public void outputChoseColor(int playerId, String color);

    public void outputTurnAdvance(int nextPlayerId);

    public void outputWild();

    public void outputGameEndPlayerWin(int winnerPlayerId);

    public void outputGameEndNoWinners();

    public void outputError(String s);

    public void newLine();

}
