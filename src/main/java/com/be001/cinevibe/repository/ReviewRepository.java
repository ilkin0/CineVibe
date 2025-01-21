package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
