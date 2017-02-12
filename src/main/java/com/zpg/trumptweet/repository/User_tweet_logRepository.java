package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.User_tweet_log;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the User_tweet_log entity.
 */
@SuppressWarnings("unused")
public interface User_tweet_logRepository extends JpaRepository<User_tweet_log,Long> {

    @Query("select user_tweet_log from User_tweet_log user_tweet_log where user_tweet_log.user.login = ?#{principal.username}")
    List<User_tweet_log> findByUserIsCurrentUser();

}
