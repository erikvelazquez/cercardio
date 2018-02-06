package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientMedicalData;
import mycercardiopackege.jh.repository.PacientMedicalDataRepository;
import mycercardiopackege.jh.repository.search.PacientMedicalDataSearchRepository;
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
 * Test class for the PacientMedicalDataResource REST controller.
 *
 * @see PacientMedicalDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientMedicalDataResourceIntTest {

    private static final String DEFAULT_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_SURGICALINTERVENTIONS = "AAAAAAAAAA";
    private static final String UPDATED_SURGICALINTERVENTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_USEOFDRUGS = "AAAAAAAAAA";
    private static final String UPDATED_USEOFDRUGS = "BBBBBBBBBB";

    private static final String DEFAULT_ALLERGIES = "AAAAAAAAAA";
    private static final String UPDATED_ALLERGIES = "BBBBBBBBBB";

    private static final String DEFAULT_AHFFATHER = "AAAAAAAAAA";
    private static final String UPDATED_AHFFATHER = "BBBBBBBBBB";

    private static final String DEFAULT_AHFMOTHER = "AAAAAAAAAA";
    private static final String UPDATED_AHFMOTHER = "BBBBBBBBBB";

    private static final String DEFAULT_AHFOTHERS = "AAAAAAAAAA";
    private static final String UPDATED_AHFOTHERS = "BBBBBBBBBB";

    @Autowired
    private PacientMedicalDataRepository pacientMedicalDataRepository;

    @Autowired
    private PacientMedicalDataSearchRepository pacientMedicalDataSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientMedicalDataMockMvc;

    private PacientMedicalData pacientMedicalData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientMedicalDataResource pacientMedicalDataResource = new PacientMedicalDataResource(pacientMedicalDataRepository, pacientMedicalDataSearchRepository);
        this.restPacientMedicalDataMockMvc = MockMvcBuilders.standaloneSetup(pacientMedicalDataResource)
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
    public static PacientMedicalData createEntity(EntityManager em) {
        PacientMedicalData pacientMedicalData = new PacientMedicalData()
            .diseases(DEFAULT_DISEASES)
            .surgicalinterventions(DEFAULT_SURGICALINTERVENTIONS)
            .useofdrugs(DEFAULT_USEOFDRUGS)
            .allergies(DEFAULT_ALLERGIES)
            .ahffather(DEFAULT_AHFFATHER)
            .ahfmother(DEFAULT_AHFMOTHER)
            .ahfothers(DEFAULT_AHFOTHERS);
        return pacientMedicalData;
    }

    @Before
    public void initTest() {
        pacientMedicalDataSearchRepository.deleteAll();
        pacientMedicalData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientMedicalData() throws Exception {
        int databaseSizeBeforeCreate = pacientMedicalDataRepository.findAll().size();

        // Create the PacientMedicalData
        restPacientMedicalDataMockMvc.perform(post("/api/pacient-medical-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientMedicalData)))
            .andExpect(status().isCreated());

        // Validate the PacientMedicalData in the database
        List<PacientMedicalData> pacientMedicalDataList = pacientMedicalDataRepository.findAll();
        assertThat(pacientMedicalDataList).hasSize(databaseSizeBeforeCreate + 1);
        PacientMedicalData testPacientMedicalData = pacientMedicalDataList.get(pacientMedicalDataList.size() - 1);
        assertThat(testPacientMedicalData.getDiseases()).isEqualTo(DEFAULT_DISEASES);
        assertThat(testPacientMedicalData.getSurgicalinterventions()).isEqualTo(DEFAULT_SURGICALINTERVENTIONS);
        assertThat(testPacientMedicalData.getUseofdrugs()).isEqualTo(DEFAULT_USEOFDRUGS);
        assertThat(testPacientMedicalData.getAllergies()).isEqualTo(DEFAULT_ALLERGIES);
        assertThat(testPacientMedicalData.getAhffather()).isEqualTo(DEFAULT_AHFFATHER);
        assertThat(testPacientMedicalData.getAhfmother()).isEqualTo(DEFAULT_AHFMOTHER);
        assertThat(testPacientMedicalData.getAhfothers()).isEqualTo(DEFAULT_AHFOTHERS);

        // Validate the PacientMedicalData in Elasticsearch
        PacientMedicalData pacientMedicalDataEs = pacientMedicalDataSearchRepository.findOne(testPacientMedicalData.getId());
        assertThat(pacientMedicalDataEs).isEqualToComparingFieldByField(testPacientMedicalData);
    }

    @Test
    @Transactional
    public void createPacientMedicalDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientMedicalDataRepository.findAll().size();

        // Create the PacientMedicalData with an existing ID
        pacientMedicalData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientMedicalDataMockMvc.perform(post("/api/pacient-medical-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientMedicalData)))
            .andExpect(status().isBadRequest());

        // Validate the PacientMedicalData in the database
        List<PacientMedicalData> pacientMedicalDataList = pacientMedicalDataRepository.findAll();
        assertThat(pacientMedicalDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientMedicalData() throws Exception {
        // Initialize the database
        pacientMedicalDataRepository.saveAndFlush(pacientMedicalData);

        // Get all the pacientMedicalDataList
        restPacientMedicalDataMockMvc.perform(get("/api/pacient-medical-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientMedicalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].diseases").value(hasItem(DEFAULT_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].surgicalinterventions").value(hasItem(DEFAULT_SURGICALINTERVENTIONS.toString())))
            .andExpect(jsonPath("$.[*].useofdrugs").value(hasItem(DEFAULT_USEOFDRUGS.toString())))
            .andExpect(jsonPath("$.[*].allergies").value(hasItem(DEFAULT_ALLERGIES.toString())))
            .andExpect(jsonPath("$.[*].ahffather").value(hasItem(DEFAULT_AHFFATHER.toString())))
            .andExpect(jsonPath("$.[*].ahfmother").value(hasItem(DEFAULT_AHFMOTHER.toString())))
            .andExpect(jsonPath("$.[*].ahfothers").value(hasItem(DEFAULT_AHFOTHERS.toString())));
    }

    @Test
    @Transactional
    public void getPacientMedicalData() throws Exception {
        // Initialize the database
        pacientMedicalDataRepository.saveAndFlush(pacientMedicalData);

        // Get the pacientMedicalData
        restPacientMedicalDataMockMvc.perform(get("/api/pacient-medical-data/{id}", pacientMedicalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientMedicalData.getId().intValue()))
            .andExpect(jsonPath("$.diseases").value(DEFAULT_DISEASES.toString()))
            .andExpect(jsonPath("$.surgicalinterventions").value(DEFAULT_SURGICALINTERVENTIONS.toString()))
            .andExpect(jsonPath("$.useofdrugs").value(DEFAULT_USEOFDRUGS.toString()))
            .andExpect(jsonPath("$.allergies").value(DEFAULT_ALLERGIES.toString()))
            .andExpect(jsonPath("$.ahffather").value(DEFAULT_AHFFATHER.toString()))
            .andExpect(jsonPath("$.ahfmother").value(DEFAULT_AHFMOTHER.toString()))
            .andExpect(jsonPath("$.ahfothers").value(DEFAULT_AHFOTHERS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPacientMedicalData() throws Exception {
        // Get the pacientMedicalData
        restPacientMedicalDataMockMvc.perform(get("/api/pacient-medical-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientMedicalData() throws Exception {
        // Initialize the database
        pacientMedicalDataRepository.saveAndFlush(pacientMedicalData);
        pacientMedicalDataSearchRepository.save(pacientMedicalData);
        int databaseSizeBeforeUpdate = pacientMedicalDataRepository.findAll().size();

        // Update the pacientMedicalData
        PacientMedicalData updatedPacientMedicalData = pacientMedicalDataRepository.findOne(pacientMedicalData.getId());
        updatedPacientMedicalData
            .diseases(UPDATED_DISEASES)
            .surgicalinterventions(UPDATED_SURGICALINTERVENTIONS)
            .useofdrugs(UPDATED_USEOFDRUGS)
            .allergies(UPDATED_ALLERGIES)
            .ahffather(UPDATED_AHFFATHER)
            .ahfmother(UPDATED_AHFMOTHER)
            .ahfothers(UPDATED_AHFOTHERS);

        restPacientMedicalDataMockMvc.perform(put("/api/pacient-medical-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientMedicalData)))
            .andExpect(status().isOk());

        // Validate the PacientMedicalData in the database
        List<PacientMedicalData> pacientMedicalDataList = pacientMedicalDataRepository.findAll();
        assertThat(pacientMedicalDataList).hasSize(databaseSizeBeforeUpdate);
        PacientMedicalData testPacientMedicalData = pacientMedicalDataList.get(pacientMedicalDataList.size() - 1);
        assertThat(testPacientMedicalData.getDiseases()).isEqualTo(UPDATED_DISEASES);
        assertThat(testPacientMedicalData.getSurgicalinterventions()).isEqualTo(UPDATED_SURGICALINTERVENTIONS);
        assertThat(testPacientMedicalData.getUseofdrugs()).isEqualTo(UPDATED_USEOFDRUGS);
        assertThat(testPacientMedicalData.getAllergies()).isEqualTo(UPDATED_ALLERGIES);
        assertThat(testPacientMedicalData.getAhffather()).isEqualTo(UPDATED_AHFFATHER);
        assertThat(testPacientMedicalData.getAhfmother()).isEqualTo(UPDATED_AHFMOTHER);
        assertThat(testPacientMedicalData.getAhfothers()).isEqualTo(UPDATED_AHFOTHERS);

        // Validate the PacientMedicalData in Elasticsearch
        PacientMedicalData pacientMedicalDataEs = pacientMedicalDataSearchRepository.findOne(testPacientMedicalData.getId());
        assertThat(pacientMedicalDataEs).isEqualToComparingFieldByField(testPacientMedicalData);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientMedicalData() throws Exception {
        int databaseSizeBeforeUpdate = pacientMedicalDataRepository.findAll().size();

        // Create the PacientMedicalData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientMedicalDataMockMvc.perform(put("/api/pacient-medical-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientMedicalData)))
            .andExpect(status().isCreated());

        // Validate the PacientMedicalData in the database
        List<PacientMedicalData> pacientMedicalDataList = pacientMedicalDataRepository.findAll();
        assertThat(pacientMedicalDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientMedicalData() throws Exception {
        // Initialize the database
        pacientMedicalDataRepository.saveAndFlush(pacientMedicalData);
        pacientMedicalDataSearchRepository.save(pacientMedicalData);
        int databaseSizeBeforeDelete = pacientMedicalDataRepository.findAll().size();

        // Get the pacientMedicalData
        restPacientMedicalDataMockMvc.perform(delete("/api/pacient-medical-data/{id}", pacientMedicalData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientMedicalDataExistsInEs = pacientMedicalDataSearchRepository.exists(pacientMedicalData.getId());
        assertThat(pacientMedicalDataExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientMedicalData> pacientMedicalDataList = pacientMedicalDataRepository.findAll();
        assertThat(pacientMedicalDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientMedicalData() throws Exception {
        // Initialize the database
        pacientMedicalDataRepository.saveAndFlush(pacientMedicalData);
        pacientMedicalDataSearchRepository.save(pacientMedicalData);

        // Search the pacientMedicalData
        restPacientMedicalDataMockMvc.perform(get("/api/_search/pacient-medical-data?query=id:" + pacientMedicalData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientMedicalData.getId().intValue())))
            .andExpect(jsonPath("$.[*].diseases").value(hasItem(DEFAULT_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].surgicalinterventions").value(hasItem(DEFAULT_SURGICALINTERVENTIONS.toString())))
            .andExpect(jsonPath("$.[*].useofdrugs").value(hasItem(DEFAULT_USEOFDRUGS.toString())))
            .andExpect(jsonPath("$.[*].allergies").value(hasItem(DEFAULT_ALLERGIES.toString())))
            .andExpect(jsonPath("$.[*].ahffather").value(hasItem(DEFAULT_AHFFATHER.toString())))
            .andExpect(jsonPath("$.[*].ahfmother").value(hasItem(DEFAULT_AHFMOTHER.toString())))
            .andExpect(jsonPath("$.[*].ahfothers").value(hasItem(DEFAULT_AHFOTHERS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacientMedicalData.class);
        PacientMedicalData pacientMedicalData1 = new PacientMedicalData();
        pacientMedicalData1.setId(1L);
        PacientMedicalData pacientMedicalData2 = new PacientMedicalData();
        pacientMedicalData2.setId(pacientMedicalData1.getId());
        assertThat(pacientMedicalData1).isEqualTo(pacientMedicalData2);
        pacientMedicalData2.setId(2L);
        assertThat(pacientMedicalData1).isNotEqualTo(pacientMedicalData2);
        pacientMedicalData1.setId(null);
        assertThat(pacientMedicalData1).isNotEqualTo(pacientMedicalData2);
    }
}
