package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.CommentDTO;
import com.be001.cinevibe.dto.CommentRequestDTO;
import com.be001.cinevibe.exception.CommentNotFoundException;
import com.be001.cinevibe.model.Comment;
import com.be001.cinevibe.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public static final String COMMENT_ERROR = "Comment not found";

    public List<CommentDTO> getAll() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream().map(this::mapCommentToCommentDTO).toList();
    }

    public CommentDTO getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_ERROR));
        return mapCommentToCommentDTO(comment);
    }

    public CommentDTO create(String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return mapCommentToCommentDTO(comment);
    }

    public CommentDTO update(Long id, CommentRequestDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_ERROR));
        comment.setContent(commentDTO.content());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return mapCommentToCommentDTO(comment);
    }

    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(COMMENT_ERROR));
        commentRepository.deleteById(comment.getId());

    }

    private CommentDTO mapCommentToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
