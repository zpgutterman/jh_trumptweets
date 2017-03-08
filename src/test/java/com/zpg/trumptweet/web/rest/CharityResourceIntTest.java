package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.Charity;
import com.zpg.trumptweet.repository.CharityRepository;

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
 * Test class for the CharityResource REST controller.
 *
 * @see CharityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class CharityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_EIN = "AAAAAAAAAA";
    private static final String UPDATED_EIN = "BBBBBBBBBB";

    @Autowired
    private CharityRepository charityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restCharityMockMvc;

    private Charity charity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            CharityResource charityResource = new CharityResource(charityRepository);
        this.restCharityMockMvc = MockMvcBuilders.standaloneSetup(charityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Charity createEntity(EntityManager em) {
        Charity charity = new Charity()
                .name(DEFAULT_NAME)
                .website(DEFAULT_WEBSITE)
                .ein(DEFAULT_EIN);
        return charity;
    }

    @Before
    public void initTest() {
        charity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharity() throws Exception {
        int databaseSizeBeforeCreate = charityRepository.findAll().size();

        // Create the Charity

        restCharityMockMvc.perform(post("/api/charities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charity)))
            .andExpect(status().isCreated());

        // Validate the Charity in the database
        List<Charity> charityList = charityRepository.findAll();
        assertThat(charityList).hasSize(databaseSizeBeforeCreate + 1);
        Charity testCharity = charityList.get(charityList.size() - 1);
        assertThat(testCharity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCharity.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testCharity.getEin()).isEqualTo(DEFAULT_EIN);
    }

    @Test
    @Transactional
    public void createCharityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = charityRepository.findAll().size();

        // Create the Charity with an existing ID
        Charity existingCharity = new Charity();
        existingCharity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharityMockMvc.perform(post("/api/charities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCharity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Charity> charityList = charityRepository.findAll();
        assertThat(charityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = charityRepository.findAll().size();
        // set the field null
        charity.setName(null);

        // Create the Charity, which fails.

        restCharityMockMvc.perform(post("/api/charities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charity)))
            .andExpect(status().isBadRequest());

        List<Charity> charityList = charityRepository.findAll();
        assertThat(charityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCharities() throws Exception {
        // Initialize the database
        charityRepository.saveAndFlush(charity);

        // Get all the charityList
        restCharityMockMvc.perform(get("/api/charities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(charity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].ein").value(hasItem(DEFAULT_EIN.toString())));
    }

    @Test
    @Transactional
    public void getCharity() throws Exception {
        // Initialize the database
        charityRepository.saveAndFlush(charity);

        // Get the charity
        restCharityMockMvc.perform(get("/api/charities/{id}", charity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(charity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.ein").value(DEFAULT_EIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCharity() throws Exception {
        // Get the charity
        restCharityMockMvc.perform(get("/api/charities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharity() throws Exception {
        // Initialize the database
        charityRepository.saveAndFlush(charity);
        int databaseSizeBeforeUpdate = charityRepository.findAll().size();

        // Update the charity
        Charity updatedCharity = charityRepository.findOne(charity.getId());
        updatedCharity
                .name(UPDATED_NAME)
                .website(UPDATED_WEBSITE)
                .ein(UPDATED_EIN);

        restCharityMockMvc.perform(put("/api/charities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCharity)))
            .andExpect(status().isOk());

        // Validate the Charity in the database
        List<Charity> charityList = charityRepository.findAll();
        assertThat(charityList).hasSize(databaseSizeBeforeUpdate);
        Charity testCharity = charityList.get(charityList.size() - 1);
        assertThat(testCharity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCharity.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testCharity.getEin()).isEqualTo(UPDATED_EIN);
    }

    @Test
    @Transactional
    public void updateNonExistingCharity() throws Exception {
        int databaseSizeBeforeUpdate = charityRepository.findAll().size();

        // Create the Charity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCharityMockMvc.perform(put("/api/charities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(charity)))
            .andExpect(status().isCreated());

        // Validate the Charity in the database
        List<Charity> charityList = charityRepository.findAll();
        assertThat(charityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCharity() throws Exception {
        // Initialize the database
        charityRepository.saveAndFlush(charity);
        int databaseSizeBeforeDelete = charityRepository.findAll().size();

        // Get the charity
        restCharityMockMvc.perform(delete("/api/charities/{id}", charity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Charity> charityList = charityRepository.findAll();
        assertThat(charityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Charity.class);
    }
}
