package main.java.service.impl;

import main.java.domain.MatchDetails;
import main.java.domain.PlayerDetails;
import main.java.domain.TeamDetails;
import main.java.repository.MatchRepository;
import main.java.service.MatchService;
import main.java.service.ScoreboardService;

public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final ScoreboardService scoreboardService;
    private final String[] onCrease;
    private final String[][] battingOrder;
    private int onStrike;

    public MatchServiceImpl(MatchRepository matchRepository, ScoreboardService scoreboardService, int nPlayersInEachTeam) {
        this.matchRepository = matchRepository;
        this.scoreboardService = scoreboardService;
        this.onCrease = new String[2];
        this.battingOrder = new String[2][nPlayersInEachTeam];
    }

    @Override
    public void setBattingOrder(int teamNumber, String[] battingOrder) {
        this.onStrike = 0;
        this.battingOrder[teamNumber] = battingOrder;
        onCrease[0] = battingOrder[0];
        onCrease[1] = battingOrder[1];

        MatchDetails details = matchRepository.getMatchDetailsByMatchId(0);
        details.getPlayerStats(teamNumber).put(battingOrder[0], new PlayerDetails());
        details.getPlayerStats(teamNumber).put(battingOrder[1], new PlayerDetails());
    }

    @Override
    public String addBallData(int team, int ballNumber, String event) {
        return addEvent(team, ballNumber, event);
    }

    private String addEvent(int team, int ballNumber, String event) {

        MatchDetails details = matchRepository.getMatchDetailsByMatchId(0);
        PlayerDetails playerStats = details.getPlayerStats(team).getOrDefault(onCrease[onStrike], new PlayerDetails());
        TeamDetails[] teamDetails = details.getTeamStats();

        switch (event.toLowerCase()) {
            case "w" -> {
                playerStats.setBallsFaced(playerStats.getBallsFaced() + 1);
                int wicketsLost = teamDetails[team].getWicketsLost() + 1;
                teamDetails[team].setWicketsLost(wicketsLost);
                if (wicketsLost == battingOrder[team].length - 1) {
                    if (team == 0) {
                        return checkFor1stInningsEnd(wicketsLost);
                    }
                    return checkFor2ndInningsEnd(wicketsLost, details, teamDetails);
                }
                onCrease[onStrike] = battingOrder[team][wicketsLost + 1];
                details.getPlayerStats(team).put(onCrease[onStrike], new PlayerDetails());
            }
            case "wd", "nb" -> teamDetails[team].setRunsScored(teamDetails[team].getRunsScored() + 1);
            default -> handleRuns(team, event, details, playerStats, teamDetails);
        }

        if (team == 1 && teamDetails[1].getRunsScored() > teamDetails[0].getRunsScored()) {
            showResult(team, details, teamDetails);
            return null;
        }

        MatchDetails finalDetails = buildMatchDetails(details, teamDetails);
        matchRepository.setMatchDetails(0, finalDetails);

        if (ballNumber == 6) {
            onStrike ^= 1;
            scoreboardService.printScorecard(team, onCrease);
        }

        return null;
    }

    private void showResult(int team, MatchDetails details, TeamDetails[] teamDetails) {
        MatchDetails finalDetails = buildMatchDetails(details, teamDetails);
        String result = getResult(teamDetails);
        details.setWinner(result);
        matchRepository.setMatchDetails(0, finalDetails);
        scoreboardService.printScorecard(team, onCrease);
        System.out.println(result);
    }

    private void handleRuns(int team, String event, MatchDetails details, PlayerDetails playerStats, TeamDetails[] teamDetails) {
        int runs = Integer.parseInt(event);
        teamDetails[team].setRunsScored(teamDetails[team].getRunsScored() + runs);
        playerStats.setRuns(playerStats.getRuns() + runs);
        playerStats.setBallsFaced(playerStats.getBallsFaced() + 1);
        if (runs == 4) {
            playerStats.setFours(playerStats.getFours() + 1);
        } else if (runs == 6) {
            playerStats.setSixes(playerStats.getSixes() + 1);
        }
        details.getPlayerStats(team).put(onCrease[onStrike], playerStats);
        if (runs % 2 == 1) {
            onStrike ^= 1;
        }
    }

    private String checkFor2ndInningsEnd(int wicketsLost, MatchDetails details, TeamDetails[] teamDetails) {
        if (wicketsLost == battingOrder[1].length - 1) {
            MatchDetails finalDetails = buildMatchDetails(details, teamDetails);
            String result = getResult(teamDetails);
            details.setWinner(result);
            matchRepository.setMatchDetails(0, finalDetails);

            scoreboardService.printScorecard(1, onCrease);
            System.out.println(result);
        }
        return null;
    }

    private String checkFor1stInningsEnd(int wicketsLost) {
        if (wicketsLost == battingOrder[0].length - 1) {
            scoreboardService.printScorecard(0, onCrease);
            return "Innings Over";
        }
        return null;
    }

    private MatchDetails buildMatchDetails(MatchDetails details, TeamDetails[] teamDetails) {
        details.setTeamStats(teamDetails);
        return details;
    }

    private String getResult(TeamDetails[] teamDetails) {
        int runs1 = teamDetails[0].getRunsScored();
        int runs2 = teamDetails[1].getRunsScored();
        String result = "Match tied";

        if (runs1 > runs2) {
            result = "Result: Team 1 won the match by " + (runs1 - runs2) + " runs";
        } else if (runs2 > runs1) {
            result = "Result: Team 2 won the match by "
                    + (battingOrder[0].length - 1 - teamDetails[1].getWicketsLost()) + " wickets";
        }
        return result;
    }
}