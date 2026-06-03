package uno.gameEngine;

public interface GameCommands {
    
    public boolean commandPlayCard(int playerId, int cardIndex);

    public boolean commandDrawCard(int playerId);

    public boolean commandSetColorAfterWildCard(int playerId, String color);
    
}
