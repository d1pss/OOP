package uno.rules;

import java.util.Map;
import uno.cardEffect.*;
import uno.cards.Card;

public class ClassicRule extends AbstractRule{

    public ClassicRule(Map<String, CardEffect> cardEffects){
        super(cardEffects);
    }

    @Override
    public boolean isNumberOfPlayersValid(int numberOfPlayers){
        return (numberOfPlayers >= 2 && numberOfPlayers <= 6);
    }


    @Override
    public boolean isPlayable(Card topCard, Card playedCard, String currentColorAfterWildCard){
        
        return (playedCard.getCardType().equals("W") || 
                playedCard.getCardColor().equals(topCard.getCardColor()) || 
                playedCard.getCardColor().equals(currentColorAfterWildCard) && topCard.isWildCard() || 
                playedCard.getCardType().equals(topCard.getCardType()));
    }

    @Override
    public CardEffect getEffectOf(Card playedCard){
        String cardType = playedCard.getCardType();
        
        return cardEffects.getOrDefault(cardType, new NormalEffect());
    }
    
}
