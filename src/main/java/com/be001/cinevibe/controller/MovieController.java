package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping
    public List<Movie> list() {
        return movieService.getList();
    }
    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return movieService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Movie movie) {
        movieService.save(movie);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        movieService.deleteById(id);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Movie movie) {
        movieService.update(id, movie);
    }
}
