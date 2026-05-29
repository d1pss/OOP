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
        if(numberOfPlayers >= 2 && numberOfPlayers <= 6) return true;
        return false;
    }


    @Override
    public boolean isPlayable(Card topCard, Card playedCard, String currentColorAfterWildCard){
        
        if (playedCard.getCardType().equals("W")) { 
            return true;
        }

        if (playedCard.getCardColor().equals(topCard.getCardColor())) {
            return true;
        }

        if(playedCard.getCardColor().equals(currentColorAfterWildCard) && topCard.isWildCard()){

        }

        if (playedCard.getCardType().equals(topCard.getCardType())) {
            return true;
        }

        return false;
    }

    @Override
    public CardEffect getEffectOf(Card playedCard){
        String cardType = playedCard.getCardType();
        
        return cardEffects.getOrDefault(cardType, new NormalEffect());
    }
    
}
