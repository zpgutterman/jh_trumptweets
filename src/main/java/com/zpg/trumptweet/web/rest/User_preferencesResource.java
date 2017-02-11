package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.User_preferences;

import com.zpg.trumptweet.repository.User_preferencesRepository;
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
 * REST controller for managing User_preferences.
 */
@RestController
@RequestMapping("/api")
public class User_preferencesResource {

    private final Logger log = LoggerFactory.getLogger(User_preferencesResource.class);

    private static final String ENTITY_NAME = "user_preferences";
        
    private final User_preferencesRepository user_preferencesRepository;

    public User_preferencesResource(User_preferencesRepository user_preferencesRepository) {
        this.user_preferencesRepository = user_preferencesRepository;
    }

    /**
     * POST  /user-preferences : Create a new user_preferences.
     *
     * @param user_preferences the user_preferences to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user_preferences, or with status 400 (Bad Request) if the user_preferences has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-preferences")
    @Timed
    public ResponseEntity<User_preferences> createUser_preferences(@RequestBody User_preferences user_preferences) throws URISyntaxException {
        log.debug("REST request to save User_preferences : {}", user_preferences);
        if (user_preferences.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user_preferences cannot already have an ID")).body(null);
        }
        User_preferences result = user_preferencesRepository.save(user_preferences);
        return ResponseEntity.created(new URI("/api/user-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-preferences : Updates an existing user_preferences.
     *
     * @param user_preferences the user_preferences to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user_preferences,
     * or with status 400 (Bad Request) if the user_preferences is not valid,
     * or with status 500 (Internal Server Error) if the user_preferences couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-preferences")
    @Timed
    public ResponseEntity<User_preferences> updateUser_preferences(@RequestBody User_preferences user_preferences) throws URISyntaxException {
        log.debug("REST request to update User_preferences : {}", user_preferences);
        if (user_preferences.getId() == null) {
            return createUser_preferences(user_preferences);
        }
        User_preferences result = user_preferencesRepository.save(user_preferences);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user_preferences.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-preferences : get all the user_preferences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of user_preferences in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-preferences")
    @Timed
    public ResponseEntity<List<User_preferences>> getAllUser_preferences(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of User_preferences");
        Page<User_preferences> page = user_preferencesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-preferences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-preferences/:id : get the "id" user_preferences.
     *
     * @param id the id of the user_preferences to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user_preferences, or with status 404 (Not Found)
     */
    @GetMapping("/user-preferences/{id}")
    @Timed
    public ResponseEntity<User_preferences> getUser_preferences(@PathVariable Long id) {
        log.debug("REST request to get User_preferences : {}", id);
        User_preferences user_preferences = user_preferencesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user_preferences));
    }

    /**
     * DELETE  /user-preferences/:id : delete the "id" user_preferences.
     *
     * @param id the id of the user_preferences to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-preferences/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser_preferences(@PathVariable Long id) {
        log.debug("REST request to delete User_preferences : {}", id);
        user_preferencesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
