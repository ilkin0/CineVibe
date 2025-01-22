package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Cast,Long> {
}
