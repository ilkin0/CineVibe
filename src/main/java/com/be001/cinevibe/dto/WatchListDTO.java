package com.be001.cinevibe.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class WatchListDTO {
    private String title;
    private List<MovieDTOMock> movieList;
    private LocalDateTime addedAt;

    public WatchListDTO() {
    }

    public WatchListDTO(List<MovieDTOMock> movieList, LocalDateTime addedAt, String title) {
        this.movieList = movieList;
        this.addedAt = addedAt;
        this.title = title;
    }

    public List<MovieDTOMock> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieDTOMock> movieList) {
        this.movieList = movieList;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
