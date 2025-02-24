package com.be001.cinevibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MovieDTO {

    private String title;
    private Integer releaseYear;
    private String posterImage;
    private Double averageRating;

}
