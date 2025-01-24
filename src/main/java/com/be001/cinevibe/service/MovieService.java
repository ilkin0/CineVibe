package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
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
        if (movie!=null) {
            movieRepository.save(movie);
        }
    }
    public void deleteById(Long id) {
        if (id != null) {
            movieRepository.deleteById(id);
        }
    }
    public void update(Long id,Movie updatedMovie) {
        if (id==null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new NoSuchElementException("Movie is not found");
        }
        Movie movie=movieOptional.get();
        movie.setTitle(updatedMovie.getTitle());
        movie.setReleaseYear(updatedMovie.getReleaseYear());
        movie.setSynopsis(updatedMovie.getSynopsis());
        movie.setDirector(updatedMovie.getDirector());
        movie.setCast(updatedMovie.getCast());
        movie.setPosterImage(updatedMovie.getPosterImage());
        movie.setAverageRating(updatedMovie.getAverageRating());
        movie.setReviewCount(updatedMovie.getReviewCount());
        movie.setCreatedAt(updatedMovie.getCreatedAt());
        movie.setUpdatedAt(updatedMovie.getUpdatedAt());
        movieRepository.save(movie);
    }
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
