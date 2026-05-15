package uno.rules;

import uno.cards.Card;

public abstract class AbstractRule implements Rule{
    protected int idPlayerInCurrTurn;
    protected boolean isGameMovingClockwise;
    protected char afterWlidCardColor;
    protected final int numberOfPlayers;

    public AbstractRule(int numberOfPlayers){
        idPlayerInCurrTurn = 0;
        isGameMovingClockwise = true;
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public void processCard(Card topCard, Card playerCard){
        if(!isValidPlay(topCard, playerCard)){
            //throw new IllegalPlayException("");
        }

        if(playerCard.getCardColor() == 'W'){
            processWildCard(playerCard);
        }else{
            processNumberCard(playerCard);
        }
        
    }

    public boolean isValidPlay(Card topCard, Card playerCard){
        char playerColor = playerCard.getCardColor();
        if(playerColor == 'W') return true;

        if(topCard.getCardColor() == playerColor || playerColor == afterWlidCardColor) return true;

        if(topCard.getCardType() == playerCard.getCardType()) return true;

        return false;
    }

    public void nextPlayer(){
        if(idPlayerInCurrTurn == numberOfPlayers - 1){
            idPlayerInCurrTurn = 0;
        }else{
            idPlayerInCurrTurn++;
        } 
    }

    public abstract void processNumberCard(Card playerCard);

    public abstract void processWildCard(Card playerCard);
}
