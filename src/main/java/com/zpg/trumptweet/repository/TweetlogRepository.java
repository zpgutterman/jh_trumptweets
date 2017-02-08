package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.Tweetlog;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tweetlog entity.
 */
@SuppressWarnings("unused")
public interface TweetlogRepository extends JpaRepository<Tweetlog,Long> {

    @Query("select tweetlog from Tweetlog tweetlog where tweetlog.categorize_user.login = ?#{principal.username}")
    List<Tweetlog> findByCategorize_userIsCurrentUser();

    @Query("select distinct tweetlog from Tweetlog tweetlog left join fetch tweetlog.categories")
    List<Tweetlog> findAllWithEagerRelationships();

    @Query("select tweetlog from Tweetlog tweetlog left join fetch tweetlog.categories where tweetlog.id =:id")
    Tweetlog findOneWithEagerRelationships(@Param("id") Long id);

}
