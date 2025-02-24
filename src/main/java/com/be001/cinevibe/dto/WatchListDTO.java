package com.be001.cinevibe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<MovieDTO> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieDTO> movieList) {
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
