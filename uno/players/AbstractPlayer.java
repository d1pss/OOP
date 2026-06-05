package uno.players;

import java.util.ArrayList;
import java.util.List;
import uno.cards.Card;

/**
 * Provides a skeletal implementation of the {@link Player} interface.
 * <p>This abstract class handles the core state common to all players in the game, 
 * specifically their unique identifier and the management of their hand of cards.
 * Subclasses only need to define the specific behaviors for placing and drawing cards.</p>
 */
public abstract class AbstractPlayer implements Player {
    
    /**
     * The collection of cards currently held by the player.
     * Declared as protected so concrete player classes can access and manipulate it.
     */
    protected List<Card> hand;
    
    /**
     * The unique numerical identifier assigned to this player.
     */
    private int id;

    /**
     * Constructs a new player with the specified identifier.
     * Automatically initializes the player's hand as an empty list, 
     * ready to receive cards during the game setup.
     *
     * @param id The unique numerical identifier for this player.
     */
    public AbstractPlayer(int id) {
        this.hand = new ArrayList<>();
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<Card> getHand() {
        return hand;
    }

    @Override
    public abstract Card placeCard(int index);

    @Override
    public abstract void drawCard(Card cardDrawn);
}