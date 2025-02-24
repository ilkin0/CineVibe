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
            SELECT CASE WHEN COUNT(mtw) > 0 THEN TRUE ELSE FALSE END
            FROM movie_to_watchlist mtw
            LEFT JOIN watch_lists wl ON mtw.watch_list_id = wl.id
            WHERE wl.id = :watchListId AND wl.user.id = :userId AND mtw.movie.id = :movieId
            """, nativeQuery = true)
    boolean existsByUserWatchListAndMovie(@Param("watchListId") Long watchListId,
                                          @Param("userId") Long userId,
                                          @Param("movieId") Long movieId);


    @Query(value = """
            DELETE mtw
            FROM movie_to_watchlist mtw
            LEFT JOIN watch_lists wl ON mtw.watch_list_id = wl.id
            WHERE wl.user_id = :userId AND mtw.watch_list_id = :watchListId AND mtw.movie_id = :movieId
            """, nativeQuery = true)
    void deleteByUserIdAndMovieId(@Param("userId") Long userId,
                                  @Param("watchListId") Long watchListId,
                                  @Param("movieId") Long movieId);

    @Query(value = """
            INSERT INTO movie_to_watchlist(watch_list_id, movie_id)
            SELECT wl.id, :movieId
            FROM watch_lists wl
            WHERE wl.user_id = :userId AND wl.id = watchListId
            """, nativeQuery = true)
    void addMovieToUserWatchList(@Param("userId") Long userId, @Param("watchListId") Long watchListId, @Param("movieId") Long movieId);
}
