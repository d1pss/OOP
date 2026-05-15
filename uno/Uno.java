package uno;

import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;

import uno.cards.Card;
import uno.player.Player;
import uno.rules.Rule;

public class Uno {
    private ArrayList<Player> players;
    private List<Card> drawPile;
    private List<Card> discardPile;
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

        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();
        players = new ArrayList<>(numberOfPlayers);

    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Card getTopCard(){
        if(discardPile.isEmpty()){
            //throw new Exception("Discard pile is empty");
        }
        return discardPile.getLast();
    }

    public void addCardToDrawPile(Card card){
        drawPile.add(card);
    }

    public void DiscardCardToPile(Card card){
        discardPile.add(card);
    }
    
    public Card DrawCardFromPile(){
        if(drawPile.isEmpty()){
            //throw new Exception("Draw pile is empty");
        }
        return drawPile.removeLast();
    }

    public void playCard(){
        
    }

}