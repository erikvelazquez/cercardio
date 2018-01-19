package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientGenerals;
import mycercardiopackege.jh.repository.PacientGeneralsRepository;
import mycercardiopackege.jh.repository.search.PacientGeneralsSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mycercardiopackege.jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PacientGeneralsResource REST controller.
 *
 * @see PacientGeneralsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientGeneralsResourceIntTest {

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEOFBIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEOFBIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACEOFBIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACEOFBIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVATENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRIVATENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIALNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SOCIALNUMBER = "BBBBBBBBBB";

    @Autowired
    private PacientGeneralsRepository pacientGeneralsRepository;

    @Autowired
    private PacientGeneralsSearchRepository pacientGeneralsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientGeneralsMockMvc;

    private PacientGenerals pacientGenerals;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientGeneralsResource pacientGeneralsResource = new PacientGeneralsResource(pacientGeneralsRepository, pacientGeneralsSearchRepository);
        this.restPacientGeneralsMockMvc = MockMvcBuilders.standaloneSetup(pacientGeneralsResource)
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
    public static PacientGenerals createEntity(EntityManager em) {
        PacientGenerals pacientGenerals = new PacientGenerals()
            .curp(DEFAULT_CURP)
            .rfc(DEFAULT_RFC)
            .dateofbirth(DEFAULT_DATEOFBIRTH)
            .placeofbirth(DEFAULT_PLACEOFBIRTH)
            .privatenumber(DEFAULT_PRIVATENUMBER)
            .socialnumber(DEFAULT_SOCIALNUMBER);
        return pacientGenerals;
    }

    @Before
    public void initTest() {
        pacientGeneralsSearchRepository.deleteAll();
        pacientGenerals = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientGenerals() throws Exception {
        int databaseSizeBeforeCreate = pacientGeneralsRepository.findAll().size();

        // Create the PacientGenerals
        restPacientGeneralsMockMvc.perform(post("/api/pacient-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientGenerals)))
            .andExpect(status().isCreated());

        // Validate the PacientGenerals in the database
        List<PacientGenerals> pacientGeneralsList = pacientGeneralsRepository.findAll();
        assertThat(pacientGeneralsList).hasSize(databaseSizeBeforeCreate + 1);
        PacientGenerals testPacientGenerals = pacientGeneralsList.get(pacientGeneralsList.size() - 1);
        assertThat(testPacientGenerals.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testPacientGenerals.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testPacientGenerals.getDateofbirth()).isEqualTo(DEFAULT_DATEOFBIRTH);
        assertThat(testPacientGenerals.getPlaceofbirth()).isEqualTo(DEFAULT_PLACEOFBIRTH);
        assertThat(testPacientGenerals.getPrivatenumber()).isEqualTo(DEFAULT_PRIVATENUMBER);
        assertThat(testPacientGenerals.getSocialnumber()).isEqualTo(DEFAULT_SOCIALNUMBER);

        // Validate the PacientGenerals in Elasticsearch
        PacientGenerals pacientGeneralsEs = pacientGeneralsSearchRepository.findOne(testPacientGenerals.getId());
        assertThat(pacientGeneralsEs).isEqualToComparingFieldByField(testPacientGenerals);
    }

    @Test
    @Transactional
    public void createPacientGeneralsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientGeneralsRepository.findAll().size();

        // Create the PacientGenerals with an existing ID
        pacientGenerals.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientGeneralsMockMvc.perform(post("/api/pacient-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientGenerals)))
            .andExpect(status().isBadRequest());

        // Validate the PacientGenerals in the database
        List<PacientGenerals> pacientGeneralsList = pacientGeneralsRepository.findAll();
        assertThat(pacientGeneralsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientGenerals() throws Exception {
        // Initialize the database
        pacientGeneralsRepository.saveAndFlush(pacientGenerals);

        // Get all the pacientGeneralsList
        restPacientGeneralsMockMvc.perform(get("/api/pacient-generals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientGenerals.getId().intValue())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeofbirth").value(hasItem(DEFAULT_PLACEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].privatenumber").value(hasItem(DEFAULT_PRIVATENUMBER.toString())))
            .andExpect(jsonPath("$.[*].socialnumber").value(hasItem(DEFAULT_SOCIALNUMBER.toString())));
    }

    @Test
    @Transactional
    public void getPacientGenerals() throws Exception {
        // Initialize the database
        pacientGeneralsRepository.saveAndFlush(pacientGenerals);

        // Get the pacientGenerals
        restPacientGeneralsMockMvc.perform(get("/api/pacient-generals/{id}", pacientGenerals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientGenerals.getId().intValue()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.dateofbirth").value(DEFAULT_DATEOFBIRTH.toString()))
            .andExpect(jsonPath("$.placeofbirth").value(DEFAULT_PLACEOFBIRTH.toString()))
            .andExpect(jsonPath("$.privatenumber").value(DEFAULT_PRIVATENUMBER.toString()))
            .andExpect(jsonPath("$.socialnumber").value(DEFAULT_SOCIALNUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPacientGenerals() throws Exception {
        // Get the pacientGenerals
        restPacientGeneralsMockMvc.perform(get("/api/pacient-generals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientGenerals() throws Exception {
        // Initialize the database
        pacientGeneralsRepository.saveAndFlush(pacientGenerals);
        pacientGeneralsSearchRepository.save(pacientGenerals);
        int databaseSizeBeforeUpdate = pacientGeneralsRepository.findAll().size();

        // Update the pacientGenerals
        PacientGenerals updatedPacientGenerals = pacientGeneralsRepository.findOne(pacientGenerals.getId());
        updatedPacientGenerals
            .curp(UPDATED_CURP)
            .rfc(UPDATED_RFC)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .placeofbirth(UPDATED_PLACEOFBIRTH)
            .privatenumber(UPDATED_PRIVATENUMBER)
            .socialnumber(UPDATED_SOCIALNUMBER);

        restPacientGeneralsMockMvc.perform(put("/api/pacient-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientGenerals)))
            .andExpect(status().isOk());

        // Validate the PacientGenerals in the database
        List<PacientGenerals> pacientGeneralsList = pacientGeneralsRepository.findAll();
        assertThat(pacientGeneralsList).hasSize(databaseSizeBeforeUpdate);
        PacientGenerals testPacientGenerals = pacientGeneralsList.get(pacientGeneralsList.size() - 1);
        assertThat(testPacientGenerals.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testPacientGenerals.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPacientGenerals.getDateofbirth()).isEqualTo(UPDATED_DATEOFBIRTH);
        assertThat(testPacientGenerals.getPlaceofbirth()).isEqualTo(UPDATED_PLACEOFBIRTH);
        assertThat(testPacientGenerals.getPrivatenumber()).isEqualTo(UPDATED_PRIVATENUMBER);
        assertThat(testPacientGenerals.getSocialnumber()).isEqualTo(UPDATED_SOCIALNUMBER);

        // Validate the PacientGenerals in Elasticsearch
        PacientGenerals pacientGeneralsEs = pacientGeneralsSearchRepository.findOne(testPacientGenerals.getId());
        assertThat(pacientGeneralsEs).isEqualToComparingFieldByField(testPacientGenerals);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientGenerals() throws Exception {
        int databaseSizeBeforeUpdate = pacientGeneralsRepository.findAll().size();

        // Create the PacientGenerals

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientGeneralsMockMvc.perform(put("/api/pacient-generals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientGenerals)))
            .andExpect(status().isCreated());

        // Validate the PacientGenerals in the database
        List<PacientGenerals> pacientGeneralsList = pacientGeneralsRepository.findAll();
        assertThat(pacientGeneralsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientGenerals() throws Exception {
        // Initialize the database
        pacientGeneralsRepository.saveAndFlush(pacientGenerals);
        pacientGeneralsSearchRepository.save(pacientGenerals);
        int databaseSizeBeforeDelete = pacientGeneralsRepository.findAll().size();

        // Get the pacientGenerals
        restPacientGeneralsMockMvc.perform(delete("/api/pacient-generals/{id}", pacientGenerals.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientGeneralsExistsInEs = pacientGeneralsSearchRepository.exists(pacientGenerals.getId());
        assertThat(pacientGeneralsExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientGenerals> pacientGeneralsList = pacientGeneralsRepository.findAll();
        assertThat(pacientGeneralsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientGenerals() throws Exception {
        // Initialize the database
        pacientGeneralsRepository.saveAndFlush(pacientGenerals);
        pacientGeneralsSearchRepository.save(pacientGenerals);

        // Search the pacientGenerals
        restPacientGeneralsMockMvc.perform(get("/api/_search/pacient-generals?query=id:" + pacientGenerals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientGenerals.getId().intValue())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeofbirth").value(hasItem(DEFAULT_PLACEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].privatenumber").value(hasItem(DEFAULT_PRIVATENUMBER.toString())))
            .andExpect(jsonPath("$.[*].socialnumber").value(hasItem(DEFAULT_SOCIALNUMBER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacientGenerals.class);
        PacientGenerals pacientGenerals1 = new PacientGenerals();
        pacientGenerals1.setId(1L);
        PacientGenerals pacientGenerals2 = new PacientGenerals();
        pacientGenerals2.setId(pacientGenerals1.getId());
        assertThat(pacientGenerals1).isEqualTo(pacientGenerals2);
        pacientGenerals2.setId(2L);
        assertThat(pacientGenerals1).isNotEqualTo(pacientGenerals2);
        pacientGenerals1.setId(null);
        assertThat(pacientGenerals1).isNotEqualTo(pacientGenerals2);
    }
}
