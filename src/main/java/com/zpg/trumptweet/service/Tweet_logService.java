package com.zpg.trumptweet.service;

import com.zpg.trumptweet.domain.Tweet_log;
import com.zpg.trumptweet.repository.Tweet_logRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Tweet_log.
 */
@Service
@Transactional
public class Tweet_logService {

    private final Logger log = LoggerFactory.getLogger(Tweet_logService.class);
    
    private final Tweet_logRepository tweet_logRepository;

    public Tweet_logService(Tweet_logRepository tweet_logRepository) {
        this.tweet_logRepository = tweet_logRepository;
    }

    /**
     * Save a tweet_log.
     *
     * @param tweet_log the entity to save
     * @return the persisted entity
     */
    public Tweet_log save(Tweet_log tweet_log) {
        log.debug("Request to save Tweet_log : {}", tweet_log);
        Tweet_log result = tweet_logRepository.save(tweet_log);
        return result;
    }

    /**
     *  Get all the tweet_logs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tweet_log> findAll(Pageable pageable) {
        log.debug("Request to get all Tweet_logs");
        Page<Tweet_log> result = tweet_logRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tweet_log by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tweet_log findOne(Long id) {
        log.debug("Request to get Tweet_log : {}", id);
        Tweet_log tweet_log = tweet_logRepository.findOne(id);
        return tweet_log;
    }

    /**
     *  Delete the  tweet_log by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tweet_log : {}", id);
        tweet_logRepository.delete(id);
    }
}
