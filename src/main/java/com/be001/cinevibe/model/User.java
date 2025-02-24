package com.be001.cinevibe.model;

import com.be001.cinevibe.model.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "follow_by"),
            inverseJoinColumns = @JoinColumn(name = "following")
    )
    private Set<User> follows = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<WatchList> watchList;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    public CustomUserDetails userDetails() {
        return new CustomUserDetails(this);
    }

    @PrePersist
    private void onCreate() {
        updatedAt = createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", username='" + username + '\'' +
               ", userRole=" + userRole +
               ", createdAt=" + createdAt +
               ", updatedAt=" + updatedAt +
               ", reviews=" + reviews +
               ", comments=" + comments +
               ", watchList=" + watchList +
               ", isAccountNonExpired=" + isAccountNonExpired +
               ", isAccountNonLocked=" + isAccountNonLocked +
               ", isCredentialsNonExpired=" + isCredentialsNonExpired +
               ", isEnabled=" + isEnabled +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return isAccountNonExpired == user.isAccountNonExpired && isAccountNonLocked == user.isAccountNonLocked && isCredentialsNonExpired == user.isCredentialsNonExpired && isEnabled == user.isEnabled && Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(username, user.username) && userRole == user.userRole && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(reviews, user.reviews) && Objects.equals(comments, user.comments) && Objects.equals(watchList, user.watchList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, username, userRole, createdAt, updatedAt, reviews, comments, watchList, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled);
    }
}