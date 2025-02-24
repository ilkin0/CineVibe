package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.service.MovieService;
import com.be001.cinevibe.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1//movies")
public class MovieController {
    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> list() {
        return ResponseEntity.ok(movieService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Movie movie) {
        movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Movie movie) {
        movieService.update(id, movie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/averageRating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        reviewService.updateRating(movie);
        return ResponseEntity.ok(movie.getAverageRating());
    }

    @GetMapping("/{id}/maxRating")
    public ResponseEntity<Integer> getMaxRating(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findMaxRating(id));
    }

    @GetMapping("/{id}/minRating")
    public ResponseEntity<Integer> getMinRating(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findMinRating(id));
    }
}
