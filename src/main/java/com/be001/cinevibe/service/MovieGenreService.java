package com.be001.cinevibe.service;

import com.be001.cinevibe.model.MovieGenre;
import com.be001.cinevibe.repository.MovieGenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;

    public MovieGenreService(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }
    public List<MovieGenre> getList() {
        return movieGenreRepository.findAll();
    }
    public MovieGenre findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        Optional<MovieGenre> movieGenre = movieGenreRepository.findById(id);
        return movieGenre.orElse(null);
    }
    public void save(MovieGenre movieGenre) {
        if (movieGenre!=null) {
            movieGenreRepository.save(movieGenre);
        }
    }
    public void deleteById(Long id) {
        if (id != null) {
            movieGenreRepository.deleteById(id);
        }
    }
    public void update(Long id, MovieGenre updatedMovieGenre) {
        if (id==null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<MovieGenre> movieGenreOptional = movieGenreRepository.findById(id);
        if (movieGenreOptional.isEmpty()) {
            throw new NoSuchElementException("Movie Genre is not found");
        }
        MovieGenre movieGenre=movieGenreOptional.get();
        movieGenre.setName(updatedMovieGenre.getName());
        movieGenre.setDescription(updatedMovieGenre.getDescription());
        movieGenreRepository.save(movieGenre);
    }
}
