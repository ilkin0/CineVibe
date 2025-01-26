package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.Review;
import com.be001.cinevibe.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> list() {
        return ResponseEntity.ok(reviewService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.findById(id));
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<Void> save(@RequestBody Review review, @PathVariable Long movieId) {
        reviewService.save(review, movieId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Review review) {
        reviewService.update(id, review);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<List<Review>> getByMovieId(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByMovieId(id));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Review>> getByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(id));
    }
}
