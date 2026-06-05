package uno.rules;

import java.util.Map;

import uno.cardEffect.*;
import uno.cards.Card;

/**
 * Represents the classic ruleset for the UNO game.
 * This class implements the specific validation logic for determining 
 * valid player counts, legal card plays, and retrieving the appropriate 
 * effects for classic UNO cards.
 */
public class ClassicRule extends AbstractRule {

    /**
     * Initializes the classic ruleset with the necessary card effects.
     *
     * @param cardEffects A map associating card type strings with their respective {@link CardEffect}.
     */
    public ClassicRule(Map<String, CardEffect> cardEffects) {
        super(cardEffects);
    }

    @Override
    public boolean isNumberOfPlayersValid(int numberOfPlayers) {
        // Evaluates if the number of players is within the classic range (2 to 6)
        return numberOfPlayers >= 2 && numberOfPlayers <= 6;
    }

    @Override
    public boolean isPlayable(Card topCard, Card playedCard, String currentColorAfterWildCard) {
        
        // Wild cards can always be played
        if (playedCard.getCardType().equals("W")) { 
            return true;
        }

        // Playable if the color matches the top card physical color
        if (playedCard.getCardColor().equals(topCard.getCardColor())) {
            return true;
        }

        // Playable if the top card was a Wild card and the played card matches the chosen color
        if (playedCard.getCardColor().equals(currentColorAfterWildCard) && topCard.isWildCard()) {
            return true; // Fixed: This block was empty and would previously fall through to false!
        }

        // Playable if the type (number or symbol) matches the top card type
        if (playedCard.getCardType().equals(topCard.getCardType())) {
            return true;
        }

        // If none of the above conditions are met, the move is invalid
        return false;
    }

    @Override
    public CardEffect getEffectOf(Card playedCard) {
        String cardType = playedCard.getCardType();
        
        // Returns the specific effect if it exists, otherwise defaults to a normal (no-action) effect
        return cardEffects.getOrDefault(cardType, new NormalEffect());
    }
    
}