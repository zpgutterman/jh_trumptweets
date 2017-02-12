package com.zpg.trumptweet.service;

import com.zpg.trumptweet.domain.User_tweet_log;
import com.zpg.trumptweet.repository.User_tweet_logRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing User_tweet_log.
 */
@Service
@Transactional
public class User_tweet_logService {

    private final Logger log = LoggerFactory.getLogger(User_tweet_logService.class);
    
    private final User_tweet_logRepository user_tweet_logRepository;

    public User_tweet_logService(User_tweet_logRepository user_tweet_logRepository) {
        this.user_tweet_logRepository = user_tweet_logRepository;
    }

    /**
     * Save a user_tweet_log.
     *
     * @param user_tweet_log the entity to save
     * @return the persisted entity
     */
    public User_tweet_log save(User_tweet_log user_tweet_log) {
        log.debug("Request to save User_tweet_log : {}", user_tweet_log);
        User_tweet_log result = user_tweet_logRepository.save(user_tweet_log);
        return result;
    }

    /**
     *  Get all the user_tweet_logs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<User_tweet_log> findAll(Pageable pageable) {
        log.debug("Request to get all User_tweet_logs");
        Page<User_tweet_log> result = user_tweet_logRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one user_tweet_log by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public User_tweet_log findOne(Long id) {
        log.debug("Request to get User_tweet_log : {}", id);
        User_tweet_log user_tweet_log = user_tweet_logRepository.findOne(id);
        return user_tweet_log;
    }

    /**
     *  Delete the  user_tweet_log by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete User_tweet_log : {}", id);
        user_tweet_logRepository.delete(id);
    }
}
