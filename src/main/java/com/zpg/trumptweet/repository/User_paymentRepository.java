package com.zpg.trumptweet.repository;

import com.zpg.trumptweet.domain.User_payment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the User_payment entity.
 */
@SuppressWarnings("unused")
public interface User_paymentRepository extends JpaRepository<User_payment,Long> {

    @Query("select user_payment from User_payment user_payment where user_payment.user.login = ?#{principal.username}")
    List<User_payment> findByUserIsCurrentUser();

}
