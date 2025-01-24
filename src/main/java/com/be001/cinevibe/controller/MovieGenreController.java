package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.MovieGenre;
import com.be001.cinevibe.service.MovieGenreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/genres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    public MovieGenreController(MovieGenreService movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @GetMapping
    public List<MovieGenre> list() {
        return movieGenreService.getList();
    }

    @GetMapping("/{id}")
    public MovieGenre getById(@PathVariable Long id) {
        return movieGenreService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody MovieGenre movieGenre) {
        movieGenreService.save(movieGenre);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        movieGenreService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody MovieGenre movieGenre) {
        movieGenreService.update(id, movieGenre);
    }
}
