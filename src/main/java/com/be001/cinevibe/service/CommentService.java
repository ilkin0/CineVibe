package com.be001.cinevibe.service;

import com.be001.cinevibe.mapper.CommentMapper;
import com.be001.cinevibe.model.Comment;
import com.be001.cinevibe.dto.CommentDTO;
import com.be001.cinevibe.exception.CommentNotFoundException;
import com.be001.cinevibe.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentDTO> getAll() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream().map(commentMapper::mapCommentToCommentDTO).toList();
    }

    public CommentDTO getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return commentMapper.mapCommentToCommentDTO(comment);
    }

    public CommentDTO create(String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return commentMapper.mapCommentToCommentDTO(comment);
    }



}
