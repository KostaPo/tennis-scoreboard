package ru.kostapo.tennisscoreboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageResDto<T> {
    private List<T> data;
    private int totalPages;
    private boolean withFilter;
    private String filterKey;
}