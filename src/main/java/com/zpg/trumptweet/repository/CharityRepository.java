package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.Charity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Charity entity.
 */
@SuppressWarnings("unused")
public interface CharityRepository extends JpaRepository<Charity,Long> {

}
