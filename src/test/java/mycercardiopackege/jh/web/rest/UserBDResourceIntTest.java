package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.UserBD;
import mycercardiopackege.jh.repository.UserBDRepository;
import mycercardiopackege.jh.repository.search.UserBDSearchRepository;
import mycercardiopackege.jh.web.rest.errors.ExceptionTranslator;

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

import static mycercardiopackege.jh.web.rest.TestUtil.sameInstant;
import static mycercardiopackege.jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserBDResource REST controller.
 *
 * @see UserBDResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class UserBDResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private UserBDRepository userBDRepository;

    @Autowired
    private UserBDSearchRepository userBDSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserBDMockMvc;

    private UserBD userBD;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserBDResource userBDResource = new UserBDResource(userBDRepository, userBDSearchRepository);
        this.restUserBDMockMvc = MockMvcBuilders.standaloneSetup(userBDResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBD createEntity(EntityManager em) {
        UserBD userBD = new UserBD()
            .username(DEFAULT_USERNAME)
            .name(DEFAULT_NAME)
            .lastname(DEFAULT_LASTNAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .imagen(DEFAULT_IMAGEN)
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT);
        return userBD;
    }

    @Before
    public void initTest() {
        userBDSearchRepository.deleteAll();
        userBD = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBD() throws Exception {
        int databaseSizeBeforeCreate = userBDRepository.findAll().size();

        // Create the UserBD
        restUserBDMockMvc.perform(post("/api/user-bds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBD)))
            .andExpect(status().isCreated());

        // Validate the UserBD in the database
        List<UserBD> userBDList = userBDRepository.findAll();
        assertThat(userBDList).hasSize(databaseSizeBeforeCreate + 1);
        UserBD testUserBD = userBDList.get(userBDList.size() - 1);
        assertThat(testUserBD.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserBD.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserBD.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testUserBD.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserBD.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserBD.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testUserBD.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testUserBD.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the UserBD in Elasticsearch
        UserBD userBDEs = userBDSearchRepository.findOne(testUserBD.getId());
        assertThat(userBDEs).isEqualToComparingFieldByField(testUserBD);
    }

    @Test
    @Transactional
    public void createUserBDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBDRepository.findAll().size();

        // Create the UserBD with an existing ID
        userBD.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBDMockMvc.perform(post("/api/user-bds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBD)))
            .andExpect(status().isBadRequest());

        // Validate the UserBD in the database
        List<UserBD> userBDList = userBDRepository.findAll();
        assertThat(userBDList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserBDS() throws Exception {
        // Initialize the database
        userBDRepository.saveAndFlush(userBD);

        // Get all the userBDList
        restUserBDMockMvc.perform(get("/api/user-bds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBD.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void getUserBD() throws Exception {
        // Initialize the database
        userBDRepository.saveAndFlush(userBD);

        // Get the userBD
        restUserBDMockMvc.perform(get("/api/user-bds/{id}", userBD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userBD.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingUserBD() throws Exception {
        // Get the userBD
        restUserBDMockMvc.perform(get("/api/user-bds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBD() throws Exception {
        // Initialize the database
        userBDRepository.saveAndFlush(userBD);
        userBDSearchRepository.save(userBD);
        int databaseSizeBeforeUpdate = userBDRepository.findAll().size();

        // Update the userBD
        UserBD updatedUserBD = userBDRepository.findOne(userBD.getId());
        updatedUserBD
            .username(UPDATED_USERNAME)
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .imagen(UPDATED_IMAGEN)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT);

        restUserBDMockMvc.perform(put("/api/user-bds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserBD)))
            .andExpect(status().isOk());

        // Validate the UserBD in the database
        List<UserBD> userBDList = userBDRepository.findAll();
        assertThat(userBDList).hasSize(databaseSizeBeforeUpdate);
        UserBD testUserBD = userBDList.get(userBDList.size() - 1);
        assertThat(testUserBD.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserBD.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserBD.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testUserBD.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserBD.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserBD.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testUserBD.isIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testUserBD.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the UserBD in Elasticsearch
        UserBD userBDEs = userBDSearchRepository.findOne(testUserBD.getId());
        assertThat(userBDEs).isEqualToComparingFieldByField(testUserBD);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBD() throws Exception {
        int databaseSizeBeforeUpdate = userBDRepository.findAll().size();

        // Create the UserBD

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserBDMockMvc.perform(put("/api/user-bds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBD)))
            .andExpect(status().isCreated());

        // Validate the UserBD in the database
        List<UserBD> userBDList = userBDRepository.findAll();
        assertThat(userBDList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserBD() throws Exception {
        // Initialize the database
        userBDRepository.saveAndFlush(userBD);
        userBDSearchRepository.save(userBD);
        int databaseSizeBeforeDelete = userBDRepository.findAll().size();

        // Get the userBD
        restUserBDMockMvc.perform(delete("/api/user-bds/{id}", userBD.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userBDExistsInEs = userBDSearchRepository.exists(userBD.getId());
        assertThat(userBDExistsInEs).isFalse();

        // Validate the database is empty
        List<UserBD> userBDList = userBDRepository.findAll();
        assertThat(userBDList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserBD() throws Exception {
        // Initialize the database
        userBDRepository.saveAndFlush(userBD);
        userBDSearchRepository.save(userBD);

        // Search the userBD
        restUserBDMockMvc.perform(get("/api/_search/user-bds?query=id:" + userBD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBD.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBD.class);
        UserBD userBD1 = new UserBD();
        userBD1.setId(1L);
        UserBD userBD2 = new UserBD();
        userBD2.setId(userBD1.getId());
        assertThat(userBD1).isEqualTo(userBD2);
        userBD2.setId(2L);
        assertThat(userBD1).isNotEqualTo(userBD2);
        userBD1.setId(null);
        assertThat(userBD1).isNotEqualTo(userBD2);
    }
}
