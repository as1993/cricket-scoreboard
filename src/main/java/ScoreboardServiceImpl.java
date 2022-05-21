package main.java;

import java.util.Map;

public class ScoreboardServiceImpl implements ScoreboardService {

    private final MatchRepository matchRepository;

    public ScoreboardServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public void printScorecard(int team, String[] onCrease) {
        MatchDetails matchDetails = matchRepository.getMatchDetailsByMatchId(0);

        System.out.println("Scorecard for team " + (team + 1) + ":");
        System.out.println("Player Name \t Score \t 4s \t 6s \t Balls");
        int ballsFaced = 0;
        for (Map.Entry<String, PlayerDetails> entry : matchDetails.getPlayerStats(team).entrySet()) {
            PlayerDetails details = entry.getValue();
            String name = entry.getKey();

            System.out.print("\t" + name);
            if (name.equalsIgnoreCase(onCrease[0]) || name.equalsIgnoreCase(onCrease[1])) {
                System.out.print("*");
            }
            System.out.println("\t\t\t\t" + details.getRuns() + "\t\t" + details.getFours()
                    + "\t\t" + details.getSixes() + "\t\t" + details.getBallsFaced());
            ballsFaced += details.getBallsFaced();
        }

        TeamDetails teamStat = matchDetails.getTeamStats()[team];
        System.out.printf("\nTotal: %d/%d", teamStat.getRunsScored(), teamStat.getWicketsLost());
        System.out.printf("\nOvers: %d.%d\n", (ballsFaced / 6), (ballsFaced % 6));
        System.out.println();
    }

    public void printMatchResult() {
    }
}
