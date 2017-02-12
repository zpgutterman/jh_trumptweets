package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.User_tweet_log;
import com.zpg.trumptweet.service.User_tweet_logService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing User_tweet_log.
 */
@RestController
@RequestMapping("/api")
public class User_tweet_logResource {

    private final Logger log = LoggerFactory.getLogger(User_tweet_logResource.class);

    private static final String ENTITY_NAME = "user_tweet_log";
        
    private final User_tweet_logService user_tweet_logService;

    public User_tweet_logResource(User_tweet_logService user_tweet_logService) {
        this.user_tweet_logService = user_tweet_logService;
    }

    /**
     * POST  /user-tweet-logs : Create a new user_tweet_log.
     *
     * @param user_tweet_log the user_tweet_log to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user_tweet_log, or with status 400 (Bad Request) if the user_tweet_log has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-tweet-logs")
    @Timed
    public ResponseEntity<User_tweet_log> createUser_tweet_log(@RequestBody User_tweet_log user_tweet_log) throws URISyntaxException {
        log.debug("REST request to save User_tweet_log : {}", user_tweet_log);
        if (user_tweet_log.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user_tweet_log cannot already have an ID")).body(null);
        }
        User_tweet_log result = user_tweet_logService.save(user_tweet_log);
        return ResponseEntity.created(new URI("/api/user-tweet-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-tweet-logs : Updates an existing user_tweet_log.
     *
     * @param user_tweet_log the user_tweet_log to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user_tweet_log,
     * or with status 400 (Bad Request) if the user_tweet_log is not valid,
     * or with status 500 (Internal Server Error) if the user_tweet_log couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-tweet-logs")
    @Timed
    public ResponseEntity<User_tweet_log> updateUser_tweet_log(@RequestBody User_tweet_log user_tweet_log) throws URISyntaxException {
        log.debug("REST request to update User_tweet_log : {}", user_tweet_log);
        if (user_tweet_log.getId() == null) {
            return createUser_tweet_log(user_tweet_log);
        }
        User_tweet_log result = user_tweet_logService.save(user_tweet_log);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user_tweet_log.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-tweet-logs : get all the user_tweet_logs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of user_tweet_logs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-tweet-logs")
    @Timed
    public ResponseEntity<List<User_tweet_log>> getAllUser_tweet_logs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of User_tweet_logs");
        Page<User_tweet_log> page = user_tweet_logService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-tweet-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-tweet-logs/:id : get the "id" user_tweet_log.
     *
     * @param id the id of the user_tweet_log to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user_tweet_log, or with status 404 (Not Found)
     */
    @GetMapping("/user-tweet-logs/{id}")
    @Timed
    public ResponseEntity<User_tweet_log> getUser_tweet_log(@PathVariable Long id) {
        log.debug("REST request to get User_tweet_log : {}", id);
        User_tweet_log user_tweet_log = user_tweet_logService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user_tweet_log));
    }

    /**
     * DELETE  /user-tweet-logs/:id : delete the "id" user_tweet_log.
     *
     * @param id the id of the user_tweet_log to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-tweet-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser_tweet_log(@PathVariable Long id) {
        log.debug("REST request to delete User_tweet_log : {}", id);
        user_tweet_logService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
