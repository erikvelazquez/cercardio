package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Timers;
import mycercardiopackege.jh.repository.TimersRepository;
import mycercardiopackege.jh.repository.search.TimersSearchRepository;
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
 * Test class for the TimersResource REST controller.
 *
 * @see TimersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class TimersResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private TimersRepository timersRepository;

    @Autowired
    private TimersSearchRepository timersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimersMockMvc;

    private Timers timers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimersResource timersResource = new TimersResource(timersRepository, timersSearchRepository);
        this.restTimersMockMvc = MockMvcBuilders.standaloneSetup(timersResource)
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
    public static Timers createEntity(EntityManager em) {
        Timers timers = new Timers()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE);
        return timers;
    }

    @Before
    public void initTest() {
        timersSearchRepository.deleteAll();
        timers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimers() throws Exception {
        int databaseSizeBeforeCreate = timersRepository.findAll().size();

        // Create the Timers
        restTimersMockMvc.perform(post("/api/timers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timers)))
            .andExpect(status().isCreated());

        // Validate the Timers in the database
        List<Timers> timersList = timersRepository.findAll();
        assertThat(timersList).hasSize(databaseSizeBeforeCreate + 1);
        Timers testTimers = timersList.get(timersList.size() - 1);
        assertThat(testTimers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTimers.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the Timers in Elasticsearch
        Timers timersEs = timersSearchRepository.findOne(testTimers.getId());
        assertThat(timersEs).isEqualToComparingFieldByField(testTimers);
    }

    @Test
    @Transactional
    public void createTimersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timersRepository.findAll().size();

        // Create the Timers with an existing ID
        timers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimersMockMvc.perform(post("/api/timers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timers)))
            .andExpect(status().isBadRequest());

        // Validate the Timers in the database
        List<Timers> timersList = timersRepository.findAll();
        assertThat(timersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimers() throws Exception {
        // Initialize the database
        timersRepository.saveAndFlush(timers);

        // Get all the timersList
        restTimersMockMvc.perform(get("/api/timers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timers.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getTimers() throws Exception {
        // Initialize the database
        timersRepository.saveAndFlush(timers);

        // Get the timers
        restTimersMockMvc.perform(get("/api/timers/{id}", timers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timers.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimers() throws Exception {
        // Get the timers
        restTimersMockMvc.perform(get("/api/timers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimers() throws Exception {
        // Initialize the database
        timersRepository.saveAndFlush(timers);
        timersSearchRepository.save(timers);
        int databaseSizeBeforeUpdate = timersRepository.findAll().size();

        // Update the timers
        Timers updatedTimers = timersRepository.findOne(timers.getId());
        updatedTimers
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE);

        restTimersMockMvc.perform(put("/api/timers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimers)))
            .andExpect(status().isOk());

        // Validate the Timers in the database
        List<Timers> timersList = timersRepository.findAll();
        assertThat(timersList).hasSize(databaseSizeBeforeUpdate);
        Timers testTimers = timersList.get(timersList.size() - 1);
        assertThat(testTimers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTimers.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the Timers in Elasticsearch
        Timers timersEs = timersSearchRepository.findOne(testTimers.getId());
        assertThat(timersEs).isEqualToComparingFieldByField(testTimers);
    }

    @Test
    @Transactional
    public void updateNonExistingTimers() throws Exception {
        int databaseSizeBeforeUpdate = timersRepository.findAll().size();

        // Create the Timers

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimersMockMvc.perform(put("/api/timers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timers)))
            .andExpect(status().isCreated());

        // Validate the Timers in the database
        List<Timers> timersList = timersRepository.findAll();
        assertThat(timersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimers() throws Exception {
        // Initialize the database
        timersRepository.saveAndFlush(timers);
        timersSearchRepository.save(timers);
        int databaseSizeBeforeDelete = timersRepository.findAll().size();

        // Get the timers
        restTimersMockMvc.perform(delete("/api/timers/{id}", timers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean timersExistsInEs = timersSearchRepository.exists(timers.getId());
        assertThat(timersExistsInEs).isFalse();

        // Validate the database is empty
        List<Timers> timersList = timersRepository.findAll();
        assertThat(timersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTimers() throws Exception {
        // Initialize the database
        timersRepository.saveAndFlush(timers);
        timersSearchRepository.save(timers);

        // Search the timers
        restTimersMockMvc.perform(get("/api/_search/timers?query=id:" + timers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timers.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timers.class);
        Timers timers1 = new Timers();
        timers1.setId(1L);
        Timers timers2 = new Timers();
        timers2.setId(timers1.getId());
        assertThat(timers1).isEqualTo(timers2);
        timers2.setId(2L);
        assertThat(timers1).isNotEqualTo(timers2);
        timers1.setId(null);
        assertThat(timers1).isNotEqualTo(timers2);
    }
}
