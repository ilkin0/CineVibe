package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
