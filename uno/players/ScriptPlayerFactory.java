package uno.players;

public class ScriptPlayerFactory implements PlayerFactory {
    
    @Override
    public Player createPlayer(int id){
        return new ScriptPlayer(id);
    }

}
