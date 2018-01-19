package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Domicile;
import mycercardiopackege.jh.repository.DomicileRepository;
import mycercardiopackege.jh.repository.search.DomicileSearchRepository;
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
 * Test class for the DomicileResource REST controller.
 *
 * @see DomicileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class DomicileResourceIntTest {

    private static final String DEFAULT_DOMICILE = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private DomicileRepository domicileRepository;

    @Autowired
    private DomicileSearchRepository domicileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDomicileMockMvc;

    private Domicile domicile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DomicileResource domicileResource = new DomicileResource(domicileRepository, domicileSearchRepository);
        this.restDomicileMockMvc = MockMvcBuilders.standaloneSetup(domicileResource)
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
    public static Domicile createEntity(EntityManager em) {
        Domicile domicile = new Domicile()
            .domicile(DEFAULT_DOMICILE)
            .isactive(DEFAULT_ISACTIVE);
        return domicile;
    }

    @Before
    public void initTest() {
        domicileSearchRepository.deleteAll();
        domicile = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomicile() throws Exception {
        int databaseSizeBeforeCreate = domicileRepository.findAll().size();

        // Create the Domicile
        restDomicileMockMvc.perform(post("/api/domiciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicile)))
            .andExpect(status().isCreated());

        // Validate the Domicile in the database
        List<Domicile> domicileList = domicileRepository.findAll();
        assertThat(domicileList).hasSize(databaseSizeBeforeCreate + 1);
        Domicile testDomicile = domicileList.get(domicileList.size() - 1);
        assertThat(testDomicile.getDomicile()).isEqualTo(DEFAULT_DOMICILE);
        assertThat(testDomicile.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the Domicile in Elasticsearch
        Domicile domicileEs = domicileSearchRepository.findOne(testDomicile.getId());
        assertThat(domicileEs).isEqualToComparingFieldByField(testDomicile);
    }

    @Test
    @Transactional
    public void createDomicileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domicileRepository.findAll().size();

        // Create the Domicile with an existing ID
        domicile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomicileMockMvc.perform(post("/api/domiciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicile)))
            .andExpect(status().isBadRequest());

        // Validate the Domicile in the database
        List<Domicile> domicileList = domicileRepository.findAll();
        assertThat(domicileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDomiciles() throws Exception {
        // Initialize the database
        domicileRepository.saveAndFlush(domicile);

        // Get all the domicileList
        restDomicileMockMvc.perform(get("/api/domiciles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicile.getId().intValue())))
            .andExpect(jsonPath("$.[*].domicile").value(hasItem(DEFAULT_DOMICILE.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getDomicile() throws Exception {
        // Initialize the database
        domicileRepository.saveAndFlush(domicile);

        // Get the domicile
        restDomicileMockMvc.perform(get("/api/domiciles/{id}", domicile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domicile.getId().intValue()))
            .andExpect(jsonPath("$.domicile").value(DEFAULT_DOMICILE.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDomicile() throws Exception {
        // Get the domicile
        restDomicileMockMvc.perform(get("/api/domiciles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomicile() throws Exception {
        // Initialize the database
        domicileRepository.saveAndFlush(domicile);
        domicileSearchRepository.save(domicile);
        int databaseSizeBeforeUpdate = domicileRepository.findAll().size();

        // Update the domicile
        Domicile updatedDomicile = domicileRepository.findOne(domicile.getId());
        updatedDomicile
            .domicile(UPDATED_DOMICILE)
            .isactive(UPDATED_ISACTIVE);

        restDomicileMockMvc.perform(put("/api/domiciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDomicile)))
            .andExpect(status().isOk());

        // Validate the Domicile in the database
        List<Domicile> domicileList = domicileRepository.findAll();
        assertThat(domicileList).hasSize(databaseSizeBeforeUpdate);
        Domicile testDomicile = domicileList.get(domicileList.size() - 1);
        assertThat(testDomicile.getDomicile()).isEqualTo(UPDATED_DOMICILE);
        assertThat(testDomicile.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the Domicile in Elasticsearch
        Domicile domicileEs = domicileSearchRepository.findOne(testDomicile.getId());
        assertThat(domicileEs).isEqualToComparingFieldByField(testDomicile);
    }

    @Test
    @Transactional
    public void updateNonExistingDomicile() throws Exception {
        int databaseSizeBeforeUpdate = domicileRepository.findAll().size();

        // Create the Domicile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDomicileMockMvc.perform(put("/api/domiciles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domicile)))
            .andExpect(status().isCreated());

        // Validate the Domicile in the database
        List<Domicile> domicileList = domicileRepository.findAll();
        assertThat(domicileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDomicile() throws Exception {
        // Initialize the database
        domicileRepository.saveAndFlush(domicile);
        domicileSearchRepository.save(domicile);
        int databaseSizeBeforeDelete = domicileRepository.findAll().size();

        // Get the domicile
        restDomicileMockMvc.perform(delete("/api/domiciles/{id}", domicile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean domicileExistsInEs = domicileSearchRepository.exists(domicile.getId());
        assertThat(domicileExistsInEs).isFalse();

        // Validate the database is empty
        List<Domicile> domicileList = domicileRepository.findAll();
        assertThat(domicileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDomicile() throws Exception {
        // Initialize the database
        domicileRepository.saveAndFlush(domicile);
        domicileSearchRepository.save(domicile);

        // Search the domicile
        restDomicileMockMvc.perform(get("/api/_search/domiciles?query=id:" + domicile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domicile.getId().intValue())))
            .andExpect(jsonPath("$.[*].domicile").value(hasItem(DEFAULT_DOMICILE.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domicile.class);
        Domicile domicile1 = new Domicile();
        domicile1.setId(1L);
        Domicile domicile2 = new Domicile();
        domicile2.setId(domicile1.getId());
        assertThat(domicile1).isEqualTo(domicile2);
        domicile2.setId(2L);
        assertThat(domicile1).isNotEqualTo(domicile2);
        domicile1.setId(null);
        assertThat(domicile1).isNotEqualTo(domicile2);
    }
}
