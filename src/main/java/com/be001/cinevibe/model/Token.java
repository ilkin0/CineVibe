package com.be001.cinevibe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String value;

    @Column
    private Instant issuedAt;

    @Column
    private Instant expiredAt;

    @Column
    private boolean isExpired;

    @Column
    private boolean isRevoked;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User user;

    public Token(String value,
                 Instant issuedAt,
                 Instant expiredAt,
                 boolean isExpired,
                 boolean isRevoked,
                 User user) {
        this.value = value;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.isExpired = isExpired;
        this.isRevoked = isRevoked;
        this.user = user;
    }
}