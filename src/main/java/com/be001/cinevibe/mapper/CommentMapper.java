package com.be001.cinevibe.mapper;

import com.be001.cinevibe.dto.CommentDTO;
import com.be001.cinevibe.model.Comment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class CommentMapper {

    public Comment mapCommentDTOToComment(CommentDTO commentDTO) {
        return Comment.builder()
                .content(commentDTO.content())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public CommentDTO mapCommentToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
