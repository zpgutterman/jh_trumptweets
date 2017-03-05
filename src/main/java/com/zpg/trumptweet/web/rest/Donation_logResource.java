package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.Donation_log;
import com.zpg.trumptweet.service.Donation_logService;
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

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Donation_log.
 */
@RestController
@RequestMapping("/api")
public class Donation_logResource {

    private final Logger log = LoggerFactory.getLogger(Donation_logResource.class);

    private static final String ENTITY_NAME = "donation_log";
        
    private final Donation_logService donation_logService;

    public Donation_logResource(Donation_logService donation_logService) {
        this.donation_logService = donation_logService;
    }

    /**
     * POST  /donation-logs : Create a new donation_log.
     *
     * @param donation_log the donation_log to create
     * @return the ResponseEntity with status 201 (Created) and with body the new donation_log, or with status 400 (Bad Request) if the donation_log has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/donation-logs")
    @Timed
    public ResponseEntity<Donation_log> createDonation_log(@Valid @RequestBody Donation_log donation_log) throws URISyntaxException {
        log.debug("REST request to save Donation_log : {}", donation_log);
        if (donation_log.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new donation_log cannot already have an ID")).body(null);
        }
        Donation_log result = donation_logService.save(donation_log);
        return ResponseEntity.created(new URI("/api/donation-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /donation-logs : Updates an existing donation_log.
     *
     * @param donation_log the donation_log to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated donation_log,
     * or with status 400 (Bad Request) if the donation_log is not valid,
     * or with status 500 (Internal Server Error) if the donation_log couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/donation-logs")
    @Timed
    public ResponseEntity<Donation_log> updateDonation_log(@Valid @RequestBody Donation_log donation_log) throws URISyntaxException {
        log.debug("REST request to update Donation_log : {}", donation_log);
        if (donation_log.getId() == null) {
            return createDonation_log(donation_log);
        }
        Donation_log result = donation_logService.save(donation_log);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, donation_log.getId().toString()))
            .body(result);
    }

    /**
     * GET  /donation-logs : get all the donation_logs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of donation_logs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/donation-logs")
    @Timed
    public ResponseEntity<List<Donation_log>> getAllDonation_logs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Donation_logs");
        Page<Donation_log> page = donation_logService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/donation-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /donation-logs/:id : get the "id" donation_log.
     *
     * @param id the id of the donation_log to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donation_log, or with status 404 (Not Found)
     */
    @GetMapping("/donation-logs/{id}")
    @Timed
    public ResponseEntity<Donation_log> getDonation_log(@PathVariable Long id) {
        log.debug("REST request to get Donation_log : {}", id);
        Donation_log donation_log = donation_logService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(donation_log));
    }
    
    /**
     * GET  /donation-logs/:id : get the "id" donation_log.
     *
     * @param id the id of the donation_log to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donation_log, or with status 404 (Not Found)
     */
    @GetMapping("/donation-total")
    @Timed
    public ResponseEntity<BigDecimal> getDonationTotal() {
        log.debug("REST request to get donation total");
        BigDecimal total = donation_logService.findTotal();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(total));
    }

    /**
     * DELETE  /donation-logs/:id : delete the "id" donation_log.
     *
     * @param id the id of the donation_log to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/donation-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDonation_log(@PathVariable Long id) {
        log.debug("REST request to delete Donation_log : {}", id);
        donation_logService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
