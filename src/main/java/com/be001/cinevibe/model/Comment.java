package com.be001.cinevibe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "comments")
@Builder
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Comment() {
    }

    @Override
    public String toString() {
        return "Comment{" +
               "id=" + id +
               ", content='" + content + '\'' +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               ", user=" + user +
               '}';
    }
}
