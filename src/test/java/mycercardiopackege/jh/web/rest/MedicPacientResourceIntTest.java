package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.MedicPacient;
import mycercardiopackege.jh.repository.MedicPacientRepository;
import mycercardiopackege.jh.repository.search.MedicPacientSearchRepository;
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
 * Test class for the MedicPacientResource REST controller.
 *
 * @see MedicPacientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class MedicPacientResourceIntTest {

    @Autowired
    private MedicPacientRepository medicPacientRepository;

    @Autowired
    private MedicPacientSearchRepository medicPacientSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicPacientMockMvc;

    private MedicPacient medicPacient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicPacientResource medicPacientResource = new MedicPacientResource(medicPacientRepository, medicPacientSearchRepository);
        this.restMedicPacientMockMvc = MockMvcBuilders.standaloneSetup(medicPacientResource)
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
    public static MedicPacient createEntity(EntityManager em) {
        MedicPacient medicPacient = new MedicPacient();
        return medicPacient;
    }

    @Before
    public void initTest() {
        medicPacientSearchRepository.deleteAll();
        medicPacient = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicPacient() throws Exception {
        int databaseSizeBeforeCreate = medicPacientRepository.findAll().size();

        // Create the MedicPacient
        restMedicPacientMockMvc.perform(post("/api/medic-pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicPacient)))
            .andExpect(status().isCreated());

        // Validate the MedicPacient in the database
        List<MedicPacient> medicPacientList = medicPacientRepository.findAll();
        assertThat(medicPacientList).hasSize(databaseSizeBeforeCreate + 1);
        MedicPacient testMedicPacient = medicPacientList.get(medicPacientList.size() - 1);

        // Validate the MedicPacient in Elasticsearch
        MedicPacient medicPacientEs = medicPacientSearchRepository.findOne(testMedicPacient.getId());
        assertThat(medicPacientEs).isEqualToComparingFieldByField(testMedicPacient);
    }

    @Test
    @Transactional
    public void createMedicPacientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicPacientRepository.findAll().size();

        // Create the MedicPacient with an existing ID
        medicPacient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicPacientMockMvc.perform(post("/api/medic-pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicPacient)))
            .andExpect(status().isBadRequest());

        // Validate the MedicPacient in the database
        List<MedicPacient> medicPacientList = medicPacientRepository.findAll();
        assertThat(medicPacientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicPacients() throws Exception {
        // Initialize the database
        medicPacientRepository.saveAndFlush(medicPacient);

        // Get all the medicPacientList
        restMedicPacientMockMvc.perform(get("/api/medic-pacients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicPacient.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMedicPacient() throws Exception {
        // Initialize the database
        medicPacientRepository.saveAndFlush(medicPacient);

        // Get the medicPacient
        restMedicPacientMockMvc.perform(get("/api/medic-pacients/{id}", medicPacient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicPacient.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicPacient() throws Exception {
        // Get the medicPacient
        restMedicPacientMockMvc.perform(get("/api/medic-pacients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicPacient() throws Exception {
        // Initialize the database
        medicPacientRepository.saveAndFlush(medicPacient);
        medicPacientSearchRepository.save(medicPacient);
        int databaseSizeBeforeUpdate = medicPacientRepository.findAll().size();

        // Update the medicPacient
        MedicPacient updatedMedicPacient = medicPacientRepository.findOne(medicPacient.getId());

        restMedicPacientMockMvc.perform(put("/api/medic-pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicPacient)))
            .andExpect(status().isOk());

        // Validate the MedicPacient in the database
        List<MedicPacient> medicPacientList = medicPacientRepository.findAll();
        assertThat(medicPacientList).hasSize(databaseSizeBeforeUpdate);
        MedicPacient testMedicPacient = medicPacientList.get(medicPacientList.size() - 1);

        // Validate the MedicPacient in Elasticsearch
        MedicPacient medicPacientEs = medicPacientSearchRepository.findOne(testMedicPacient.getId());
        assertThat(medicPacientEs).isEqualToComparingFieldByField(testMedicPacient);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicPacient() throws Exception {
        int databaseSizeBeforeUpdate = medicPacientRepository.findAll().size();

        // Create the MedicPacient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicPacientMockMvc.perform(put("/api/medic-pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicPacient)))
            .andExpect(status().isCreated());

        // Validate the MedicPacient in the database
        List<MedicPacient> medicPacientList = medicPacientRepository.findAll();
        assertThat(medicPacientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicPacient() throws Exception {
        // Initialize the database
        medicPacientRepository.saveAndFlush(medicPacient);
        medicPacientSearchRepository.save(medicPacient);
        int databaseSizeBeforeDelete = medicPacientRepository.findAll().size();

        // Get the medicPacient
        restMedicPacientMockMvc.perform(delete("/api/medic-pacients/{id}", medicPacient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medicPacientExistsInEs = medicPacientSearchRepository.exists(medicPacient.getId());
        assertThat(medicPacientExistsInEs).isFalse();

        // Validate the database is empty
        List<MedicPacient> medicPacientList = medicPacientRepository.findAll();
        assertThat(medicPacientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedicPacient() throws Exception {
        // Initialize the database
        medicPacientRepository.saveAndFlush(medicPacient);
        medicPacientSearchRepository.save(medicPacient);

        // Search the medicPacient
        restMedicPacientMockMvc.perform(get("/api/_search/medic-pacients?query=id:" + medicPacient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicPacient.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicPacient.class);
        MedicPacient medicPacient1 = new MedicPacient();
        medicPacient1.setId(1L);
        MedicPacient medicPacient2 = new MedicPacient();
        medicPacient2.setId(medicPacient1.getId());
        assertThat(medicPacient1).isEqualTo(medicPacient2);
        medicPacient2.setId(2L);
        assertThat(medicPacient1).isNotEqualTo(medicPacient2);
        medicPacient1.setId(null);
        assertThat(medicPacient1).isNotEqualTo(medicPacient2);
    }
}
