package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Comment;
import com.be001.cinevibe.model.dto.CommentDTO;
import com.be001.cinevibe.model.dto.CommentNotFoundException;
import com.be001.cinevibe.model.dto.CommentRequestDTO;
import com.be001.cinevibe.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<CommentDTO> getAll() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream().map(this::mapCommentToCommentDTO).toList();
    }

    public CommentDTO getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
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
        Comment comment=commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        comment.setContent(commentDTO.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return mapCommentToCommentDTO(comment);
    }

    public void delete(Long id) {
        Comment comment=commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        commentRepository.deleteById(comment.getId());

    }


    private Comment mapCommentDTOToComment(CommentDTO commentDTO) {
        return Comment.builder()
                .content(commentDTO.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private CommentDTO mapCommentToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}
