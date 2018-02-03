package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.CivilStatus;
import mycercardiopackege.jh.repository.CivilStatusRepository;
import mycercardiopackege.jh.repository.search.CivilStatusSearchRepository;
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
 * Test class for the CivilStatusResource REST controller.
 *
 * @see CivilStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class CivilStatusResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private CivilStatusRepository civilStatusRepository;

    @Autowired
    private CivilStatusSearchRepository civilStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCivilStatusMockMvc;

    private CivilStatus civilStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CivilStatusResource civilStatusResource = new CivilStatusResource(civilStatusRepository, civilStatusSearchRepository);
        this.restCivilStatusMockMvc = MockMvcBuilders.standaloneSetup(civilStatusResource)
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
    public static CivilStatus createEntity(EntityManager em) {
        CivilStatus civilStatus = new CivilStatus()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE);
        return civilStatus;
    }

    @Before
    public void initTest() {
        civilStatusSearchRepository.deleteAll();
        civilStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCivilStatus() throws Exception {
        int databaseSizeBeforeCreate = civilStatusRepository.findAll().size();

        // Create the CivilStatus
        restCivilStatusMockMvc.perform(post("/api/civil-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilStatus)))
            .andExpect(status().isCreated());

        // Validate the CivilStatus in the database
        List<CivilStatus> civilStatusList = civilStatusRepository.findAll();
        assertThat(civilStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CivilStatus testCivilStatus = civilStatusList.get(civilStatusList.size() - 1);
        assertThat(testCivilStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCivilStatus.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the CivilStatus in Elasticsearch
        CivilStatus civilStatusEs = civilStatusSearchRepository.findOne(testCivilStatus.getId());
        assertThat(civilStatusEs).isEqualToComparingFieldByField(testCivilStatus);
    }

    @Test
    @Transactional
    public void createCivilStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = civilStatusRepository.findAll().size();

        // Create the CivilStatus with an existing ID
        civilStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCivilStatusMockMvc.perform(post("/api/civil-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CivilStatus in the database
        List<CivilStatus> civilStatusList = civilStatusRepository.findAll();
        assertThat(civilStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCivilStatuses() throws Exception {
        // Initialize the database
        civilStatusRepository.saveAndFlush(civilStatus);

        // Get all the civilStatusList
        restCivilStatusMockMvc.perform(get("/api/civil-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civilStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCivilStatus() throws Exception {
        // Initialize the database
        civilStatusRepository.saveAndFlush(civilStatus);

        // Get the civilStatus
        restCivilStatusMockMvc.perform(get("/api/civil-statuses/{id}", civilStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(civilStatus.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCivilStatus() throws Exception {
        // Get the civilStatus
        restCivilStatusMockMvc.perform(get("/api/civil-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCivilStatus() throws Exception {
        // Initialize the database
        civilStatusRepository.saveAndFlush(civilStatus);
        civilStatusSearchRepository.save(civilStatus);
        int databaseSizeBeforeUpdate = civilStatusRepository.findAll().size();

        // Update the civilStatus
        CivilStatus updatedCivilStatus = civilStatusRepository.findOne(civilStatus.getId());
        updatedCivilStatus
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE);

        restCivilStatusMockMvc.perform(put("/api/civil-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCivilStatus)))
            .andExpect(status().isOk());

        // Validate the CivilStatus in the database
        List<CivilStatus> civilStatusList = civilStatusRepository.findAll();
        assertThat(civilStatusList).hasSize(databaseSizeBeforeUpdate);
        CivilStatus testCivilStatus = civilStatusList.get(civilStatusList.size() - 1);
        assertThat(testCivilStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCivilStatus.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the CivilStatus in Elasticsearch
        CivilStatus civilStatusEs = civilStatusSearchRepository.findOne(testCivilStatus.getId());
        assertThat(civilStatusEs).isEqualToComparingFieldByField(testCivilStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingCivilStatus() throws Exception {
        int databaseSizeBeforeUpdate = civilStatusRepository.findAll().size();

        // Create the CivilStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCivilStatusMockMvc.perform(put("/api/civil-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(civilStatus)))
            .andExpect(status().isCreated());

        // Validate the CivilStatus in the database
        List<CivilStatus> civilStatusList = civilStatusRepository.findAll();
        assertThat(civilStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCivilStatus() throws Exception {
        // Initialize the database
        civilStatusRepository.saveAndFlush(civilStatus);
        civilStatusSearchRepository.save(civilStatus);
        int databaseSizeBeforeDelete = civilStatusRepository.findAll().size();

        // Get the civilStatus
        restCivilStatusMockMvc.perform(delete("/api/civil-statuses/{id}", civilStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean civilStatusExistsInEs = civilStatusSearchRepository.exists(civilStatus.getId());
        assertThat(civilStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<CivilStatus> civilStatusList = civilStatusRepository.findAll();
        assertThat(civilStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCivilStatus() throws Exception {
        // Initialize the database
        civilStatusRepository.saveAndFlush(civilStatus);
        civilStatusSearchRepository.save(civilStatus);

        // Search the civilStatus
        restCivilStatusMockMvc.perform(get("/api/_search/civil-statuses?query=id:" + civilStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(civilStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CivilStatus.class);
        CivilStatus civilStatus1 = new CivilStatus();
        civilStatus1.setId(1L);
        CivilStatus civilStatus2 = new CivilStatus();
        civilStatus2.setId(civilStatus1.getId());
        assertThat(civilStatus1).isEqualTo(civilStatus2);
        civilStatus2.setId(2L);
        assertThat(civilStatus1).isNotEqualTo(civilStatus2);
        civilStatus1.setId(null);
        assertThat(civilStatus1).isNotEqualTo(civilStatus2);
    }
}
