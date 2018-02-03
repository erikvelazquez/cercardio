package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientApnp;
import mycercardiopackege.jh.repository.PacientApnpRepository;
import mycercardiopackege.jh.repository.search.PacientApnpSearchRepository;
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
 * Test class for the PacientApnpResource REST controller.
 *
 * @see PacientApnpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientApnpResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final LocalDate DEFAULT_DATESTARTS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATESTARTS = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEEND = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEEND = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PacientApnpRepository pacientApnpRepository;

    @Autowired
    private PacientApnpSearchRepository pacientApnpSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientApnpMockMvc;

    private PacientApnp pacientApnp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientApnpResource pacientApnpResource = new PacientApnpResource(pacientApnpRepository, pacientApnpSearchRepository);
        this.restPacientApnpMockMvc = MockMvcBuilders.standaloneSetup(pacientApnpResource)
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
    public static PacientApnp createEntity(EntityManager em) {
        PacientApnp pacientApnp = new PacientApnp()
            .quantity(DEFAULT_QUANTITY)
            .datestarts(DEFAULT_DATESTARTS)
            .dateend(DEFAULT_DATEEND);
        return pacientApnp;
    }

    @Before
    public void initTest() {
        pacientApnpSearchRepository.deleteAll();
        pacientApnp = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientApnp() throws Exception {
        int databaseSizeBeforeCreate = pacientApnpRepository.findAll().size();

        // Create the PacientApnp
        restPacientApnpMockMvc.perform(post("/api/pacient-apnps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientApnp)))
            .andExpect(status().isCreated());

        // Validate the PacientApnp in the database
        List<PacientApnp> pacientApnpList = pacientApnpRepository.findAll();
        assertThat(pacientApnpList).hasSize(databaseSizeBeforeCreate + 1);
        PacientApnp testPacientApnp = pacientApnpList.get(pacientApnpList.size() - 1);
        assertThat(testPacientApnp.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPacientApnp.getDatestarts()).isEqualTo(DEFAULT_DATESTARTS);
        assertThat(testPacientApnp.getDateend()).isEqualTo(DEFAULT_DATEEND);

        // Validate the PacientApnp in Elasticsearch
        PacientApnp pacientApnpEs = pacientApnpSearchRepository.findOne(testPacientApnp.getId());
        assertThat(pacientApnpEs).isEqualToComparingFieldByField(testPacientApnp);
    }

    @Test
    @Transactional
    public void createPacientApnpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientApnpRepository.findAll().size();

        // Create the PacientApnp with an existing ID
        pacientApnp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientApnpMockMvc.perform(post("/api/pacient-apnps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientApnp)))
            .andExpect(status().isBadRequest());

        // Validate the PacientApnp in the database
        List<PacientApnp> pacientApnpList = pacientApnpRepository.findAll();
        assertThat(pacientApnpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientApnps() throws Exception {
        // Initialize the database
        pacientApnpRepository.saveAndFlush(pacientApnp);

        // Get all the pacientApnpList
        restPacientApnpMockMvc.perform(get("/api/pacient-apnps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientApnp.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].datestarts").value(hasItem(DEFAULT_DATESTARTS.toString())))
            .andExpect(jsonPath("$.[*].dateend").value(hasItem(DEFAULT_DATEEND.toString())));
    }

    @Test
    @Transactional
    public void getPacientApnp() throws Exception {
        // Initialize the database
        pacientApnpRepository.saveAndFlush(pacientApnp);

        // Get the pacientApnp
        restPacientApnpMockMvc.perform(get("/api/pacient-apnps/{id}", pacientApnp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientApnp.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.datestarts").value(DEFAULT_DATESTARTS.toString()))
            .andExpect(jsonPath("$.dateend").value(DEFAULT_DATEEND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPacientApnp() throws Exception {
        // Get the pacientApnp
        restPacientApnpMockMvc.perform(get("/api/pacient-apnps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientApnp() throws Exception {
        // Initialize the database
        pacientApnpRepository.saveAndFlush(pacientApnp);
        pacientApnpSearchRepository.save(pacientApnp);
        int databaseSizeBeforeUpdate = pacientApnpRepository.findAll().size();

        // Update the pacientApnp
        PacientApnp updatedPacientApnp = pacientApnpRepository.findOne(pacientApnp.getId());
        updatedPacientApnp
            .quantity(UPDATED_QUANTITY)
            .datestarts(UPDATED_DATESTARTS)
            .dateend(UPDATED_DATEEND);

        restPacientApnpMockMvc.perform(put("/api/pacient-apnps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientApnp)))
            .andExpect(status().isOk());

        // Validate the PacientApnp in the database
        List<PacientApnp> pacientApnpList = pacientApnpRepository.findAll();
        assertThat(pacientApnpList).hasSize(databaseSizeBeforeUpdate);
        PacientApnp testPacientApnp = pacientApnpList.get(pacientApnpList.size() - 1);
        assertThat(testPacientApnp.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPacientApnp.getDatestarts()).isEqualTo(UPDATED_DATESTARTS);
        assertThat(testPacientApnp.getDateend()).isEqualTo(UPDATED_DATEEND);

        // Validate the PacientApnp in Elasticsearch
        PacientApnp pacientApnpEs = pacientApnpSearchRepository.findOne(testPacientApnp.getId());
        assertThat(pacientApnpEs).isEqualToComparingFieldByField(testPacientApnp);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientApnp() throws Exception {
        int databaseSizeBeforeUpdate = pacientApnpRepository.findAll().size();

        // Create the PacientApnp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientApnpMockMvc.perform(put("/api/pacient-apnps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientApnp)))
            .andExpect(status().isCreated());

        // Validate the PacientApnp in the database
        List<PacientApnp> pacientApnpList = pacientApnpRepository.findAll();
        assertThat(pacientApnpList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientApnp() throws Exception {
        // Initialize the database
        pacientApnpRepository.saveAndFlush(pacientApnp);
        pacientApnpSearchRepository.save(pacientApnp);
        int databaseSizeBeforeDelete = pacientApnpRepository.findAll().size();

        // Get the pacientApnp
        restPacientApnpMockMvc.perform(delete("/api/pacient-apnps/{id}", pacientApnp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientApnpExistsInEs = pacientApnpSearchRepository.exists(pacientApnp.getId());
        assertThat(pacientApnpExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientApnp> pacientApnpList = pacientApnpRepository.findAll();
        assertThat(pacientApnpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientApnp() throws Exception {
        // Initialize the database
        pacientApnpRepository.saveAndFlush(pacientApnp);
        pacientApnpSearchRepository.save(pacientApnp);

        // Search the pacientApnp
        restPacientApnpMockMvc.perform(get("/api/_search/pacient-apnps?query=id:" + pacientApnp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientApnp.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].datestarts").value(hasItem(DEFAULT_DATESTARTS.toString())))
            .andExpect(jsonPath("$.[*].dateend").value(hasItem(DEFAULT_DATEEND.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacientApnp.class);
        PacientApnp pacientApnp1 = new PacientApnp();
        pacientApnp1.setId(1L);
        PacientApnp pacientApnp2 = new PacientApnp();
        pacientApnp2.setId(pacientApnp1.getId());
        assertThat(pacientApnp1).isEqualTo(pacientApnp2);
        pacientApnp2.setId(2L);
        assertThat(pacientApnp1).isNotEqualTo(pacientApnp2);
        pacientApnp1.setId(null);
        assertThat(pacientApnp1).isNotEqualTo(pacientApnp2);
    }
}
