package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String value);

    @Transactional
    void deleteByUserId(Long userId);
}
