package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.DrugAddiction;
import mycercardiopackege.jh.repository.DrugAddictionRepository;
import mycercardiopackege.jh.repository.search.DrugAddictionSearchRepository;
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
 * Test class for the DrugAddictionResource REST controller.
 *
 * @see DrugAddictionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class DrugAddictionResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private DrugAddictionRepository drugAddictionRepository;

    @Autowired
    private DrugAddictionSearchRepository drugAddictionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDrugAddictionMockMvc;

    private DrugAddiction drugAddiction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugAddictionResource drugAddictionResource = new DrugAddictionResource(drugAddictionRepository, drugAddictionSearchRepository);
        this.restDrugAddictionMockMvc = MockMvcBuilders.standaloneSetup(drugAddictionResource)
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
    public static DrugAddiction createEntity(EntityManager em) {
        DrugAddiction drugAddiction = new DrugAddiction()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE);
        return drugAddiction;
    }

    @Before
    public void initTest() {
        drugAddictionSearchRepository.deleteAll();
        drugAddiction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugAddiction() throws Exception {
        int databaseSizeBeforeCreate = drugAddictionRepository.findAll().size();

        // Create the DrugAddiction
        restDrugAddictionMockMvc.perform(post("/api/drug-addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugAddiction)))
            .andExpect(status().isCreated());

        // Validate the DrugAddiction in the database
        List<DrugAddiction> drugAddictionList = drugAddictionRepository.findAll();
        assertThat(drugAddictionList).hasSize(databaseSizeBeforeCreate + 1);
        DrugAddiction testDrugAddiction = drugAddictionList.get(drugAddictionList.size() - 1);
        assertThat(testDrugAddiction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDrugAddiction.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the DrugAddiction in Elasticsearch
        DrugAddiction drugAddictionEs = drugAddictionSearchRepository.findOne(testDrugAddiction.getId());
        assertThat(drugAddictionEs).isEqualToComparingFieldByField(testDrugAddiction);
    }

    @Test
    @Transactional
    public void createDrugAddictionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugAddictionRepository.findAll().size();

        // Create the DrugAddiction with an existing ID
        drugAddiction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugAddictionMockMvc.perform(post("/api/drug-addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugAddiction)))
            .andExpect(status().isBadRequest());

        // Validate the DrugAddiction in the database
        List<DrugAddiction> drugAddictionList = drugAddictionRepository.findAll();
        assertThat(drugAddictionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDrugAddictions() throws Exception {
        // Initialize the database
        drugAddictionRepository.saveAndFlush(drugAddiction);

        // Get all the drugAddictionList
        restDrugAddictionMockMvc.perform(get("/api/drug-addictions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugAddiction.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getDrugAddiction() throws Exception {
        // Initialize the database
        drugAddictionRepository.saveAndFlush(drugAddiction);

        // Get the drugAddiction
        restDrugAddictionMockMvc.perform(get("/api/drug-addictions/{id}", drugAddiction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drugAddiction.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDrugAddiction() throws Exception {
        // Get the drugAddiction
        restDrugAddictionMockMvc.perform(get("/api/drug-addictions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugAddiction() throws Exception {
        // Initialize the database
        drugAddictionRepository.saveAndFlush(drugAddiction);
        drugAddictionSearchRepository.save(drugAddiction);
        int databaseSizeBeforeUpdate = drugAddictionRepository.findAll().size();

        // Update the drugAddiction
        DrugAddiction updatedDrugAddiction = drugAddictionRepository.findOne(drugAddiction.getId());
        updatedDrugAddiction
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE);

        restDrugAddictionMockMvc.perform(put("/api/drug-addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrugAddiction)))
            .andExpect(status().isOk());

        // Validate the DrugAddiction in the database
        List<DrugAddiction> drugAddictionList = drugAddictionRepository.findAll();
        assertThat(drugAddictionList).hasSize(databaseSizeBeforeUpdate);
        DrugAddiction testDrugAddiction = drugAddictionList.get(drugAddictionList.size() - 1);
        assertThat(testDrugAddiction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDrugAddiction.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the DrugAddiction in Elasticsearch
        DrugAddiction drugAddictionEs = drugAddictionSearchRepository.findOne(testDrugAddiction.getId());
        assertThat(drugAddictionEs).isEqualToComparingFieldByField(testDrugAddiction);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugAddiction() throws Exception {
        int databaseSizeBeforeUpdate = drugAddictionRepository.findAll().size();

        // Create the DrugAddiction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDrugAddictionMockMvc.perform(put("/api/drug-addictions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drugAddiction)))
            .andExpect(status().isCreated());

        // Validate the DrugAddiction in the database
        List<DrugAddiction> drugAddictionList = drugAddictionRepository.findAll();
        assertThat(drugAddictionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDrugAddiction() throws Exception {
        // Initialize the database
        drugAddictionRepository.saveAndFlush(drugAddiction);
        drugAddictionSearchRepository.save(drugAddiction);
        int databaseSizeBeforeDelete = drugAddictionRepository.findAll().size();

        // Get the drugAddiction
        restDrugAddictionMockMvc.perform(delete("/api/drug-addictions/{id}", drugAddiction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean drugAddictionExistsInEs = drugAddictionSearchRepository.exists(drugAddiction.getId());
        assertThat(drugAddictionExistsInEs).isFalse();

        // Validate the database is empty
        List<DrugAddiction> drugAddictionList = drugAddictionRepository.findAll();
        assertThat(drugAddictionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDrugAddiction() throws Exception {
        // Initialize the database
        drugAddictionRepository.saveAndFlush(drugAddiction);
        drugAddictionSearchRepository.save(drugAddiction);

        // Search the drugAddiction
        restDrugAddictionMockMvc.perform(get("/api/_search/drug-addictions?query=id:" + drugAddiction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugAddiction.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrugAddiction.class);
        DrugAddiction drugAddiction1 = new DrugAddiction();
        drugAddiction1.setId(1L);
        DrugAddiction drugAddiction2 = new DrugAddiction();
        drugAddiction2.setId(drugAddiction1.getId());
        assertThat(drugAddiction1).isEqualTo(drugAddiction2);
        drugAddiction2.setId(2L);
        assertThat(drugAddiction1).isNotEqualTo(drugAddiction2);
        drugAddiction1.setId(null);
        assertThat(drugAddiction1).isNotEqualTo(drugAddiction2);
    }
}
