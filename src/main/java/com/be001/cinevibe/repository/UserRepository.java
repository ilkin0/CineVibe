package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findById(Long userId);

    Optional<User> findByUsername(String username);
}
