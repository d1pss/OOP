package uno.rules;

import uno.Uno;
import uno.cards.Card;
import uno.player.Player;

public abstract class AbstractRule implements Rule{
    protected int idPlayerInCurrTurn;
    protected boolean isGameMovingClockwise;
    protected char afterWlidCardColor;
    protected final int numberOfPlayers;

    public AbstractRule(int numberOfPlayers){
        idPlayerInCurrTurn = 0;
        isGameMovingClockwise = true;
        this.numberOfPlayers = numberOfPlayers;
        afterWlidCardColor = '\0';
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public void setAfterWlidCardColor(char afterWlidCardColor){
        this.afterWlidCardColor = afterWlidCardColor;
    }

    public void applyRulesToCard(Card playerCard, Uno game) throws Exception{
        Card topCard = null;
        try {
            topCard = game.getTopCard();
        } catch (Exception e) {
            throw e;
        }

        //check if the card played by the player is valid, if not throw an exception
        if(!isValidPlay(topCard, playerCard)){
            throw new Exception("Card used is not valid");
        }

        //if the card is valid, apply the effects of the card
        if(playerCard.isNumberCard()){
            processNumberCard(playerCard, game);
        }else{
            processSpecialCard(playerCard, game);
        }
    }

    /**
     * Check if the player card can be played on top of the current card in play
     * @param topCard the current card in play (the last card in the discard pile)
     * @param playerCard the card to be played
     * @return true if the card can be played, false otherwise
     */
    protected boolean isValidPlay(Card topCard, Card playerCard){
        char playerColor = playerCard.getCardColor();

        //if the player card is a wild card, it can be played on top of any card
        if(playerCard.isWildCard()) return true;

        //if the player card has the same color or type as the top card, it can be played
        if(topCard.getCardColor() == playerColor) return true;

        //if the player card has the same type as the top card, it can be played
        if(topCard.getCardType() == playerCard.getCardType()) return true;

        //if the top card is a wild card and the player card has the same color as the color chosen by the player that played the wild card, it can be played
        if(topCard.isWildCard() && afterWlidCardColor == playerColor) return true;

        //if none of the above conditions are met, the card cannot be played
        return false;
    }

    /**
     * Get the id of the next player in turn
     * @return the id of the next player in turn
     */
    protected int nextPlayerId(){
        if(isGameMovingClockwise){
            //if the game is moving clockwise
            //if the current player is the last player, the next player is the first player (id 0)
            if(idPlayerInCurrTurn == numberOfPlayers - 1){
                return 0;
            }else{
                return idPlayerInCurrTurn + 1;
            } 
        }else{
            //if the game is moving counterclockwise
            //if the current player is the first player, the next player is the last player (id numberOfPlayers - 1)
            if(idPlayerInCurrTurn == 0){
                return numberOfPlayers - 1;
            }else{
                return idPlayerInCurrTurn - 1;
            }
        }
    }

    /**
     * Draw n cards and add to player hand
     * @param player the player that recive the drawn cards
     * @param game the game instance, used to draw cards from the pile
     * @param numberOfCardsToDraw the number of cards to draw
     */
    protected void drawNCards(Player player, Uno game, int numberOfCardsToDraw){

        //draw n cards and add to player hand
        for(int i = 0; i < numberOfCardsToDraw; i++){
            
            //draw card from unused cards pile
            Card drawnCard = game.DrawCardFromPile();

            //add card to player hand
            player.drawCard(drawnCard);

        }

    }

    /**
     * Apply the effects of a number card
     * @param playerCard the card played by the player, which is a number card
     * @param game the game instance, used to apply the effects of the card
     */
    protected abstract void processNumberCard(Card playerCard, Uno game);

    /**
     * Apply the effects of a special card
     * @param playerCard the card played by the player, which is a special card
     * @param game the game instance, used to apply the effects of the card
     */
    protected abstract void processSpecialCard(Card playerCard, Uno game);
}
