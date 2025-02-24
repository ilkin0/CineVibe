package com.be001.cinevibe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class WatchListDTO {
    private String title;
    private List<MovieDTO> movieList;
    private LocalDateTime addedAt;

    public WatchListDTO() {
    }

    public WatchListDTO(List<MovieDTO> movieList, LocalDateTime addedAt, String title) {
        this.movieList = movieList;
        this.addedAt = addedAt;
        this.title = title;
    }
}
