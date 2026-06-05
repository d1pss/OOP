package unoExtended.cards;

import uno.cards.Card;
import uno.cards.CardFactory;

/**
 * A concrete implementation of the {@link CardFactory} interface for the extended ruleset.
 * <p>This factory is responsible for instantiating {@link ExtendedCard} objects, allowing 
 * the game engine to seamlessly load and play with new, extended card types 
 * (such as DRAW_THREE) without requiring any modifications to the core loading system.</p>
 */
public class ExtendedCardFactory implements CardFactory {

    /**
     * Constructs a new factory dedicated to creating extended UNO cards.
     */
    public ExtendedCardFactory() {
        // Default constructor
    }

    @Override
    public Card createCard(String color, String type) {
        // Instantiates and returns a new ExtendedCard with the provided properties
        return new ExtendedCard(color, type);
    }
    
}