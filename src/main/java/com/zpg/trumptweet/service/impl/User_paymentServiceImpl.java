package com.zpg.trumptweet.service.impl;

import com.zpg.trumptweet.service.User_paymentService;
import com.zpg.trumptweet.domain.User_payment;
import com.zpg.trumptweet.repository.User_paymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing User_payment.
 */
@Service
@Transactional
public class User_paymentServiceImpl implements User_paymentService{

    private final Logger log = LoggerFactory.getLogger(User_paymentServiceImpl.class);
    
    private final User_paymentRepository user_paymentRepository;

    public User_paymentServiceImpl(User_paymentRepository user_paymentRepository) {
        this.user_paymentRepository = user_paymentRepository;
    }

    /**
     * Save a user_payment.
     *
     * @param user_payment the entity to save
     * @return the persisted entity
     */
    @Override
    public User_payment save(User_payment user_payment) {
        log.debug("Request to save User_payment : {}", user_payment);
        User_payment result = user_paymentRepository.save(user_payment);
        return result;
    }

    /**
     *  Get all the user_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User_payment> findAll(Pageable pageable) {
        log.debug("Request to get all User_payments");
        Page<User_payment> result = user_paymentRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one user_payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public User_payment findOne(Long id) {
        log.debug("Request to get User_payment : {}", id);
        User_payment user_payment = user_paymentRepository.findOne(id);
        return user_payment;
    }

    /**
     *  Delete the  user_payment by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete User_payment : {}", id);
        user_paymentRepository.delete(id);
    }
}
