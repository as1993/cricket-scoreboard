package main.java.domain;

public class TeamDetails {

    private String[] players;
    private int runsScored;
    private int ballsBowled;
    private int wicketsLost;

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public void setBallsBowled(int oversBowled) {
        this.ballsBowled = oversBowled;
    }

    public int getWicketsLost() {
        return wicketsLost;
    }

    public void setWicketsLost(int wicketsLost) {
        this.wicketsLost = wicketsLost;
    }
}
