package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Comment;
import com.be001.cinevibe.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getList() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElse(null);
    }

    public void save(Comment comment) {
        if (comment != null) {
            commentRepository.save(comment);
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            commentRepository.deleteById(id);
        }
    }

    public void update(Long id, Comment updatedComment) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Comment comment = commentRepository.findById(id).orElseThrow();
        if (updatedComment.getContent() != null) {
            comment.setContent(updatedComment.getContent());
        }
        if (updatedComment.getUpdatedAt() != null) {
            comment.setUpdatedAt(LocalDateTime.now());
        }
        if (updatedComment.getUser() != null) {
            comment.setUser(updatedComment.getUser());
        }
        if (updatedComment.getReview() != null) {
            comment.setReview(updatedComment.getReview());
        }
        commentRepository.save(comment);
    }
}
