package uno.rules;

import uno.cards.Card;

public class ClassicRule extends AbstractRule {

    public ClassicRule(int numberOfPlayers){
        super(numberOfPlayers);
    }

    public void processNumberCard(Card playerCard){
        nextPlayer();
    }

    public void processWildCard(Card playerCard){

    }
}
