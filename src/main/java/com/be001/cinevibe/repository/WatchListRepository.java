package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchListRepository extends JpaRepository<WatchList, Long> {

    Optional<WatchList> findWatchListById(Long id);

    List<WatchList> findAllByUserId(Long userId);

    @Query(value = """
            DELETE mtw
            FROM movie_to_watchlist mtw
            LEFT JOIN watch_lists wl ON mtw.match_list_id = wl.id
            WHERE wl.user_id = :userId AND mtw.movie_id = :movieId
            """, nativeQuery = true)
    void deleteByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);
    @Query(value = """
            INSERT INTO movie_to_watchlist(watch_list_id, movie_id)
            SELECT wl.id, :movieId
            FROM watch_lists wl
            WHERE wl.user_id = :userId
            """, nativeQuery = true)
    void addMovieToUserWatchList(@Param("userId") Long userId, @Param("movieId") Long movieId);
}
