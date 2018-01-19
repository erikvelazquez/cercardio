package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.SocialHealthInsurance;
import mycercardiopackege.jh.repository.SocialHealthInsuranceRepository;
import mycercardiopackege.jh.repository.search.SocialHealthInsuranceSearchRepository;
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
 * Test class for the SocialHealthInsuranceResource REST controller.
 *
 * @see SocialHealthInsuranceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class SocialHealthInsuranceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private SocialHealthInsuranceRepository socialHealthInsuranceRepository;

    @Autowired
    private SocialHealthInsuranceSearchRepository socialHealthInsuranceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSocialHealthInsuranceMockMvc;

    private SocialHealthInsurance socialHealthInsurance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocialHealthInsuranceResource socialHealthInsuranceResource = new SocialHealthInsuranceResource(socialHealthInsuranceRepository, socialHealthInsuranceSearchRepository);
        this.restSocialHealthInsuranceMockMvc = MockMvcBuilders.standaloneSetup(socialHealthInsuranceResource)
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
    public static SocialHealthInsurance createEntity(EntityManager em) {
        SocialHealthInsurance socialHealthInsurance = new SocialHealthInsurance()
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE);
        return socialHealthInsurance;
    }

    @Before
    public void initTest() {
        socialHealthInsuranceSearchRepository.deleteAll();
        socialHealthInsurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialHealthInsurance() throws Exception {
        int databaseSizeBeforeCreate = socialHealthInsuranceRepository.findAll().size();

        // Create the SocialHealthInsurance
        restSocialHealthInsuranceMockMvc.perform(post("/api/social-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialHealthInsurance)))
            .andExpect(status().isCreated());

        // Validate the SocialHealthInsurance in the database
        List<SocialHealthInsurance> socialHealthInsuranceList = socialHealthInsuranceRepository.findAll();
        assertThat(socialHealthInsuranceList).hasSize(databaseSizeBeforeCreate + 1);
        SocialHealthInsurance testSocialHealthInsurance = socialHealthInsuranceList.get(socialHealthInsuranceList.size() - 1);
        assertThat(testSocialHealthInsurance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialHealthInsurance.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the SocialHealthInsurance in Elasticsearch
        SocialHealthInsurance socialHealthInsuranceEs = socialHealthInsuranceSearchRepository.findOne(testSocialHealthInsurance.getId());
        assertThat(socialHealthInsuranceEs).isEqualToComparingFieldByField(testSocialHealthInsurance);
    }

    @Test
    @Transactional
    public void createSocialHealthInsuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialHealthInsuranceRepository.findAll().size();

        // Create the SocialHealthInsurance with an existing ID
        socialHealthInsurance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialHealthInsuranceMockMvc.perform(post("/api/social-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialHealthInsurance)))
            .andExpect(status().isBadRequest());

        // Validate the SocialHealthInsurance in the database
        List<SocialHealthInsurance> socialHealthInsuranceList = socialHealthInsuranceRepository.findAll();
        assertThat(socialHealthInsuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocialHealthInsurances() throws Exception {
        // Initialize the database
        socialHealthInsuranceRepository.saveAndFlush(socialHealthInsurance);

        // Get all the socialHealthInsuranceList
        restSocialHealthInsuranceMockMvc.perform(get("/api/social-health-insurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialHealthInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getSocialHealthInsurance() throws Exception {
        // Initialize the database
        socialHealthInsuranceRepository.saveAndFlush(socialHealthInsurance);

        // Get the socialHealthInsurance
        restSocialHealthInsuranceMockMvc.perform(get("/api/social-health-insurances/{id}", socialHealthInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialHealthInsurance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialHealthInsurance() throws Exception {
        // Get the socialHealthInsurance
        restSocialHealthInsuranceMockMvc.perform(get("/api/social-health-insurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialHealthInsurance() throws Exception {
        // Initialize the database
        socialHealthInsuranceRepository.saveAndFlush(socialHealthInsurance);
        socialHealthInsuranceSearchRepository.save(socialHealthInsurance);
        int databaseSizeBeforeUpdate = socialHealthInsuranceRepository.findAll().size();

        // Update the socialHealthInsurance
        SocialHealthInsurance updatedSocialHealthInsurance = socialHealthInsuranceRepository.findOne(socialHealthInsurance.getId());
        updatedSocialHealthInsurance
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE);

        restSocialHealthInsuranceMockMvc.perform(put("/api/social-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocialHealthInsurance)))
            .andExpect(status().isOk());

        // Validate the SocialHealthInsurance in the database
        List<SocialHealthInsurance> socialHealthInsuranceList = socialHealthInsuranceRepository.findAll();
        assertThat(socialHealthInsuranceList).hasSize(databaseSizeBeforeUpdate);
        SocialHealthInsurance testSocialHealthInsurance = socialHealthInsuranceList.get(socialHealthInsuranceList.size() - 1);
        assertThat(testSocialHealthInsurance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialHealthInsurance.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the SocialHealthInsurance in Elasticsearch
        SocialHealthInsurance socialHealthInsuranceEs = socialHealthInsuranceSearchRepository.findOne(testSocialHealthInsurance.getId());
        assertThat(socialHealthInsuranceEs).isEqualToComparingFieldByField(testSocialHealthInsurance);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialHealthInsurance() throws Exception {
        int databaseSizeBeforeUpdate = socialHealthInsuranceRepository.findAll().size();

        // Create the SocialHealthInsurance

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocialHealthInsuranceMockMvc.perform(put("/api/social-health-insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialHealthInsurance)))
            .andExpect(status().isCreated());

        // Validate the SocialHealthInsurance in the database
        List<SocialHealthInsurance> socialHealthInsuranceList = socialHealthInsuranceRepository.findAll();
        assertThat(socialHealthInsuranceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocialHealthInsurance() throws Exception {
        // Initialize the database
        socialHealthInsuranceRepository.saveAndFlush(socialHealthInsurance);
        socialHealthInsuranceSearchRepository.save(socialHealthInsurance);
        int databaseSizeBeforeDelete = socialHealthInsuranceRepository.findAll().size();

        // Get the socialHealthInsurance
        restSocialHealthInsuranceMockMvc.perform(delete("/api/social-health-insurances/{id}", socialHealthInsurance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean socialHealthInsuranceExistsInEs = socialHealthInsuranceSearchRepository.exists(socialHealthInsurance.getId());
        assertThat(socialHealthInsuranceExistsInEs).isFalse();

        // Validate the database is empty
        List<SocialHealthInsurance> socialHealthInsuranceList = socialHealthInsuranceRepository.findAll();
        assertThat(socialHealthInsuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSocialHealthInsurance() throws Exception {
        // Initialize the database
        socialHealthInsuranceRepository.saveAndFlush(socialHealthInsurance);
        socialHealthInsuranceSearchRepository.save(socialHealthInsurance);

        // Search the socialHealthInsurance
        restSocialHealthInsuranceMockMvc.perform(get("/api/_search/social-health-insurances?query=id:" + socialHealthInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialHealthInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialHealthInsurance.class);
        SocialHealthInsurance socialHealthInsurance1 = new SocialHealthInsurance();
        socialHealthInsurance1.setId(1L);
        SocialHealthInsurance socialHealthInsurance2 = new SocialHealthInsurance();
        socialHealthInsurance2.setId(socialHealthInsurance1.getId());
        assertThat(socialHealthInsurance1).isEqualTo(socialHealthInsurance2);
        socialHealthInsurance2.setId(2L);
        assertThat(socialHealthInsurance1).isNotEqualTo(socialHealthInsurance2);
        socialHealthInsurance1.setId(null);
        assertThat(socialHealthInsurance1).isNotEqualTo(socialHealthInsurance2);
    }
}
