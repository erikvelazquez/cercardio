package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.MedicNurse;
import mycercardiopackege.jh.repository.MedicNurseRepository;
import mycercardiopackege.jh.repository.search.MedicNurseSearchRepository;
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
 * Test class for the MedicNurseResource REST controller.
 *
 * @see MedicNurseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class MedicNurseResourceIntTest {

    private static final Integer DEFAULT_IDNURSE = 1;
    private static final Integer UPDATED_IDNURSE = 2;

    @Autowired
    private MedicNurseRepository medicNurseRepository;

    @Autowired
    private MedicNurseSearchRepository medicNurseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicNurseMockMvc;

    private MedicNurse medicNurse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicNurseResource medicNurseResource = new MedicNurseResource(medicNurseRepository, medicNurseSearchRepository);
        this.restMedicNurseMockMvc = MockMvcBuilders.standaloneSetup(medicNurseResource)
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
    public static MedicNurse createEntity(EntityManager em) {
        MedicNurse medicNurse = new MedicNurse()
            .idnurse(DEFAULT_IDNURSE);
        return medicNurse;
    }

    @Before
    public void initTest() {
        medicNurseSearchRepository.deleteAll();
        medicNurse = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicNurse() throws Exception {
        int databaseSizeBeforeCreate = medicNurseRepository.findAll().size();

        // Create the MedicNurse
        restMedicNurseMockMvc.perform(post("/api/medic-nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicNurse)))
            .andExpect(status().isCreated());

        // Validate the MedicNurse in the database
        List<MedicNurse> medicNurseList = medicNurseRepository.findAll();
        assertThat(medicNurseList).hasSize(databaseSizeBeforeCreate + 1);
        MedicNurse testMedicNurse = medicNurseList.get(medicNurseList.size() - 1);
        assertThat(testMedicNurse.getIdnurse()).isEqualTo(DEFAULT_IDNURSE);

        // Validate the MedicNurse in Elasticsearch
        MedicNurse medicNurseEs = medicNurseSearchRepository.findOne(testMedicNurse.getId());
        assertThat(medicNurseEs).isEqualToComparingFieldByField(testMedicNurse);
    }

    @Test
    @Transactional
    public void createMedicNurseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicNurseRepository.findAll().size();

        // Create the MedicNurse with an existing ID
        medicNurse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicNurseMockMvc.perform(post("/api/medic-nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicNurse)))
            .andExpect(status().isBadRequest());

        // Validate the MedicNurse in the database
        List<MedicNurse> medicNurseList = medicNurseRepository.findAll();
        assertThat(medicNurseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicNurses() throws Exception {
        // Initialize the database
        medicNurseRepository.saveAndFlush(medicNurse);

        // Get all the medicNurseList
        restMedicNurseMockMvc.perform(get("/api/medic-nurses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicNurse.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnurse").value(hasItem(DEFAULT_IDNURSE)));
    }

    @Test
    @Transactional
    public void getMedicNurse() throws Exception {
        // Initialize the database
        medicNurseRepository.saveAndFlush(medicNurse);

        // Get the medicNurse
        restMedicNurseMockMvc.perform(get("/api/medic-nurses/{id}", medicNurse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicNurse.getId().intValue()))
            .andExpect(jsonPath("$.idnurse").value(DEFAULT_IDNURSE));
    }

    @Test
    @Transactional
    public void getNonExistingMedicNurse() throws Exception {
        // Get the medicNurse
        restMedicNurseMockMvc.perform(get("/api/medic-nurses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicNurse() throws Exception {
        // Initialize the database
        medicNurseRepository.saveAndFlush(medicNurse);
        medicNurseSearchRepository.save(medicNurse);
        int databaseSizeBeforeUpdate = medicNurseRepository.findAll().size();

        // Update the medicNurse
        MedicNurse updatedMedicNurse = medicNurseRepository.findOne(medicNurse.getId());
        updatedMedicNurse
            .idnurse(UPDATED_IDNURSE);

        restMedicNurseMockMvc.perform(put("/api/medic-nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicNurse)))
            .andExpect(status().isOk());

        // Validate the MedicNurse in the database
        List<MedicNurse> medicNurseList = medicNurseRepository.findAll();
        assertThat(medicNurseList).hasSize(databaseSizeBeforeUpdate);
        MedicNurse testMedicNurse = medicNurseList.get(medicNurseList.size() - 1);
        assertThat(testMedicNurse.getIdnurse()).isEqualTo(UPDATED_IDNURSE);

        // Validate the MedicNurse in Elasticsearch
        MedicNurse medicNurseEs = medicNurseSearchRepository.findOne(testMedicNurse.getId());
        assertThat(medicNurseEs).isEqualToComparingFieldByField(testMedicNurse);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicNurse() throws Exception {
        int databaseSizeBeforeUpdate = medicNurseRepository.findAll().size();

        // Create the MedicNurse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicNurseMockMvc.perform(put("/api/medic-nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicNurse)))
            .andExpect(status().isCreated());

        // Validate the MedicNurse in the database
        List<MedicNurse> medicNurseList = medicNurseRepository.findAll();
        assertThat(medicNurseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicNurse() throws Exception {
        // Initialize the database
        medicNurseRepository.saveAndFlush(medicNurse);
        medicNurseSearchRepository.save(medicNurse);
        int databaseSizeBeforeDelete = medicNurseRepository.findAll().size();

        // Get the medicNurse
        restMedicNurseMockMvc.perform(delete("/api/medic-nurses/{id}", medicNurse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medicNurseExistsInEs = medicNurseSearchRepository.exists(medicNurse.getId());
        assertThat(medicNurseExistsInEs).isFalse();

        // Validate the database is empty
        List<MedicNurse> medicNurseList = medicNurseRepository.findAll();
        assertThat(medicNurseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedicNurse() throws Exception {
        // Initialize the database
        medicNurseRepository.saveAndFlush(medicNurse);
        medicNurseSearchRepository.save(medicNurse);

        // Search the medicNurse
        restMedicNurseMockMvc.perform(get("/api/_search/medic-nurses?query=id:" + medicNurse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicNurse.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnurse").value(hasItem(DEFAULT_IDNURSE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicNurse.class);
        MedicNurse medicNurse1 = new MedicNurse();
        medicNurse1.setId(1L);
        MedicNurse medicNurse2 = new MedicNurse();
        medicNurse2.setId(medicNurse1.getId());
        assertThat(medicNurse1).isEqualTo(medicNurse2);
        medicNurse2.setId(2L);
        assertThat(medicNurse1).isNotEqualTo(medicNurse2);
        medicNurse1.setId(null);
        assertThat(medicNurse1).isNotEqualTo(medicNurse2);
    }
}
