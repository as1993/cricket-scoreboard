package main.java;

import java.util.HashMap;
import java.util.Map;

public class PlayerRepository {

    private final Map<String, PlayerDetails> allPlayerStats;

    public PlayerRepository(){
        allPlayerStats = new HashMap<>();
    }

    public void createPlayer(String name){
        allPlayerStats.put(name, new PlayerDetails());
    }

    public Map<String, PlayerDetails> getAllPlayerStats() {
        return allPlayerStats;
    }

    public PlayerDetails getPlayerDetails(String name){
        return allPlayerStats.get(name);
    }
}
