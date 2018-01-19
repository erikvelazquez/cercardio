package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Type_Program;
import mycercardiopackege.jh.repository.Type_ProgramRepository;
import mycercardiopackege.jh.repository.search.Type_ProgramSearchRepository;
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
 * Test class for the Type_ProgramResource REST controller.
 *
 * @see Type_ProgramResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class Type_ProgramResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private Type_ProgramRepository type_ProgramRepository;

    @Autowired
    private Type_ProgramSearchRepository type_ProgramSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restType_ProgramMockMvc;

    private Type_Program type_Program;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Type_ProgramResource type_ProgramResource = new Type_ProgramResource(type_ProgramRepository, type_ProgramSearchRepository);
        this.restType_ProgramMockMvc = MockMvcBuilders.standaloneSetup(type_ProgramResource)
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
    public static Type_Program createEntity(EntityManager em) {
        Type_Program type_Program = new Type_Program()
            .name(DEFAULT_NAME)
            .isactive(DEFAULT_ISACTIVE);
        return type_Program;
    }

    @Before
    public void initTest() {
        type_ProgramSearchRepository.deleteAll();
        type_Program = createEntity(em);
    }

    @Test
    @Transactional
    public void createType_Program() throws Exception {
        int databaseSizeBeforeCreate = type_ProgramRepository.findAll().size();

        // Create the Type_Program
        restType_ProgramMockMvc.perform(post("/api/type-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(type_Program)))
            .andExpect(status().isCreated());

        // Validate the Type_Program in the database
        List<Type_Program> type_ProgramList = type_ProgramRepository.findAll();
        assertThat(type_ProgramList).hasSize(databaseSizeBeforeCreate + 1);
        Type_Program testType_Program = type_ProgramList.get(type_ProgramList.size() - 1);
        assertThat(testType_Program.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testType_Program.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the Type_Program in Elasticsearch
        Type_Program type_ProgramEs = type_ProgramSearchRepository.findOne(testType_Program.getId());
        assertThat(type_ProgramEs).isEqualToComparingFieldByField(testType_Program);
    }

    @Test
    @Transactional
    public void createType_ProgramWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = type_ProgramRepository.findAll().size();

        // Create the Type_Program with an existing ID
        type_Program.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restType_ProgramMockMvc.perform(post("/api/type-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(type_Program)))
            .andExpect(status().isBadRequest());

        // Validate the Type_Program in the database
        List<Type_Program> type_ProgramList = type_ProgramRepository.findAll();
        assertThat(type_ProgramList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllType_Programs() throws Exception {
        // Initialize the database
        type_ProgramRepository.saveAndFlush(type_Program);

        // Get all the type_ProgramList
        restType_ProgramMockMvc.perform(get("/api/type-programs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(type_Program.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getType_Program() throws Exception {
        // Initialize the database
        type_ProgramRepository.saveAndFlush(type_Program);

        // Get the type_Program
        restType_ProgramMockMvc.perform(get("/api/type-programs/{id}", type_Program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(type_Program.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingType_Program() throws Exception {
        // Get the type_Program
        restType_ProgramMockMvc.perform(get("/api/type-programs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_Program() throws Exception {
        // Initialize the database
        type_ProgramRepository.saveAndFlush(type_Program);
        type_ProgramSearchRepository.save(type_Program);
        int databaseSizeBeforeUpdate = type_ProgramRepository.findAll().size();

        // Update the type_Program
        Type_Program updatedType_Program = type_ProgramRepository.findOne(type_Program.getId());
        updatedType_Program
            .name(UPDATED_NAME)
            .isactive(UPDATED_ISACTIVE);

        restType_ProgramMockMvc.perform(put("/api/type-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedType_Program)))
            .andExpect(status().isOk());

        // Validate the Type_Program in the database
        List<Type_Program> type_ProgramList = type_ProgramRepository.findAll();
        assertThat(type_ProgramList).hasSize(databaseSizeBeforeUpdate);
        Type_Program testType_Program = type_ProgramList.get(type_ProgramList.size() - 1);
        assertThat(testType_Program.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testType_Program.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the Type_Program in Elasticsearch
        Type_Program type_ProgramEs = type_ProgramSearchRepository.findOne(testType_Program.getId());
        assertThat(type_ProgramEs).isEqualToComparingFieldByField(testType_Program);
    }

    @Test
    @Transactional
    public void updateNonExistingType_Program() throws Exception {
        int databaseSizeBeforeUpdate = type_ProgramRepository.findAll().size();

        // Create the Type_Program

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restType_ProgramMockMvc.perform(put("/api/type-programs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(type_Program)))
            .andExpect(status().isCreated());

        // Validate the Type_Program in the database
        List<Type_Program> type_ProgramList = type_ProgramRepository.findAll();
        assertThat(type_ProgramList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteType_Program() throws Exception {
        // Initialize the database
        type_ProgramRepository.saveAndFlush(type_Program);
        type_ProgramSearchRepository.save(type_Program);
        int databaseSizeBeforeDelete = type_ProgramRepository.findAll().size();

        // Get the type_Program
        restType_ProgramMockMvc.perform(delete("/api/type-programs/{id}", type_Program.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean type_ProgramExistsInEs = type_ProgramSearchRepository.exists(type_Program.getId());
        assertThat(type_ProgramExistsInEs).isFalse();

        // Validate the database is empty
        List<Type_Program> type_ProgramList = type_ProgramRepository.findAll();
        assertThat(type_ProgramList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchType_Program() throws Exception {
        // Initialize the database
        type_ProgramRepository.saveAndFlush(type_Program);
        type_ProgramSearchRepository.save(type_Program);

        // Search the type_Program
        restType_ProgramMockMvc.perform(get("/api/_search/type-programs?query=id:" + type_Program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(type_Program.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Type_Program.class);
        Type_Program type_Program1 = new Type_Program();
        type_Program1.setId(1L);
        Type_Program type_Program2 = new Type_Program();
        type_Program2.setId(type_Program1.getId());
        assertThat(type_Program1).isEqualTo(type_Program2);
        type_Program2.setId(2L);
        assertThat(type_Program1).isNotEqualTo(type_Program2);
        type_Program1.setId(null);
        assertThat(type_Program1).isNotEqualTo(type_Program2);
    }
}
