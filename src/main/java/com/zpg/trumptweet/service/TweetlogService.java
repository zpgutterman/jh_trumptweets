package com.zpg.trumptweet.service;

import com.zpg.trumptweet.domain.Tweetlog;
import com.zpg.trumptweet.repository.TweetlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Tweetlog.
 */
@Service
@Transactional
public class TweetlogService {

    private final Logger log = LoggerFactory.getLogger(TweetlogService.class);
    
    private final TweetlogRepository tweetlogRepository;

    public TweetlogService(TweetlogRepository tweetlogRepository) {
        this.tweetlogRepository = tweetlogRepository;
    }

    /**
     * Save a tweetlog.
     *
     * @param tweetlog the entity to save
     * @return the persisted entity
     */
    public Tweetlog save(Tweetlog tweetlog) {
        log.debug("Request to save Tweetlog : {}", tweetlog);
        Tweetlog result = tweetlogRepository.save(tweetlog);
        return result;
    }

    /**
     *  Get all the tweetlogs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tweetlog> findAll(Pageable pageable) {
        log.debug("Request to get all Tweetlogs");
        Page<Tweetlog> result = tweetlogRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tweetlog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Tweetlog findOne(Long id) {
        log.debug("Request to get Tweetlog : {}", id);
        Tweetlog tweetlog = tweetlogRepository.findOneWithEagerRelationships(id);
        return tweetlog;
    }

    /**
     *  Delete the  tweetlog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tweetlog : {}", id);
        tweetlogRepository.delete(id);
    }
}
