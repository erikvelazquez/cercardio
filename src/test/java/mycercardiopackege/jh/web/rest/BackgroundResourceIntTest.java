package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Background;
import mycercardiopackege.jh.repository.BackgroundRepository;
import mycercardiopackege.jh.repository.search.BackgroundSearchRepository;
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
 * Test class for the BackgroundResource REST controller.
 *
 * @see BackgroundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class BackgroundResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private BackgroundRepository backgroundRepository;

    @Autowired
    private BackgroundSearchRepository backgroundSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBackgroundMockMvc;

    private Background background;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BackgroundResource backgroundResource = new BackgroundResource(backgroundRepository, backgroundSearchRepository);
        this.restBackgroundMockMvc = MockMvcBuilders.standaloneSetup(backgroundResource)
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
    public static Background createEntity(EntityManager em) {
        Background background = new Background()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE);
        return background;
    }

    @Before
    public void initTest() {
        backgroundSearchRepository.deleteAll();
        background = createEntity(em);
    }

    @Test
    @Transactional
    public void createBackground() throws Exception {
        int databaseSizeBeforeCreate = backgroundRepository.findAll().size();

        // Create the Background
        restBackgroundMockMvc.perform(post("/api/backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(background)))
            .andExpect(status().isCreated());

        // Validate the Background in the database
        List<Background> backgroundList = backgroundRepository.findAll();
        assertThat(backgroundList).hasSize(databaseSizeBeforeCreate + 1);
        Background testBackground = backgroundList.get(backgroundList.size() - 1);
        assertThat(testBackground.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBackground.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the Background in Elasticsearch
        Background backgroundEs = backgroundSearchRepository.findOne(testBackground.getId());
        assertThat(backgroundEs).isEqualToComparingFieldByField(testBackground);
    }

    @Test
    @Transactional
    public void createBackgroundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backgroundRepository.findAll().size();

        // Create the Background with an existing ID
        background.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBackgroundMockMvc.perform(post("/api/backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(background)))
            .andExpect(status().isBadRequest());

        // Validate the Background in the database
        List<Background> backgroundList = backgroundRepository.findAll();
        assertThat(backgroundList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBackgrounds() throws Exception {
        // Initialize the database
        backgroundRepository.saveAndFlush(background);

        // Get all the backgroundList
        restBackgroundMockMvc.perform(get("/api/backgrounds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(background.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getBackground() throws Exception {
        // Initialize the database
        backgroundRepository.saveAndFlush(background);

        // Get the background
        restBackgroundMockMvc.perform(get("/api/backgrounds/{id}", background.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(background.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBackground() throws Exception {
        // Get the background
        restBackgroundMockMvc.perform(get("/api/backgrounds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBackground() throws Exception {
        // Initialize the database
        backgroundRepository.saveAndFlush(background);
        backgroundSearchRepository.save(background);
        int databaseSizeBeforeUpdate = backgroundRepository.findAll().size();

        // Update the background
        Background updatedBackground = backgroundRepository.findOne(background.getId());
        updatedBackground
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE);

        restBackgroundMockMvc.perform(put("/api/backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBackground)))
            .andExpect(status().isOk());

        // Validate the Background in the database
        List<Background> backgroundList = backgroundRepository.findAll();
        assertThat(backgroundList).hasSize(databaseSizeBeforeUpdate);
        Background testBackground = backgroundList.get(backgroundList.size() - 1);
        assertThat(testBackground.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBackground.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the Background in Elasticsearch
        Background backgroundEs = backgroundSearchRepository.findOne(testBackground.getId());
        assertThat(backgroundEs).isEqualToComparingFieldByField(testBackground);
    }

    @Test
    @Transactional
    public void updateNonExistingBackground() throws Exception {
        int databaseSizeBeforeUpdate = backgroundRepository.findAll().size();

        // Create the Background

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBackgroundMockMvc.perform(put("/api/backgrounds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(background)))
            .andExpect(status().isCreated());

        // Validate the Background in the database
        List<Background> backgroundList = backgroundRepository.findAll();
        assertThat(backgroundList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBackground() throws Exception {
        // Initialize the database
        backgroundRepository.saveAndFlush(background);
        backgroundSearchRepository.save(background);
        int databaseSizeBeforeDelete = backgroundRepository.findAll().size();

        // Get the background
        restBackgroundMockMvc.perform(delete("/api/backgrounds/{id}", background.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean backgroundExistsInEs = backgroundSearchRepository.exists(background.getId());
        assertThat(backgroundExistsInEs).isFalse();

        // Validate the database is empty
        List<Background> backgroundList = backgroundRepository.findAll();
        assertThat(backgroundList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBackground() throws Exception {
        // Initialize the database
        backgroundRepository.saveAndFlush(background);
        backgroundSearchRepository.save(background);

        // Search the background
        restBackgroundMockMvc.perform(get("/api/_search/backgrounds?query=id:" + background.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(background.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Background.class);
        Background background1 = new Background();
        background1.setId(1L);
        Background background2 = new Background();
        background2.setId(background1.getId());
        assertThat(background1).isEqualTo(background2);
        background2.setId(2L);
        assertThat(background1).isNotEqualTo(background2);
        background1.setId(null);
        assertThat(background1).isNotEqualTo(background2);
    }
}
