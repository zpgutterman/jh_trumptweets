package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.User_payment;
import com.zpg.trumptweet.service.User_paymentService;
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
 * REST controller for managing User_payment.
 */
@RestController
@RequestMapping("/api")
public class User_paymentResource {

    private final Logger log = LoggerFactory.getLogger(User_paymentResource.class);

    private static final String ENTITY_NAME = "user_payment";
        
    private final User_paymentService user_paymentService;

    public User_paymentResource(User_paymentService user_paymentService) {
        this.user_paymentService = user_paymentService;
    }

    /**
     * POST  /user-payments : Create a new user_payment.
     *
     * @param user_payment the user_payment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user_payment, or with status 400 (Bad Request) if the user_payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-payments")
    @Timed
    public ResponseEntity<User_payment> createUser_payment(@Valid @RequestBody User_payment user_payment) throws URISyntaxException {
        log.debug("REST request to save User_payment : {}", user_payment);
        if (user_payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user_payment cannot already have an ID")).body(null);
        }
        User_payment result = user_paymentService.save(user_payment);
        return ResponseEntity.created(new URI("/api/user-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-payments : Updates an existing user_payment.
     *
     * @param user_payment the user_payment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user_payment,
     * or with status 400 (Bad Request) if the user_payment is not valid,
     * or with status 500 (Internal Server Error) if the user_payment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-payments")
    @Timed
    public ResponseEntity<User_payment> updateUser_payment(@Valid @RequestBody User_payment user_payment) throws URISyntaxException {
        log.debug("REST request to update User_payment : {}", user_payment);
        if (user_payment.getId() == null) {
            return createUser_payment(user_payment);
        }
        User_payment result = user_paymentService.save(user_payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user_payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-payments : get all the user_payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of user_payments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-payments")
    @Timed
    public ResponseEntity<List<User_payment>> getAllUser_payments(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of User_payments");
        Page<User_payment> page = user_paymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-payments/:id : get the "id" user_payment.
     *
     * @param id the id of the user_payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user_payment, or with status 404 (Not Found)
     */
    @GetMapping("/user-payments/{id}")
    @Timed
    public ResponseEntity<User_payment> getUser_payment(@PathVariable Long id) {
        log.debug("REST request to get User_payment : {}", id);
        User_payment user_payment = user_paymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user_payment));
    }

    /**
     * DELETE  /user-payments/:id : delete the "id" user_payment.
     *
     * @param id the id of the user_payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser_payment(@PathVariable Long id) {
        log.debug("REST request to delete User_payment : {}", id);
        user_paymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
