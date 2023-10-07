package ru.kostapo.tennisscoreboard.dao;

import ru.kostapo.tennisscoreboard.dto.MatchReqDto;
import ru.kostapo.tennisscoreboard.dto.PageReqDto;
import ru.kostapo.tennisscoreboard.dto.PageResDto;
import ru.kostapo.tennisscoreboard.model.Match;
import ru.kostapo.tennisscoreboard.model.Player;
import ru.kostapo.tennisscoreboard.model.score.State;
import ru.kostapo.tennisscoreboard.utils.PropertiesUtil;
import ru.kostapo.tennisscoreboard.utils.StringUtil;

public class MatchDAO extends MatchRepository {

    private final int PAGE_SIZE;
    private final PlayerDAO playerDAO;

    public MatchDAO() {
        this.playerDAO = new PlayerDAO();
        this.PAGE_SIZE = Integer.parseInt(PropertiesUtil.getProperty("default.pageSize"));
    }

    public void saveMatch(MatchReqDto matchDto) {
        Player firstPlayer = playerDAO.getPlayerByName(matchDto.getFirstPlayer().getName());
        Player secondPlayer = playerDAO.getPlayerByName(matchDto.getSecondPlayer().getName());
        Player winner = matchDto.getScore().getState().equals(State.PLAYER_1_WIN)
                ? firstPlayer
                : secondPlayer;
        Match match = new Match();
        match.setPlayer1(firstPlayer);
        match.setPlayer2(secondPlayer);
        match.setWinner(winner);
        save(match);
    }

    public PageResDto<Match> getPage(PageReqDto pageRequest) {
        int pageNumber = pageRequest.getPageNumber() == null
                ? 1
                : StringUtil.validateAndGetNumber(pageRequest.getPageNumber());
        String playerName = null;
        if (pageRequest.getFilterKey() != null) {
            if (StringUtil.validatePlayerName(pageRequest.getFilterKey())) {
                playerName = pageRequest.getFilterKey();
            }
        }
        return getPageDto(PAGE_SIZE, pageNumber, playerName);
    }
}
