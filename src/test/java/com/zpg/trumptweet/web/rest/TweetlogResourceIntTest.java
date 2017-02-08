package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.Tweetlog;
import com.zpg.trumptweet.repository.TweetlogRepository;
import com.zpg.trumptweet.service.TweetlogService;

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
 * Test class for the TweetlogResource REST controller.
 *
 * @see TweetlogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class TweetlogResourceIntTest {

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
    private TweetlogRepository tweetlogRepository;

    @Autowired
    private TweetlogService tweetlogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTweetlogMockMvc;

    private Tweetlog tweetlog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TweetlogResource tweetlogResource = new TweetlogResource(tweetlogService);
        this.restTweetlogMockMvc = MockMvcBuilders.standaloneSetup(tweetlogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tweetlog createEntity(EntityManager em) {
        Tweetlog tweetlog = new Tweetlog()
                .tweet(DEFAULT_TWEET)
                .tweet_date(DEFAULT_TWEET_DATE)
                .handle(DEFAULT_HANDLE)
                .processed(DEFAULT_PROCESSED)
                .categorize_time(DEFAULT_CATEGORIZE_TIME);
        return tweetlog;
    }

    @Before
    public void initTest() {
        tweetlog = createEntity(em);
    }

    @Test
    @Transactional
    public void createTweetlog() throws Exception {
        int databaseSizeBeforeCreate = tweetlogRepository.findAll().size();

        // Create the Tweetlog

        restTweetlogMockMvc.perform(post("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetlog)))
            .andExpect(status().isCreated());

        // Validate the Tweetlog in the database
        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeCreate + 1);
        Tweetlog testTweetlog = tweetlogList.get(tweetlogList.size() - 1);
        assertThat(testTweetlog.getTweet()).isEqualTo(DEFAULT_TWEET);
        assertThat(testTweetlog.getTweet_date()).isEqualTo(DEFAULT_TWEET_DATE);
        assertThat(testTweetlog.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testTweetlog.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testTweetlog.getCategorize_time()).isEqualTo(DEFAULT_CATEGORIZE_TIME);
    }

    @Test
    @Transactional
    public void createTweetlogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tweetlogRepository.findAll().size();

        // Create the Tweetlog with an existing ID
        Tweetlog existingTweetlog = new Tweetlog();
        existingTweetlog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTweetlogMockMvc.perform(post("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTweetlog)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTweetIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetlogRepository.findAll().size();
        // set the field null
        tweetlog.setTweet(null);

        // Create the Tweetlog, which fails.

        restTweetlogMockMvc.perform(post("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetlog)))
            .andExpect(status().isBadRequest());

        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTweet_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetlogRepository.findAll().size();
        // set the field null
        tweetlog.setTweet_date(null);

        // Create the Tweetlog, which fails.

        restTweetlogMockMvc.perform(post("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetlog)))
            .andExpect(status().isBadRequest());

        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHandleIsRequired() throws Exception {
        int databaseSizeBeforeTest = tweetlogRepository.findAll().size();
        // set the field null
        tweetlog.setHandle(null);

        // Create the Tweetlog, which fails.

        restTweetlogMockMvc.perform(post("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetlog)))
            .andExpect(status().isBadRequest());

        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTweetlogs() throws Exception {
        // Initialize the database
        tweetlogRepository.saveAndFlush(tweetlog);

        // Get all the tweetlogList
        restTweetlogMockMvc.perform(get("/api/tweetlogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tweetlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].tweet").value(hasItem(DEFAULT_TWEET.toString())))
            .andExpect(jsonPath("$.[*].tweet_date").value(hasItem(sameInstant(DEFAULT_TWEET_DATE))))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE.toString())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].categorize_time").value(hasItem(sameInstant(DEFAULT_CATEGORIZE_TIME))));
    }

    @Test
    @Transactional
    public void getTweetlog() throws Exception {
        // Initialize the database
        tweetlogRepository.saveAndFlush(tweetlog);

        // Get the tweetlog
        restTweetlogMockMvc.perform(get("/api/tweetlogs/{id}", tweetlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tweetlog.getId().intValue()))
            .andExpect(jsonPath("$.tweet").value(DEFAULT_TWEET.toString()))
            .andExpect(jsonPath("$.tweet_date").value(sameInstant(DEFAULT_TWEET_DATE)))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE.toString()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.categorize_time").value(sameInstant(DEFAULT_CATEGORIZE_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingTweetlog() throws Exception {
        // Get the tweetlog
        restTweetlogMockMvc.perform(get("/api/tweetlogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTweetlog() throws Exception {
        // Initialize the database
        tweetlogService.save(tweetlog);

        int databaseSizeBeforeUpdate = tweetlogRepository.findAll().size();

        // Update the tweetlog
        Tweetlog updatedTweetlog = tweetlogRepository.findOne(tweetlog.getId());
        updatedTweetlog
                .tweet(UPDATED_TWEET)
                .tweet_date(UPDATED_TWEET_DATE)
                .handle(UPDATED_HANDLE)
                .processed(UPDATED_PROCESSED)
                .categorize_time(UPDATED_CATEGORIZE_TIME);

        restTweetlogMockMvc.perform(put("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTweetlog)))
            .andExpect(status().isOk());

        // Validate the Tweetlog in the database
        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeUpdate);
        Tweetlog testTweetlog = tweetlogList.get(tweetlogList.size() - 1);
        assertThat(testTweetlog.getTweet()).isEqualTo(UPDATED_TWEET);
        assertThat(testTweetlog.getTweet_date()).isEqualTo(UPDATED_TWEET_DATE);
        assertThat(testTweetlog.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testTweetlog.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testTweetlog.getCategorize_time()).isEqualTo(UPDATED_CATEGORIZE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTweetlog() throws Exception {
        int databaseSizeBeforeUpdate = tweetlogRepository.findAll().size();

        // Create the Tweetlog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTweetlogMockMvc.perform(put("/api/tweetlogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tweetlog)))
            .andExpect(status().isCreated());

        // Validate the Tweetlog in the database
        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTweetlog() throws Exception {
        // Initialize the database
        tweetlogService.save(tweetlog);

        int databaseSizeBeforeDelete = tweetlogRepository.findAll().size();

        // Get the tweetlog
        restTweetlogMockMvc.perform(delete("/api/tweetlogs/{id}", tweetlog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tweetlog> tweetlogList = tweetlogRepository.findAll();
        assertThat(tweetlogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tweetlog.class);
    }
}
