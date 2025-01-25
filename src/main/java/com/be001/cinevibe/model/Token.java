package com.be001.cinevibe.model;

import jakarta.persistence.*;

import java.time.Instant;

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

    public Token() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean revoked) {
        isRevoked = revoked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", issuedAt=" + issuedAt +
                ", expiredAt=" + expiredAt +
                ", isExpired=" + isExpired +
                ", isRevoked=" + isRevoked +
                ", user=" + user +
                '}';
    }
}