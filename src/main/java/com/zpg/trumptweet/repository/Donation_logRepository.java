package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.Donation_log;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Donation_log entity.
 */
@SuppressWarnings("unused")
public interface Donation_logRepository extends JpaRepository<Donation_log,Long> {

    @Query("select donation_log from Donation_log donation_log where donation_log.user.login = ?#{principal.username}")
    List<Donation_log> findByUserIsCurrentUser();

}
