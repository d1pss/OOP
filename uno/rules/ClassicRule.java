package uno.rules;

import java.util.List;

import uno.Uno;
import uno.cards.Card;
import uno.player.Player;

public class ClassicRule extends AbstractRule {

    public ClassicRule(int numberOfPlayers){
        super(numberOfPlayers);
    }
    
    @Override
    public void processNumberCard(Card playerCard, Uno game){
        //for a number card, simply move to the next player as there are no special effects to apply in Classic Uno
        idPlayerInCurrTurn = nextPlayerId();
    }

    @Override
    public void processSpecialCard(Card playerCard, Uno game){
        String cardType = playerCard.getCardType();
        List<Player> players = game.getPlayers();

        if(cardType == "SKIP"){

            //skip the next player
            idPlayerInCurrTurn = nextPlayerId();

        }else if(cardType == "REVERSE"){

            //reverse the direction of the game
            isGameMovingClockwise = !isGameMovingClockwise;

        }else if(cardType == "DRAW_TWO"){

            //the next player draws 2 cards and loses their turn
            idPlayerInCurrTurn = nextPlayerId();
            Player playerToDraw = players.get(idPlayerInCurrTurn);
            drawNCards(playerToDraw, game, 2);
            

        }else if(cardType == "WILD"){

            //the player can choose the color to continue the game
            //afterWlidCardColor should be set to the color that will be chosen by the player in the next command

        }else if(cardType == "WILD_DRAW_FOUR"){

            //the next player draws 4 cards and loses their turn
            idPlayerInCurrTurn = nextPlayerId();
            Player playerToDraw = players.get(idPlayerInCurrTurn);
            drawNCards(playerToDraw, game, 4);

        }else{
            //throw new InvalidCardException("");
        }

        //after applying the effects of the card, move to the next player
        idPlayerInCurrTurn = nextPlayerId();

    }

}
