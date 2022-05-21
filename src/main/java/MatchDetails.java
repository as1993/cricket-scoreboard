package main.java;

import java.util.HashMap;
import java.util.Map;

public class MatchDetails {

    private Map<String, PlayerDetails>[] playerStats;
    private TeamDetails[] teamStats;
    private String winner;

    public MatchDetails(int nPlayersInEachTeam) {
        this.teamStats = new TeamDetails[2];
        this.teamStats[0] = new TeamDetails();
        this.teamStats[1] = new TeamDetails();
        this.playerStats = new HashMap[2];
        playerStats[0] = new HashMap<>();
        playerStats[1] = new HashMap<>();
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Map<String, PlayerDetails> getPlayerStats(int team) {
        return playerStats[team];
    }

    public TeamDetails[] getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(TeamDetails[] teamStats) {
        this.teamStats = teamStats;
    }
}
