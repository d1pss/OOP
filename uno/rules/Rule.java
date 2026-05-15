package uno.rules;

import uno.Uno;
import uno.cards.Card;


public interface Rule {

    /**
     * Get the number of players in the game
     * @return the number of players in the game
     */
    public int getNumberOfPlayers();

    /**
     * Set the color of the top wild card after it is played, this method is used to set the chosen color of the wildcard after it is played, which is used to check if the next player can play a card on top of the wild card
     * @param afterWlidCardColor the color chosen by the player that played the wild card
     */
    public void setAfterWlidCardColor(char afterWlidCardColor);

    /**
     * Apply the rules of the game to the card played by the player, this method is responsible for checking if the card played is valid and applying the effects of the card if it is a special card
     * @param playerCard the card to be played
     * @param game the game instance
     */
    public void applyRulesToCard(Card playerCard, Uno game);
}