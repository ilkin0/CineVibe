package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getList() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }

    public void save(Movie movie) {
        if (movie != null) {
            movieRepository.save(movie);
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            movieRepository.deleteById(id);
        }
    }

    public void update(Long id, Movie updatedMovie) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Movie movie = movieRepository.findById(id).orElseThrow();
        if (updatedMovie.getTitle() != null) {
            movie.setTitle(updatedMovie.getTitle());
        }
        if (updatedMovie.getReleaseYear() != null) {
            movie.setReleaseYear(updatedMovie.getReleaseYear());
        }
        if (updatedMovie.getSynopsis() != null) {
            movie.setSynopsis(updatedMovie.getSynopsis());
        }
        if (updatedMovie.getDirector() != null) {
            movie.setDirector(updatedMovie.getDirector());
        }
        if (updatedMovie.getCast() != null) {
            movie.setCast(updatedMovie.getCast());
        }
        if (updatedMovie.getPosterImage() != null) {
            movie.setPosterImage(updatedMovie.getPosterImage());
        }
        if (updatedMovie.getAverageRating() != null) {
            movie.setAverageRating(updatedMovie.getAverageRating());
        }
        if (updatedMovie.getReviewCount() != null) {
            movie.setReviewCount(updatedMovie.getReviewCount());
        }
        if (updatedMovie.getUpdatedAt() != null) {
            movie.setUpdatedAt(LocalDateTime.now());
        }

        movieRepository.save(movie);
    }
}
