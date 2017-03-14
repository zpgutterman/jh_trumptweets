package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.Donation_log;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Donation_log entity.
 */
@SuppressWarnings("unused")
public interface Donation_logRepository extends JpaRepository<Donation_log, Long> {

	@Query("select donation_log from Donation_log donation_log where donation_log.user.login = ?#{principal.username}")
	List<Donation_log> findByUserIsCurrentUser();

	@Query("select distinct donation_log from Donation_log donation_log left join fetch donation_log.user_tweet_logs")
	List<Donation_log> findAllWithEagerRelationships();

	@Query("select donation_log from Donation_log donation_log left join fetch donation_log.user_tweet_logs where donation_log.id =:id")
	Donation_log findOneWithEagerRelationships(@Param("id") Long id);

	@Query("select donation_log from Donation_log donation_log where donation_log.user.login = ?#{principal.username} and donation_log.processed != true")
	List<Donation_log> findPendingPaymentsByCurrentUser();

	@Query("select donation_log from Donation_log donation_log where donation_log.user.login = ?#{principal.username} and donation_log.processed = true and to_char(donation_log.processed_date,'YYYY/MM') = :date")
	List<Donation_log> findTotalMonthCurrentUser(@Param("date") String date);

}
