package com.be001.cinevibe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    private String content;

    private boolean containsSpoilers;

    private Integer helpfulCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "review")
    @ToString.Exclude
    private List<Comment> comment;

    public Review(Long id, Integer rating, String content, boolean containsSpoilers, Integer helpfulCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.content = content;
        this.containsSpoilers = containsSpoilers;
        this.helpfulCount = helpfulCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Review() {
    }
}
