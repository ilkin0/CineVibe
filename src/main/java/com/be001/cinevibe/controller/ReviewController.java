package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.model.Review;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.service.MovieService;
import com.be001.cinevibe.service.ReviewService;
import org.springframework.http.HttpStatus;
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
    public List<Review> list() {
        return reviewService.getList();
    }

    @GetMapping("/{id}")
    public Review getById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping("/{movieId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Review review, @PathVariable Long movieId) {
        reviewService.save(review, movieId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reviewService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Review review) {
        reviewService.update(id, review);
    }

    @GetMapping("/movies/{id}")
    public List<Review> getByMovieId(@PathVariable Long id) {
        return reviewService.getReviewsByMovieId(id);
    }

    @GetMapping("/users/{id}")
    public List<Review> getByUserId(@PathVariable Long id) {
        return reviewService.getReviewsByUserId(id);
    }


}
