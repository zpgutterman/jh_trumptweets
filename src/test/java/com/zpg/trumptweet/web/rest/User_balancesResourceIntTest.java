package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.User_balances;
import com.zpg.trumptweet.repository.User_balancesRepository;
import com.zpg.trumptweet.service.User_balancesService;

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
 * Test class for the User_balancesResource REST controller.
 *
 * @see User_balancesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class User_balancesResourceIntTest {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    @Autowired
    private User_balancesRepository user_balancesRepository;

    @Autowired
    private User_balancesService user_balancesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restUser_balancesMockMvc;

    private User_balances user_balances;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User_balancesResource user_balancesResource = new User_balancesResource(user_balancesService);
        this.restUser_balancesMockMvc = MockMvcBuilders.standaloneSetup(user_balancesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User_balances createEntity(EntityManager em) {
        User_balances user_balances = new User_balances()
                .balance(DEFAULT_BALANCE);
        return user_balances;
    }

    @Before
    public void initTest() {
        user_balances = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser_balances() throws Exception {
        int databaseSizeBeforeCreate = user_balancesRepository.findAll().size();

        // Create the User_balances

        restUser_balancesMockMvc.perform(post("/api/user-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_balances)))
            .andExpect(status().isCreated());

        // Validate the User_balances in the database
        List<User_balances> user_balancesList = user_balancesRepository.findAll();
        assertThat(user_balancesList).hasSize(databaseSizeBeforeCreate + 1);
        User_balances testUser_balances = user_balancesList.get(user_balancesList.size() - 1);
        assertThat(testUser_balances.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    public void createUser_balancesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = user_balancesRepository.findAll().size();

        // Create the User_balances with an existing ID
        User_balances existingUser_balances = new User_balances();
        existingUser_balances.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser_balancesMockMvc.perform(post("/api/user-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUser_balances)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<User_balances> user_balancesList = user_balancesRepository.findAll();
        assertThat(user_balancesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUser_balances() throws Exception {
        // Initialize the database
        user_balancesRepository.saveAndFlush(user_balances);

        // Get all the user_balancesList
        restUser_balancesMockMvc.perform(get("/api/user-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user_balances.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getUser_balances() throws Exception {
        // Initialize the database
        user_balancesRepository.saveAndFlush(user_balances);

        // Get the user_balances
        restUser_balancesMockMvc.perform(get("/api/user-balances/{id}", user_balances.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user_balances.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUser_balances() throws Exception {
        // Get the user_balances
        restUser_balancesMockMvc.perform(get("/api/user-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser_balances() throws Exception {
        // Initialize the database
        user_balancesService.save(user_balances);

        int databaseSizeBeforeUpdate = user_balancesRepository.findAll().size();

        // Update the user_balances
        User_balances updatedUser_balances = user_balancesRepository.findOne(user_balances.getId());
        updatedUser_balances
                .balance(UPDATED_BALANCE);

        restUser_balancesMockMvc.perform(put("/api/user-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUser_balances)))
            .andExpect(status().isOk());

        // Validate the User_balances in the database
        List<User_balances> user_balancesList = user_balancesRepository.findAll();
        assertThat(user_balancesList).hasSize(databaseSizeBeforeUpdate);
        User_balances testUser_balances = user_balancesList.get(user_balancesList.size() - 1);
        assertThat(testUser_balances.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingUser_balances() throws Exception {
        int databaseSizeBeforeUpdate = user_balancesRepository.findAll().size();

        // Create the User_balances

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUser_balancesMockMvc.perform(put("/api/user-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_balances)))
            .andExpect(status().isCreated());

        // Validate the User_balances in the database
        List<User_balances> user_balancesList = user_balancesRepository.findAll();
        assertThat(user_balancesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUser_balances() throws Exception {
        // Initialize the database
        user_balancesService.save(user_balances);

        int databaseSizeBeforeDelete = user_balancesRepository.findAll().size();

        // Get the user_balances
        restUser_balancesMockMvc.perform(delete("/api/user-balances/{id}", user_balances.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User_balances> user_balancesList = user_balancesRepository.findAll();
        assertThat(user_balancesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User_balances.class);
    }
}
