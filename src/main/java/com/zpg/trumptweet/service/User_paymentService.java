package com.zpg.trumptweet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zpg.trumptweet.domain.User_payment;
import com.zpg.trumptweet.web.rest.vm.ManagedUserVM;

/**
 * Service Interface for managing User_payment.
 */
public interface User_paymentService {

    /**
     * Save a user_payment.
     *
     * @param user_payment the entity to save
     * @return the persisted entity
     */
    User_payment save(User_payment user_payment);

    /**
     *  Get all the user_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<User_payment> findAll(Pageable pageable);

    /**
     *  Get the "id" user_payment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    User_payment findOne(Long id);

    /**
     *  Delete the "id" user_payment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

	String createCustomerToken(ManagedUserVM managedUserVM);
}
