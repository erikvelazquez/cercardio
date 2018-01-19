package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientApp;
import mycercardiopackege.jh.repository.PacientAppRepository;
import mycercardiopackege.jh.repository.search.PacientAppSearchRepository;
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
 * Test class for the PacientAppResource REST controller.
 *
 * @see PacientAppResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientAppResourceIntTest {

    private static final String DEFAULT_CHRONICDEGENERATIVE = "AAAAAAAAAA";
    private static final String UPDATED_CHRONICDEGENERATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_TRAUMATIC = "AAAAAAAAAA";
    private static final String UPDATED_TRAUMATIC = "BBBBBBBBBB";

    private static final String DEFAULT_GYNECOLOGICALOBSTETRICS = "AAAAAAAAAA";
    private static final String UPDATED_GYNECOLOGICALOBSTETRICS = "BBBBBBBBBB";

    private static final String DEFAULT_OTHERS = "AAAAAAAAAA";
    private static final String UPDATED_OTHERS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DAYTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAYTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PacientAppRepository pacientAppRepository;

    @Autowired
    private PacientAppSearchRepository pacientAppSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientAppMockMvc;

    private PacientApp pacientApp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientAppResource pacientAppResource = new PacientAppResource(pacientAppRepository, pacientAppSearchRepository);
        this.restPacientAppMockMvc = MockMvcBuilders.standaloneSetup(pacientAppResource)
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
    public static PacientApp createEntity(EntityManager em) {
        PacientApp pacientApp = new PacientApp()
            .chronicdegenerative(DEFAULT_CHRONICDEGENERATIVE)
            .traumatic(DEFAULT_TRAUMATIC)
            .gynecologicalobstetrics(DEFAULT_GYNECOLOGICALOBSTETRICS)
            .others(DEFAULT_OTHERS)
            .daytime(DEFAULT_DAYTIME);
        return pacientApp;
    }

    @Before
    public void initTest() {
        pacientAppSearchRepository.deleteAll();
        pacientApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientApp() throws Exception {
        int databaseSizeBeforeCreate = pacientAppRepository.findAll().size();

        // Create the PacientApp
        restPacientAppMockMvc.perform(post("/api/pacient-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientApp)))
            .andExpect(status().isCreated());

        // Validate the PacientApp in the database
        List<PacientApp> pacientAppList = pacientAppRepository.findAll();
        assertThat(pacientAppList).hasSize(databaseSizeBeforeCreate + 1);
        PacientApp testPacientApp = pacientAppList.get(pacientAppList.size() - 1);
        assertThat(testPacientApp.getChronicdegenerative()).isEqualTo(DEFAULT_CHRONICDEGENERATIVE);
        assertThat(testPacientApp.getTraumatic()).isEqualTo(DEFAULT_TRAUMATIC);
        assertThat(testPacientApp.getGynecologicalobstetrics()).isEqualTo(DEFAULT_GYNECOLOGICALOBSTETRICS);
        assertThat(testPacientApp.getOthers()).isEqualTo(DEFAULT_OTHERS);
        assertThat(testPacientApp.getDaytime()).isEqualTo(DEFAULT_DAYTIME);

        // Validate the PacientApp in Elasticsearch
        PacientApp pacientAppEs = pacientAppSearchRepository.findOne(testPacientApp.getId());
        assertThat(pacientAppEs).isEqualToComparingFieldByField(testPacientApp);
    }

    @Test
    @Transactional
    public void createPacientAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientAppRepository.findAll().size();

        // Create the PacientApp with an existing ID
        pacientApp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientAppMockMvc.perform(post("/api/pacient-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientApp)))
            .andExpect(status().isBadRequest());

        // Validate the PacientApp in the database
        List<PacientApp> pacientAppList = pacientAppRepository.findAll();
        assertThat(pacientAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientApps() throws Exception {
        // Initialize the database
        pacientAppRepository.saveAndFlush(pacientApp);

        // Get all the pacientAppList
        restPacientAppMockMvc.perform(get("/api/pacient-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].chronicdegenerative").value(hasItem(DEFAULT_CHRONICDEGENERATIVE.toString())))
            .andExpect(jsonPath("$.[*].traumatic").value(hasItem(DEFAULT_TRAUMATIC.toString())))
            .andExpect(jsonPath("$.[*].gynecologicalobstetrics").value(hasItem(DEFAULT_GYNECOLOGICALOBSTETRICS.toString())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.toString())))
            .andExpect(jsonPath("$.[*].daytime").value(hasItem(sameInstant(DEFAULT_DAYTIME))));
    }

    @Test
    @Transactional
    public void getPacientApp() throws Exception {
        // Initialize the database
        pacientAppRepository.saveAndFlush(pacientApp);

        // Get the pacientApp
        restPacientAppMockMvc.perform(get("/api/pacient-apps/{id}", pacientApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientApp.getId().intValue()))
            .andExpect(jsonPath("$.chronicdegenerative").value(DEFAULT_CHRONICDEGENERATIVE.toString()))
            .andExpect(jsonPath("$.traumatic").value(DEFAULT_TRAUMATIC.toString()))
            .andExpect(jsonPath("$.gynecologicalobstetrics").value(DEFAULT_GYNECOLOGICALOBSTETRICS.toString()))
            .andExpect(jsonPath("$.others").value(DEFAULT_OTHERS.toString()))
            .andExpect(jsonPath("$.daytime").value(sameInstant(DEFAULT_DAYTIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPacientApp() throws Exception {
        // Get the pacientApp
        restPacientAppMockMvc.perform(get("/api/pacient-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientApp() throws Exception {
        // Initialize the database
        pacientAppRepository.saveAndFlush(pacientApp);
        pacientAppSearchRepository.save(pacientApp);
        int databaseSizeBeforeUpdate = pacientAppRepository.findAll().size();

        // Update the pacientApp
        PacientApp updatedPacientApp = pacientAppRepository.findOne(pacientApp.getId());
        updatedPacientApp
            .chronicdegenerative(UPDATED_CHRONICDEGENERATIVE)
            .traumatic(UPDATED_TRAUMATIC)
            .gynecologicalobstetrics(UPDATED_GYNECOLOGICALOBSTETRICS)
            .others(UPDATED_OTHERS)
            .daytime(UPDATED_DAYTIME);

        restPacientAppMockMvc.perform(put("/api/pacient-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientApp)))
            .andExpect(status().isOk());

        // Validate the PacientApp in the database
        List<PacientApp> pacientAppList = pacientAppRepository.findAll();
        assertThat(pacientAppList).hasSize(databaseSizeBeforeUpdate);
        PacientApp testPacientApp = pacientAppList.get(pacientAppList.size() - 1);
        assertThat(testPacientApp.getChronicdegenerative()).isEqualTo(UPDATED_CHRONICDEGENERATIVE);
        assertThat(testPacientApp.getTraumatic()).isEqualTo(UPDATED_TRAUMATIC);
        assertThat(testPacientApp.getGynecologicalobstetrics()).isEqualTo(UPDATED_GYNECOLOGICALOBSTETRICS);
        assertThat(testPacientApp.getOthers()).isEqualTo(UPDATED_OTHERS);
        assertThat(testPacientApp.getDaytime()).isEqualTo(UPDATED_DAYTIME);

        // Validate the PacientApp in Elasticsearch
        PacientApp pacientAppEs = pacientAppSearchRepository.findOne(testPacientApp.getId());
        assertThat(pacientAppEs).isEqualToComparingFieldByField(testPacientApp);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientApp() throws Exception {
        int databaseSizeBeforeUpdate = pacientAppRepository.findAll().size();

        // Create the PacientApp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientAppMockMvc.perform(put("/api/pacient-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientApp)))
            .andExpect(status().isCreated());

        // Validate the PacientApp in the database
        List<PacientApp> pacientAppList = pacientAppRepository.findAll();
        assertThat(pacientAppList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientApp() throws Exception {
        // Initialize the database
        pacientAppRepository.saveAndFlush(pacientApp);
        pacientAppSearchRepository.save(pacientApp);
        int databaseSizeBeforeDelete = pacientAppRepository.findAll().size();

        // Get the pacientApp
        restPacientAppMockMvc.perform(delete("/api/pacient-apps/{id}", pacientApp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientAppExistsInEs = pacientAppSearchRepository.exists(pacientApp.getId());
        assertThat(pacientAppExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientApp> pacientAppList = pacientAppRepository.findAll();
        assertThat(pacientAppList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientApp() throws Exception {
        // Initialize the database
        pacientAppRepository.saveAndFlush(pacientApp);
        pacientAppSearchRepository.save(pacientApp);

        // Search the pacientApp
        restPacientAppMockMvc.perform(get("/api/_search/pacient-apps?query=id:" + pacientApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientApp.getId().intValue())))
            .andExpect(jsonPath("$.[*].chronicdegenerative").value(hasItem(DEFAULT_CHRONICDEGENERATIVE.toString())))
            .andExpect(jsonPath("$.[*].traumatic").value(hasItem(DEFAULT_TRAUMATIC.toString())))
            .andExpect(jsonPath("$.[*].gynecologicalobstetrics").value(hasItem(DEFAULT_GYNECOLOGICALOBSTETRICS.toString())))
            .andExpect(jsonPath("$.[*].others").value(hasItem(DEFAULT_OTHERS.toString())))
            .andExpect(jsonPath("$.[*].daytime").value(hasItem(sameInstant(DEFAULT_DAYTIME))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacientApp.class);
        PacientApp pacientApp1 = new PacientApp();
        pacientApp1.setId(1L);
        PacientApp pacientApp2 = new PacientApp();
        pacientApp2.setId(pacientApp1.getId());
        assertThat(pacientApp1).isEqualTo(pacientApp2);
        pacientApp2.setId(2L);
        assertThat(pacientApp1).isNotEqualTo(pacientApp2);
        pacientApp1.setId(null);
        assertThat(pacientApp1).isNotEqualTo(pacientApp2);
    }
}
