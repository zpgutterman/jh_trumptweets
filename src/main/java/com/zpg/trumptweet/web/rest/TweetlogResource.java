package com.zpg.trumptweet.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.zpg.trumptweet.domain.Tweetlog;
import com.zpg.trumptweet.service.TweetlogService;
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
 * REST controller for managing Tweetlog.
 */
@RestController
@RequestMapping("/api")
public class TweetlogResource {

    private final Logger log = LoggerFactory.getLogger(TweetlogResource.class);

    private static final String ENTITY_NAME = "tweetlog";
        
    private final TweetlogService tweetlogService;

    public TweetlogResource(TweetlogService tweetlogService) {
        this.tweetlogService = tweetlogService;
    }

    /**
     * POST  /tweetlogs : Create a new tweetlog.
     *
     * @param tweetlog the tweetlog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tweetlog, or with status 400 (Bad Request) if the tweetlog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tweetlogs")
    @Timed
    public ResponseEntity<Tweetlog> createTweetlog(@Valid @RequestBody Tweetlog tweetlog) throws URISyntaxException {
        log.debug("REST request to save Tweetlog : {}", tweetlog);
        if (tweetlog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tweetlog cannot already have an ID")).body(null);
        }
        Tweetlog result = tweetlogService.save(tweetlog);
        return ResponseEntity.created(new URI("/api/tweetlogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tweetlogs : Updates an existing tweetlog.
     *
     * @param tweetlog the tweetlog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tweetlog,
     * or with status 400 (Bad Request) if the tweetlog is not valid,
     * or with status 500 (Internal Server Error) if the tweetlog couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tweetlogs")
    @Timed
    public ResponseEntity<Tweetlog> updateTweetlog(@Valid @RequestBody Tweetlog tweetlog) throws URISyntaxException {
        log.debug("REST request to update Tweetlog : {}", tweetlog);
        if (tweetlog.getId() == null) {
            return createTweetlog(tweetlog);
        }
        Tweetlog result = tweetlogService.save(tweetlog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tweetlog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tweetlogs : get all the tweetlogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tweetlogs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tweetlogs")
    @Timed
    public ResponseEntity<List<Tweetlog>> getAllTweetlogs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tweetlogs");
        Page<Tweetlog> page = tweetlogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tweetlogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tweetlogs/:id : get the "id" tweetlog.
     *
     * @param id the id of the tweetlog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tweetlog, or with status 404 (Not Found)
     */
    @GetMapping("/tweetlogs/{id}")
    @Timed
    public ResponseEntity<Tweetlog> getTweetlog(@PathVariable Long id) {
        log.debug("REST request to get Tweetlog : {}", id);
        Tweetlog tweetlog = tweetlogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tweetlog));
    }

    /**
     * DELETE  /tweetlogs/:id : delete the "id" tweetlog.
     *
     * @param id the id of the tweetlog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tweetlogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTweetlog(@PathVariable Long id) {
        log.debug("REST request to delete Tweetlog : {}", id);
        tweetlogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
