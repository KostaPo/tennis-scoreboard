package ru.kostapo.tennisscoreboard.model.score;

import java.util.HashMap;

public abstract class Game {

    protected final int PLAYER1 = 1;
    protected final int PLAYER2 = 2;
    protected State gameState;
    protected final HashMap<Integer, Integer> playersPoints;

    public Game() {
        playersPoints = new HashMap<>();
        playersPoints.put(PLAYER1, 0);
        playersPoints.put(PLAYER2, 0);
        gameState = State.GO_ON;
    }

    abstract void updateState();

    public String getPlayerPoints(Integer playerIndex) {
        return String.valueOf(playersPoints.get(playerIndex));
    }

    public void addPoint(Integer playerIndex) {
        Integer currentPoint = playersPoints.get(playerIndex);
        playersPoints.replace(playerIndex, currentPoint + 1);
        updateState();
    }

    public State getState() {
        return gameState;
    }
}
