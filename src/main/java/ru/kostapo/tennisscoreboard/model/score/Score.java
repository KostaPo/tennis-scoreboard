package ru.kostapo.tennisscoreboard.model.score;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Score {

    private final int SETS_TO_WIN = 2;

    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;

    private Set currentSet;
    private State currentState;
    private final HashMap<Integer, Integer> setsScore;
    private final HashMap<Integer, Set> setsDetailsHistory;


    public Score() {
        setsScore = new HashMap<>();
        setsScore.put(PLAYER1, 0);
        setsScore.put(PLAYER2, 0);
        currentState = State.GO_ON;
        setsDetailsHistory = new HashMap<>();
        currentSet = new Set();
        setsDetailsHistory.put(setsDetailsHistory.size() + 1, currentSet);
    }

    public void addPoint(Integer playerIndex) {
        currentSet.scored(playerIndex);
        updateState();
    }

    public void updateState() {
        if (!currentSet.getState().equals(State.GO_ON)) {
            Integer currentPoint = setsScore.get(currentSet.getSetState().ordinal());
            setsScore.replace(currentSet.getSetState().ordinal(), currentPoint + 1);
            int player1Score = setsScore.get(PLAYER1);
            int player2Score = setsScore.get(PLAYER2);
            if (player1Score == SETS_TO_WIN || player2Score == SETS_TO_WIN) {
                currentState = player1Score > player2Score
                        ? State.PLAYER_1_WIN
                        : State.PLAYER_2_WIN;
            } else {
                currentSet = new Set();
                setsDetailsHistory.put(setsDetailsHistory.size() + 1, currentSet);
            }
        }
    }

    public String getPlayerSetScore(Integer playerIndex, Integer setIndex) {
        if (setsDetailsHistory.containsKey(setIndex)) {
            return String.valueOf(setsDetailsHistory.get(setIndex).getPlayerGames(playerIndex));
        } else {
            return "";
        }
    }

    public String getPlayerCurrentGamePoint(Integer playerIndex) {
        return currentSet.getCurrentGame().getPlayerPoints(playerIndex);
    }

    public State getState() {
        return currentState;
    }
}
