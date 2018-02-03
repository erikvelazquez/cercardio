package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Way_of_Administration;
import mycercardiopackege.jh.repository.Way_of_AdministrationRepository;
import mycercardiopackege.jh.repository.search.Way_of_AdministrationSearchRepository;
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
 * Test class for the Way_of_AdministrationResource REST controller.
 *
 * @see Way_of_AdministrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class Way_of_AdministrationResourceIntTest {

    private static final String DEFAULT_WAYOFADMINISTRATION = "AAAAAAAAAA";
    private static final String UPDATED_WAYOFADMINISTRATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private Way_of_AdministrationRepository way_of_AdministrationRepository;

    @Autowired
    private Way_of_AdministrationSearchRepository way_of_AdministrationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWay_of_AdministrationMockMvc;

    private Way_of_Administration way_of_Administration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Way_of_AdministrationResource way_of_AdministrationResource = new Way_of_AdministrationResource(way_of_AdministrationRepository, way_of_AdministrationSearchRepository);
        this.restWay_of_AdministrationMockMvc = MockMvcBuilders.standaloneSetup(way_of_AdministrationResource)
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
    public static Way_of_Administration createEntity(EntityManager em) {
        Way_of_Administration way_of_Administration = new Way_of_Administration()
            .wayofadministration(DEFAULT_WAYOFADMINISTRATION)
            .isactive(DEFAULT_ISACTIVE);
        return way_of_Administration;
    }

    @Before
    public void initTest() {
        way_of_AdministrationSearchRepository.deleteAll();
        way_of_Administration = createEntity(em);
    }

    @Test
    @Transactional
    public void createWay_of_Administration() throws Exception {
        int databaseSizeBeforeCreate = way_of_AdministrationRepository.findAll().size();

        // Create the Way_of_Administration
        restWay_of_AdministrationMockMvc.perform(post("/api/way-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(way_of_Administration)))
            .andExpect(status().isCreated());

        // Validate the Way_of_Administration in the database
        List<Way_of_Administration> way_of_AdministrationList = way_of_AdministrationRepository.findAll();
        assertThat(way_of_AdministrationList).hasSize(databaseSizeBeforeCreate + 1);
        Way_of_Administration testWay_of_Administration = way_of_AdministrationList.get(way_of_AdministrationList.size() - 1);
        assertThat(testWay_of_Administration.getWayofadministration()).isEqualTo(DEFAULT_WAYOFADMINISTRATION);
        assertThat(testWay_of_Administration.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the Way_of_Administration in Elasticsearch
        Way_of_Administration way_of_AdministrationEs = way_of_AdministrationSearchRepository.findOne(testWay_of_Administration.getId());
        assertThat(way_of_AdministrationEs).isEqualToComparingFieldByField(testWay_of_Administration);
    }

    @Test
    @Transactional
    public void createWay_of_AdministrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = way_of_AdministrationRepository.findAll().size();

        // Create the Way_of_Administration with an existing ID
        way_of_Administration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWay_of_AdministrationMockMvc.perform(post("/api/way-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(way_of_Administration)))
            .andExpect(status().isBadRequest());

        // Validate the Way_of_Administration in the database
        List<Way_of_Administration> way_of_AdministrationList = way_of_AdministrationRepository.findAll();
        assertThat(way_of_AdministrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWay_of_Administrations() throws Exception {
        // Initialize the database
        way_of_AdministrationRepository.saveAndFlush(way_of_Administration);

        // Get all the way_of_AdministrationList
        restWay_of_AdministrationMockMvc.perform(get("/api/way-of-administrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(way_of_Administration.getId().intValue())))
            .andExpect(jsonPath("$.[*].wayofadministration").value(hasItem(DEFAULT_WAYOFADMINISTRATION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getWay_of_Administration() throws Exception {
        // Initialize the database
        way_of_AdministrationRepository.saveAndFlush(way_of_Administration);

        // Get the way_of_Administration
        restWay_of_AdministrationMockMvc.perform(get("/api/way-of-administrations/{id}", way_of_Administration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(way_of_Administration.getId().intValue()))
            .andExpect(jsonPath("$.wayofadministration").value(DEFAULT_WAYOFADMINISTRATION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWay_of_Administration() throws Exception {
        // Get the way_of_Administration
        restWay_of_AdministrationMockMvc.perform(get("/api/way-of-administrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWay_of_Administration() throws Exception {
        // Initialize the database
        way_of_AdministrationRepository.saveAndFlush(way_of_Administration);
        way_of_AdministrationSearchRepository.save(way_of_Administration);
        int databaseSizeBeforeUpdate = way_of_AdministrationRepository.findAll().size();

        // Update the way_of_Administration
        Way_of_Administration updatedWay_of_Administration = way_of_AdministrationRepository.findOne(way_of_Administration.getId());
        updatedWay_of_Administration
            .wayofadministration(UPDATED_WAYOFADMINISTRATION)
            .isactive(UPDATED_ISACTIVE);

        restWay_of_AdministrationMockMvc.perform(put("/api/way-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWay_of_Administration)))
            .andExpect(status().isOk());

        // Validate the Way_of_Administration in the database
        List<Way_of_Administration> way_of_AdministrationList = way_of_AdministrationRepository.findAll();
        assertThat(way_of_AdministrationList).hasSize(databaseSizeBeforeUpdate);
        Way_of_Administration testWay_of_Administration = way_of_AdministrationList.get(way_of_AdministrationList.size() - 1);
        assertThat(testWay_of_Administration.getWayofadministration()).isEqualTo(UPDATED_WAYOFADMINISTRATION);
        assertThat(testWay_of_Administration.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the Way_of_Administration in Elasticsearch
        Way_of_Administration way_of_AdministrationEs = way_of_AdministrationSearchRepository.findOne(testWay_of_Administration.getId());
        assertThat(way_of_AdministrationEs).isEqualToComparingFieldByField(testWay_of_Administration);
    }

    @Test
    @Transactional
    public void updateNonExistingWay_of_Administration() throws Exception {
        int databaseSizeBeforeUpdate = way_of_AdministrationRepository.findAll().size();

        // Create the Way_of_Administration

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWay_of_AdministrationMockMvc.perform(put("/api/way-of-administrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(way_of_Administration)))
            .andExpect(status().isCreated());

        // Validate the Way_of_Administration in the database
        List<Way_of_Administration> way_of_AdministrationList = way_of_AdministrationRepository.findAll();
        assertThat(way_of_AdministrationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWay_of_Administration() throws Exception {
        // Initialize the database
        way_of_AdministrationRepository.saveAndFlush(way_of_Administration);
        way_of_AdministrationSearchRepository.save(way_of_Administration);
        int databaseSizeBeforeDelete = way_of_AdministrationRepository.findAll().size();

        // Get the way_of_Administration
        restWay_of_AdministrationMockMvc.perform(delete("/api/way-of-administrations/{id}", way_of_Administration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean way_of_AdministrationExistsInEs = way_of_AdministrationSearchRepository.exists(way_of_Administration.getId());
        assertThat(way_of_AdministrationExistsInEs).isFalse();

        // Validate the database is empty
        List<Way_of_Administration> way_of_AdministrationList = way_of_AdministrationRepository.findAll();
        assertThat(way_of_AdministrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchWay_of_Administration() throws Exception {
        // Initialize the database
        way_of_AdministrationRepository.saveAndFlush(way_of_Administration);
        way_of_AdministrationSearchRepository.save(way_of_Administration);

        // Search the way_of_Administration
        restWay_of_AdministrationMockMvc.perform(get("/api/_search/way-of-administrations?query=id:" + way_of_Administration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(way_of_Administration.getId().intValue())))
            .andExpect(jsonPath("$.[*].wayofadministration").value(hasItem(DEFAULT_WAYOFADMINISTRATION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Way_of_Administration.class);
        Way_of_Administration way_of_Administration1 = new Way_of_Administration();
        way_of_Administration1.setId(1L);
        Way_of_Administration way_of_Administration2 = new Way_of_Administration();
        way_of_Administration2.setId(way_of_Administration1.getId());
        assertThat(way_of_Administration1).isEqualTo(way_of_Administration2);
        way_of_Administration2.setId(2L);
        assertThat(way_of_Administration1).isNotEqualTo(way_of_Administration2);
        way_of_Administration1.setId(null);
        assertThat(way_of_Administration1).isNotEqualTo(way_of_Administration2);
    }
}
