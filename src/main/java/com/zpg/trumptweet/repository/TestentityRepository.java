package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.Testentity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Testentity entity.
 */
@SuppressWarnings("unused")
public interface TestentityRepository extends JpaRepository<Testentity,Long> {

}
