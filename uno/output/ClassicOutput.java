package uno.output;

import java.util.List;

import uno.players.Player;
import uno.cards.Card;

public class ClassicOutput implements OutputCommand {
    
    @Override
    public void outputGameStart(List<Player> players, Card topCard){
        System.out.print("GAME_START players=<" + players.size() + ">\n");

        System.out.print("EVENT TOP_CARD <" + topCard.getCardType() + "> color=<" + topCard.getCardColor() + ">\n");

        for(int i = 0; i < players.size(); i++){
            System.out.print("EVENT HAND player=<" + i + "> cards=");
            for(int j = 0; j < players.get(i).getHand().size(); j++){
                System.out.print("<" + players.get(i).getHand().get(j).getCardString() + "> ");
            }
            newLine();
        }

        System.out.print("EVENT TURN_START player=<0>\n");
        
    }

    @Override
    public void outputCommand(String command){
        System.out.print("EVENT COMAND line=\"" + command + "\"\n");
    }
    
    @Override
    public void outputPlayCard(int playerId, Card cardplayed){
        if(cardplayed.isNumberCard()){
            System.out.print("EVENT PLAY_CARD Player " + playerId + " Played " + cardplayed.getCardString());
        }else{
            System.out.print("EVENT PLAY_CARD Player " + playerId + " Played " + cardplayed.getCardType());
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
        System.out.print("EVENT CHOOSE_COLOR Player " + playerId + " chose color " + color + "\n");
    }

    @Override
    public void outputTurnAdvance(int nextPlayerId){
        System.out.print("EVENT TURN_ADVANCE Next player: " + nextPlayerId + "\n");
    }

    @Override
    public void newLine(){
        System.out.print("\n");
    }
}
