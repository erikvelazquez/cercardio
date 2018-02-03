package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientMedicalAnalysis;
import mycercardiopackege.jh.repository.PacientMedicalAnalysisRepository;
import mycercardiopackege.jh.repository.search.PacientMedicalAnalysisSearchRepository;
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
 * Test class for the PacientMedicalAnalysisResource REST controller.
 *
 * @see PacientMedicalAnalysisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientMedicalAnalysisResourceIntTest {

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
    private PacientMedicalAnalysisRepository pacientMedicalAnalysisRepository;

    @Autowired
    private PacientMedicalAnalysisSearchRepository pacientMedicalAnalysisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientMedicalAnalysisMockMvc;

    private PacientMedicalAnalysis pacientMedicalAnalysis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientMedicalAnalysisResource pacientMedicalAnalysisResource = new PacientMedicalAnalysisResource(pacientMedicalAnalysisRepository, pacientMedicalAnalysisSearchRepository);
        this.restPacientMedicalAnalysisMockMvc = MockMvcBuilders.standaloneSetup(pacientMedicalAnalysisResource)
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
    public static PacientMedicalAnalysis createEntity(EntityManager em) {
        PacientMedicalAnalysis pacientMedicalAnalysis = new PacientMedicalAnalysis()
            .presentation(DEFAULT_PRESENTATION)
            .subjective(DEFAULT_SUBJECTIVE)
            .objective(DEFAULT_OBJECTIVE)
            .analysis(DEFAULT_ANALYSIS)
            .disease(DEFAULT_DISEASE)
            .tests(DEFAULT_TESTS)
            .treatment(DEFAULT_TREATMENT)
            .medicine(DEFAULT_MEDICINE)
            .daytime(DEFAULT_DAYTIME);
        return pacientMedicalAnalysis;
    }

    @Before
    public void initTest() {
        pacientMedicalAnalysisSearchRepository.deleteAll();
        pacientMedicalAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientMedicalAnalysis() throws Exception {
        int databaseSizeBeforeCreate = pacientMedicalAnalysisRepository.findAll().size();

        // Create the PacientMedicalAnalysis
        restPacientMedicalAnalysisMockMvc.perform(post("/api/pacient-medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientMedicalAnalysis)))
            .andExpect(status().isCreated());

        // Validate the PacientMedicalAnalysis in the database
        List<PacientMedicalAnalysis> pacientMedicalAnalysisList = pacientMedicalAnalysisRepository.findAll();
        assertThat(pacientMedicalAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        PacientMedicalAnalysis testPacientMedicalAnalysis = pacientMedicalAnalysisList.get(pacientMedicalAnalysisList.size() - 1);
        assertThat(testPacientMedicalAnalysis.getPresentation()).isEqualTo(DEFAULT_PRESENTATION);
        assertThat(testPacientMedicalAnalysis.getSubjective()).isEqualTo(DEFAULT_SUBJECTIVE);
        assertThat(testPacientMedicalAnalysis.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testPacientMedicalAnalysis.getAnalysis()).isEqualTo(DEFAULT_ANALYSIS);
        assertThat(testPacientMedicalAnalysis.getDisease()).isEqualTo(DEFAULT_DISEASE);
        assertThat(testPacientMedicalAnalysis.getTests()).isEqualTo(DEFAULT_TESTS);
        assertThat(testPacientMedicalAnalysis.getTreatment()).isEqualTo(DEFAULT_TREATMENT);
        assertThat(testPacientMedicalAnalysis.getMedicine()).isEqualTo(DEFAULT_MEDICINE);
        assertThat(testPacientMedicalAnalysis.getDaytime()).isEqualTo(DEFAULT_DAYTIME);

        // Validate the PacientMedicalAnalysis in Elasticsearch
        PacientMedicalAnalysis pacientMedicalAnalysisEs = pacientMedicalAnalysisSearchRepository.findOne(testPacientMedicalAnalysis.getId());
        assertThat(pacientMedicalAnalysisEs).isEqualToComparingFieldByField(testPacientMedicalAnalysis);
    }

    @Test
    @Transactional
    public void createPacientMedicalAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientMedicalAnalysisRepository.findAll().size();

        // Create the PacientMedicalAnalysis with an existing ID
        pacientMedicalAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientMedicalAnalysisMockMvc.perform(post("/api/pacient-medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientMedicalAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the PacientMedicalAnalysis in the database
        List<PacientMedicalAnalysis> pacientMedicalAnalysisList = pacientMedicalAnalysisRepository.findAll();
        assertThat(pacientMedicalAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientMedicalAnalyses() throws Exception {
        // Initialize the database
        pacientMedicalAnalysisRepository.saveAndFlush(pacientMedicalAnalysis);

        // Get all the pacientMedicalAnalysisList
        restPacientMedicalAnalysisMockMvc.perform(get("/api/pacient-medical-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientMedicalAnalysis.getId().intValue())))
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
    public void getPacientMedicalAnalysis() throws Exception {
        // Initialize the database
        pacientMedicalAnalysisRepository.saveAndFlush(pacientMedicalAnalysis);

        // Get the pacientMedicalAnalysis
        restPacientMedicalAnalysisMockMvc.perform(get("/api/pacient-medical-analyses/{id}", pacientMedicalAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientMedicalAnalysis.getId().intValue()))
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
    public void getNonExistingPacientMedicalAnalysis() throws Exception {
        // Get the pacientMedicalAnalysis
        restPacientMedicalAnalysisMockMvc.perform(get("/api/pacient-medical-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientMedicalAnalysis() throws Exception {
        // Initialize the database
        pacientMedicalAnalysisRepository.saveAndFlush(pacientMedicalAnalysis);
        pacientMedicalAnalysisSearchRepository.save(pacientMedicalAnalysis);
        int databaseSizeBeforeUpdate = pacientMedicalAnalysisRepository.findAll().size();

        // Update the pacientMedicalAnalysis
        PacientMedicalAnalysis updatedPacientMedicalAnalysis = pacientMedicalAnalysisRepository.findOne(pacientMedicalAnalysis.getId());
        updatedPacientMedicalAnalysis
            .presentation(UPDATED_PRESENTATION)
            .subjective(UPDATED_SUBJECTIVE)
            .objective(UPDATED_OBJECTIVE)
            .analysis(UPDATED_ANALYSIS)
            .disease(UPDATED_DISEASE)
            .tests(UPDATED_TESTS)
            .treatment(UPDATED_TREATMENT)
            .medicine(UPDATED_MEDICINE)
            .daytime(UPDATED_DAYTIME);

        restPacientMedicalAnalysisMockMvc.perform(put("/api/pacient-medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientMedicalAnalysis)))
            .andExpect(status().isOk());

        // Validate the PacientMedicalAnalysis in the database
        List<PacientMedicalAnalysis> pacientMedicalAnalysisList = pacientMedicalAnalysisRepository.findAll();
        assertThat(pacientMedicalAnalysisList).hasSize(databaseSizeBeforeUpdate);
        PacientMedicalAnalysis testPacientMedicalAnalysis = pacientMedicalAnalysisList.get(pacientMedicalAnalysisList.size() - 1);
        assertThat(testPacientMedicalAnalysis.getPresentation()).isEqualTo(UPDATED_PRESENTATION);
        assertThat(testPacientMedicalAnalysis.getSubjective()).isEqualTo(UPDATED_SUBJECTIVE);
        assertThat(testPacientMedicalAnalysis.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testPacientMedicalAnalysis.getAnalysis()).isEqualTo(UPDATED_ANALYSIS);
        assertThat(testPacientMedicalAnalysis.getDisease()).isEqualTo(UPDATED_DISEASE);
        assertThat(testPacientMedicalAnalysis.getTests()).isEqualTo(UPDATED_TESTS);
        assertThat(testPacientMedicalAnalysis.getTreatment()).isEqualTo(UPDATED_TREATMENT);
        assertThat(testPacientMedicalAnalysis.getMedicine()).isEqualTo(UPDATED_MEDICINE);
        assertThat(testPacientMedicalAnalysis.getDaytime()).isEqualTo(UPDATED_DAYTIME);

        // Validate the PacientMedicalAnalysis in Elasticsearch
        PacientMedicalAnalysis pacientMedicalAnalysisEs = pacientMedicalAnalysisSearchRepository.findOne(testPacientMedicalAnalysis.getId());
        assertThat(pacientMedicalAnalysisEs).isEqualToComparingFieldByField(testPacientMedicalAnalysis);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientMedicalAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = pacientMedicalAnalysisRepository.findAll().size();

        // Create the PacientMedicalAnalysis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientMedicalAnalysisMockMvc.perform(put("/api/pacient-medical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientMedicalAnalysis)))
            .andExpect(status().isCreated());

        // Validate the PacientMedicalAnalysis in the database
        List<PacientMedicalAnalysis> pacientMedicalAnalysisList = pacientMedicalAnalysisRepository.findAll();
        assertThat(pacientMedicalAnalysisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientMedicalAnalysis() throws Exception {
        // Initialize the database
        pacientMedicalAnalysisRepository.saveAndFlush(pacientMedicalAnalysis);
        pacientMedicalAnalysisSearchRepository.save(pacientMedicalAnalysis);
        int databaseSizeBeforeDelete = pacientMedicalAnalysisRepository.findAll().size();

        // Get the pacientMedicalAnalysis
        restPacientMedicalAnalysisMockMvc.perform(delete("/api/pacient-medical-analyses/{id}", pacientMedicalAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientMedicalAnalysisExistsInEs = pacientMedicalAnalysisSearchRepository.exists(pacientMedicalAnalysis.getId());
        assertThat(pacientMedicalAnalysisExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientMedicalAnalysis> pacientMedicalAnalysisList = pacientMedicalAnalysisRepository.findAll();
        assertThat(pacientMedicalAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientMedicalAnalysis() throws Exception {
        // Initialize the database
        pacientMedicalAnalysisRepository.saveAndFlush(pacientMedicalAnalysis);
        pacientMedicalAnalysisSearchRepository.save(pacientMedicalAnalysis);

        // Search the pacientMedicalAnalysis
        restPacientMedicalAnalysisMockMvc.perform(get("/api/_search/pacient-medical-analyses?query=id:" + pacientMedicalAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientMedicalAnalysis.getId().intValue())))
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
        TestUtil.equalsVerifier(PacientMedicalAnalysis.class);
        PacientMedicalAnalysis pacientMedicalAnalysis1 = new PacientMedicalAnalysis();
        pacientMedicalAnalysis1.setId(1L);
        PacientMedicalAnalysis pacientMedicalAnalysis2 = new PacientMedicalAnalysis();
        pacientMedicalAnalysis2.setId(pacientMedicalAnalysis1.getId());
        assertThat(pacientMedicalAnalysis1).isEqualTo(pacientMedicalAnalysis2);
        pacientMedicalAnalysis2.setId(2L);
        assertThat(pacientMedicalAnalysis1).isNotEqualTo(pacientMedicalAnalysis2);
        pacientMedicalAnalysis1.setId(null);
        assertThat(pacientMedicalAnalysis1).isNotEqualTo(pacientMedicalAnalysis2);
    }
}
