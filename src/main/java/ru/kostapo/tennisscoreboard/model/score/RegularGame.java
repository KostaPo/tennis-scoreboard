package ru.kostapo.tennisscoreboard.model.score;

public class RegularGame extends Game {

    @Override
    public String getPlayerPoints(Integer playerIndex) {
        return Point.getByIndex(playersPoints.get(playerIndex));
    }

    @Override
    void updateState() {
        int player1Score = playersPoints.get(PLAYER1);
        int player2Score = playersPoints.get(PLAYER2);
        if (player1Score >= 4 && player1Score - player2Score >= 2) {
            gameState = State.PLAYER_1_WIN;
        } else if (player2Score >= 4 && player2Score - player1Score >= 2) {
            gameState = State.PLAYER_2_WIN;
        } else if (player1Score >= 3 && player2Score >= 3) {
            if (player1Score == player2Score) {
                deuce();
            }
        }
    }

    private void deuce() {
        playersPoints.replace(PLAYER1, 3);
        playersPoints.replace(PLAYER2, 3);
    }

    private enum Point {
        LOVE("0"),
        FIFTEEN("15"),
        THIRTY("30"),
        FORTY("40"),
        ADVANTAGE("AD");

        private final String value;

        Point(String stringValue) {
            this.value = stringValue;
        }

        public static String getByIndex(int index) {
            return values()[index].value;
        }
    }
}
