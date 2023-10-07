package ru.kostapo.tennisscoreboard.dao;

import ru.kostapo.tennisscoreboard.model.Player;

import java.util.Optional;

public class PlayerDAO extends PlayerRepository {

    public Player getPlayerByName(String name) {
        Optional<Player> p = findByName(name);
        if (p.isPresent()) {
            return p.get();
        }
        Player player = new Player();
        player.setName(name);
        return save(player);
    }
}
