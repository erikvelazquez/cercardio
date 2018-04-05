package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.SocioEconomicLevel;
import mycercardiopackege.jh.repository.SocioEconomicLevelRepository;
import mycercardiopackege.jh.repository.search.SocioEconomicLevelSearchRepository;
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
 * Test class for the SocioeconomicLevelResource REST controller.
 *
 * @see SocioeconomicLevelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class SocioEconomicLevelResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private SocioEconomicLevelRepository socioEconomicLevelRepository;

    @Autowired
    private SocioEconomicLevelSearchRepository socioEconomicLevelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSocioEconomicLevelMockMvc;

    private SocioEconomicLevel socioEconomicLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocioEconomicLevelResource socioEconomicLevelResource = new SocioEconomicLevelResource(socioEconomicLevelRepository, socioEconomicLevelSearchRepository);
        this.restSocioEconomicLevelMockMvc = MockMvcBuilders.standaloneSetup(socioEconomicLevelResource)
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
    public static SocioEconomicLevel createEntity(EntityManager em) {
        SocioEconomicLevel socioEconomicLevel = new SocioEconomicLevel()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE);
        return socioEconomicLevel;
    }

    @Before
    public void initTest() {
        socioEconomicLevelSearchRepository.deleteAll();
        socioEconomicLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocioEconomicLevel() throws Exception {
        int databaseSizeBeforeCreate = socioEconomicLevelRepository.findAll().size();

        // Create the SocioEconomicLevel
        restSocioEconomicLevelMockMvc.perform(post("/api/socioEconomic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socioEconomicLevel)))
            .andExpect(status().isCreated());

        // Validate the SocioEconomicLevel in the database
        List<SocioEconomicLevel> socioEconomicLevelList = socioEconomicLevelRepository.findAll();
        assertThat(socioEconomicLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SocioEconomicLevel testSocioEconomicLevel = socioEconomicLevelList.get(socioEconomicLevelList.size() - 1);
        assertThat(testSocioEconomicLevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSocioEconomicLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocioEconomicLevel.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the SocioEconomicLevel in Elasticsearch
        SocioEconomicLevel socioEconomicLevelEs = socioEconomicLevelSearchRepository.findOne(testSocioEconomicLevel.getId());
        assertThat(socioEconomicLevelEs).isEqualToComparingFieldByField(testSocioEconomicLevel);
    }

    @Test
    @Transactional
    public void createSocioEconomicLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socioEconomicLevelRepository.findAll().size();

        // Create the SocioEconomicLevel with an existing ID
        socioEconomicLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocioEconomicLevelMockMvc.perform(post("/api/socio-economic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socioEconomicLevel)))
            .andExpect(status().isBadRequest());

        // Validate the SocioEconomicLevel in the database
        List<SocioEconomicLevel> socioEconomicLevelList = socioEconomicLevelRepository.findAll();
        assertThat(socioEconomicLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocioEconomicLevels() throws Exception {
        // Initialize the database
        socioEconomicLevelRepository.saveAndFlush(socioEconomicLevel);

        // Get all the socioEconomicLevelList
        restSocioEconomicLevelMockMvc.perform(get("/api/socio-economic-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socioEconomicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getSocioEconomicLevel() throws Exception {
        // Initialize the database
        socioEconomicLevelRepository.saveAndFlush(socioEconomicLevel);

        // Get the socioEconomicLevel
        restSocioEconomicLevelMockMvc.perform(get("/api/socio-economic-levels/{id}", socioEconomicLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socioEconomicLevel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSocioEconomicLevel() throws Exception {
        // Get the socioEconomicLevel
        restSocioEconomicLevelMockMvc.perform(get("/api/socio-economic-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocioEconomicLevel() throws Exception {
        // Initialize the database
        socioEconomicLevelRepository.saveAndFlush(socioEconomicLevel);
        socioEconomicLevelSearchRepository.save(socioEconomicLevel);
        int databaseSizeBeforeUpdate = socioEconomicLevelRepository.findAll().size();

        // Update the socioEconomicLevel
        SocioEconomicLevel updatedSocioEconomicLevel = socioEconomicLevelRepository.findOne(socioEconomicLevel.getId());
        updatedSocioEconomicLevel
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE);

        restSocioEconomicLevelMockMvc.perform(put("/api/socio-economic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocioEconomicLevel)))
            .andExpect(status().isOk());

        // Validate the SocioEconomicLevel in the database
        List<SocioEconomicLevel> socioEconomicLevelList = socioEconomicLevelRepository.findAll();
        assertThat(socioEconomicLevelList).hasSize(databaseSizeBeforeUpdate);
        SocioEconomicLevel testSocioEconomicLevel = socioEconomicLevelList.get(socioEconomicLevelList.size() - 1);
        assertThat(testSocioEconomicLevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSocioEconomicLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocioEconomicLevel.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the SocioEconomicLevel in Elasticsearch
        SocioEconomicLevel socioEconomicLevelEs = socioEconomicLevelSearchRepository.findOne(testSocioEconomicLevel.getId());
        assertThat(socioEconomicLevelEs).isEqualToComparingFieldByField(testSocioEconomicLevel);
    }

    @Test
    @Transactional
    public void updateNonExistingSocioEconomicLevel() throws Exception {
        int databaseSizeBeforeUpdate = socioEconomicLevelRepository.findAll().size();

        // Create the SocioEconomicLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocioEconomicLevelMockMvc.perform(put("/api/socio-economic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socioEconomicLevel)))
            .andExpect(status().isCreated());

        // Validate the SocioEconomicLevel in the database
        List<SocioEconomicLevel> socioEconomicLevelList = socioEconomicLevelRepository.findAll();
        assertThat(socioEconomicLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocioEconomicLevel() throws Exception {
        // Initialize the database
        socioEconomicLevelRepository.saveAndFlush(socioEconomicLevel);
        socioEconomicLevelSearchRepository.save(socioEconomicLevel);
        int databaseSizeBeforeDelete = socioEconomicLevelRepository.findAll().size();

        // Get the socioEconomicLevel
        restSocioEconomicLevelMockMvc.perform(delete("/api/socio-economic-levels/{id}", socioEconomicLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean socioEconomicLevelExistsInEs = socioEconomicLevelSearchRepository.exists(socioEconomicLevel.getId());
        assertThat(socioEconomicLevelExistsInEs).isFalse();

        // Validate the database is empty
        List<SocioEconomicLevel> socioEconomicLevelList = socioEconomicLevelRepository.findAll();
        assertThat(socioEconomicLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSocioEconomicLevel() throws Exception {
        // Initialize the database
        socioEconomicLevelRepository.saveAndFlush(socioEconomicLevel);
        socioEconomicLevelSearchRepository.save(socioEconomicLevel);

        // Search the socioEconomicLevel
        restSocioEconomicLevelMockMvc.perform(get("/api/_search/socio-economic-levels?query=id:" + socioEconomicLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socioEconomicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocioEconomicLevel.class);
        SocioEconomicLevel socioEconomicLevel1 = new SocioEconomicLevel();
        socioEconomicLevel1.setId(1L);
        SocioEconomicLevel socioEconomicLevel2 = new SocioEconomicLevel();
        socioEconomicLevel2.setId(socioEconomicLevel1.getId());
        assertThat(socioEconomicLevel1).isEqualTo(socioEconomicLevel2);
        socioEconomicLevel2.setId(2L);
        assertThat(socioEconomicLevel1).isNotEqualTo(socioEconomicLevel2);
        socioEconomicLevel1.setId(null);
        assertThat(socioEconomicLevel1).isNotEqualTo(socioEconomicLevel2);
    }
}
