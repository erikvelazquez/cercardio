package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Indigenous_Languages;
import mycercardiopackege.jh.repository.Indigenous_LanguagesRepository;
import mycercardiopackege.jh.repository.search.Indigenous_LanguagesSearchRepository;
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
 * Test class for the Indigenous_LanguagesResource REST controller.
 *
 * @see Indigenous_LanguagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class Indigenous_LanguagesResourceIntTest {

    private static final String DEFAULT_CATALOGKEY = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGKEY = "BBBBBBBBBB";

    private static final String DEFAULT_INDIGENOUSLANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_INDIGENOUSLANGUAGE = "BBBBBBBBBB";

    @Autowired
    private Indigenous_LanguagesRepository indigenous_LanguagesRepository;

    @Autowired
    private Indigenous_LanguagesSearchRepository indigenous_LanguagesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndigenous_LanguagesMockMvc;

    private Indigenous_Languages indigenous_Languages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Indigenous_LanguagesResource indigenous_LanguagesResource = new Indigenous_LanguagesResource(indigenous_LanguagesRepository, indigenous_LanguagesSearchRepository);
        this.restIndigenous_LanguagesMockMvc = MockMvcBuilders.standaloneSetup(indigenous_LanguagesResource)
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
    public static Indigenous_Languages createEntity(EntityManager em) {
        Indigenous_Languages indigenous_Languages = new Indigenous_Languages()
            .catalogkey(DEFAULT_CATALOGKEY)
            .indigenouslanguage(DEFAULT_INDIGENOUSLANGUAGE);
        return indigenous_Languages;
    }

    @Before
    public void initTest() {
        indigenous_LanguagesSearchRepository.deleteAll();
        indigenous_Languages = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndigenous_Languages() throws Exception {
        int databaseSizeBeforeCreate = indigenous_LanguagesRepository.findAll().size();

        // Create the Indigenous_Languages
        restIndigenous_LanguagesMockMvc.perform(post("/api/indigenous-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indigenous_Languages)))
            .andExpect(status().isCreated());

        // Validate the Indigenous_Languages in the database
        List<Indigenous_Languages> indigenous_LanguagesList = indigenous_LanguagesRepository.findAll();
        assertThat(indigenous_LanguagesList).hasSize(databaseSizeBeforeCreate + 1);
        Indigenous_Languages testIndigenous_Languages = indigenous_LanguagesList.get(indigenous_LanguagesList.size() - 1);
        assertThat(testIndigenous_Languages.getCatalogkey()).isEqualTo(DEFAULT_CATALOGKEY);
        assertThat(testIndigenous_Languages.getIndigenouslanguage()).isEqualTo(DEFAULT_INDIGENOUSLANGUAGE);

        // Validate the Indigenous_Languages in Elasticsearch
        Indigenous_Languages indigenous_LanguagesEs = indigenous_LanguagesSearchRepository.findOne(testIndigenous_Languages.getId());
        assertThat(indigenous_LanguagesEs).isEqualToComparingFieldByField(testIndigenous_Languages);
    }

    @Test
    @Transactional
    public void createIndigenous_LanguagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indigenous_LanguagesRepository.findAll().size();

        // Create the Indigenous_Languages with an existing ID
        indigenous_Languages.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndigenous_LanguagesMockMvc.perform(post("/api/indigenous-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indigenous_Languages)))
            .andExpect(status().isBadRequest());

        // Validate the Indigenous_Languages in the database
        List<Indigenous_Languages> indigenous_LanguagesList = indigenous_LanguagesRepository.findAll();
        assertThat(indigenous_LanguagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIndigenous_Languages() throws Exception {
        // Initialize the database
        indigenous_LanguagesRepository.saveAndFlush(indigenous_Languages);

        // Get all the indigenous_LanguagesList
        restIndigenous_LanguagesMockMvc.perform(get("/api/indigenous-languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indigenous_Languages.getId().intValue())))
            .andExpect(jsonPath("$.[*].catalogkey").value(hasItem(DEFAULT_CATALOGKEY.toString())))
            .andExpect(jsonPath("$.[*].indigenouslanguage").value(hasItem(DEFAULT_INDIGENOUSLANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getIndigenous_Languages() throws Exception {
        // Initialize the database
        indigenous_LanguagesRepository.saveAndFlush(indigenous_Languages);

        // Get the indigenous_Languages
        restIndigenous_LanguagesMockMvc.perform(get("/api/indigenous-languages/{id}", indigenous_Languages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(indigenous_Languages.getId().intValue()))
            .andExpect(jsonPath("$.catalogkey").value(DEFAULT_CATALOGKEY.toString()))
            .andExpect(jsonPath("$.indigenouslanguage").value(DEFAULT_INDIGENOUSLANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndigenous_Languages() throws Exception {
        // Get the indigenous_Languages
        restIndigenous_LanguagesMockMvc.perform(get("/api/indigenous-languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndigenous_Languages() throws Exception {
        // Initialize the database
        indigenous_LanguagesRepository.saveAndFlush(indigenous_Languages);
        indigenous_LanguagesSearchRepository.save(indigenous_Languages);
        int databaseSizeBeforeUpdate = indigenous_LanguagesRepository.findAll().size();

        // Update the indigenous_Languages
        Indigenous_Languages updatedIndigenous_Languages = indigenous_LanguagesRepository.findOne(indigenous_Languages.getId());
        updatedIndigenous_Languages
            .catalogkey(UPDATED_CATALOGKEY)
            .indigenouslanguage(UPDATED_INDIGENOUSLANGUAGE);

        restIndigenous_LanguagesMockMvc.perform(put("/api/indigenous-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndigenous_Languages)))
            .andExpect(status().isOk());

        // Validate the Indigenous_Languages in the database
        List<Indigenous_Languages> indigenous_LanguagesList = indigenous_LanguagesRepository.findAll();
        assertThat(indigenous_LanguagesList).hasSize(databaseSizeBeforeUpdate);
        Indigenous_Languages testIndigenous_Languages = indigenous_LanguagesList.get(indigenous_LanguagesList.size() - 1);
        assertThat(testIndigenous_Languages.getCatalogkey()).isEqualTo(UPDATED_CATALOGKEY);
        assertThat(testIndigenous_Languages.getIndigenouslanguage()).isEqualTo(UPDATED_INDIGENOUSLANGUAGE);

        // Validate the Indigenous_Languages in Elasticsearch
        Indigenous_Languages indigenous_LanguagesEs = indigenous_LanguagesSearchRepository.findOne(testIndigenous_Languages.getId());
        assertThat(indigenous_LanguagesEs).isEqualToComparingFieldByField(testIndigenous_Languages);
    }

    @Test
    @Transactional
    public void updateNonExistingIndigenous_Languages() throws Exception {
        int databaseSizeBeforeUpdate = indigenous_LanguagesRepository.findAll().size();

        // Create the Indigenous_Languages

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndigenous_LanguagesMockMvc.perform(put("/api/indigenous-languages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indigenous_Languages)))
            .andExpect(status().isCreated());

        // Validate the Indigenous_Languages in the database
        List<Indigenous_Languages> indigenous_LanguagesList = indigenous_LanguagesRepository.findAll();
        assertThat(indigenous_LanguagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndigenous_Languages() throws Exception {
        // Initialize the database
        indigenous_LanguagesRepository.saveAndFlush(indigenous_Languages);
        indigenous_LanguagesSearchRepository.save(indigenous_Languages);
        int databaseSizeBeforeDelete = indigenous_LanguagesRepository.findAll().size();

        // Get the indigenous_Languages
        restIndigenous_LanguagesMockMvc.perform(delete("/api/indigenous-languages/{id}", indigenous_Languages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean indigenous_LanguagesExistsInEs = indigenous_LanguagesSearchRepository.exists(indigenous_Languages.getId());
        assertThat(indigenous_LanguagesExistsInEs).isFalse();

        // Validate the database is empty
        List<Indigenous_Languages> indigenous_LanguagesList = indigenous_LanguagesRepository.findAll();
        assertThat(indigenous_LanguagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndigenous_Languages() throws Exception {
        // Initialize the database
        indigenous_LanguagesRepository.saveAndFlush(indigenous_Languages);
        indigenous_LanguagesSearchRepository.save(indigenous_Languages);

        // Search the indigenous_Languages
        restIndigenous_LanguagesMockMvc.perform(get("/api/_search/indigenous-languages?query=id:" + indigenous_Languages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indigenous_Languages.getId().intValue())))
            .andExpect(jsonPath("$.[*].catalogkey").value(hasItem(DEFAULT_CATALOGKEY.toString())))
            .andExpect(jsonPath("$.[*].indigenouslanguage").value(hasItem(DEFAULT_INDIGENOUSLANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indigenous_Languages.class);
        Indigenous_Languages indigenous_Languages1 = new Indigenous_Languages();
        indigenous_Languages1.setId(1L);
        Indigenous_Languages indigenous_Languages2 = new Indigenous_Languages();
        indigenous_Languages2.setId(indigenous_Languages1.getId());
        assertThat(indigenous_Languages1).isEqualTo(indigenous_Languages2);
        indigenous_Languages2.setId(2L);
        assertThat(indigenous_Languages1).isNotEqualTo(indigenous_Languages2);
        indigenous_Languages1.setId(null);
        assertThat(indigenous_Languages1).isNotEqualTo(indigenous_Languages2);
    }
}
