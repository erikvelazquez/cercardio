package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.SocioeconomicLevel;
import mycercardiopackege.jh.repository.SocioeconomicLevelRepository;
import mycercardiopackege.jh.repository.search.SocioeconomicLevelSearchRepository;
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
public class SocioeconomicLevelResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private SocioeconomicLevelRepository socioeconomicLevelRepository;

    @Autowired
    private SocioeconomicLevelSearchRepository socioeconomicLevelSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSocioeconomicLevelMockMvc;

    private SocioeconomicLevel socioeconomicLevel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocioeconomicLevelResource socioeconomicLevelResource = new SocioeconomicLevelResource(socioeconomicLevelRepository, socioeconomicLevelSearchRepository);
        this.restSocioeconomicLevelMockMvc = MockMvcBuilders.standaloneSetup(socioeconomicLevelResource)
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
    public static SocioeconomicLevel createEntity(EntityManager em) {
        SocioeconomicLevel socioeconomicLevel = new SocioeconomicLevel()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE);
        return socioeconomicLevel;
    }

    @Before
    public void initTest() {
        socioeconomicLevelSearchRepository.deleteAll();
        socioeconomicLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocioeconomicLevel() throws Exception {
        int databaseSizeBeforeCreate = socioeconomicLevelRepository.findAll().size();

        // Create the SocioeconomicLevel
        restSocioeconomicLevelMockMvc.perform(post("/api/socioeconomic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socioeconomicLevel)))
            .andExpect(status().isCreated());

        // Validate the SocioeconomicLevel in the database
        List<SocioeconomicLevel> socioeconomicLevelList = socioeconomicLevelRepository.findAll();
        assertThat(socioeconomicLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SocioeconomicLevel testSocioeconomicLevel = socioeconomicLevelList.get(socioeconomicLevelList.size() - 1);
        assertThat(testSocioeconomicLevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSocioeconomicLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocioeconomicLevel.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the SocioeconomicLevel in Elasticsearch
        SocioeconomicLevel socioeconomicLevelEs = socioeconomicLevelSearchRepository.findOne(testSocioeconomicLevel.getId());
        assertThat(socioeconomicLevelEs).isEqualToComparingFieldByField(testSocioeconomicLevel);
    }

    @Test
    @Transactional
    public void createSocioeconomicLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socioeconomicLevelRepository.findAll().size();

        // Create the SocioeconomicLevel with an existing ID
        socioeconomicLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocioeconomicLevelMockMvc.perform(post("/api/socioeconomic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socioeconomicLevel)))
            .andExpect(status().isBadRequest());

        // Validate the SocioeconomicLevel in the database
        List<SocioeconomicLevel> socioeconomicLevelList = socioeconomicLevelRepository.findAll();
        assertThat(socioeconomicLevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocioeconomicLevels() throws Exception {
        // Initialize the database
        socioeconomicLevelRepository.saveAndFlush(socioeconomicLevel);

        // Get all the socioeconomicLevelList
        restSocioeconomicLevelMockMvc.perform(get("/api/socioeconomic-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socioeconomicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getSocioeconomicLevel() throws Exception {
        // Initialize the database
        socioeconomicLevelRepository.saveAndFlush(socioeconomicLevel);

        // Get the socioeconomicLevel
        restSocioeconomicLevelMockMvc.perform(get("/api/socioeconomic-levels/{id}", socioeconomicLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socioeconomicLevel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSocioeconomicLevel() throws Exception {
        // Get the socioeconomicLevel
        restSocioeconomicLevelMockMvc.perform(get("/api/socioeconomic-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocioeconomicLevel() throws Exception {
        // Initialize the database
        socioeconomicLevelRepository.saveAndFlush(socioeconomicLevel);
        socioeconomicLevelSearchRepository.save(socioeconomicLevel);
        int databaseSizeBeforeUpdate = socioeconomicLevelRepository.findAll().size();

        // Update the socioeconomicLevel
        SocioeconomicLevel updatedSocioeconomicLevel = socioeconomicLevelRepository.findOne(socioeconomicLevel.getId());
        updatedSocioeconomicLevel
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE);

        restSocioeconomicLevelMockMvc.perform(put("/api/socioeconomic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocioeconomicLevel)))
            .andExpect(status().isOk());

        // Validate the SocioeconomicLevel in the database
        List<SocioeconomicLevel> socioeconomicLevelList = socioeconomicLevelRepository.findAll();
        assertThat(socioeconomicLevelList).hasSize(databaseSizeBeforeUpdate);
        SocioeconomicLevel testSocioeconomicLevel = socioeconomicLevelList.get(socioeconomicLevelList.size() - 1);
        assertThat(testSocioeconomicLevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSocioeconomicLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocioeconomicLevel.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the SocioeconomicLevel in Elasticsearch
        SocioeconomicLevel socioeconomicLevelEs = socioeconomicLevelSearchRepository.findOne(testSocioeconomicLevel.getId());
        assertThat(socioeconomicLevelEs).isEqualToComparingFieldByField(testSocioeconomicLevel);
    }

    @Test
    @Transactional
    public void updateNonExistingSocioeconomicLevel() throws Exception {
        int databaseSizeBeforeUpdate = socioeconomicLevelRepository.findAll().size();

        // Create the SocioeconomicLevel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocioeconomicLevelMockMvc.perform(put("/api/socioeconomic-levels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socioeconomicLevel)))
            .andExpect(status().isCreated());

        // Validate the SocioeconomicLevel in the database
        List<SocioeconomicLevel> socioeconomicLevelList = socioeconomicLevelRepository.findAll();
        assertThat(socioeconomicLevelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocioeconomicLevel() throws Exception {
        // Initialize the database
        socioeconomicLevelRepository.saveAndFlush(socioeconomicLevel);
        socioeconomicLevelSearchRepository.save(socioeconomicLevel);
        int databaseSizeBeforeDelete = socioeconomicLevelRepository.findAll().size();

        // Get the socioeconomicLevel
        restSocioeconomicLevelMockMvc.perform(delete("/api/socioeconomic-levels/{id}", socioeconomicLevel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean socioeconomicLevelExistsInEs = socioeconomicLevelSearchRepository.exists(socioeconomicLevel.getId());
        assertThat(socioeconomicLevelExistsInEs).isFalse();

        // Validate the database is empty
        List<SocioeconomicLevel> socioeconomicLevelList = socioeconomicLevelRepository.findAll();
        assertThat(socioeconomicLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSocioeconomicLevel() throws Exception {
        // Initialize the database
        socioeconomicLevelRepository.saveAndFlush(socioeconomicLevel);
        socioeconomicLevelSearchRepository.save(socioeconomicLevel);

        // Search the socioeconomicLevel
        restSocioeconomicLevelMockMvc.perform(get("/api/_search/socioeconomic-levels?query=id:" + socioeconomicLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socioeconomicLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocioeconomicLevel.class);
        SocioeconomicLevel socioeconomicLevel1 = new SocioeconomicLevel();
        socioeconomicLevel1.setId(1L);
        SocioeconomicLevel socioeconomicLevel2 = new SocioeconomicLevel();
        socioeconomicLevel2.setId(socioeconomicLevel1.getId());
        assertThat(socioeconomicLevel1).isEqualTo(socioeconomicLevel2);
        socioeconomicLevel2.setId(2L);
        assertThat(socioeconomicLevel1).isNotEqualTo(socioeconomicLevel2);
        socioeconomicLevel1.setId(null);
        assertThat(socioeconomicLevel1).isNotEqualTo(socioeconomicLevel2);
    }
}
