package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.User_balances;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the User_balances entity.
 */
@SuppressWarnings("unused")
public interface User_balancesRepository extends JpaRepository<User_balances,Long> {

    @Query("select user_balances from User_balances user_balances where user_balances.user.login = ?#{principal.username}")
    List<User_balances> findByUserIsCurrentUser();

    @Query("select distinct user_balances from User_balances user_balances left join fetch user_balances.user_tweet_logs")
    List<User_balances> findAllWithEagerRelationships();

    @Query("select user_balances from User_balances user_balances left join fetch user_balances.user_tweet_logs where user_balances.id =:id")
    User_balances findOneWithEagerRelationships(@Param("id") Long id);

}
