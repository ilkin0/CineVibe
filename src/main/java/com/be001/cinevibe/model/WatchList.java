package com.be001.cinevibe.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "watch_lists")
@AllArgsConstructor
@Builder
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "movie_to_watchlist",
            joinColumns = @JoinColumn(name = "watch_list_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "movie_id", nullable = false)
    )
    private Set<Movie> movies = new HashSet<>();
    private LocalDateTime addedAt;


    public WatchList(User user, Set<Movie> movies) {
        this.user = user;
        this.movies = movies;
        this.addedAt = LocalDateTime.now();
    }

    public WatchList() {
    }

}
