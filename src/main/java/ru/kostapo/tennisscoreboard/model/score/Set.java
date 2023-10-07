package ru.kostapo.tennisscoreboard.model.score;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Set {

    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;

    private Game currentGame;
    private State setState;
    private final HashMap<Integer, Integer> gamesScore;


    public Set() {
        setState = State.GO_ON;
        gamesScore = new HashMap<>();
        gamesScore.put(PLAYER1, 0);
        gamesScore.put(PLAYER2, 0);
        currentGame = new RegularGame();
    }

    public void scored(Integer playerIndex) {
        currentGame.addPoint(playerIndex);
        updateState();
    }

    private void updateState() {
        if (!currentGame.getState().equals(State.GO_ON)) {
            Integer currentPoint = gamesScore.get(currentGame.getState().ordinal());
            gamesScore.replace(currentGame.getState().ordinal(), currentPoint + 1);
            int player1Games = gamesScore.get(PLAYER1);
            int player2Games = gamesScore.get(PLAYER2);
            if (isWonAfterTiebreaker(player1Games, player2Games)
                    || isWonAfterRegular(player1Games, player2Games)) {
                setState = player1Games > player2Games
                        ? State.PLAYER_1_WIN
                        : State.PLAYER_2_WIN;
            } else {
                currentGame = isNextGameTiebreaker(player1Games, player2Games)
                        ? new TiebreakerGame()
                        : new RegularGame();
            }
        }
    }

    private boolean isWonAfterTiebreaker(int player1Games, int player2Games) {
        if (player1Games == 7 || player2Games == 7) {
            return Math.abs(player1Games - player2Games) == 1;
        }
        return false;
    }

    private boolean isWonAfterRegular(int player1Games, int player2Games) {
        if (player1Games >= 6 || player2Games >= 6) {
            return Math.abs(player1Games - player2Games) >= 2;
        }
        return false;
    }

    private boolean isNextGameTiebreaker(int player1Games, int player2Games) {
        return player1Games == 6 && player2Games == 6;
    }

    public String getPlayerGames(Integer playerIndex) {
        return String.valueOf(gamesScore.get(playerIndex));
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public State getState() {
        return setState;
    }
}
