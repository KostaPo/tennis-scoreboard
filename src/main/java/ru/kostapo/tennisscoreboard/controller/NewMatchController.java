package ru.kostapo.tennisscoreboard.controller;

import ru.kostapo.tennisscoreboard.dto.MatchReqDto;
import ru.kostapo.tennisscoreboard.dto.PlayerReqDto;
import ru.kostapo.tennisscoreboard.service.OngoingMatchesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewMatchController", value = "/new-match")
public class NewMatchController extends HttpServlet {

    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext()
                .getAttribute("ongoingMatchesService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("view/new-match.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            MatchReqDto newMatch = new MatchReqDto();
            newMatch.setFirstPlayer(new PlayerReqDto(request.getParameter("player1")));
            newMatch.setSecondPlayer(new PlayerReqDto(request.getParameter("player2")));
            ongoingMatchesService.startNewMatch(newMatch);
            String redirectUrl = String.format("/match-score?uuid=%s", newMatch.getUuid());
            response.sendRedirect(redirectUrl);
        } catch (Exception ex) {
            request.setAttribute("exception_message", ex.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }
}
