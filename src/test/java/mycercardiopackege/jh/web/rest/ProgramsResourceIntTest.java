package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Programs;
import mycercardiopackege.jh.repository.ProgramsRepository;
import mycercardiopackege.jh.repository.search.ProgramsSearchRepository;
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
 * Test class for the ProgramsResource REST controller.
 *
 * @see ProgramsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class ProgramsResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROGRAMPARENTID = 1;
    private static final Integer UPDATED_PROGRAMPARENTID = 2;

    private static final Integer DEFAULT_BYORDER = 1;
    private static final Integer UPDATED_BYORDER = 2;

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private ProgramsRepository programsRepository;

    @Autowired
    private ProgramsSearchRepository programsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProgramsMockMvc;

    private Programs programs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgramsResource programsResource = new ProgramsResource(programsRepository, programsSearchRepository);
        this.restProgramsMockMvc = MockMvcBuilders.standaloneSetup(programsResource)
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
    public static Programs createEntity(EntityManager em) {
        Programs programs = new Programs()
            .description(DEFAULT_DESCRIPTION)
            .url(DEFAULT_URL)
            .icon(DEFAULT_ICON)
            .programparentid(DEFAULT_PROGRAMPARENTID)
            .byorder(DEFAULT_BYORDER)
            .isactive(DEFAULT_ISACTIVE);
        return programs;
    }

    @Before
    public void initTest() {
        programsSearchRepository.deleteAll();
        programs = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrograms() throws Exception {
        int databaseSizeBeforeCreate = programsRepository.findAll().size();

        // Create the Programs
        restProgramsMockMvc.perform(post("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programs)))
            .andExpect(status().isCreated());

        // Validate the Programs in the database
        List<Programs> programsList = programsRepository.findAll();
        assertThat(programsList).hasSize(databaseSizeBeforeCreate + 1);
        Programs testPrograms = programsList.get(programsList.size() - 1);
        assertThat(testPrograms.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPrograms.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testPrograms.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testPrograms.getProgramparentid()).isEqualTo(DEFAULT_PROGRAMPARENTID);
        assertThat(testPrograms.getByorder()).isEqualTo(DEFAULT_BYORDER);
        assertThat(testPrograms.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the Programs in Elasticsearch
        Programs programsEs = programsSearchRepository.findOne(testPrograms.getId());
        assertThat(programsEs).isEqualToComparingFieldByField(testPrograms);
    }

    @Test
    @Transactional
    public void createProgramsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programsRepository.findAll().size();

        // Create the Programs with an existing ID
        programs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramsMockMvc.perform(post("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programs)))
            .andExpect(status().isBadRequest());

        // Validate the Programs in the database
        List<Programs> programsList = programsRepository.findAll();
        assertThat(programsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrograms() throws Exception {
        // Initialize the database
        programsRepository.saveAndFlush(programs);

        // Get all the programsList
        restProgramsMockMvc.perform(get("/api/programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programs.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].programparentid").value(hasItem(DEFAULT_PROGRAMPARENTID)))
            .andExpect(jsonPath("$.[*].byorder").value(hasItem(DEFAULT_BYORDER)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrograms() throws Exception {
        // Initialize the database
        programsRepository.saveAndFlush(programs);

        // Get the programs
        restProgramsMockMvc.perform(get("/api/programs/{id}", programs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programs.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.programparentid").value(DEFAULT_PROGRAMPARENTID))
            .andExpect(jsonPath("$.byorder").value(DEFAULT_BYORDER))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrograms() throws Exception {
        // Get the programs
        restProgramsMockMvc.perform(get("/api/programs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrograms() throws Exception {
        // Initialize the database
        programsRepository.saveAndFlush(programs);
        programsSearchRepository.save(programs);
        int databaseSizeBeforeUpdate = programsRepository.findAll().size();

        // Update the programs
        Programs updatedPrograms = programsRepository.findOne(programs.getId());
        updatedPrograms
            .description(UPDATED_DESCRIPTION)
            .url(UPDATED_URL)
            .icon(UPDATED_ICON)
            .programparentid(UPDATED_PROGRAMPARENTID)
            .byorder(UPDATED_BYORDER)
            .isactive(UPDATED_ISACTIVE);

        restProgramsMockMvc.perform(put("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrograms)))
            .andExpect(status().isOk());

        // Validate the Programs in the database
        List<Programs> programsList = programsRepository.findAll();
        assertThat(programsList).hasSize(databaseSizeBeforeUpdate);
        Programs testPrograms = programsList.get(programsList.size() - 1);
        assertThat(testPrograms.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPrograms.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testPrograms.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testPrograms.getProgramparentid()).isEqualTo(UPDATED_PROGRAMPARENTID);
        assertThat(testPrograms.getByorder()).isEqualTo(UPDATED_BYORDER);
        assertThat(testPrograms.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the Programs in Elasticsearch
        Programs programsEs = programsSearchRepository.findOne(testPrograms.getId());
        assertThat(programsEs).isEqualToComparingFieldByField(testPrograms);
    }

    @Test
    @Transactional
    public void updateNonExistingPrograms() throws Exception {
        int databaseSizeBeforeUpdate = programsRepository.findAll().size();

        // Create the Programs

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProgramsMockMvc.perform(put("/api/programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programs)))
            .andExpect(status().isCreated());

        // Validate the Programs in the database
        List<Programs> programsList = programsRepository.findAll();
        assertThat(programsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrograms() throws Exception {
        // Initialize the database
        programsRepository.saveAndFlush(programs);
        programsSearchRepository.save(programs);
        int databaseSizeBeforeDelete = programsRepository.findAll().size();

        // Get the programs
        restProgramsMockMvc.perform(delete("/api/programs/{id}", programs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean programsExistsInEs = programsSearchRepository.exists(programs.getId());
        assertThat(programsExistsInEs).isFalse();

        // Validate the database is empty
        List<Programs> programsList = programsRepository.findAll();
        assertThat(programsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPrograms() throws Exception {
        // Initialize the database
        programsRepository.saveAndFlush(programs);
        programsSearchRepository.save(programs);

        // Search the programs
        restProgramsMockMvc.perform(get("/api/_search/programs?query=id:" + programs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programs.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].programparentid").value(hasItem(DEFAULT_PROGRAMPARENTID)))
            .andExpect(jsonPath("$.[*].byorder").value(hasItem(DEFAULT_BYORDER)))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programs.class);
        Programs programs1 = new Programs();
        programs1.setId(1L);
        Programs programs2 = new Programs();
        programs2.setId(programs1.getId());
        assertThat(programs1).isEqualTo(programs2);
        programs2.setId(2L);
        assertThat(programs1).isNotEqualTo(programs2);
        programs1.setId(null);
        assertThat(programs1).isNotEqualTo(programs2);
    }
}
