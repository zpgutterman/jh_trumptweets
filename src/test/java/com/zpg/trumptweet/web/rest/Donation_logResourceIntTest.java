package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.Donation_log;
import com.zpg.trumptweet.domain.User;
import com.zpg.trumptweet.domain.Category;
import com.zpg.trumptweet.repository.Donation_logRepository;
import com.zpg.trumptweet.service.Donation_logService;

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
import java.math.BigDecimal;
import java.util.List;

import static com.zpg.trumptweet.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Donation_logResource REST controller.
 *
 * @see Donation_logResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class Donation_logResourceIntTest {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final ZonedDateTime DEFAULT_PROCESSED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PROCESSED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private Donation_logRepository donation_logRepository;

    @Autowired
    private Donation_logService donation_logService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restDonation_logMockMvc;

    private Donation_log donation_log;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Donation_logResource donation_logResource = new Donation_logResource(donation_logService);
        this.restDonation_logMockMvc = MockMvcBuilders.standaloneSetup(donation_logResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donation_log createEntity(EntityManager em) {
        Donation_log donation_log = new Donation_log()
                .amount(DEFAULT_AMOUNT)
                .processed(DEFAULT_PROCESSED)
                .processed_date(DEFAULT_PROCESSED_DATE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        donation_log.setUser(user);
        // Add required entity
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        donation_log.setCategory(category);
        return donation_log;
    }

    @Before
    public void initTest() {
        donation_log = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonation_log() throws Exception {
        int databaseSizeBeforeCreate = donation_logRepository.findAll().size();

        // Create the Donation_log

        restDonation_logMockMvc.perform(post("/api/donation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation_log)))
            .andExpect(status().isCreated());

        // Validate the Donation_log in the database
        List<Donation_log> donation_logList = donation_logRepository.findAll();
        assertThat(donation_logList).hasSize(databaseSizeBeforeCreate + 1);
        Donation_log testDonation_log = donation_logList.get(donation_logList.size() - 1);
        assertThat(testDonation_log.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDonation_log.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testDonation_log.getProcessed_date()).isEqualTo(DEFAULT_PROCESSED_DATE);
    }

    @Test
    @Transactional
    public void createDonation_logWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donation_logRepository.findAll().size();

        // Create the Donation_log with an existing ID
        Donation_log existingDonation_log = new Donation_log();
        existingDonation_log.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonation_logMockMvc.perform(post("/api/donation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDonation_log)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Donation_log> donation_logList = donation_logRepository.findAll();
        assertThat(donation_logList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = donation_logRepository.findAll().size();
        // set the field null
        donation_log.setAmount(null);

        // Create the Donation_log, which fails.

        restDonation_logMockMvc.perform(post("/api/donation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation_log)))
            .andExpect(status().isBadRequest());

        List<Donation_log> donation_logList = donation_logRepository.findAll();
        assertThat(donation_logList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDonation_logs() throws Exception {
        // Initialize the database
        donation_logRepository.saveAndFlush(donation_log);

        // Get all the donation_logList
        restDonation_logMockMvc.perform(get("/api/donation-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donation_log.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed_date").value(hasItem(sameInstant(DEFAULT_PROCESSED_DATE))));
    }

    @Test
    @Transactional
    public void getDonation_log() throws Exception {
        // Initialize the database
        donation_logRepository.saveAndFlush(donation_log);

        // Get the donation_log
        restDonation_logMockMvc.perform(get("/api/donation-logs/{id}", donation_log.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donation_log.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.processed_date").value(sameInstant(DEFAULT_PROCESSED_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingDonation_log() throws Exception {
        // Get the donation_log
        restDonation_logMockMvc.perform(get("/api/donation-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonation_log() throws Exception {
        // Initialize the database
        donation_logService.save(donation_log);

        int databaseSizeBeforeUpdate = donation_logRepository.findAll().size();

        // Update the donation_log
        Donation_log updatedDonation_log = donation_logRepository.findOne(donation_log.getId());
        updatedDonation_log
                .amount(UPDATED_AMOUNT)
                .processed(UPDATED_PROCESSED)
                .processed_date(UPDATED_PROCESSED_DATE);

        restDonation_logMockMvc.perform(put("/api/donation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonation_log)))
            .andExpect(status().isOk());

        // Validate the Donation_log in the database
        List<Donation_log> donation_logList = donation_logRepository.findAll();
        assertThat(donation_logList).hasSize(databaseSizeBeforeUpdate);
        Donation_log testDonation_log = donation_logList.get(donation_logList.size() - 1);
        assertThat(testDonation_log.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDonation_log.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testDonation_log.getProcessed_date()).isEqualTo(UPDATED_PROCESSED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDonation_log() throws Exception {
        int databaseSizeBeforeUpdate = donation_logRepository.findAll().size();

        // Create the Donation_log

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDonation_logMockMvc.perform(put("/api/donation-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation_log)))
            .andExpect(status().isCreated());

        // Validate the Donation_log in the database
        List<Donation_log> donation_logList = donation_logRepository.findAll();
        assertThat(donation_logList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDonation_log() throws Exception {
        // Initialize the database
        donation_logService.save(donation_log);

        int databaseSizeBeforeDelete = donation_logRepository.findAll().size();

        // Get the donation_log
        restDonation_logMockMvc.perform(delete("/api/donation-logs/{id}", donation_log.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Donation_log> donation_logList = donation_logRepository.findAll();
        assertThat(donation_logList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donation_log.class);
    }
}
