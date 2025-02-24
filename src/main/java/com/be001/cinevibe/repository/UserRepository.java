package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"follows"})
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"follows"})
    @NonNull
    Optional<User> findById(@NonNull Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follows WHERE follow_by = :followerId AND following = :followingId", nativeQuery = true)
    void deleteFollowRelation(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

}
