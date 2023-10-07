package ru.kostapo.tennisscoreboard.dto;

import lombok.Getter;
import lombok.Setter;
import ru.kostapo.tennisscoreboard.model.score.Score;

import java.util.UUID;

@Setter
@Getter
public class MatchReqDto {
    PlayerReqDto firstPlayer;
    PlayerReqDto secondPlayer;
    UUID uuid = UUID.randomUUID();
    Score score = new Score();
}
