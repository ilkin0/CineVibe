package com.be001.cinevibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Builder
public record CommentDTO(String content,
                         LocalDateTime createdAt) {

}
