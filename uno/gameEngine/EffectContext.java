package uno.gameEngine;

import java.util.List;

import uno.players.Player;

public interface EffectContext {

    public void drawCards(Player player, int numberCardsToDraw);

    public void reverseWay();

    public void skipPlayers(int numberSkips);

    public int nextPlayerId();

    public List<Player> getReadOnlyPlayersList();
    
}
