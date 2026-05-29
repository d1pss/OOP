package uno.rules;

import java.util.Map;

import uno.cardEffect.CardEffect;
import uno.cards.Card;


public abstract class AbstractRule implements Rule {
    protected Map<String, CardEffect> cardEffects;

    protected AbstractRule(Map<String, CardEffect> cardEffects){
        this.cardEffects = cardEffects;
    }

    public abstract boolean isNumberOfPlayersValid(int numberOfPlayers);

    public abstract boolean isPlayable(Card topCard, Card playedCard, String currentColor);

    public abstract CardEffect getEffectOf(Card playedCard);
}
