package com.zpg.trumptweet.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zpg.trumptweet.domain.Tweetlog;

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
    
    @Query("select tweetlog from Tweetlog tweetlog where tweetlog.categorize_user != null")
    Page<Tweetlog> findByCategorize_userNotNull(Pageable pageable);

}
