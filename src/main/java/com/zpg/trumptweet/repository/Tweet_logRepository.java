package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.Tweet_log;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tweet_log entity.
 */
@SuppressWarnings("unused")
public interface Tweet_logRepository extends JpaRepository<Tweet_log,Long> {

}
