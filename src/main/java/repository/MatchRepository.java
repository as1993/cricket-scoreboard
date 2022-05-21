package main.java.repository;

import main.java.domain.MatchDetails;

import java.util.HashMap;
import java.util.Map;

public class MatchRepository {

    private static int CURR_MATCH_ID;
    private final Map<Integer, MatchDetails> allMatchDetails;

    public MatchRepository(int nPlayersInEachTeam) {
        this.allMatchDetails = new HashMap<>();
        this.allMatchDetails.put(CURR_MATCH_ID, new MatchDetails(nPlayersInEachTeam));
    }

    public MatchDetails getMatchDetailsByMatchId(int id) {
        return allMatchDetails.get(id);
    }

    public void setMatchDetails(int id, MatchDetails matchDetails) {
        allMatchDetails.put(id, matchDetails);
    }

    public void addMatchDetails(MatchDetails matchDetails) {
        allMatchDetails.put(++CURR_MATCH_ID, matchDetails);
    }
}
