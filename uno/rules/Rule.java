package uno.rules;

import uno.cards.*;
import uno.cardEffect.*;

public interface Rule {

    public boolean isNumberOfPlayersValid(int numberOfPlayers);

    public boolean isPlayable(Card topCard, Card playedCard, String currentColor);

    public CardEffect getEffectOf(Card playedCard);
}
