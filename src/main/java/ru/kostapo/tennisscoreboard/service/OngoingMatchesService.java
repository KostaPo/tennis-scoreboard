package ru.kostapo.tennisscoreboard.service;

import ru.kostapo.tennisscoreboard.dto.MatchReqDto;
import ru.kostapo.tennisscoreboard.common.exception.BadParameterException;
import ru.kostapo.tennisscoreboard.common.exception.NotFoundException;
import ru.kostapo.tennisscoreboard.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {

    private final Map<UUID, MatchReqDto> current_matches = new HashMap<>();

    public synchronized void startNewMatch(MatchReqDto newMatch) {
        try {
            if (newMatch.getFirstPlayer().getName().equals(newMatch.getSecondPlayer().getName())) {
                throw new BadParameterException("игрок не может играть сам с собой");
            }
            if (StringUtil.validatePlayerName(newMatch.getFirstPlayer().getName())
                    && StringUtil.validatePlayerName(newMatch.getSecondPlayer().getName())) {
                current_matches.put(newMatch.getUuid(), newMatch);
            }
        } catch (Exception e) {
            throw new BadParameterException(e.getMessage());
        }
    }

    public synchronized MatchReqDto getMatchByUUID(UUID uuid) {
        if (current_matches.containsKey(uuid)) {
            return current_matches.get(uuid);
        } else {
            throw new NotFoundException(String.format("id %s не найден", uuid));
        }
    }

    public synchronized void removeFromCacheByUUID(UUID uuid) {
        if (current_matches.containsKey(uuid)) {
            current_matches.remove(uuid);
        } else {
            throw new NotFoundException(String.format("id %s не найден", uuid));
        }
    }
}
