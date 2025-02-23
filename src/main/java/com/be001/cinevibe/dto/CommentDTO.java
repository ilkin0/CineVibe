package com.be001.cinevibe.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDTO(String content,
                         LocalDateTime createdAt) {

}
