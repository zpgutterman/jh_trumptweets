package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.User_preferences;
import com.zpg.trumptweet.repository.User_preferencesRepository;

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
 * Test class for the User_preferencesResource REST controller.
 *
 * @see User_preferencesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class User_preferencesResourceIntTest {

    @Autowired
    private User_preferencesRepository user_preferencesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restUser_preferencesMockMvc;

    private User_preferences user_preferences;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            User_preferencesResource user_preferencesResource = new User_preferencesResource(user_preferencesRepository);
        this.restUser_preferencesMockMvc = MockMvcBuilders.standaloneSetup(user_preferencesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User_preferences createEntity(EntityManager em) {
        User_preferences user_preferences = new User_preferences();
        return user_preferences;
    }

    @Before
    public void initTest() {
        user_preferences = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser_preferences() throws Exception {
        int databaseSizeBeforeCreate = user_preferencesRepository.findAll().size();

        // Create the User_preferences

        restUser_preferencesMockMvc.perform(post("/api/user-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_preferences)))
            .andExpect(status().isCreated());

        // Validate the User_preferences in the database
        List<User_preferences> user_preferencesList = user_preferencesRepository.findAll();
        assertThat(user_preferencesList).hasSize(databaseSizeBeforeCreate + 1);
        User_preferences testUser_preferences = user_preferencesList.get(user_preferencesList.size() - 1);
    }

    @Test
    @Transactional
    public void createUser_preferencesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = user_preferencesRepository.findAll().size();

        // Create the User_preferences with an existing ID
        User_preferences existingUser_preferences = new User_preferences();
        existingUser_preferences.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser_preferencesMockMvc.perform(post("/api/user-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUser_preferences)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<User_preferences> user_preferencesList = user_preferencesRepository.findAll();
        assertThat(user_preferencesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUser_preferences() throws Exception {
        // Initialize the database
        user_preferencesRepository.saveAndFlush(user_preferences);

        // Get all the user_preferencesList
        restUser_preferencesMockMvc.perform(get("/api/user-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user_preferences.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUser_preferences() throws Exception {
        // Initialize the database
        user_preferencesRepository.saveAndFlush(user_preferences);

        // Get the user_preferences
        restUser_preferencesMockMvc.perform(get("/api/user-preferences/{id}", user_preferences.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user_preferences.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUser_preferences() throws Exception {
        // Get the user_preferences
        restUser_preferencesMockMvc.perform(get("/api/user-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser_preferences() throws Exception {
        // Initialize the database
        user_preferencesRepository.saveAndFlush(user_preferences);
        int databaseSizeBeforeUpdate = user_preferencesRepository.findAll().size();

        // Update the user_preferences
        User_preferences updatedUser_preferences = user_preferencesRepository.findOne(user_preferences.getId());

        restUser_preferencesMockMvc.perform(put("/api/user-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUser_preferences)))
            .andExpect(status().isOk());

        // Validate the User_preferences in the database
        List<User_preferences> user_preferencesList = user_preferencesRepository.findAll();
        assertThat(user_preferencesList).hasSize(databaseSizeBeforeUpdate);
        User_preferences testUser_preferences = user_preferencesList.get(user_preferencesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUser_preferences() throws Exception {
        int databaseSizeBeforeUpdate = user_preferencesRepository.findAll().size();

        // Create the User_preferences

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUser_preferencesMockMvc.perform(put("/api/user-preferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_preferences)))
            .andExpect(status().isCreated());

        // Validate the User_preferences in the database
        List<User_preferences> user_preferencesList = user_preferencesRepository.findAll();
        assertThat(user_preferencesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUser_preferences() throws Exception {
        // Initialize the database
        user_preferencesRepository.saveAndFlush(user_preferences);
        int databaseSizeBeforeDelete = user_preferencesRepository.findAll().size();

        // Get the user_preferences
        restUser_preferencesMockMvc.perform(delete("/api/user-preferences/{id}", user_preferences.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User_preferences> user_preferencesList = user_preferencesRepository.findAll();
        assertThat(user_preferencesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User_preferences.class);
    }
}
