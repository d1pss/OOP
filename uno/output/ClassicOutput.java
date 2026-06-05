package uno.output;

import java.util.List;

import uno.players.Player;
import uno.cards.Card;

/**
 * Concrete implementation of the {@link OutputCommand} interface.
 * <p>This class is responsible for logging all game events, actions, and errors 
 * to the standard terminal output (console). It formats the output string 
 * according to the specific strict requirements of the classic UNO game engine.</p>
 */
public class ClassicOutput implements OutputCommand {

    /**
     * Helper method to convert a single-character color code into its full readable name.
     *
     * @param colorCode The short string representation of the color (e.g., "R", "G", "B", "Y").
     * @return The full name of the color, or "Unknown" if the code is not recognized.
     */
    private String colorToString(String colorCode) {
        switch (colorCode) {
            case "R":
                return "RED";
            case "G":
                return "GREEN";
            case "B":
                return "BLUE";
            case "Y":
                return "YELLOW";
            default:
                return "Unknown";
        }
    }
    
    @Override
    public void outputGameStart(List<Player> players, Card topCard) {
        System.out.print("GAME_START players=" + players.size() + "\n");

        System.out.print("EVENT TOP_CARD " + topCard.getCardString() + " color=" + colorToString(topCard.getCardColor()) + "\n");

        for (int i = 0; i < players.size(); i++) {
            System.out.print("EVENT HAND player=" + i + " cards=");
            for (int j = 0; j < players.get(i).getHand().size(); j++) {
                System.out.print(players.get(i).getHand().get(j).getCardString() + " ");
            }
            newLine();
        }

        System.out.print("EVENT TURN_START player=0\n");
    }

    @Override
    public void outputCommand(String command) {
        System.out.print("EVENT COMMAND line=\"" + command + "\"\n");
    }
    
    @Override
    public void outputPlayCard(int playerId, Card cardPlayed) {
        if (cardPlayed.isNumberCard()) {
            System.out.print("EVENT PLAY_CARD Player " + playerId + " played " + cardPlayed.getCardString());
        } else {
            System.out.print("EVENT PLAY_CARD Player " + playerId + " played " + cardPlayed.getCardType());
        }
    }

    @Override
    public void outputDrawCardFromPile(int playerId, Card cardDrawn) {
        System.out.print("EVENT DRAW_CARD Player " + playerId + " draws 1 card (" + cardDrawn.getCardString() + ")\n");
    }

    @Override
    public void outputDrawCard(int nextPlayerId, int amountOfCardsToDraw) {
        // Note: This starts with a semicolon because it is meant to be appended 
        // to the output of the card that caused the draw (e.g., DRAW_TWO).
        System.out.print("; Player " + nextPlayerId + " draws " + amountOfCardsToDraw + " and is skipped\n");
    }

    @Override
    public void outputChoseColor(int playerId, String color) {
        System.out.print("EVENT CHOOSE_COLOR Player " + playerId + " chose color " + colorToString(color) + "\n");
    }

    @Override
    public void outputTurnAdvance(int nextPlayerId) {
        System.out.print("EVENT TURN_ADVANCE Next player: " + nextPlayerId + "\n");
    }

    @Override
    public void outputWild() {
        System.out.print(" (color will be chosen)\n");
    }

    @Override
    public void outputGameEndPlayerWin(int winnerPlayerId) {
        System.out.print("EVENT GAME_END Player " + winnerPlayerId + " wins\nEVENT WINNER player=" + winnerPlayerId + "\nGAME_END\n");
    }

    @Override
    public void outputGameEndNoWinners() {
        System.out.print("EVENT GAME_END No cards available to draw\nGAME_END\n");
    }

    @Override
    public void outputError(String errorMessage) {
         System.out.print("EVENT ERROR " + errorMessage + "\n");
    }

    @Override
    public void newLine() {
        System.out.print("\n");
    }
}