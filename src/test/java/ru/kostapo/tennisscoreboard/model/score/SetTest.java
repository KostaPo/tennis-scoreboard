package ru.kostapo.tennisscoreboard.model.score;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetTest {

    @Test()
    public void testFlawlessVictorySet() {
        final int GAMES_COUNT = 6;
        final int POINTS_IN_GAME_COUNT = 4;

        Set set = new Set();
        for (int i = 0; i < GAMES_COUNT; i++) {
            for (int j = 0; j < POINTS_IN_GAME_COUNT; j++) {
                set.scored(1);
            }
        }

        assertEquals(State.PLAYER_1_WIN, set.getState());
    }

    @Test()
    public void testTiebreakerSet() {
        final int GAMES_COUNT = 6;
        final int POINTS_IN_GAME_COUNT = 4;

        Set set = new Set();
        for (int i = 1; i <= GAMES_COUNT * 2; i++) {
            for (int j = 0; j < POINTS_IN_GAME_COUNT; j++) {
                if (i % 2 == 0) {
                    set.scored(1);
                } else {
                    set.scored(2);
                }
            }
        }
        Game game = set.getCurrentGame();

        assertTrue(game instanceof TiebreakerGame);
    }
}
