package uno;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import uno.cards.Card;
import uno.cards.CardFactory;
import uno.parser.*;
import uno.player.Player;
import uno.player.PlayerFactory;
import uno.rules.Rule;

public class Uno {
    private ArrayList<Player> players;
    private List<Card> drawPile;
    private List<Card> discardPile;
    private final int numberOfPlayers;

    private final Rule rule;
    private final CardFactory cardFactory;
    private final PlayerFactory playerFactory;

    private DeckLoader deckLoader;
    private ScriptParser scriptParser;

    // Used Singleton Patern
    private static Uno instance = null;

    public static Uno getInstance(Rule ruleSet, CardFactory cardFactory, PlayerFactory playerFactory){
        if(instance == null){
            instance = new Uno(ruleSet.getNumberOfPlayers(), ruleSet, cardFactory, playerFactory);
        }
        return instance;
    }

    private Uno(int numberOfPlayers, Rule ruleSet, CardFactory cardFactory, PlayerFactory playerFactory){

        this.numberOfPlayers = numberOfPlayers;

        this.rule = ruleSet;
        this.cardFactory = cardFactory;
        this.playerFactory = playerFactory;

        discardPile = new ArrayList<>();
        players = new ArrayList<>(numberOfPlayers);

        deckLoader = new DeckLoader();
    }

    public void startGame(Reader deck, Reader script){
        scriptParser = new ScriptParser(script);
        try {
            drawPile = deckLoader.createDeck(deck, cardFactory);
        } catch (Exception e) {
            // TODO: handle exception
        }

        for(int j = 0; j < numberOfPlayers; j++){
            players.add(playerFactory.createPlayer(j));
        }

        Card nextCard = null;
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < numberOfPlayers; j++){

                try {
                    nextCard = drawCardFromPile();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                
                players.get(i).drawCard(nextCard);
            }
        }

        
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Card getTopCard() throws Exception{
        if(discardPile.isEmpty()){
            throw new Exception("Discard pile is empty");
        }
        return discardPile.getLast();
    }

    private void discardCardToPile(Card card){
        discardPile.add(card);
    }
    
    public Card drawCardFromPile() throws Exception{
        if(drawPile.isEmpty()){
            throw new Exception("Draw pile is empty");
        }
        return drawPile.removeLast();
    }

    public void playCard(Player player, int cardIndex){
        Card cardToBeUsed = player.placeCard(cardIndex);

        try{
        rule.applyRulesToCard(cardToBeUsed, this);
        } catch(Exception e) {
            // TODO: handle exception
        }

        discardCardToPile(cardToBeUsed);

    }

}