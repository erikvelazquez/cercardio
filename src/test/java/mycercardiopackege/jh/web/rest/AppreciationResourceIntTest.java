package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Appreciation;
import mycercardiopackege.jh.repository.AppreciationRepository;
import mycercardiopackege.jh.repository.search.AppreciationSearchRepository;
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
 * Test class for the AppreciationResource REST controller.
 *
 * @see AppreciationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class AppreciationResourceIntTest {

    private static final Float DEFAULT_HEIGHT = 1F;
    private static final Float UPDATED_HEIGHT = 2F;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Float DEFAULT_SIZE = 1F;
    private static final Float UPDATED_SIZE = 2F;

    private static final Float DEFAULT_BMI = 1F;
    private static final Float UPDATED_BMI = 2F;

    private static final Float DEFAULT_TEMPERATURE = 1F;
    private static final Float UPDATED_TEMPERATURE = 2F;

    private static final Float DEFAULT_SATURATION = 1F;
    private static final Float UPDATED_SATURATION = 2F;

    private static final Float DEFAULT_BLOODPRESSUERE = 1F;
    private static final Float UPDATED_BLOODPRESSUERE = 2F;

    private static final Float DEFAULT_HEARTRATE = 1F;
    private static final Float UPDATED_HEARTRATE = 2F;

    private static final Float DEFAULT_BREATHINGFREQUENCY = 1F;
    private static final Float UPDATED_BREATHINGFREQUENCY = 2F;

    private static final String DEFAULT_OTHERS = "AAAAAAAAAA";
    private static final String UPDATED_OTHERS = "BBBBBBBBBB";

    private static final String DEFAULT_HEAD = "AAAAAAAAAA";
    private static final String UPDATED_HEAD = "BBBBBBBBBB";

    private static final String DEFAULT_NECK = "AAAAAAAAAA";
    private static final String UPDATED_NECK = "BBBBBBBBBB";

    private static final String DEFAULT_CHEST = "AAAAAAAAAA";
    private static final String UPDATED_CHEST = "BBBBBBBBBB";

    private static final String DEFAULT_ABDOMEN = "AAAAAAAAAA";
    private static final String UPDATED_ABDOMEN = "BBBBBBBBBB";

    private static final String DEFAULT_BODYPART = "AAAAAAAAAA";
    private static final String UPDATED_BODYPART = "BBBBBBBBBB";

    private static final String DEFAULT_GENITALS = "AAAAAAAAAA";
    private static final String UPDATED_GENITALS = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERSPHYSICAL = "AAAAAAAAAA";
    private static final String UPDATED_OTHERSPHYSICAL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AppreciationRepository appreciationRepository;

    @Autowired
    private AppreciationSearchRepository appreciationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppreciationMockMvc;

    private Appreciation appreciation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppreciationResource appreciationResource = new AppreciationResource(appreciationRepository, appreciationSearchRepository);
        this.restAppreciationMockMvc = MockMvcBuilders.standaloneSetup(appreciationResource)
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
    public static Appreciation createEntity(EntityManager em) {
        Appreciation appreciation = new Appreciation()
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .size(DEFAULT_SIZE)
            .bmi(DEFAULT_BMI)
            .temperature(DEFAULT_TEMPERATURE)
            .saturation(DEFAULT_SATURATION)
            .bloodpressuere(DEFAULT_BLOODPRESSUERE)
            .heartrate(DEFAULT_HEARTRATE)
            .breathingfrequency(DEFAULT_BREATHINGFREQUENCY)
            .others(DEFAULT_OTHERS)
            .head(DEFAULT_HEAD)
            .neck(DEFAULT_NECK)
            .chest(DEFAULT_CHEST)
            .abdomen(DEFAULT_ABDOMEN)
            .bodypart(DEFAULT_BODYPART)
            .genitals(DEFAULT_GENITALS)
            .othersphysical(DEFAULT_OTHERSPHYSICAL)
            .createdat(DEFAULT_CREATEDAT);
        return appreciation;
    }

    @Before
    public void initTest() {
        appreciationSearchRepository.deleteAll();
        appreciation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppreciation() throws Exception {
        int databaseSizeBeforeCreate = appreciationRepository.findAll().size();

        // Create the Appreciation
        restAppreciationMockMvc.perform(post("/api/appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appreciation)))
            .andExpect(status().isCreated());

        // Validate the Appreciation in the database
        List<Appreciation> appreciationList = appreciationRepository.findAll();
        assertThat(appreciationList).hasSize(databaseSizeBeforeCreate + 1);
        Appreciation testAppreciation = appreciationList.get(appreciationList.size() - 1);
        assertThat(testAppreciation.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testAppreciation.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testAppreciation.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testAppreciation.getBmi()).isEqualTo(DEFAULT_BMI);
        assertThat(testAppreciation.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testAppreciation.getSaturation()).isEqualTo(DEFAULT_SATURATION);
        assertThat(testAppreciation.getBloodpressuere()).isEqualTo(DEFAULT_BLOODPRESSUERE);
        assertThat(testAppreciation.getHeartrate()).isEqualTo(DEFAULT_HEARTRATE);
        assertThat(testAppreciation.getBreathingfrequency()).isEqualTo(DEFAULT_BREATHINGFREQUENCY);
        assertThat(testAppreciation.getOthers()).isEqualTo(DEFAULT_OTHERS);
        assertThat(testAppreciation.getHead()).isEqualTo(DEFAULT_HEAD);
        assertThat(testAppreciation.getNeck()).isEqualTo(DEFAULT_NECK);
        assertThat(testAppreciation.getChest()).isEqualTo(DEFAULT_CHEST);
        assertThat(testAppreciation.getAbdomen()).isEqualTo(DEFAULT_ABDOMEN);
        assertThat(testAppreciation.getBodypart()).isEqualTo(DEFAULT_BODYPART);
        assertThat(testAppreciation.getGenitals()).isEqualTo(DEFAULT_GENITALS);
        assertThat(testAppreciation.getOthersphysical()).isEqualTo(DEFAULT_OTHERSPHYSICAL);
        assertThat(testAppreciation.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the Appreciation in Elasticsearch
        Appreciation appreciationEs = appreciationSearchRepository.findOne(testAppreciation.getId());
        assertThat(appreciationEs).isEqualToComparingFieldByField(testAppreciation);
    }

    @Test
    @Transactional
    public void createAppreciationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appreciationRepository.findAll().size();

        // Create the Appreciation with an existing ID
        appreciation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppreciationMockMvc.perform(post("/api/appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appreciation)))
            .andExpect(status().isBadRequest());

        // Validate the Appreciation in the database
        List<Appreciation> appreciationList = appreciationRepository.findAll();
        assertThat(appreciationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppreciations() throws Exception {
        // Initialize the database
        appreciationRepository.saveAndFlush(appreciation);

        // Get all the appreciationList
        restAppreciationMockMvc.perform(get("/api/appreciations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appreciation.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].bmi").value(hasItem(DEFAULT_BMI.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].saturation").value(hasItem(DEFAULT_SATURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodpressuere").value(hasItem(DEFAULT_BLOODPRESSUERE.doubleValue())))
            .andExpect(jsonPath("$.[*].heartrate").value(hasItem(DEFAULT_HEARTRATE.doubleValue())))
            .andExpect(jsonPath("$.[*].breathingfrequency").value(hasItem(DEFAULT_BREATHINGFREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.toString())))
            .andExpect(jsonPath("$.[*].head").value(hasItem(DEFAULT_HEAD.toString())))
            .andExpect(jsonPath("$.[*].neck").value(hasItem(DEFAULT_NECK.toString())))
            .andExpect(jsonPath("$.[*].chest").value(hasItem(DEFAULT_CHEST.toString())))
            .andExpect(jsonPath("$.[*].abdomen").value(hasItem(DEFAULT_ABDOMEN.toString())))
            .andExpect(jsonPath("$.[*].bodypart").value(hasItem(DEFAULT_BODYPART.toString())))
            .andExpect(jsonPath("$.[*].genitals").value(hasItem(DEFAULT_GENITALS.toString())))
            .andExpect(jsonPath("$.[*].othersphysical").value(hasItem(DEFAULT_OTHERSPHYSICAL.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void getAppreciation() throws Exception {
        // Initialize the database
        appreciationRepository.saveAndFlush(appreciation);

        // Get the appreciation
        restAppreciationMockMvc.perform(get("/api/appreciations/{id}", appreciation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appreciation.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE.doubleValue()))
            .andExpect(jsonPath("$.bmi").value(DEFAULT_BMI.doubleValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.saturation").value(DEFAULT_SATURATION.doubleValue()))
            .andExpect(jsonPath("$.bloodpressuere").value(DEFAULT_BLOODPRESSUERE.doubleValue()))
            .andExpect(jsonPath("$.heartrate").value(DEFAULT_HEARTRATE.doubleValue()))
            .andExpect(jsonPath("$.breathingfrequency").value(DEFAULT_BREATHINGFREQUENCY.doubleValue()))
            .andExpect(jsonPath("$.others").value(DEFAULT_OTHERS.toString()))
            .andExpect(jsonPath("$.head").value(DEFAULT_HEAD.toString()))
            .andExpect(jsonPath("$.neck").value(DEFAULT_NECK.toString()))
            .andExpect(jsonPath("$.chest").value(DEFAULT_CHEST.toString()))
            .andExpect(jsonPath("$.abdomen").value(DEFAULT_ABDOMEN.toString()))
            .andExpect(jsonPath("$.bodypart").value(DEFAULT_BODYPART.toString()))
            .andExpect(jsonPath("$.genitals").value(DEFAULT_GENITALS.toString()))
            .andExpect(jsonPath("$.othersphysical").value(DEFAULT_OTHERSPHYSICAL.toString()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingAppreciation() throws Exception {
        // Get the appreciation
        restAppreciationMockMvc.perform(get("/api/appreciations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppreciation() throws Exception {
        // Initialize the database
        appreciationRepository.saveAndFlush(appreciation);
        appreciationSearchRepository.save(appreciation);
        int databaseSizeBeforeUpdate = appreciationRepository.findAll().size();

        // Update the appreciation
        Appreciation updatedAppreciation = appreciationRepository.findOne(appreciation.getId());
        updatedAppreciation
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .size(UPDATED_SIZE)
            .bmi(UPDATED_BMI)
            .temperature(UPDATED_TEMPERATURE)
            .saturation(UPDATED_SATURATION)
            .bloodpressuere(UPDATED_BLOODPRESSUERE)
            .heartrate(UPDATED_HEARTRATE)
            .breathingfrequency(UPDATED_BREATHINGFREQUENCY)
            .others(UPDATED_OTHERS)
            .head(UPDATED_HEAD)
            .neck(UPDATED_NECK)
            .chest(UPDATED_CHEST)
            .abdomen(UPDATED_ABDOMEN)
            .bodypart(UPDATED_BODYPART)
            .genitals(UPDATED_GENITALS)
            .othersphysical(UPDATED_OTHERSPHYSICAL)
            .createdat(UPDATED_CREATEDAT);

        restAppreciationMockMvc.perform(put("/api/appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppreciation)))
            .andExpect(status().isOk());

        // Validate the Appreciation in the database
        List<Appreciation> appreciationList = appreciationRepository.findAll();
        assertThat(appreciationList).hasSize(databaseSizeBeforeUpdate);
        Appreciation testAppreciation = appreciationList.get(appreciationList.size() - 1);
        assertThat(testAppreciation.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testAppreciation.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testAppreciation.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testAppreciation.getBmi()).isEqualTo(UPDATED_BMI);
        assertThat(testAppreciation.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testAppreciation.getSaturation()).isEqualTo(UPDATED_SATURATION);
        assertThat(testAppreciation.getBloodpressuere()).isEqualTo(UPDATED_BLOODPRESSUERE);
        assertThat(testAppreciation.getHeartrate()).isEqualTo(UPDATED_HEARTRATE);
        assertThat(testAppreciation.getBreathingfrequency()).isEqualTo(UPDATED_BREATHINGFREQUENCY);
        assertThat(testAppreciation.getOthers()).isEqualTo(UPDATED_OTHERS);
        assertThat(testAppreciation.getHead()).isEqualTo(UPDATED_HEAD);
        assertThat(testAppreciation.getNeck()).isEqualTo(UPDATED_NECK);
        assertThat(testAppreciation.getChest()).isEqualTo(UPDATED_CHEST);
        assertThat(testAppreciation.getAbdomen()).isEqualTo(UPDATED_ABDOMEN);
        assertThat(testAppreciation.getBodypart()).isEqualTo(UPDATED_BODYPART);
        assertThat(testAppreciation.getGenitals()).isEqualTo(UPDATED_GENITALS);
        assertThat(testAppreciation.getOthersphysical()).isEqualTo(UPDATED_OTHERSPHYSICAL);
        assertThat(testAppreciation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the Appreciation in Elasticsearch
        Appreciation appreciationEs = appreciationSearchRepository.findOne(testAppreciation.getId());
        assertThat(appreciationEs).isEqualToComparingFieldByField(testAppreciation);
    }

    @Test
    @Transactional
    public void updateNonExistingAppreciation() throws Exception {
        int databaseSizeBeforeUpdate = appreciationRepository.findAll().size();

        // Create the Appreciation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppreciationMockMvc.perform(put("/api/appreciations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appreciation)))
            .andExpect(status().isCreated());

        // Validate the Appreciation in the database
        List<Appreciation> appreciationList = appreciationRepository.findAll();
        assertThat(appreciationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppreciation() throws Exception {
        // Initialize the database
        appreciationRepository.saveAndFlush(appreciation);
        appreciationSearchRepository.save(appreciation);
        int databaseSizeBeforeDelete = appreciationRepository.findAll().size();

        // Get the appreciation
        restAppreciationMockMvc.perform(delete("/api/appreciations/{id}", appreciation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean appreciationExistsInEs = appreciationSearchRepository.exists(appreciation.getId());
        assertThat(appreciationExistsInEs).isFalse();

        // Validate the database is empty
        List<Appreciation> appreciationList = appreciationRepository.findAll();
        assertThat(appreciationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAppreciation() throws Exception {
        // Initialize the database
        appreciationRepository.saveAndFlush(appreciation);
        appreciationSearchRepository.save(appreciation);

        // Search the appreciation
        restAppreciationMockMvc.perform(get("/api/_search/appreciations?query=id:" + appreciation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appreciation.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].bmi").value(hasItem(DEFAULT_BMI.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].saturation").value(hasItem(DEFAULT_SATURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodpressuere").value(hasItem(DEFAULT_BLOODPRESSUERE.doubleValue())))
            .andExpect(jsonPath("$.[*].heartrate").value(hasItem(DEFAULT_HEARTRATE.doubleValue())))
            .andExpect(jsonPath("$.[*].breathingfrequency").value(hasItem(DEFAULT_BREATHINGFREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.toString())))
            .andExpect(jsonPath("$.[*].head").value(hasItem(DEFAULT_HEAD.toString())))
            .andExpect(jsonPath("$.[*].neck").value(hasItem(DEFAULT_NECK.toString())))
            .andExpect(jsonPath("$.[*].chest").value(hasItem(DEFAULT_CHEST.toString())))
            .andExpect(jsonPath("$.[*].abdomen").value(hasItem(DEFAULT_ABDOMEN.toString())))
            .andExpect(jsonPath("$.[*].bodypart").value(hasItem(DEFAULT_BODYPART.toString())))
            .andExpect(jsonPath("$.[*].genitals").value(hasItem(DEFAULT_GENITALS.toString())))
            .andExpect(jsonPath("$.[*].othersphysical").value(hasItem(DEFAULT_OTHERSPHYSICAL.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appreciation.class);
        Appreciation appreciation1 = new Appreciation();
        appreciation1.setId(1L);
        Appreciation appreciation2 = new Appreciation();
        appreciation2.setId(appreciation1.getId());
        assertThat(appreciation1).isEqualTo(appreciation2);
        appreciation2.setId(2L);
        assertThat(appreciation1).isNotEqualTo(appreciation2);
        appreciation1.setId(null);
        assertThat(appreciation1).isNotEqualTo(appreciation2);
    }
}
