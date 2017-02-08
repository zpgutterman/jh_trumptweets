package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.Testentity;
import com.zpg.trumptweet.repository.TestentityRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TestentityResource REST controller.
 *
 * @see TestentityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class TestentityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TestentityRepository testentityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restTestentityMockMvc;

    private Testentity testentity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TestentityResource testentityResource = new TestentityResource(testentityRepository);
        this.restTestentityMockMvc = MockMvcBuilders.standaloneSetup(testentityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testentity createEntity(EntityManager em) {
        Testentity testentity = new Testentity()
                .name(DEFAULT_NAME);
        return testentity;
    }

    @Before
    public void initTest() {
        testentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestentity() throws Exception {
        int databaseSizeBeforeCreate = testentityRepository.findAll().size();

        // Create the Testentity

        restTestentityMockMvc.perform(post("/api/testentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testentity)))
            .andExpect(status().isCreated());

        // Validate the Testentity in the database
        List<Testentity> testentityList = testentityRepository.findAll();
        assertThat(testentityList).hasSize(databaseSizeBeforeCreate + 1);
        Testentity testTestentity = testentityList.get(testentityList.size() - 1);
        assertThat(testTestentity.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTestentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testentityRepository.findAll().size();

        // Create the Testentity with an existing ID
        Testentity existingTestentity = new Testentity();
        existingTestentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestentityMockMvc.perform(post("/api/testentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTestentity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Testentity> testentityList = testentityRepository.findAll();
        assertThat(testentityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTestentities() throws Exception {
        // Initialize the database
        testentityRepository.saveAndFlush(testentity);

        // Get all the testentityList
        restTestentityMockMvc.perform(get("/api/testentities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getTestentity() throws Exception {
        // Initialize the database
        testentityRepository.saveAndFlush(testentity);

        // Get the testentity
        restTestentityMockMvc.perform(get("/api/testentities/{id}", testentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testentity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestentity() throws Exception {
        // Get the testentity
        restTestentityMockMvc.perform(get("/api/testentities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestentity() throws Exception {
        // Initialize the database
        testentityRepository.saveAndFlush(testentity);
        int databaseSizeBeforeUpdate = testentityRepository.findAll().size();

        // Update the testentity
        Testentity updatedTestentity = testentityRepository.findOne(testentity.getId());
        updatedTestentity
                .name(UPDATED_NAME);

        restTestentityMockMvc.perform(put("/api/testentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTestentity)))
            .andExpect(status().isOk());

        // Validate the Testentity in the database
        List<Testentity> testentityList = testentityRepository.findAll();
        assertThat(testentityList).hasSize(databaseSizeBeforeUpdate);
        Testentity testTestentity = testentityList.get(testentityList.size() - 1);
        assertThat(testTestentity.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTestentity() throws Exception {
        int databaseSizeBeforeUpdate = testentityRepository.findAll().size();

        // Create the Testentity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTestentityMockMvc.perform(put("/api/testentities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(testentity)))
            .andExpect(status().isCreated());

        // Validate the Testentity in the database
        List<Testentity> testentityList = testentityRepository.findAll();
        assertThat(testentityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTestentity() throws Exception {
        // Initialize the database
        testentityRepository.saveAndFlush(testentity);
        int databaseSizeBeforeDelete = testentityRepository.findAll().size();

        // Get the testentity
        restTestentityMockMvc.perform(delete("/api/testentities/{id}", testentity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Testentity> testentityList = testentityRepository.findAll();
        assertThat(testentityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Testentity.class);
    }
}
