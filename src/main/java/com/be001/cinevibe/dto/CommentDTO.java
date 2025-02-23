package com.be001.cinevibe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public record CommentDTO(String content,
                         LocalDateTime createdAt) {

}
