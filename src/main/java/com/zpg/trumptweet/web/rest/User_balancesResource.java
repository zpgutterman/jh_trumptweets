package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.User_balances;
import com.zpg.trumptweet.service.User_balancesService;
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
 * REST controller for managing User_balances.
 */
@RestController
@RequestMapping("/api")
public class User_balancesResource {

    private final Logger log = LoggerFactory.getLogger(User_balancesResource.class);

    private static final String ENTITY_NAME = "user_balances";
        
    private final User_balancesService user_balancesService;

    public User_balancesResource(User_balancesService user_balancesService) {
        this.user_balancesService = user_balancesService;
    }

    /**
     * POST  /user-balances : Create a new user_balances.
     *
     * @param user_balances the user_balances to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user_balances, or with status 400 (Bad Request) if the user_balances has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-balances")
    @Timed
    public ResponseEntity<User_balances> createUser_balances(@RequestBody User_balances user_balances) throws URISyntaxException {
        log.debug("REST request to save User_balances : {}", user_balances);
        if (user_balances.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user_balances cannot already have an ID")).body(null);
        }
        User_balances result = user_balancesService.save(user_balances);
        return ResponseEntity.created(new URI("/api/user-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-balances : Updates an existing user_balances.
     *
     * @param user_balances the user_balances to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user_balances,
     * or with status 400 (Bad Request) if the user_balances is not valid,
     * or with status 500 (Internal Server Error) if the user_balances couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-balances")
    @Timed
    public ResponseEntity<User_balances> updateUser_balances(@RequestBody User_balances user_balances) throws URISyntaxException {
        log.debug("REST request to update User_balances : {}", user_balances);
        if (user_balances.getId() == null) {
            return createUser_balances(user_balances);
        }
        User_balances result = user_balancesService.save(user_balances);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user_balances.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-balances : get all the user_balances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of user_balances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-balances")
    @Timed
    public ResponseEntity<List<User_balances>> getAllUser_balances(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of User_balances");
        Page<User_balances> page = user_balancesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-balances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-balances/:id : get the "id" user_balances.
     *
     * @param id the id of the user_balances to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user_balances, or with status 404 (Not Found)
     */
    @GetMapping("/user-balances/{id}")
    @Timed
    public ResponseEntity<User_balances> getUser_balances(@PathVariable Long id) {
        log.debug("REST request to get User_balances : {}", id);
        User_balances user_balances = user_balancesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user_balances));
    }
    
        
        /**
         * GET  /user-balances/user/ : get all of the user_balances for a user.
         *
         * @param id the id of the user to retrieve
         * @return the ResponseEntity with status 200 (OK) and with body the user_balances, or with status 404 (Not Found)
         * @throws URISyntaxException 
         */
        @GetMapping("/ub-user/")
        @Timed
        public ResponseEntity<List<User_balances>> getBalanceByUser() {
            log.debug("REST request to get User_balances for user : {}");
            List<User_balances> result = user_balancesService.findByCurrentUser();
            log.debug(result.toString());
            return new ResponseEntity<List<User_balances>>(result, HttpStatus.OK);
        }

    /**
     * DELETE  /user-balances/:id : delete the "id" user_balances.
     *
     * @param id the id of the user_balances to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-balances/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser_balances(@PathVariable Long id) {
        log.debug("REST request to delete User_balances : {}", id);
        user_balancesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
