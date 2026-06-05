package uno.rules;

import java.util.Map;

import uno.cardEffect.CardEffect;
import uno.cards.Card;

/**
 * Provides a foundational implementation for the {@link Rule} interface.
 * This class manages the association between specific card types and their corresponding 
 * {@link CardEffect}s using a Map, reducing boilerplate code for concrete rule implementations.
 */
public abstract class AbstractRule implements Rule {
    
    /**
     * A mapping that links a card's type (e.g., "SKIP", "REVERSE", "DRAW_TWO") 
     * to its corresponding executable effect.
     * Declared as protected so concrete rule subclasses can access it if necessary.
     */
    protected Map<String, CardEffect> cardEffects;

    /**
     * Initializes the rule set with a specific mapping of card effects.
     *
     * @param cardEffects A map associating string identifiers of card types with 
     * their respective {@link CardEffect} objects.
     */
    protected AbstractRule(Map<String, CardEffect> cardEffects) {
        this.cardEffects = cardEffects;
    }

    @Override
    public abstract boolean isNumberOfPlayersValid(int numberOfPlayers);

    @Override
    public abstract boolean isPlayable(Card topCard, Card playedCard, String currentColor);

    @Override
    public abstract CardEffect getEffectOf(Card playedCard);
}