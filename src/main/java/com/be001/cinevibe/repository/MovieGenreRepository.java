package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

}