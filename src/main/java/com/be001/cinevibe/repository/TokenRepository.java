package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
