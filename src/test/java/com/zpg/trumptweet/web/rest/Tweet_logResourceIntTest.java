package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.Tweet_log;
import com.zpg.trumptweet.repository.Tweet_logRepository;
import com.zpg.trumptweet.service.Tweet_logService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.zpg.trumptweet.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Tweet_logResource REST controller.
 *
 * @see Tweet_logResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class Tweet_logResourceIntTest {

    private static final String DEFAULT_TWEET = "AAAAAAAAAA";
    private static final String UPDATED_TWEET = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TWEET_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TWEET_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final ZonedDateTime DEFAULT_CATEGORIZE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CATEGORIZE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private Tweet_logRepository tweet_logRepository;

    @Autowired
    private Tweet_logService tweet_logService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTweet_logMockMvc;

    private Tweet_log tweet_log;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tweet_logResource tweet_logResource = new Tweet_logResource(tweet_logService);
        this.restTweet_logMockMvc = MockMvcBuilders.standaloneSetup(tweet_logResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tweet_log createEntity(EntityManager em) {
        Tweet_log tweet_log = new Tweet_log()
                .tweet(DEFAULT_TWEET)
                .tweet_date(DEFAULT_TWEET_DATE)
                .handle(DEFAULT_HANDLE)
                .processed(DEFAULT_PROCESSED)
                .categorize_time(DEFAULT_CATEGORIZE_TIME);
        return tweet_log;
    }

    @Before
    public void initTest() {
        tweet_log = createEntity(em);
    }

    @Test
    @Transactional
    public void createTweet_log() throws Exception {
        int databaseSizeBeforeCreate = tweet_logRepository.findAll().size();

        // Create the Tweet_log

        restTweet_logMockMvc.perform(post("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet_log)))
            .andExpect(status().isCreated());

        // Validate the Tweet_log in the database
        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeCreate + 1);
        Tweet_log testTweet_log = tweet_logList.get(tweet_logList.size() - 1);
        assertThat(testTweet_log.getTweet()).isEqualTo(DEFAULT_TWEET);
        assertThat(testTweet_log.getTweet_date()).isEqualTo(DEFAULT_TWEET_DATE);
        assertThat(testTweet_log.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testTweet_log.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testTweet_log.getCategorize_time()).isEqualTo(DEFAULT_CATEGORIZE_TIME);
    }

    @Test
    @Transactional
    public void createTweet_logWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tweet_logRepository.findAll().size();

        // Create the Tweet_log with an existing ID
        Tweet_log existingTweet_log = new Tweet_log();
        existingTweet_log.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTweet_logMockMvc.perform(post("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTweet_log)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTweetIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweet_logRepository.findAll().size();
        // set the field null
        tweet_log.setTweet(null);

        // Create the Tweet_log, which fails.

        restTweet_logMockMvc.perform(post("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet_log)))
            .andExpect(status().isBadRequest());

        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTweet_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweet_logRepository.findAll().size();
        // set the field null
        tweet_log.setTweet_date(null);

        // Create the Tweet_log, which fails.

        restTweet_logMockMvc.perform(post("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet_log)))
            .andExpect(status().isBadRequest());

        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHandleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweet_logRepository.findAll().size();
        // set the field null
        tweet_log.setHandle(null);

        // Create the Tweet_log, which fails.

        restTweet_logMockMvc.perform(post("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet_log)))
            .andExpect(status().isBadRequest());

        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTweet_logs() throws Exception {
        // Initialize the database
        tweet_logRepository.saveAndFlush(tweet_log);

        // Get all the tweet_logList
        restTweet_logMockMvc.perform(get("/api/tweet-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweet_log.getId().intValue())))
            .andExpect(jsonPath("$.[*].tweet").value(hasItem(DEFAULT_TWEET.toString())))
            .andExpect(jsonPath("$.[*].tweet_date").value(hasItem(sameInstant(DEFAULT_TWEET_DATE))))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE.toString())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].categorize_time").value(hasItem(sameInstant(DEFAULT_CATEGORIZE_TIME))));
    }

    @Test
    @Transactional
    public void getTweet_log() throws Exception {
        // Initialize the database
        tweet_logRepository.saveAndFlush(tweet_log);

        // Get the tweet_log
        restTweet_logMockMvc.perform(get("/api/tweet-logs/{id}", tweet_log.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tweet_log.getId().intValue()))
            .andExpect(jsonPath("$.tweet").value(DEFAULT_TWEET.toString()))
            .andExpect(jsonPath("$.tweet_date").value(sameInstant(DEFAULT_TWEET_DATE)))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE.toString()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.categorize_time").value(sameInstant(DEFAULT_CATEGORIZE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingTweet_log() throws Exception {
        // Get the tweet_log
        restTweet_logMockMvc.perform(get("/api/tweet-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTweet_log() throws Exception {
        // Initialize the database
        tweet_logService.save(tweet_log);

        int databaseSizeBeforeUpdate = tweet_logRepository.findAll().size();

        // Update the tweet_log
        Tweet_log updatedTweet_log = tweet_logRepository.findOne(tweet_log.getId());
        updatedTweet_log
                .tweet(UPDATED_TWEET)
                .tweet_date(UPDATED_TWEET_DATE)
                .handle(UPDATED_HANDLE)
                .processed(UPDATED_PROCESSED)
                .categorize_time(UPDATED_CATEGORIZE_TIME);

        restTweet_logMockMvc.perform(put("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTweet_log)))
            .andExpect(status().isOk());

        // Validate the Tweet_log in the database
        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeUpdate);
        Tweet_log testTweet_log = tweet_logList.get(tweet_logList.size() - 1);
        assertThat(testTweet_log.getTweet()).isEqualTo(UPDATED_TWEET);
        assertThat(testTweet_log.getTweet_date()).isEqualTo(UPDATED_TWEET_DATE);
        assertThat(testTweet_log.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testTweet_log.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testTweet_log.getCategorize_time()).isEqualTo(UPDATED_CATEGORIZE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTweet_log() throws Exception {
        int databaseSizeBeforeUpdate = tweet_logRepository.findAll().size();

        // Create the Tweet_log

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTweet_logMockMvc.perform(put("/api/tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweet_log)))
            .andExpect(status().isCreated());

        // Validate the Tweet_log in the database
        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTweet_log() throws Exception {
        // Initialize the database
        tweet_logService.save(tweet_log);

        int databaseSizeBeforeDelete = tweet_logRepository.findAll().size();

        // Get the tweet_log
        restTweet_logMockMvc.perform(delete("/api/tweet-logs/{id}", tweet_log.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tweet_log> tweet_logList = tweet_logRepository.findAll();
        assertThat(tweet_logList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tweet_log.class);
    }
}
