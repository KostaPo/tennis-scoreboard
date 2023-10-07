package ru.kostapo.tennisscoreboard.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.kostapo.tennisscoreboard.dto.PageResDto;
import ru.kostapo.tennisscoreboard.model.Match;
import ru.kostapo.tennisscoreboard.model.Player;
import ru.kostapo.tennisscoreboard.utils.HibernateUtil;

import javax.persistence.criteria.*;

public class MatchRepository implements Repository<Match> {

    protected PageResDto<Match> getPageDto(int pageSize, int pageNumber, String playerName) {
        Transaction transaction = null;
        PageResDto<Match> responsePage = new PageResDto<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Match> criteriaQuery = criteriaBuilder.createQuery(Match.class);
            Root<Match> root = criteriaQuery.from(Match.class);
            root.fetch("player1", JoinType.LEFT);
            root.fetch("player2", JoinType.LEFT);
            criteriaQuery.select(root);
            if (playerName != null) {
                responsePage.setWithFilter(true);
                responsePage.setFilterKey(playerName);
                Predicate playerNamePredicate = getPlayerNamePredicate(criteriaBuilder, root, playerName);
                criteriaQuery.where(playerNamePredicate);
            }
            Query<Match> query = createMatchQuery(session, criteriaQuery, pageNumber, pageSize);
            responsePage.setData(query.list());
            responsePage.setTotalPages(getTotalPages(pageSize, playerName));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Can't get page");
        }
        return responsePage;
    }

    private int getTotalPages(int pageSize, String playerName) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Match> root = criteriaQuery.from(Match.class);
            if (playerName != null) {
                Predicate playerNamePredicate = getPlayerNamePredicate(criteriaBuilder, root, playerName);
                criteriaQuery.where(playerNamePredicate);
            }
            criteriaQuery.select(criteriaBuilder.count(root));
            Long totalRecords = getTotalRecords(session, criteriaQuery);
            transaction.commit();
            return (int) Math.ceil(totalRecords / (double) pageSize);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("Can't get total match pages");
        }
    }

    private Predicate getPlayerNamePredicate(CriteriaBuilder criteriaBuilder, Root<Match> root, String playerName) {
        Join<Match, Player> joinPlayer1 = root.join("player1");
        Join<Match, Player> joinPlayer2 = root.join("player2");
        return criteriaBuilder.or(
                criteriaBuilder.equal(joinPlayer1.get("name"), playerName),
                criteriaBuilder.equal(joinPlayer2.get("name"), playerName));
    }

    private Long getTotalRecords(Session session, CriteriaQuery<Long> criteriaQuery) {
        Query<Long> query = session.createQuery(criteriaQuery);
        return query.uniqueResult();
    }

    private Query<Match> createMatchQuery(Session session, CriteriaQuery<Match> criteriaQuery, int pageNumber, int pageSize) {
        Query<Match> query = session.createQuery(criteriaQuery);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query;
    }
}
