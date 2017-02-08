package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.Charity;

import com.zpg.trumptweet.repository.CharityRepository;
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
 * REST controller for managing Charity.
 */
@RestController
@RequestMapping("/api")
public class CharityResource {

    private final Logger log = LoggerFactory.getLogger(CharityResource.class);

    private static final String ENTITY_NAME = "charity";
        
    private final CharityRepository charityRepository;

    public CharityResource(CharityRepository charityRepository) {
        this.charityRepository = charityRepository;
    }

    /**
     * POST  /charities : Create a new charity.
     *
     * @param charity the charity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new charity, or with status 400 (Bad Request) if the charity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charities")
    @Timed
    public ResponseEntity<Charity> createCharity(@Valid @RequestBody Charity charity) throws URISyntaxException {
        log.debug("REST request to save Charity : {}", charity);
        if (charity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new charity cannot already have an ID")).body(null);
        }
        Charity result = charityRepository.save(charity);
        return ResponseEntity.created(new URI("/api/charities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charities : Updates an existing charity.
     *
     * @param charity the charity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated charity,
     * or with status 400 (Bad Request) if the charity is not valid,
     * or with status 500 (Internal Server Error) if the charity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charities")
    @Timed
    public ResponseEntity<Charity> updateCharity(@Valid @RequestBody Charity charity) throws URISyntaxException {
        log.debug("REST request to update Charity : {}", charity);
        if (charity.getId() == null) {
            return createCharity(charity);
        }
        Charity result = charityRepository.save(charity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, charity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charities : get all the charities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of charities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/charities")
    @Timed
    public ResponseEntity<List<Charity>> getAllCharities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Charities");
        Page<Charity> page = charityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/charities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /charities/:id : get the "id" charity.
     *
     * @param id the id of the charity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the charity, or with status 404 (Not Found)
     */
    @GetMapping("/charities/{id}")
    @Timed
    public ResponseEntity<Charity> getCharity(@PathVariable Long id) {
        log.debug("REST request to get Charity : {}", id);
        Charity charity = charityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(charity));
    }

    /**
     * DELETE  /charities/:id : delete the "id" charity.
     *
     * @param id the id of the charity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCharity(@PathVariable Long id) {
        log.debug("REST request to delete Charity : {}", id);
        charityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
