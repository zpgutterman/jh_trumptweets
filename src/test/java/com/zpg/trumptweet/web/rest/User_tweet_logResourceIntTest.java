package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.User_tweet_log;
import com.zpg.trumptweet.repository.User_tweet_logRepository;
import com.zpg.trumptweet.service.User_tweet_logService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the User_tweet_logResource REST controller.
 *
 * @see User_tweet_logResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class User_tweet_logResourceIntTest {

    private static final BigDecimal DEFAULT_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHARGE = new BigDecimal(2);

    @Autowired
    private User_tweet_logRepository user_tweet_logRepository;

    @Autowired
    private User_tweet_logService user_tweet_logService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restUser_tweet_logMockMvc;

    private User_tweet_log user_tweet_log;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User_tweet_logResource user_tweet_logResource = new User_tweet_logResource(user_tweet_logService);
        this.restUser_tweet_logMockMvc = MockMvcBuilders.standaloneSetup(user_tweet_logResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User_tweet_log createEntity(EntityManager em) {
        User_tweet_log user_tweet_log = new User_tweet_log()
                .charge(DEFAULT_CHARGE);
        return user_tweet_log;
    }

    @Before
    public void initTest() {
        user_tweet_log = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser_tweet_log() throws Exception {
        int databaseSizeBeforeCreate = user_tweet_logRepository.findAll().size();

        // Create the User_tweet_log

        restUser_tweet_logMockMvc.perform(post("/api/user-tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_tweet_log)))
            .andExpect(status().isCreated());

        // Validate the User_tweet_log in the database
        List<User_tweet_log> user_tweet_logList = user_tweet_logRepository.findAll();
        assertThat(user_tweet_logList).hasSize(databaseSizeBeforeCreate + 1);
        User_tweet_log testUser_tweet_log = user_tweet_logList.get(user_tweet_logList.size() - 1);
        assertThat(testUser_tweet_log.getCharge()).isEqualTo(DEFAULT_CHARGE);
    }

    @Test
    @Transactional
    public void createUser_tweet_logWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = user_tweet_logRepository.findAll().size();

        // Create the User_tweet_log with an existing ID
        User_tweet_log existingUser_tweet_log = new User_tweet_log();
        existingUser_tweet_log.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser_tweet_logMockMvc.perform(post("/api/user-tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUser_tweet_log)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<User_tweet_log> user_tweet_logList = user_tweet_logRepository.findAll();
        assertThat(user_tweet_logList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUser_tweet_logs() throws Exception {
        // Initialize the database
        user_tweet_logRepository.saveAndFlush(user_tweet_log);

        // Get all the user_tweet_logList
        restUser_tweet_logMockMvc.perform(get("/api/user-tweet-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user_tweet_log.getId().intValue())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.intValue())));
    }

    @Test
    @Transactional
    public void getUser_tweet_log() throws Exception {
        // Initialize the database
        user_tweet_logRepository.saveAndFlush(user_tweet_log);

        // Get the user_tweet_log
        restUser_tweet_logMockMvc.perform(get("/api/user-tweet-logs/{id}", user_tweet_log.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user_tweet_log.getId().intValue()))
            .andExpect(jsonPath("$.charge").value(DEFAULT_CHARGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUser_tweet_log() throws Exception {
        // Get the user_tweet_log
        restUser_tweet_logMockMvc.perform(get("/api/user-tweet-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser_tweet_log() throws Exception {
        // Initialize the database
        user_tweet_logService.save(user_tweet_log);

        int databaseSizeBeforeUpdate = user_tweet_logRepository.findAll().size();

        // Update the user_tweet_log
        User_tweet_log updatedUser_tweet_log = user_tweet_logRepository.findOne(user_tweet_log.getId());
        updatedUser_tweet_log
                .charge(UPDATED_CHARGE);

        restUser_tweet_logMockMvc.perform(put("/api/user-tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUser_tweet_log)))
            .andExpect(status().isOk());

        // Validate the User_tweet_log in the database
        List<User_tweet_log> user_tweet_logList = user_tweet_logRepository.findAll();
        assertThat(user_tweet_logList).hasSize(databaseSizeBeforeUpdate);
        User_tweet_log testUser_tweet_log = user_tweet_logList.get(user_tweet_logList.size() - 1);
        assertThat(testUser_tweet_log.getCharge()).isEqualTo(UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void updateNonExistingUser_tweet_log() throws Exception {
        int databaseSizeBeforeUpdate = user_tweet_logRepository.findAll().size();

        // Create the User_tweet_log

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUser_tweet_logMockMvc.perform(put("/api/user-tweet-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_tweet_log)))
            .andExpect(status().isCreated());

        // Validate the User_tweet_log in the database
        List<User_tweet_log> user_tweet_logList = user_tweet_logRepository.findAll();
        assertThat(user_tweet_logList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUser_tweet_log() throws Exception {
        // Initialize the database
        user_tweet_logService.save(user_tweet_log);

        int databaseSizeBeforeDelete = user_tweet_logRepository.findAll().size();

        // Get the user_tweet_log
        restUser_tweet_logMockMvc.perform(delete("/api/user-tweet-logs/{id}", user_tweet_log.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User_tweet_log> user_tweet_logList = user_tweet_logRepository.findAll();
        assertThat(user_tweet_logList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User_tweet_log.class);
    }
}
