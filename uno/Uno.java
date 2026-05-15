package uno;

import java.util.ArrayList;
import java.util.List;

import uno.cards.Card;
import uno.rules.Rule;

public class Uno {
    private List<Card> deck;
    private final int numberOfPlayers;
    private Rule rule;

    // Used Singleton Patern
    private static Uno instance = null;

    public static Uno getInstance(Rule ruleSet){
        if(instance == null){
            instance = new Uno(ruleSet.getNumberOfPlayers(), ruleSet);
        }
        return instance;
    }

    private Uno(int numberOfPlayers, Rule ruleSet){
        this.numberOfPlayers = numberOfPlayers;
        this.rule = ruleSet;
        deck = new ArrayList<>();
    }

    
    public void addCardToDeck(Card card){
        deck.add(card);
    }
    
    public Card removeCardFromDeck(){
        if(deck.isEmpty()){
            // TODO
            //leave need to Throw exeption
        }
        return deck.removeLast();
    }

    //public void playCard(){}

}