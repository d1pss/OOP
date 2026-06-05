package uno.players;

import java.util.List;

import uno.cards.Card;

/**
 * Defines the contract for a participant in the UNO game.
 * This interface establishes the essential behaviors required 
 * for any player to interact with the game engine, 
 * manage their identity, and manipulate their hand of cards.
 */
public interface Player {

    /**
     * Retrieves the unique identifier of the player.
     *
     * @return The integer ID representing this player in the game engine.
     */
    public int getId();

    /**
     * Retrieves the current collection of cards held by the player.
     *
     * @return A list of {@link Card} objects representing the player's active hand.
     */
    public List<Card> getHand();

    /**
     * Removes and returns a card from the player hand based on its position.
     * This method is called when the player actively plays a card during their turn.
     *
     * @param index The zero-based index of the card to be played from the hand.
     * @return The {@link Card} object that was removed from the player's hand.
     */
    public Card placeCard(int index);

    /**
     * Adds a newly drawn card to the player hand.
     * This method is triggered when a player voluntarily draws from the pile 
     * or is forced to draw due to a penalty (e.g., a DRAW_TWO card effect).
     *
     * @param cardDrawn The {@link Card} that was drawn and should be added to the hand.
     */
    public void drawCard(Card cardDrawn);

}