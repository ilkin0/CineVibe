package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"follows"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"follows"})
    @NonNull
    Optional<User> findById(@NonNull Long id);

}
