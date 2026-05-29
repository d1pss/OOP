package uno.gameEngine;

public interface GameCommands {
    
    public void commandPlayCard(int playerId, int cardIndex);

    public void commandDrawCard(int playerId);

    public void commandSetColorAfterWildCard(int playerId, String color);
    
}
