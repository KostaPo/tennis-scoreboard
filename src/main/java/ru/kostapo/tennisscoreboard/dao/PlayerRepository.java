package ru.kostapo.tennisscoreboard.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.kostapo.tennisscoreboard.model.Player;
import ru.kostapo.tennisscoreboard.utils.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class PlayerRepository implements Repository<Player> {

    protected Optional<Player> findByName(String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Player> query = builder.createQuery(Player.class);
            Root<Player> root = query.from(Player.class);
            query.select(root).where(builder.equal(root.get("name"), name));
            List<Player> players = session.createQuery(query).getResultList();
            transaction.commit();
            return players.isEmpty()
                    ? Optional.empty()
                    : Optional.of(players.get(0));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException(String.format("can't get by name: %s", name));
        }
    }
}
