package com.be001.cinevibe.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "watch_lists")
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
