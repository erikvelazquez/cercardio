package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PrivateHealthInsurance;
import mycercardiopackege.jh.repository.PrivateHealthInsuranceRepository;
import mycercardiopackege.jh.repository.search.PrivateHealthInsuranceSearchRepository;
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
import java.util.List;

import static mycercardiopackege.jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrivateHealthInsuranceResource REST controller.
 *
 * @see PrivateHealthInsuranceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PrivateHealthInsuranceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private PrivateHealthInsuranceRepository privateHealthInsuranceRepository;

    @Autowired
    private PrivateHealthInsuranceSearchRepository privateHealthInsuranceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrivateHealthInsuranceMockMvc;

    private PrivateHealthInsurance privateHealthInsurance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrivateHealthInsuranceResource privateHealthInsuranceResource = new PrivateHealthInsuranceResource(privateHealthInsuranceRepository, privateHealthInsuranceSearchRepository);
        this.restPrivateHealthInsuranceMockMvc = MockMvcBuilders.standaloneSetup(privateHealthInsuranceResource)
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
    public static PrivateHealthInsurance createEntity(EntityManager em) {
        PrivateHealthInsurance privateHealthInsurance = new PrivateHealthInsurance()
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE);
        return privateHealthInsurance;
    }

    @Before
    public void initTest() {
        privateHealthInsuranceSearchRepository.deleteAll();
        privateHealthInsurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrivateHealthInsurance() throws Exception {
        int databaseSizeBeforeCreate = privateHealthInsuranceRepository.findAll().size();

        // Create the PrivateHealthInsurance
        restPrivateHealthInsuranceMockMvc.perform(post("/api/private-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privateHealthInsurance)))
            .andExpect(status().isCreated());

        // Validate the PrivateHealthInsurance in the database
        List<PrivateHealthInsurance> privateHealthInsuranceList = privateHealthInsuranceRepository.findAll();
        assertThat(privateHealthInsuranceList).hasSize(databaseSizeBeforeCreate + 1);
        PrivateHealthInsurance testPrivateHealthInsurance = privateHealthInsuranceList.get(privateHealthInsuranceList.size() - 1);
        assertThat(testPrivateHealthInsurance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrivateHealthInsurance.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the PrivateHealthInsurance in Elasticsearch
        PrivateHealthInsurance privateHealthInsuranceEs = privateHealthInsuranceSearchRepository.findOne(testPrivateHealthInsurance.getId());
        assertThat(privateHealthInsuranceEs).isEqualToComparingFieldByField(testPrivateHealthInsurance);
    }

    @Test
    @Transactional
    public void createPrivateHealthInsuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = privateHealthInsuranceRepository.findAll().size();

        // Create the PrivateHealthInsurance with an existing ID
        privateHealthInsurance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivateHealthInsuranceMockMvc.perform(post("/api/private-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privateHealthInsurance)))
            .andExpect(status().isBadRequest());

        // Validate the PrivateHealthInsurance in the database
        List<PrivateHealthInsurance> privateHealthInsuranceList = privateHealthInsuranceRepository.findAll();
        assertThat(privateHealthInsuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrivateHealthInsurances() throws Exception {
        // Initialize the database
        privateHealthInsuranceRepository.saveAndFlush(privateHealthInsurance);

        // Get all the privateHealthInsuranceList
        restPrivateHealthInsuranceMockMvc.perform(get("/api/private-health-insurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privateHealthInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrivateHealthInsurance() throws Exception {
        // Initialize the database
        privateHealthInsuranceRepository.saveAndFlush(privateHealthInsurance);

        // Get the privateHealthInsurance
        restPrivateHealthInsuranceMockMvc.perform(get("/api/private-health-insurances/{id}", privateHealthInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(privateHealthInsurance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrivateHealthInsurance() throws Exception {
        // Get the privateHealthInsurance
        restPrivateHealthInsuranceMockMvc.perform(get("/api/private-health-insurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrivateHealthInsurance() throws Exception {
        // Initialize the database
        privateHealthInsuranceRepository.saveAndFlush(privateHealthInsurance);
        privateHealthInsuranceSearchRepository.save(privateHealthInsurance);
        int databaseSizeBeforeUpdate = privateHealthInsuranceRepository.findAll().size();

        // Update the privateHealthInsurance
        PrivateHealthInsurance updatedPrivateHealthInsurance = privateHealthInsuranceRepository.findOne(privateHealthInsurance.getId());
        updatedPrivateHealthInsurance
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE);

        restPrivateHealthInsuranceMockMvc.perform(put("/api/private-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrivateHealthInsurance)))
            .andExpect(status().isOk());

        // Validate the PrivateHealthInsurance in the database
        List<PrivateHealthInsurance> privateHealthInsuranceList = privateHealthInsuranceRepository.findAll();
        assertThat(privateHealthInsuranceList).hasSize(databaseSizeBeforeUpdate);
        PrivateHealthInsurance testPrivateHealthInsurance = privateHealthInsuranceList.get(privateHealthInsuranceList.size() - 1);
        assertThat(testPrivateHealthInsurance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrivateHealthInsurance.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the PrivateHealthInsurance in Elasticsearch
        PrivateHealthInsurance privateHealthInsuranceEs = privateHealthInsuranceSearchRepository.findOne(testPrivateHealthInsurance.getId());
        assertThat(privateHealthInsuranceEs).isEqualToComparingFieldByField(testPrivateHealthInsurance);
    }

    @Test
    @Transactional
    public void updateNonExistingPrivateHealthInsurance() throws Exception {
        int databaseSizeBeforeUpdate = privateHealthInsuranceRepository.findAll().size();

        // Create the PrivateHealthInsurance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrivateHealthInsuranceMockMvc.perform(put("/api/private-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privateHealthInsurance)))
            .andExpect(status().isCreated());

        // Validate the PrivateHealthInsurance in the database
        List<PrivateHealthInsurance> privateHealthInsuranceList = privateHealthInsuranceRepository.findAll();
        assertThat(privateHealthInsuranceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrivateHealthInsurance() throws Exception {
        // Initialize the database
        privateHealthInsuranceRepository.saveAndFlush(privateHealthInsurance);
        privateHealthInsuranceSearchRepository.save(privateHealthInsurance);
        int databaseSizeBeforeDelete = privateHealthInsuranceRepository.findAll().size();

        // Get the privateHealthInsurance
        restPrivateHealthInsuranceMockMvc.perform(delete("/api/private-health-insurances/{id}", privateHealthInsurance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean privateHealthInsuranceExistsInEs = privateHealthInsuranceSearchRepository.exists(privateHealthInsurance.getId());
        assertThat(privateHealthInsuranceExistsInEs).isFalse();

        // Validate the database is empty
        List<PrivateHealthInsurance> privateHealthInsuranceList = privateHealthInsuranceRepository.findAll();
        assertThat(privateHealthInsuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPrivateHealthInsurance() throws Exception {
        // Initialize the database
        privateHealthInsuranceRepository.saveAndFlush(privateHealthInsurance);
        privateHealthInsuranceSearchRepository.save(privateHealthInsurance);

        // Search the privateHealthInsurance
        restPrivateHealthInsuranceMockMvc.perform(get("/api/_search/private-health-insurances?query=id:" + privateHealthInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privateHealthInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrivateHealthInsurance.class);
        PrivateHealthInsurance privateHealthInsurance1 = new PrivateHealthInsurance();
        privateHealthInsurance1.setId(1L);
        PrivateHealthInsurance privateHealthInsurance2 = new PrivateHealthInsurance();
        privateHealthInsurance2.setId(privateHealthInsurance1.getId());
        assertThat(privateHealthInsurance1).isEqualTo(privateHealthInsurance2);
        privateHealthInsurance2.setId(2L);
        assertThat(privateHealthInsurance1).isNotEqualTo(privateHealthInsurance2);
        privateHealthInsurance1.setId(null);
        assertThat(privateHealthInsurance1).isNotEqualTo(privateHealthInsurance2);
    }
}
