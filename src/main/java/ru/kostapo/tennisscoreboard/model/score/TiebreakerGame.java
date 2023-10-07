package ru.kostapo.tennisscoreboard.model.score;

public class TiebreakerGame extends Game {

    @Override
    void updateState() {
        int player1Score = playersPoints.get(PLAYER1);
        int player2Score = playersPoints.get(PLAYER2);
        if (player1Score >= 7 && player1Score - player2Score >= 2) {
            gameState = State.PLAYER_1_WIN;
        }
        if (player2Score >= 7 && player2Score - player1Score >= 2) {
            gameState = State.PLAYER_2_WIN;
        }
    }
}
