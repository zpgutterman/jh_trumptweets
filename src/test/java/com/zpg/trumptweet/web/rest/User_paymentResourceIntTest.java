package com.zpg.trumptweet.web.rest;

import com.zpg.trumptweet.TrumptweetApp;

import com.zpg.trumptweet.domain.User_payment;
import com.zpg.trumptweet.domain.User;
import com.zpg.trumptweet.repository.User_paymentRepository;
import com.zpg.trumptweet.service.User_paymentService;

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
 * Test class for the User_paymentResource REST controller.
 *
 * @see User_paymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrumptweetApp.class)
public class User_paymentResourceIntTest {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_METHOD = "BBBBBBBBBB";

    @Autowired
    private User_paymentRepository user_paymentRepository;

    @Autowired
    private User_paymentService user_paymentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restUser_paymentMockMvc;

    private User_payment user_payment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        User_paymentResource user_paymentResource = new User_paymentResource(user_paymentService);
        this.restUser_paymentMockMvc = MockMvcBuilders.standaloneSetup(user_paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static User_payment createEntity(EntityManager em) {
        User_payment user_payment = new User_payment()
                .token(DEFAULT_TOKEN)
                .name(DEFAULT_NAME)
                .method(DEFAULT_METHOD);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        user_payment.setUser(user);
        return user_payment;
    }

    @Before
    public void initTest() {
        user_payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createUser_payment() throws Exception {
        int databaseSizeBeforeCreate = user_paymentRepository.findAll().size();

        // Create the User_payment

        restUser_paymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_payment)))
            .andExpect(status().isCreated());

        // Validate the User_payment in the database
        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeCreate + 1);
        User_payment testUser_payment = user_paymentList.get(user_paymentList.size() - 1);
        assertThat(testUser_payment.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testUser_payment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUser_payment.getMethod()).isEqualTo(DEFAULT_METHOD);
    }

    @Test
    @Transactional
    public void createUser_paymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = user_paymentRepository.findAll().size();

        // Create the User_payment with an existing ID
        User_payment existingUser_payment = new User_payment();
        existingUser_payment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUser_paymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUser_payment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = user_paymentRepository.findAll().size();
        // set the field null
        user_payment.setToken(null);

        // Create the User_payment, which fails.

        restUser_paymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_payment)))
            .andExpect(status().isBadRequest());

        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = user_paymentRepository.findAll().size();
        // set the field null
        user_payment.setMethod(null);

        // Create the User_payment, which fails.

        restUser_paymentMockMvc.perform(post("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_payment)))
            .andExpect(status().isBadRequest());

        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUser_payments() throws Exception {
        // Initialize the database
        user_paymentRepository.saveAndFlush(user_payment);

        // Get all the user_paymentList
        restUser_paymentMockMvc.perform(get("/api/user-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(user_payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].method").value(hasItem(DEFAULT_METHOD.toString())));
    }

    @Test
    @Transactional
    public void getUser_payment() throws Exception {
        // Initialize the database
        user_paymentRepository.saveAndFlush(user_payment);

        // Get the user_payment
        restUser_paymentMockMvc.perform(get("/api/user-payments/{id}", user_payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(user_payment.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.method").value(DEFAULT_METHOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUser_payment() throws Exception {
        // Get the user_payment
        restUser_paymentMockMvc.perform(get("/api/user-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser_payment() throws Exception {
        // Initialize the database
        user_paymentService.save(user_payment);

        int databaseSizeBeforeUpdate = user_paymentRepository.findAll().size();

        // Update the user_payment
        User_payment updatedUser_payment = user_paymentRepository.findOne(user_payment.getId());
        updatedUser_payment
                .token(UPDATED_TOKEN)
                .name(UPDATED_NAME)
                .method(UPDATED_METHOD);

        restUser_paymentMockMvc.perform(put("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUser_payment)))
            .andExpect(status().isOk());

        // Validate the User_payment in the database
        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeUpdate);
        User_payment testUser_payment = user_paymentList.get(user_paymentList.size() - 1);
        assertThat(testUser_payment.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testUser_payment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUser_payment.getMethod()).isEqualTo(UPDATED_METHOD);
    }

    @Test
    @Transactional
    public void updateNonExistingUser_payment() throws Exception {
        int databaseSizeBeforeUpdate = user_paymentRepository.findAll().size();

        // Create the User_payment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUser_paymentMockMvc.perform(put("/api/user-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(user_payment)))
            .andExpect(status().isCreated());

        // Validate the User_payment in the database
        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUser_payment() throws Exception {
        // Initialize the database
        user_paymentService.save(user_payment);

        int databaseSizeBeforeDelete = user_paymentRepository.findAll().size();

        // Get the user_payment
        restUser_paymentMockMvc.perform(delete("/api/user-payments/{id}", user_payment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<User_payment> user_paymentList = user_paymentRepository.findAll();
        assertThat(user_paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User_payment.class);
    }
}
