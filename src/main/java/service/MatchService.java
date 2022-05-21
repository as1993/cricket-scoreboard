package main.java.service;

public interface MatchService {
    void setBattingOrder(int teamNumber, String[] battingOrder);

    String addBallData(int team, int ballNumber, String event);
}
