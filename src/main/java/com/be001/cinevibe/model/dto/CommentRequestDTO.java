package com.be001.cinevibe.model.dto;

import lombok.Builder;

@Builder
public class CommentRequestDTO {

    private String content;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
