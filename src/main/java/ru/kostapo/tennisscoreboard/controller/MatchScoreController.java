package ru.kostapo.tennisscoreboard.controller;

import ru.kostapo.tennisscoreboard.dto.MatchReqDto;
import ru.kostapo.tennisscoreboard.model.score.State;
import ru.kostapo.tennisscoreboard.service.FinishedMatchesService;
import ru.kostapo.tennisscoreboard.service.MatchScoreService;
import ru.kostapo.tennisscoreboard.service.OngoingMatchesService;
import ru.kostapo.tennisscoreboard.utils.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "MatchScoreController", value = "/match-score")
public class MatchScoreController extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;
    private MatchScoreService matchScoreService;
    private FinishedMatchesService finishedMatchesService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext()
                .getAttribute("ongoingMatchesService");
        matchScoreService = (MatchScoreService) config.getServletContext()
                .getAttribute("matchScoreCalculationService");
        finishedMatchesService = (FinishedMatchesService) config.getServletContext()
                .getAttribute("finishedMatchesPersistenceService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID uuid = StringUtil.validateAndGetUUID(request.getParameter("uuid"));
            MatchReqDto match = ongoingMatchesService.getMatchByUUID(uuid);
            request.setAttribute("match", match);
            request.getRequestDispatcher("view/match-score.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception_message", ex.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UUID uuid = StringUtil.validateAndGetUUID(request.getParameter("uuid"));
            Integer playerNumber = StringUtil.validateAndGetNumber(request.getParameter("player"));
            MatchReqDto match = ongoingMatchesService.getMatchByUUID(uuid);
            matchScoreService.addScoreToPlayer(playerNumber, match.getUuid());
            if (!match.getScore().getState().equals(State.GO_ON)) {
                finishedMatchesService.persist(match);
            }
            request.setAttribute("match", match);
            request.getRequestDispatcher("view/match-score.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception_message", ex.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }
}
