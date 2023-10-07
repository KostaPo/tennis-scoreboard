package ru.kostapo.tennisscoreboard.model.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    @Test()
    public void testDeuceRegularGame() {
        Game game = new RegularGame();

        game.addPoint(1);
        game.addPoint(1);
        game.addPoint(1);

        game.addPoint(2);
        game.addPoint(2);
        game.addPoint(2);

        game.addPoint(1);

        assertEquals(State.GO_ON, game.getState());
    }

    @Test()
    public void testFlawlessVictoryRegularGame() {
        Game game = new RegularGame();
        game.addPoint(1);
        game.addPoint(1);
        game.addPoint(1);
        game.addPoint(1);
        assertEquals(State.PLAYER_1_WIN, game.getState());
    }

    @Test()
    public void testDeuceTiebreakerGame() {
        final int POINTS_COUNT = 6;

        Game game = new TiebreakerGame();
        for (int i = 0; i < POINTS_COUNT; i++) {
            game.addPoint(1);
            game.addPoint(2);
        }
        game.addPoint(1);

        assertEquals(State.GO_ON, game.getState());
    }

    @Test()
    public void testFlawlessVictoryTiebreakerGame() {
        final int POINTS_COUNT = 6;

        Game game = new RegularGame();
        for (int i = 0; i < POINTS_COUNT; i++) {
            game.addPoint(1);
        }
        game.addPoint(1);

        assertEquals(State.PLAYER_1_WIN, game.getState());
    }
}
