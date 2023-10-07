package ru.kostapo.tennisscoreboard.service;

import ru.kostapo.tennisscoreboard.model.score.State;
import ru.kostapo.tennisscoreboard.model.score.Score;

import java.util.UUID;

public class MatchScoreService {
    private final OngoingMatchesService ongoingMatchesService;

    public MatchScoreService(OngoingMatchesService ongoingMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
    }

    public synchronized State addScoreToPlayer(Integer playerIndex, UUID matchUUID) {
        Score currentScore = getMatchScore(matchUUID);
        currentScore.addPoint(playerIndex);
        return currentScore.getState();
    }

    private synchronized Score getMatchScore(UUID matchUUID) {
        return ongoingMatchesService.getMatchByUUID(matchUUID).getScore();
    }
}
