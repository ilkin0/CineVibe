package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.MovieGenre;
import com.be001.cinevibe.service.MovieGenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1//genres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    public MovieGenreController(MovieGenreService movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @GetMapping
    public ResponseEntity<List<MovieGenre>> list() {
        return ResponseEntity.ok(movieGenreService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieGenre> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieGenreService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody MovieGenre movieGenre) {
        movieGenreService.save(movieGenre);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieGenreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody MovieGenre movieGenre) {
        movieGenreService.update(id, movieGenre);
        return ResponseEntity.ok().build();
    }
}
