package com.zpg.trumptweet.service;

import com.zpg.trumptweet.domain.User_balances;
import com.zpg.trumptweet.repository.User_balancesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing User_balances.
 */
@Service
@Transactional
public class User_balancesService {

    private final Logger log = LoggerFactory.getLogger(User_balancesService.class);
    
    private final User_balancesRepository user_balancesRepository;

    public User_balancesService(User_balancesRepository user_balancesRepository) {
        this.user_balancesRepository = user_balancesRepository;
    }

    /**
     * Save a user_balances.
     *
     * @param user_balances the entity to save
     * @return the persisted entity
     */
    public User_balances save(User_balances user_balances) {
        log.debug("Request to save User_balances : {}", user_balances);
        User_balances result = user_balancesRepository.save(user_balances);
        return result;
    }

    /**
     *  Get all the user_balances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<User_balances> findAll(Pageable pageable) {
        log.debug("Request to get all User_balances");
        Page<User_balances> result = user_balancesRepository.findAll(pageable);
        return result;
    }
    
    /**
     *  Get all the user_balances by user ID.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<User_balances> findByCurrentUser() {
    	List<User_balances> result = user_balancesRepository.findByUserIsCurrentUser();
    	log.debug("****USER BALANCES" + result.get(0).toString());
        return result;
    }

    /**
     *  Get one user_balances by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public User_balances findOne(Long id) {
        log.debug("Request to get User_balances : {}", id);
        User_balances user_balances = user_balancesRepository.findOne(id);
        return user_balances;
    }

    /**
     *  Delete the  user_balances by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete User_balances : {}", id);
        user_balancesRepository.delete(id);
    }
}
