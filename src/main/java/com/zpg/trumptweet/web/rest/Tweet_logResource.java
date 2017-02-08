package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.Tweet_log;
import com.zpg.trumptweet.service.Tweet_logService;
import com.zpg.trumptweet.web.rest.util.HeaderUtil;
import com.zpg.trumptweet.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tweet_log.
 */
@RestController
@RequestMapping("/api")
public class Tweet_logResource {

    private final Logger log = LoggerFactory.getLogger(Tweet_logResource.class);

    private static final String ENTITY_NAME = "tweet_log";
        
    private final Tweet_logService tweet_logService;

    public Tweet_logResource(Tweet_logService tweet_logService) {
        this.tweet_logService = tweet_logService;
    }

    /**
     * POST  /tweet-logs : Create a new tweet_log.
     *
     * @param tweet_log the tweet_log to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tweet_log, or with status 400 (Bad Request) if the tweet_log has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tweet-logs")
    @Timed
    public ResponseEntity<Tweet_log> createTweet_log(@Valid @RequestBody Tweet_log tweet_log) throws URISyntaxException {
        log.debug("REST request to save Tweet_log : {}", tweet_log);
        if (tweet_log.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tweet_log cannot already have an ID")).body(null);
        }
        Tweet_log result = tweet_logService.save(tweet_log);
        return ResponseEntity.created(new URI("/api/tweet-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tweet-logs : Updates an existing tweet_log.
     *
     * @param tweet_log the tweet_log to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tweet_log,
     * or with status 400 (Bad Request) if the tweet_log is not valid,
     * or with status 500 (Internal Server Error) if the tweet_log couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tweet-logs")
    @Timed
    public ResponseEntity<Tweet_log> updateTweet_log(@Valid @RequestBody Tweet_log tweet_log) throws URISyntaxException {
        log.debug("REST request to update Tweet_log : {}", tweet_log);
        if (tweet_log.getId() == null) {
            return createTweet_log(tweet_log);
        }
        Tweet_log result = tweet_logService.save(tweet_log);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tweet_log.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tweet-logs : get all the tweet_logs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tweet_logs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tweet-logs")
    @Timed
    public ResponseEntity<List<Tweet_log>> getAllTweet_logs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tweet_logs");
        Page<Tweet_log> page = tweet_logService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tweet-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tweet-logs/:id : get the "id" tweet_log.
     *
     * @param id the id of the tweet_log to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tweet_log, or with status 404 (Not Found)
     */
    @GetMapping("/tweet-logs/{id}")
    @Timed
    public ResponseEntity<Tweet_log> getTweet_log(@PathVariable Long id) {
        log.debug("REST request to get Tweet_log : {}", id);
        Tweet_log tweet_log = tweet_logService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tweet_log));
    }

    /**
     * DELETE  /tweet-logs/:id : delete the "id" tweet_log.
     *
     * @param id the id of the tweet_log to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tweet-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTweet_log(@PathVariable Long id) {
        log.debug("REST request to delete Tweet_log : {}", id);
        tweet_logService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
