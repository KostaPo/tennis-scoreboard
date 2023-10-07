package ru.kostapo.tennisscoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PageReqDto {
    private String pageNumber;
    private String filterKey;
}