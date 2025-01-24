package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Review;
import com.be001.cinevibe.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    public List<Review> getList() {
        return reviewRepository.findAll();
    }
    public Review findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        Optional<Review> review = reviewRepository.findById(id);
        return review.orElse(null);
    }
    public void save(Review review) {
        if (review!=null) {
            reviewRepository.save(review);
        }
    }
    public void deleteById(Long id) {
        if (id != null) {
            reviewRepository.deleteById(id);
        }
    }
    public void update(Long id,Review updatedReview) {
        if (id==null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Optional<Review> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isEmpty()) {
            throw new NoSuchElementException("Review is not found");
        }
        Review review=reviewOptional.get();
        review.setRating(updatedReview.getRating());
        review.setContent(updatedReview.getContent());
        review.setContainsSpoilers(updatedReview.isContainsSpoilers());
        review.setHelpfulCount(updatedReview.getHelpfulCount());
        review.setCreatedAt(updatedReview.getCreatedAt());
        review.setUpdatedAt(updatedReview.getUpdatedAt());
        reviewRepository.save(review);
    }
}
