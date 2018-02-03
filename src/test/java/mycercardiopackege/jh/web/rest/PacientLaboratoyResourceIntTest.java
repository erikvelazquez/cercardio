package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientLaboratoy;
import mycercardiopackege.jh.repository.PacientLaboratoyRepository;
import mycercardiopackege.jh.repository.search.PacientLaboratoySearchRepository;
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
 * Test class for the PacientLaboratoyResource REST controller.
 *
 * @see PacientLaboratoyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientLaboratoyResourceIntTest {

    private static final String DEFAULT_LABORATORY = "AAAAAAAAAA";
    private static final String UPDATED_LABORATORY = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOFELABORATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOFELABORATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PacientLaboratoyRepository pacientLaboratoyRepository;

    @Autowired
    private PacientLaboratoySearchRepository pacientLaboratoySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientLaboratoyMockMvc;

    private PacientLaboratoy pacientLaboratoy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientLaboratoyResource pacientLaboratoyResource = new PacientLaboratoyResource(pacientLaboratoyRepository, pacientLaboratoySearchRepository);
        this.restPacientLaboratoyMockMvc = MockMvcBuilders.standaloneSetup(pacientLaboratoyResource)
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
    public static PacientLaboratoy createEntity(EntityManager em) {
        PacientLaboratoy pacientLaboratoy = new PacientLaboratoy()
            .laboratory(DEFAULT_LABORATORY)
            .number(DEFAULT_NUMBER)
            .dateofelaboration(DEFAULT_DATEOFELABORATION);
        return pacientLaboratoy;
    }

    @Before
    public void initTest() {
        pacientLaboratoySearchRepository.deleteAll();
        pacientLaboratoy = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientLaboratoy() throws Exception {
        int databaseSizeBeforeCreate = pacientLaboratoyRepository.findAll().size();

        // Create the PacientLaboratoy
        restPacientLaboratoyMockMvc.perform(post("/api/pacient-laboratoys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientLaboratoy)))
            .andExpect(status().isCreated());

        // Validate the PacientLaboratoy in the database
        List<PacientLaboratoy> pacientLaboratoyList = pacientLaboratoyRepository.findAll();
        assertThat(pacientLaboratoyList).hasSize(databaseSizeBeforeCreate + 1);
        PacientLaboratoy testPacientLaboratoy = pacientLaboratoyList.get(pacientLaboratoyList.size() - 1);
        assertThat(testPacientLaboratoy.getLaboratory()).isEqualTo(DEFAULT_LABORATORY);
        assertThat(testPacientLaboratoy.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPacientLaboratoy.getDateofelaboration()).isEqualTo(DEFAULT_DATEOFELABORATION);

        // Validate the PacientLaboratoy in Elasticsearch
        PacientLaboratoy pacientLaboratoyEs = pacientLaboratoySearchRepository.findOne(testPacientLaboratoy.getId());
        assertThat(pacientLaboratoyEs).isEqualToComparingFieldByField(testPacientLaboratoy);
    }

    @Test
    @Transactional
    public void createPacientLaboratoyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientLaboratoyRepository.findAll().size();

        // Create the PacientLaboratoy with an existing ID
        pacientLaboratoy.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientLaboratoyMockMvc.perform(post("/api/pacient-laboratoys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientLaboratoy)))
            .andExpect(status().isBadRequest());

        // Validate the PacientLaboratoy in the database
        List<PacientLaboratoy> pacientLaboratoyList = pacientLaboratoyRepository.findAll();
        assertThat(pacientLaboratoyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientLaboratoys() throws Exception {
        // Initialize the database
        pacientLaboratoyRepository.saveAndFlush(pacientLaboratoy);

        // Get all the pacientLaboratoyList
        restPacientLaboratoyMockMvc.perform(get("/api/pacient-laboratoys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientLaboratoy.getId().intValue())))
            .andExpect(jsonPath("$.[*].laboratory").value(hasItem(DEFAULT_LABORATORY.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateofelaboration").value(hasItem(sameInstant(DEFAULT_DATEOFELABORATION))));
    }

    @Test
    @Transactional
    public void getPacientLaboratoy() throws Exception {
        // Initialize the database
        pacientLaboratoyRepository.saveAndFlush(pacientLaboratoy);

        // Get the pacientLaboratoy
        restPacientLaboratoyMockMvc.perform(get("/api/pacient-laboratoys/{id}", pacientLaboratoy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientLaboratoy.getId().intValue()))
            .andExpect(jsonPath("$.laboratory").value(DEFAULT_LABORATORY.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.dateofelaboration").value(sameInstant(DEFAULT_DATEOFELABORATION)));
    }

    @Test
    @Transactional
    public void getNonExistingPacientLaboratoy() throws Exception {
        // Get the pacientLaboratoy
        restPacientLaboratoyMockMvc.perform(get("/api/pacient-laboratoys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientLaboratoy() throws Exception {
        // Initialize the database
        pacientLaboratoyRepository.saveAndFlush(pacientLaboratoy);
        pacientLaboratoySearchRepository.save(pacientLaboratoy);
        int databaseSizeBeforeUpdate = pacientLaboratoyRepository.findAll().size();

        // Update the pacientLaboratoy
        PacientLaboratoy updatedPacientLaboratoy = pacientLaboratoyRepository.findOne(pacientLaboratoy.getId());
        updatedPacientLaboratoy
            .laboratory(UPDATED_LABORATORY)
            .number(UPDATED_NUMBER)
            .dateofelaboration(UPDATED_DATEOFELABORATION);

        restPacientLaboratoyMockMvc.perform(put("/api/pacient-laboratoys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientLaboratoy)))
            .andExpect(status().isOk());

        // Validate the PacientLaboratoy in the database
        List<PacientLaboratoy> pacientLaboratoyList = pacientLaboratoyRepository.findAll();
        assertThat(pacientLaboratoyList).hasSize(databaseSizeBeforeUpdate);
        PacientLaboratoy testPacientLaboratoy = pacientLaboratoyList.get(pacientLaboratoyList.size() - 1);
        assertThat(testPacientLaboratoy.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testPacientLaboratoy.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPacientLaboratoy.getDateofelaboration()).isEqualTo(UPDATED_DATEOFELABORATION);

        // Validate the PacientLaboratoy in Elasticsearch
        PacientLaboratoy pacientLaboratoyEs = pacientLaboratoySearchRepository.findOne(testPacientLaboratoy.getId());
        assertThat(pacientLaboratoyEs).isEqualToComparingFieldByField(testPacientLaboratoy);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientLaboratoy() throws Exception {
        int databaseSizeBeforeUpdate = pacientLaboratoyRepository.findAll().size();

        // Create the PacientLaboratoy

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientLaboratoyMockMvc.perform(put("/api/pacient-laboratoys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientLaboratoy)))
            .andExpect(status().isCreated());

        // Validate the PacientLaboratoy in the database
        List<PacientLaboratoy> pacientLaboratoyList = pacientLaboratoyRepository.findAll();
        assertThat(pacientLaboratoyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientLaboratoy() throws Exception {
        // Initialize the database
        pacientLaboratoyRepository.saveAndFlush(pacientLaboratoy);
        pacientLaboratoySearchRepository.save(pacientLaboratoy);
        int databaseSizeBeforeDelete = pacientLaboratoyRepository.findAll().size();

        // Get the pacientLaboratoy
        restPacientLaboratoyMockMvc.perform(delete("/api/pacient-laboratoys/{id}", pacientLaboratoy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientLaboratoyExistsInEs = pacientLaboratoySearchRepository.exists(pacientLaboratoy.getId());
        assertThat(pacientLaboratoyExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientLaboratoy> pacientLaboratoyList = pacientLaboratoyRepository.findAll();
        assertThat(pacientLaboratoyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientLaboratoy() throws Exception {
        // Initialize the database
        pacientLaboratoyRepository.saveAndFlush(pacientLaboratoy);
        pacientLaboratoySearchRepository.save(pacientLaboratoy);

        // Search the pacientLaboratoy
        restPacientLaboratoyMockMvc.perform(get("/api/_search/pacient-laboratoys?query=id:" + pacientLaboratoy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientLaboratoy.getId().intValue())))
            .andExpect(jsonPath("$.[*].laboratory").value(hasItem(DEFAULT_LABORATORY.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateofelaboration").value(hasItem(sameInstant(DEFAULT_DATEOFELABORATION))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacientLaboratoy.class);
        PacientLaboratoy pacientLaboratoy1 = new PacientLaboratoy();
        pacientLaboratoy1.setId(1L);
        PacientLaboratoy pacientLaboratoy2 = new PacientLaboratoy();
        pacientLaboratoy2.setId(pacientLaboratoy1.getId());
        assertThat(pacientLaboratoy1).isEqualTo(pacientLaboratoy2);
        pacientLaboratoy2.setId(2L);
        assertThat(pacientLaboratoy1).isNotEqualTo(pacientLaboratoy2);
        pacientLaboratoy1.setId(null);
        assertThat(pacientLaboratoy1).isNotEqualTo(pacientLaboratoy2);
    }
}
