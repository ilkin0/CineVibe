package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.model.Review;
import com.be001.cinevibe.repository.MovieRepository;
import com.be001.cinevibe.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    public List<Review> getList() {
        return reviewRepository.findAll();
    }

    public Review findById(Long id) {
        checkNull(id);
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }

    public void save(Review review, Long movieId) {
        checkNull(movieId);
        if (review != null) {
            Movie movie = movieRepository.findById(movieId).orElseThrow();
            review.setMovie(movie);
            reviewRepository.save(review);
            updateRating(movie);
        }
    }

    public void updateRating(Movie movie) {
        List<Review> reviews = reviewRepository.findByMovieId(movie.getId());
        double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElseThrow();
        movie.setAverageRating(averageRating);
        movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        checkNull(id);
        reviewRepository.deleteById(id);
    }

    public void update(Long id, Review updatedReview) {
        checkNull(id);
        Review review = reviewRepository.findById(id).orElseThrow();
        if (updatedReview.getRating() != null) {
            review.setRating(updatedReview.getRating());
        }
        if (updatedReview.getContent() != null) {
            review.setContent(updatedReview.getContent());
        }
        review.setContainsSpoilers(updatedReview.isContainsSpoilers());
        if (updatedReview.getHelpfulCount() != null) {
            review.setHelpfulCount(updatedReview.getHelpfulCount());
        }
        if (updatedReview.getUpdatedAt() != null) {
            review.setUpdatedAt(LocalDateTime.now());
        }
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByMovieId(Long movieId) {
        checkNull(movieId);
        return reviewRepository.findByMovieId(movieId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        checkNull(userId);
        return reviewRepository.findByUserId(userId);
    }

    public Integer findMaxRating(Long movieId) {
        checkNull(movieId);
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream().mapToInt(Review::getRating).max().orElseThrow();
    }
    public Integer findMinRating(Long movieId) {
        List<Review> reviews = reviewRepository.findByMovieId(movieId);
        return reviews.stream().mapToInt(Review::getRating).min().orElseThrow();
    }
    public void checkNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
    }
}
