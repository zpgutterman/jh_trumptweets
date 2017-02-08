package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.Testentity;

import com.zpg.trumptweet.repository.TestentityRepository;
import com.zpg.trumptweet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Testentity.
 */
@RestController
@RequestMapping("/api")
public class TestentityResource {

    private final Logger log = LoggerFactory.getLogger(TestentityResource.class);

    private static final String ENTITY_NAME = "testentity";
        
    private final TestentityRepository testentityRepository;

    public TestentityResource(TestentityRepository testentityRepository) {
        this.testentityRepository = testentityRepository;
    }

    /**
     * POST  /testentities : Create a new testentity.
     *
     * @param testentity the testentity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testentity, or with status 400 (Bad Request) if the testentity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/testentities")
    @Timed
    public ResponseEntity<Testentity> createTestentity(@RequestBody Testentity testentity) throws URISyntaxException {
        log.debug("REST request to save Testentity : {}", testentity);
        if (testentity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new testentity cannot already have an ID")).body(null);
        }
        Testentity result = testentityRepository.save(testentity);
        return ResponseEntity.created(new URI("/api/testentities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testentities : Updates an existing testentity.
     *
     * @param testentity the testentity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testentity,
     * or with status 400 (Bad Request) if the testentity is not valid,
     * or with status 500 (Internal Server Error) if the testentity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/testentities")
    @Timed
    public ResponseEntity<Testentity> updateTestentity(@RequestBody Testentity testentity) throws URISyntaxException {
        log.debug("REST request to update Testentity : {}", testentity);
        if (testentity.getId() == null) {
            return createTestentity(testentity);
        }
        Testentity result = testentityRepository.save(testentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, testentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testentities : get all the testentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testentities in body
     */
    @GetMapping("/testentities")
    @Timed
    public List<Testentity> getAllTestentities() {
        log.debug("REST request to get all Testentities");
        List<Testentity> testentities = testentityRepository.findAll();
        return testentities;
    }

    /**
     * GET  /testentities/:id : get the "id" testentity.
     *
     * @param id the id of the testentity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testentity, or with status 404 (Not Found)
     */
    @GetMapping("/testentities/{id}")
    @Timed
    public ResponseEntity<Testentity> getTestentity(@PathVariable Long id) {
        log.debug("REST request to get Testentity : {}", id);
        Testentity testentity = testentityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(testentity));
    }

    /**
     * DELETE  /testentities/:id : delete the "id" testentity.
     *
     * @param id the id of the testentity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/testentities/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestentity(@PathVariable Long id) {
        log.debug("REST request to delete Testentity : {}", id);
        testentityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
