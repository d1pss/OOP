package uno.players;

import uno.cards.Card;

/**
 * A concrete implementation of a player that is controlled by the script commands.
 * <p>This class extends {@link AbstractPlayer} and provides the concrete mechanics 
 * for adding and removing cards from the player hand during the game.</p>
 */
public class ScriptPlayer extends AbstractPlayer {

    /**
     * Constructs a new script-controlled player with the specified identifier.
     *
     * @param id The unique numerical identifier assigned to this player.
     */
    public ScriptPlayer(int id) {
        super(id);
    }
    
    @Override
    public Card placeCard(int index) {
        // Removes the card from the list at the specified index and returns it
        return hand.remove(index);
    }

    @Override
    public void drawCard(Card cardDrawn) {
        // Appends the newly drawn card to the end of the player's hand
        hand.add(cardDrawn);
    }
}