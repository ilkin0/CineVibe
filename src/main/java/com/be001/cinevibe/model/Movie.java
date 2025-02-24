package com.be001.cinevibe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private Integer releaseYear;

    private String synopsis;

    @ManyToMany
    @JoinTable(
            name = "movie_genre_mapping",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<MovieGenre> genres = new HashSet<>();

    private String director;
    @ManyToMany
    @JoinTable(
            name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cast_id")
    )

    private Set<Cast> cast = new HashSet<>();

    private String posterImage;

    private Double averageRating;

    private Integer reviewCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    @ManyToMany(mappedBy = "movies")
    private Set<WatchList> watchLists = new HashSet<>();

    @Override
    public String toString() {
        return "Movie{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", releaseYear=" + releaseYear +
               ", synopsis='" + synopsis + '\'' +
               ", director='" + director + '\'' +
               ", cast=" + cast +
               ", posterImage='" + posterImage + '\'' +
               ", averageRating=" + averageRating +
               ", reviewCount=" + reviewCount +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               '}';
    }
}
