package com.be001.cinevibe.repository.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MovieDTOMock {

    private String title;
    private Integer releaseYear;
    private String posterImage;
    private Double averageRating;

}
