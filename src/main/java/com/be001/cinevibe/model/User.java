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
    private Set<User> follows;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;

    public User(String email, String password, String username, UserRole userRole, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isEnabled) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isEnabled = isEnabled;
    }

    public User(String email, String password, String username, UserRole userRole, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userRole = userRole;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
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