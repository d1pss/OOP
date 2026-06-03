package uno.output;

import java.util.List;

import uno.players.Player;
import uno.cards.Card;

public class ClassicOutput implements OutputCommand {
    
    @Override
    public void outputGameStart(List<Player> players, Card topCard){
        System.out.print("GAME_START players=" + players.size() + "\n");

        System.out.print("EVENT TOP_CARD " + topCard.getCardString() + " color=" + colorToString(topCard.getCardColor()) + "\n");

        for(int i = 0; i < players.size(); i++){
            System.out.print("EVENT HAND player=" + i + " cards=");
            for(int j = 0; j < players.get(i).getHand().size(); j++){
                System.out.print(players.get(i).getHand().get(j).getCardString() + " ");
            }
            newLine();
        }

        System.out.print("EVENT TURN_START player=0\n");
        
    }

    private String colorToString(String colorCode){
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
    public void outputCommand(String command){
        System.out.print("EVENT COMMAND line=\"" + command + "\"\n");
    }
    
    @Override
    public void outputPlayCard(int playerId, Card cardplayed){
        if(cardplayed.isNumberCard()){
            System.out.print("EVENT PLAY_CARD Player " + playerId + " played " + cardplayed.getCardString());
        }else{
            System.out.print("EVENT PLAY_CARD Player " + playerId + " played " + cardplayed.getCardType());
        }
    }

    @Override
    public void outputDrawCardFromPile(int playerId, Card cardDrawn){
        System.out.print("EVENT DRAW_CARD Player " + playerId + " draws 1 card (" + cardDrawn.getCardString() + ")\n");
    }

    @Override
    public void outputDrawCard(int nextPlayerId, int amountOfCardsToDraw){
        System.out.print("; Player " + nextPlayerId + " draws " + amountOfCardsToDraw + " and is skipped\n");
    }

    @Override
    public void outputChoseColor(int playerId, String color){
        System.out.print("EVENT CHOOSE_COLOR Player " + playerId + " chose color " + colorToString(color) + "\n");
    }

    @Override
    public void outputTurnAdvance(int nextPlayerId){
        System.out.print("EVENT TURN_ADVANCE Next player: " + nextPlayerId + "\n");
    }

    @Override
    public void outputWild(){
        System.out.print(" (color will be chosen)\n");
    }

    @Override
    public void outputGameEndPlayerWin(int winnerPlayerId){
        System.out.print("EVENT GAME_END Player " + winnerPlayerId + " wins\nEVENT WINNER player=" + winnerPlayerId + "\nGAME_END\n");
    }

    @Override
    public void outputGameEndNoWinners(){
        System.out.print("EVENT GAME_END No cards available to draw\nGAME_END\n");
    }

    @Override
    public void outputError(String s){
         System.out.print("EVENT ERROR " + s + "\n");
    }

    @Override
    public void newLine(){
        System.out.print("\n");
    }
}
