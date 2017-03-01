package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.User_balances;
import com.zpg.trumptweet.domain.User_preferences;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the User_preferences entity.
 */
@SuppressWarnings("unused")
public interface User_preferencesRepository extends JpaRepository<User_preferences,Long> {

    @Query("select distinct user_preferences from User_preferences user_preferences left join fetch user_preferences.excluded_categories")
    List<User_preferences> findAllWithEagerRelationships();

    @Query("select user_preferences from User_preferences user_preferences left join fetch user_preferences.excluded_categories where user_preferences.id =:id")
    User_preferences findOneWithEagerRelationships(@Param("id") Long id);
    
    @Query("select user_preferences from User_preferences user_preferences left join fetch user_preferences.excluded_categories where user_preferences.user.login = ?#{principal.username}")
    User_preferences findByUserIsCurrentUser();

}
