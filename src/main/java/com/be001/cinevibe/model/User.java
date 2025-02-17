package com.be001.cinevibe.model;

import com.be001.cinevibe.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    private String urlProfile;

    private UserRole userRole;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Boolean enabled;

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

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    public User(String email, String password, String username, UserRole userRole, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean enabled, String urlProfile) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.enabled = enabled;
        this.urlProfile = urlProfile;
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }


    public CustomUserDetails userDetails() {
        return new CustomUserDetails(this);
    }

}
