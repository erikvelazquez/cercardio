package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.AcademicDegree;
import mycercardiopackege.jh.repository.AcademicDegreeRepository;
import mycercardiopackege.jh.repository.search.AcademicDegreeSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static mycercardiopackege.jh.web.rest.TestUtil.sameInstant;
import static mycercardiopackege.jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AcademicDegreeResource REST controller.
 *
 * @see AcademicDegreeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class AcademicDegreeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AcademicDegreeRepository academicDegreeRepository;

    @Autowired
    private AcademicDegreeSearchRepository academicDegreeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAcademicDegreeMockMvc;

    private AcademicDegree academicDegree;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcademicDegreeResource academicDegreeResource = new AcademicDegreeResource(academicDegreeRepository, academicDegreeSearchRepository);
        this.restAcademicDegreeMockMvc = MockMvcBuilders.standaloneSetup(academicDegreeResource)
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
    public static AcademicDegree createEntity(EntityManager em) {
        AcademicDegree academicDegree = new AcademicDegree()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE)
            .createdat(DEFAULT_CREATEDAT);
        return academicDegree;
    }

    @Before
    public void initTest() {
        academicDegreeSearchRepository.deleteAll();
        academicDegree = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicDegree() throws Exception {
        int databaseSizeBeforeCreate = academicDegreeRepository.findAll().size();

        // Create the AcademicDegree
        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicDegree)))
            .andExpect(status().isCreated());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicDegree testAcademicDegree = academicDegreeList.get(academicDegreeList.size() - 1);
        assertThat(testAcademicDegree.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAcademicDegree.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);
        assertThat(testAcademicDegree.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the AcademicDegree in Elasticsearch
        AcademicDegree academicDegreeEs = academicDegreeSearchRepository.findOne(testAcademicDegree.getId());
        assertThat(academicDegreeEs).isEqualToComparingFieldByField(testAcademicDegree);
    }

    @Test
    @Transactional
    public void createAcademicDegreeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicDegreeRepository.findAll().size();

        // Create the AcademicDegree with an existing ID
        academicDegree.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicDegreeMockMvc.perform(post("/api/academic-degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicDegree)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAcademicDegrees() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);

        // Get all the academicDegreeList
        restAcademicDegreeMockMvc.perform(get("/api/academic-degrees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicDegree.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void getAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);

        // Get the academicDegree
        restAcademicDegreeMockMvc.perform(get("/api/academic-degrees/{id}", academicDegree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academicDegree.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingAcademicDegree() throws Exception {
        // Get the academicDegree
        restAcademicDegreeMockMvc.perform(get("/api/academic-degrees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);
        academicDegreeSearchRepository.save(academicDegree);
        int databaseSizeBeforeUpdate = academicDegreeRepository.findAll().size();

        // Update the academicDegree
        AcademicDegree updatedAcademicDegree = academicDegreeRepository.findOne(academicDegree.getId());
        updatedAcademicDegree
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE)
            .createdat(UPDATED_CREATEDAT);

        restAcademicDegreeMockMvc.perform(put("/api/academic-degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcademicDegree)))
            .andExpect(status().isOk());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeUpdate);
        AcademicDegree testAcademicDegree = academicDegreeList.get(academicDegreeList.size() - 1);
        assertThat(testAcademicDegree.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAcademicDegree.isIsactive()).isEqualTo(UPDATED_ISACTIVE);
        assertThat(testAcademicDegree.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the AcademicDegree in Elasticsearch
        AcademicDegree academicDegreeEs = academicDegreeSearchRepository.findOne(testAcademicDegree.getId());
        assertThat(academicDegreeEs).isEqualToComparingFieldByField(testAcademicDegree);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicDegree() throws Exception {
        int databaseSizeBeforeUpdate = academicDegreeRepository.findAll().size();

        // Create the AcademicDegree

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcademicDegreeMockMvc.perform(put("/api/academic-degrees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicDegree)))
            .andExpect(status().isCreated());

        // Validate the AcademicDegree in the database
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);
        academicDegreeSearchRepository.save(academicDegree);
        int databaseSizeBeforeDelete = academicDegreeRepository.findAll().size();

        // Get the academicDegree
        restAcademicDegreeMockMvc.perform(delete("/api/academic-degrees/{id}", academicDegree.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean academicDegreeExistsInEs = academicDegreeSearchRepository.exists(academicDegree.getId());
        assertThat(academicDegreeExistsInEs).isFalse();

        // Validate the database is empty
        List<AcademicDegree> academicDegreeList = academicDegreeRepository.findAll();
        assertThat(academicDegreeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAcademicDegree() throws Exception {
        // Initialize the database
        academicDegreeRepository.saveAndFlush(academicDegree);
        academicDegreeSearchRepository.save(academicDegree);

        // Search the academicDegree
        restAcademicDegreeMockMvc.perform(get("/api/_search/academic-degrees?query=id:" + academicDegree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicDegree.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicDegree.class);
        AcademicDegree academicDegree1 = new AcademicDegree();
        academicDegree1.setId(1L);
        AcademicDegree academicDegree2 = new AcademicDegree();
        academicDegree2.setId(academicDegree1.getId());
        assertThat(academicDegree1).isEqualTo(academicDegree2);
        academicDegree2.setId(2L);
        assertThat(academicDegree1).isNotEqualTo(academicDegree2);
        academicDegree1.setId(null);
        assertThat(academicDegree1).isNotEqualTo(academicDegree2);
    }
}
