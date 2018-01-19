package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Disabilities;
import mycercardiopackege.jh.repository.DisabilitiesRepository;
import mycercardiopackege.jh.repository.search.DisabilitiesSearchRepository;
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
 * Test class for the DisabilitiesResource REST controller.
 *
 * @see DisabilitiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class DisabilitiesResourceIntTest {

    private static final String DEFAULT_SUBGROUP = "AAAAAAAAAA";
    private static final String UPDATED_SUBGROUP = "BBBBBBBBBB";

    private static final String DEFAULT_DISABILITY = "AAAAAAAAAA";
    private static final String UPDATED_DISABILITY = "BBBBBBBBBB";

    @Autowired
    private DisabilitiesRepository disabilitiesRepository;

    @Autowired
    private DisabilitiesSearchRepository disabilitiesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDisabilitiesMockMvc;

    private Disabilities disabilities;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisabilitiesResource disabilitiesResource = new DisabilitiesResource(disabilitiesRepository, disabilitiesSearchRepository);
        this.restDisabilitiesMockMvc = MockMvcBuilders.standaloneSetup(disabilitiesResource)
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
    public static Disabilities createEntity(EntityManager em) {
        Disabilities disabilities = new Disabilities()
            .subgroup(DEFAULT_SUBGROUP)
            .disability(DEFAULT_DISABILITY);
        return disabilities;
    }

    @Before
    public void initTest() {
        disabilitiesSearchRepository.deleteAll();
        disabilities = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisabilities() throws Exception {
        int databaseSizeBeforeCreate = disabilitiesRepository.findAll().size();

        // Create the Disabilities
        restDisabilitiesMockMvc.perform(post("/api/disabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disabilities)))
            .andExpect(status().isCreated());

        // Validate the Disabilities in the database
        List<Disabilities> disabilitiesList = disabilitiesRepository.findAll();
        assertThat(disabilitiesList).hasSize(databaseSizeBeforeCreate + 1);
        Disabilities testDisabilities = disabilitiesList.get(disabilitiesList.size() - 1);
        assertThat(testDisabilities.getSubgroup()).isEqualTo(DEFAULT_SUBGROUP);
        assertThat(testDisabilities.getDisability()).isEqualTo(DEFAULT_DISABILITY);

        // Validate the Disabilities in Elasticsearch
        Disabilities disabilitiesEs = disabilitiesSearchRepository.findOne(testDisabilities.getId());
        assertThat(disabilitiesEs).isEqualToComparingFieldByField(testDisabilities);
    }

    @Test
    @Transactional
    public void createDisabilitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disabilitiesRepository.findAll().size();

        // Create the Disabilities with an existing ID
        disabilities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisabilitiesMockMvc.perform(post("/api/disabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disabilities)))
            .andExpect(status().isBadRequest());

        // Validate the Disabilities in the database
        List<Disabilities> disabilitiesList = disabilitiesRepository.findAll();
        assertThat(disabilitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDisabilities() throws Exception {
        // Initialize the database
        disabilitiesRepository.saveAndFlush(disabilities);

        // Get all the disabilitiesList
        restDisabilitiesMockMvc.perform(get("/api/disabilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disabilities.getId().intValue())))
            .andExpect(jsonPath("$.[*].subgroup").value(hasItem(DEFAULT_SUBGROUP.toString())))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY.toString())));
    }

    @Test
    @Transactional
    public void getDisabilities() throws Exception {
        // Initialize the database
        disabilitiesRepository.saveAndFlush(disabilities);

        // Get the disabilities
        restDisabilitiesMockMvc.perform(get("/api/disabilities/{id}", disabilities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disabilities.getId().intValue()))
            .andExpect(jsonPath("$.subgroup").value(DEFAULT_SUBGROUP.toString()))
            .andExpect(jsonPath("$.disability").value(DEFAULT_DISABILITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDisabilities() throws Exception {
        // Get the disabilities
        restDisabilitiesMockMvc.perform(get("/api/disabilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisabilities() throws Exception {
        // Initialize the database
        disabilitiesRepository.saveAndFlush(disabilities);
        disabilitiesSearchRepository.save(disabilities);
        int databaseSizeBeforeUpdate = disabilitiesRepository.findAll().size();

        // Update the disabilities
        Disabilities updatedDisabilities = disabilitiesRepository.findOne(disabilities.getId());
        updatedDisabilities
            .subgroup(UPDATED_SUBGROUP)
            .disability(UPDATED_DISABILITY);

        restDisabilitiesMockMvc.perform(put("/api/disabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisabilities)))
            .andExpect(status().isOk());

        // Validate the Disabilities in the database
        List<Disabilities> disabilitiesList = disabilitiesRepository.findAll();
        assertThat(disabilitiesList).hasSize(databaseSizeBeforeUpdate);
        Disabilities testDisabilities = disabilitiesList.get(disabilitiesList.size() - 1);
        assertThat(testDisabilities.getSubgroup()).isEqualTo(UPDATED_SUBGROUP);
        assertThat(testDisabilities.getDisability()).isEqualTo(UPDATED_DISABILITY);

        // Validate the Disabilities in Elasticsearch
        Disabilities disabilitiesEs = disabilitiesSearchRepository.findOne(testDisabilities.getId());
        assertThat(disabilitiesEs).isEqualToComparingFieldByField(testDisabilities);
    }

    @Test
    @Transactional
    public void updateNonExistingDisabilities() throws Exception {
        int databaseSizeBeforeUpdate = disabilitiesRepository.findAll().size();

        // Create the Disabilities

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDisabilitiesMockMvc.perform(put("/api/disabilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disabilities)))
            .andExpect(status().isCreated());

        // Validate the Disabilities in the database
        List<Disabilities> disabilitiesList = disabilitiesRepository.findAll();
        assertThat(disabilitiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDisabilities() throws Exception {
        // Initialize the database
        disabilitiesRepository.saveAndFlush(disabilities);
        disabilitiesSearchRepository.save(disabilities);
        int databaseSizeBeforeDelete = disabilitiesRepository.findAll().size();

        // Get the disabilities
        restDisabilitiesMockMvc.perform(delete("/api/disabilities/{id}", disabilities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean disabilitiesExistsInEs = disabilitiesSearchRepository.exists(disabilities.getId());
        assertThat(disabilitiesExistsInEs).isFalse();

        // Validate the database is empty
        List<Disabilities> disabilitiesList = disabilitiesRepository.findAll();
        assertThat(disabilitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDisabilities() throws Exception {
        // Initialize the database
        disabilitiesRepository.saveAndFlush(disabilities);
        disabilitiesSearchRepository.save(disabilities);

        // Search the disabilities
        restDisabilitiesMockMvc.perform(get("/api/_search/disabilities?query=id:" + disabilities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disabilities.getId().intValue())))
            .andExpect(jsonPath("$.[*].subgroup").value(hasItem(DEFAULT_SUBGROUP.toString())))
            .andExpect(jsonPath("$.[*].disability").value(hasItem(DEFAULT_DISABILITY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disabilities.class);
        Disabilities disabilities1 = new Disabilities();
        disabilities1.setId(1L);
        Disabilities disabilities2 = new Disabilities();
        disabilities2.setId(disabilities1.getId());
        assertThat(disabilities1).isEqualTo(disabilities2);
        disabilities2.setId(2L);
        assertThat(disabilities1).isNotEqualTo(disabilities2);
        disabilities1.setId(null);
        assertThat(disabilities1).isNotEqualTo(disabilities2);
    }
}
