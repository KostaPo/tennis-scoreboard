package ru.kostapo.tennisscoreboard.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MatchResDto {
    String id;
    PlayerResDto firstPlayer;
    PlayerResDto secondPlayer;
    PlayerResDto winner;
}
