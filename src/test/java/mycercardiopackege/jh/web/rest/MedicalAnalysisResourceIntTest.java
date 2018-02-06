package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.MedicalAnalysis;
import mycercardiopackege.jh.repository.MedicalAnalysisRepository;
import mycercardiopackege.jh.repository.search.MedicalAnalysisSearchRepository;
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
 * Test class for the MedicalAnalysisResource REST controller.
 *
 * @see MedicalAnalysisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class MedicalAnalysisResourceIntTest {

    private static final String DEFAULT_PRESENTATION = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTATION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYSIS = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSIS = "BBBBBBBBBB";

    private static final String DEFAULT_DISEASE = "AAAAAAAAAA";
    private static final String UPDATED_DISEASE = "BBBBBBBBBB";

    private static final String DEFAULT_TESTS = "AAAAAAAAAA";
    private static final String UPDATED_TESTS = "BBBBBBBBBB";

    private static final String DEFAULT_TREATMENT = "AAAAAAAAAA";
    private static final String UPDATED_TREATMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICINE = "AAAAAAAAAA";
    private static final String UPDATED_MEDICINE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DAYTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAYTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private MedicalAnalysisRepository medicalAnalysisRepository;

    @Autowired
    private MedicalAnalysisSearchRepository medicalAnalysisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicalAnalysisMockMvc;

    private MedicalAnalysis medicalAnalysis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicalAnalysisResource medicalAnalysisResource = new MedicalAnalysisResource(medicalAnalysisRepository, medicalAnalysisSearchRepository);
        this.restMedicalAnalysisMockMvc = MockMvcBuilders.standaloneSetup(medicalAnalysisResource)
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
    public static MedicalAnalysis createEntity(EntityManager em) {
        MedicalAnalysis medicalAnalysis = new MedicalAnalysis()
            .presentation(DEFAULT_PRESENTATION)
            .subjective(DEFAULT_SUBJECTIVE)
            .objective(DEFAULT_OBJECTIVE)
            .analysis(DEFAULT_ANALYSIS)
            .disease(DEFAULT_DISEASE)
            .tests(DEFAULT_TESTS)
            .treatment(DEFAULT_TREATMENT)
            .medicine(DEFAULT_MEDICINE)
            .daytime(DEFAULT_DAYTIME);
        return medicalAnalysis;
    }

    @Before
    public void initTest() {
        medicalAnalysisSearchRepository.deleteAll();
        medicalAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalAnalysis() throws Exception {
        int databaseSizeBeforeCreate = medicalAnalysisRepository.findAll().size();

        // Create the MedicalAnalysis
        restMedicalAnalysisMockMvc.perform(post("/api/medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalAnalysis)))
            .andExpect(status().isCreated());

        // Validate the MedicalAnalysis in the database
        List<MedicalAnalysis> medicalAnalysisList = medicalAnalysisRepository.findAll();
        assertThat(medicalAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalAnalysis testMedicalAnalysis = medicalAnalysisList.get(medicalAnalysisList.size() - 1);
        assertThat(testMedicalAnalysis.getPresentation()).isEqualTo(DEFAULT_PRESENTATION);
        assertThat(testMedicalAnalysis.getSubjective()).isEqualTo(DEFAULT_SUBJECTIVE);
        assertThat(testMedicalAnalysis.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testMedicalAnalysis.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testMedicalAnalysis.getDisease()).isEqualTo(DEFAULT_DISEASE);
        assertThat(testMedicalAnalysis.getTests()).isEqualTo(DEFAULT_TESTS);
        assertThat(testMedicalAnalysis.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testMedicalAnalysis.getMedicine()).isEqualTo(DEFAULT_MEDICINE);
        assertThat(testMedicalAnalysis.getDaytime()).isEqualTo(DEFAULT_DAYTIME);

        // Validate the MedicalAnalysis in Elasticsearch
        MedicalAnalysis medicalAnalysisEs = medicalAnalysisSearchRepository.findOne(testMedicalAnalysis.getId());
        assertThat(medicalAnalysisEs).isEqualToComparingFieldByField(testMedicalAnalysis);
    }

    @Test
    @Transactional
    public void createMedicalAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalAnalysisRepository.findAll().size();

        // Create the MedicalAnalysis with an existing ID
        medicalAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalAnalysisMockMvc.perform(post("/api/medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalAnalysis in the database
        List<MedicalAnalysis> medicalAnalysisList = medicalAnalysisRepository.findAll();
        assertThat(medicalAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicalAnalyses() throws Exception {
        // Initialize the database
        medicalAnalysisRepository.saveAndFlush(medicalAnalysis);

        // Get all the medicalAnalysisList
        restMedicalAnalysisMockMvc.perform(get("/api/medical-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(DEFAULT_PRESENTATION.toString())))
            .andExpect(jsonPath("$.[*].subjective").value(hasItem(DEFAULT_SUBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS.toString())))
            .andExpect(jsonPath("$.[*].disease").value(hasItem(DEFAULT_DISEASE.toString())))
            .andExpect(jsonPath("$.[*].tests").value(hasItem(DEFAULT_TESTS.toString())))
            .andExpect(jsonPath("$.[*].treatment").value(hasItem(DEFAULT_TREATMENT.toString())))
            .andExpect(jsonPath("$.[*].medicine").value(hasItem(DEFAULT_MEDICINE.toString())))
            .andExpect(jsonPath("$.[*].daytime").value(hasItem(sameInstant(DEFAULT_DAYTIME))));
    }

    @Test
    @Transactional
    public void getMedicalAnalysis() throws Exception {
        // Initialize the database
        medicalAnalysisRepository.saveAndFlush(medicalAnalysis);

        // Get the medicalAnalysis
        restMedicalAnalysisMockMvc.perform(get("/api/medical-analyses/{id}", medicalAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicalAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.presentation").value(DEFAULT_PRESENTATION.toString()))
            .andExpect(jsonPath("$.subjective").value(DEFAULT_SUBJECTIVE.toString()))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE.toString()))
            .andExpect(jsonPath("$.analysis").value(DEFAULT_ANALYSIS.toString()))
            .andExpect(jsonPath("$.disease").value(DEFAULT_DISEASE.toString()))
            .andExpect(jsonPath("$.tests").value(DEFAULT_TESTS.toString()))
            .andExpect(jsonPath("$.treatment").value(DEFAULT_TREATMENT.toString()))
            .andExpect(jsonPath("$.medicine").value(DEFAULT_MEDICINE.toString()))
            .andExpect(jsonPath("$.daytime").value(sameInstant(DEFAULT_DAYTIME)));
    }

    @Test
    @Transactional
    public void getNonExistingMedicalAnalysis() throws Exception {
        // Get the medicalAnalysis
        restMedicalAnalysisMockMvc.perform(get("/api/medical-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalAnalysis() throws Exception {
        // Initialize the database
        medicalAnalysisRepository.saveAndFlush(medicalAnalysis);
        medicalAnalysisSearchRepository.save(medicalAnalysis);
        int databaseSizeBeforeUpdate = medicalAnalysisRepository.findAll().size();

        // Update the medicalAnalysis
        MedicalAnalysis updatedMedicalAnalysis = medicalAnalysisRepository.findOne(medicalAnalysis.getId());
        updatedMedicalAnalysis
            .presentation(UPDATED_PRESENTATION)
            .subjective(UPDATED_SUBJECTIVE)
            .objective(UPDATED_OBJECTIVE)
            .analysis(UPDATED_ANALYSIS)
            .disease(UPDATED_DISEASE)
            .tests(UPDATED_TESTS)
            .treatment(UPDATED_TREATMENT)
            .medicine(UPDATED_MEDICINE)
            .daytime(UPDATED_DAYTIME);

        restMedicalAnalysisMockMvc.perform(put("/api/medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalAnalysis)))
            .andExpect(status().isOk());

        // Validate the MedicalAnalysis in the database
        List<MedicalAnalysis> medicalAnalysisList = medicalAnalysisRepository.findAll();
        assertThat(medicalAnalysisList).hasSize(databaseSizeBeforeUpdate);
        MedicalAnalysis testMedicalAnalysis = medicalAnalysisList.get(medicalAnalysisList.size() - 1);
        assertThat(testMedicalAnalysis.getPresentation()).isEqualTo(UPDATED_PRESENTATION);
        assertThat(testMedicalAnalysis.getSubjective()).isEqualTo(UPDATED_SUBJECTIVE);
        assertThat(testMedicalAnalysis.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testMedicalAnalysis.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testMedicalAnalysis.getDisease()).isEqualTo(UPDATED_DISEASE);
        assertThat(testMedicalAnalysis.getTests()).isEqualTo(UPDATED_TESTS);
        assertThat(testMedicalAnalysis.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testMedicalAnalysis.getMedicine()).isEqualTo(UPDATED_MEDICINE);
        assertThat(testMedicalAnalysis.getDaytime()).isEqualTo(UPDATED_DAYTIME);

        // Validate the MedicalAnalysis in Elasticsearch
        MedicalAnalysis medicalAnalysisEs = medicalAnalysisSearchRepository.findOne(testMedicalAnalysis.getId());
        assertThat(medicalAnalysisEs).isEqualToComparingFieldByField(testMedicalAnalysis);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = medicalAnalysisRepository.findAll().size();

        // Create the MedicalAnalysis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicalAnalysisMockMvc.perform(put("/api/medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicalAnalysis)))
            .andExpect(status().isCreated());

        // Validate the MedicalAnalysis in the database
        List<MedicalAnalysis> medicalAnalysisList = medicalAnalysisRepository.findAll();
        assertThat(medicalAnalysisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicalAnalysis() throws Exception {
        // Initialize the database
        medicalAnalysisRepository.saveAndFlush(medicalAnalysis);
        medicalAnalysisSearchRepository.save(medicalAnalysis);
        int databaseSizeBeforeDelete = medicalAnalysisRepository.findAll().size();

        // Get the medicalAnalysis
        restMedicalAnalysisMockMvc.perform(delete("/api/medical-analyses/{id}", medicalAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medicalAnalysisExistsInEs = medicalAnalysisSearchRepository.exists(medicalAnalysis.getId());
        assertThat(medicalAnalysisExistsInEs).isFalse();

        // Validate the database is empty
        List<MedicalAnalysis> medicalAnalysisList = medicalAnalysisRepository.findAll();
        assertThat(medicalAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedicalAnalysis() throws Exception {
        // Initialize the database
        medicalAnalysisRepository.saveAndFlush(medicalAnalysis);
        medicalAnalysisSearchRepository.save(medicalAnalysis);

        // Search the medicalAnalysis
        restMedicalAnalysisMockMvc.perform(get("/api/_search/medical-analyses?query=id:" + medicalAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(DEFAULT_PRESENTATION.toString())))
            .andExpect(jsonPath("$.[*].subjective").value(hasItem(DEFAULT_SUBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE.toString())))
            .andExpect(jsonPath("$.[*].analysis").value(hasItem(DEFAULT_ANALYSIS.toString())))
            .andExpect(jsonPath("$.[*].disease").value(hasItem(DEFAULT_DISEASE.toString())))
            .andExpect(jsonPath("$.[*].tests").value(hasItem(DEFAULT_TESTS.toString())))
            .andExpect(jsonPath("$.[*].treatment").value(hasItem(DEFAULT_TREATMENT.toString())))
            .andExpect(jsonPath("$.[*].medicine").value(hasItem(DEFAULT_MEDICINE.toString())))
            .andExpect(jsonPath("$.[*].daytime").value(hasItem(sameInstant(DEFAULT_DAYTIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalAnalysis.class);
        MedicalAnalysis medicalAnalysis1 = new MedicalAnalysis();
        medicalAnalysis1.setId(1L);
        MedicalAnalysis medicalAnalysis2 = new MedicalAnalysis();
        medicalAnalysis2.setId(medicalAnalysis1.getId());
        assertThat(medicalAnalysis1).isEqualTo(medicalAnalysis2);
        medicalAnalysis2.setId(2L);
        assertThat(medicalAnalysis1).isNotEqualTo(medicalAnalysis2);
        medicalAnalysis1.setId(null);
        assertThat(medicalAnalysis1).isNotEqualTo(medicalAnalysis2);
    }
}
