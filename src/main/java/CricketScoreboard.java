package main.java;

import java.util.Scanner;

public class CricketScoreboard {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("No. of players for each team: ");
        int nPlayers = Integer.parseInt(sc.next());

        System.out.println("No. of overs: ");
        int nOvers = Integer.parseInt(sc.next());

        MatchRepository matchRepository = new MatchRepository(nPlayers);
        ScoreboardService scoreboardService = new ScoreboardServiceImpl(matchRepository);
        MatchService matchService = new MatchService(matchRepository, scoreboardService, nPlayers);

        for (int k = 0; k < 2; k++) {
            String[] battingOrder = new String[nPlayers];
            System.out.println("Batting Order for team " + (k + 1) + ": ");
            for (int i = 0; i < nPlayers; i++) {
                battingOrder[i] = sc.next();
            }
            matchService.setBattingOrder(k, battingOrder);

            outer:
            for (int j = 0; j < nOvers; j++) {
                System.out.println("Over " + (j + 1) + ": ");
                for (int i = 0; i < 6; i++) {
                    String event = sc.next();
                    if (event.equalsIgnoreCase("wd") || event.equalsIgnoreCase("nb")) {
                        i--;
                    }
                    String s = matchService.addBallData(k, i + 1, event);
                    if (s != null) {
                        break outer;
                    }
                }
            }
        }
    }
}
