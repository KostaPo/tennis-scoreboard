package ru.kostapo.tennisscoreboard.service;

import ru.kostapo.tennisscoreboard.dao.MatchDAO;
import ru.kostapo.tennisscoreboard.dto.MatchReqDto;

public class FinishedMatchesService {

    private final MatchDAO matchDAO;
    private final OngoingMatchesService ongoingMatchesService;

    public FinishedMatchesService(OngoingMatchesService ongoingMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.matchDAO = new MatchDAO();
    }

    public synchronized void persist(MatchReqDto matchDto) {
        matchDAO.saveMatch(matchDto);
        ongoingMatchesService.removeFromCacheByUUID(matchDto.getUuid());
    }
}
