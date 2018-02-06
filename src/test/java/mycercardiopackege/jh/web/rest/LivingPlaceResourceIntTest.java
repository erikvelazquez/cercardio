package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.LivingPlace;
import mycercardiopackege.jh.repository.LivingPlaceRepository;
import mycercardiopackege.jh.repository.search.LivingPlaceSearchRepository;
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
 * Test class for the LivingPlaceResource REST controller.
 *
 * @see LivingPlaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class LivingPlaceResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    @Autowired
    private LivingPlaceRepository livingPlaceRepository;

    @Autowired
    private LivingPlaceSearchRepository livingPlaceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLivingPlaceMockMvc;

    private LivingPlace livingPlace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LivingPlaceResource livingPlaceResource = new LivingPlaceResource(livingPlaceRepository, livingPlaceSearchRepository);
        this.restLivingPlaceMockMvc = MockMvcBuilders.standaloneSetup(livingPlaceResource)
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
    public static LivingPlace createEntity(EntityManager em) {
        LivingPlace livingPlace = new LivingPlace()
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return livingPlace;
    }

    @Before
    public void initTest() {
        livingPlaceSearchRepository.deleteAll();
        livingPlace = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivingPlace() throws Exception {
        int databaseSizeBeforeCreate = livingPlaceRepository.findAll().size();

        // Create the LivingPlace
        restLivingPlaceMockMvc.perform(post("/api/living-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livingPlace)))
            .andExpect(status().isCreated());

        // Validate the LivingPlace in the database
        List<LivingPlace> livingPlaceList = livingPlaceRepository.findAll();
        assertThat(livingPlaceList).hasSize(databaseSizeBeforeCreate + 1);
        LivingPlace testLivingPlace = livingPlaceList.get(livingPlaceList.size() - 1);
        assertThat(testLivingPlace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLivingPlace.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the LivingPlace in Elasticsearch
        LivingPlace livingPlaceEs = livingPlaceSearchRepository.findOne(testLivingPlace.getId());
        assertThat(livingPlaceEs).isEqualToComparingFieldByField(testLivingPlace);
    }

    @Test
    @Transactional
    public void createLivingPlaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livingPlaceRepository.findAll().size();

        // Create the LivingPlace with an existing ID
        livingPlace.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivingPlaceMockMvc.perform(post("/api/living-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livingPlace)))
            .andExpect(status().isBadRequest());

        // Validate the LivingPlace in the database
        List<LivingPlace> livingPlaceList = livingPlaceRepository.findAll();
        assertThat(livingPlaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLivingPlaces() throws Exception {
        // Initialize the database
        livingPlaceRepository.saveAndFlush(livingPlace);

        // Get all the livingPlaceList
        restLivingPlaceMockMvc.perform(get("/api/living-places?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livingPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())));
    }

    @Test
    @Transactional
    public void getLivingPlace() throws Exception {
        // Initialize the database
        livingPlaceRepository.saveAndFlush(livingPlace);

        // Get the livingPlace
        restLivingPlaceMockMvc.perform(get("/api/living-places/{id}", livingPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(livingPlace.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLivingPlace() throws Exception {
        // Get the livingPlace
        restLivingPlaceMockMvc.perform(get("/api/living-places/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivingPlace() throws Exception {
        // Initialize the database
        livingPlaceRepository.saveAndFlush(livingPlace);
        livingPlaceSearchRepository.save(livingPlace);
        int databaseSizeBeforeUpdate = livingPlaceRepository.findAll().size();

        // Update the livingPlace
        LivingPlace updatedLivingPlace = livingPlaceRepository.findOne(livingPlace.getId());
        updatedLivingPlace
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);

        restLivingPlaceMockMvc.perform(put("/api/living-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivingPlace)))
            .andExpect(status().isOk());

        // Validate the LivingPlace in the database
        List<LivingPlace> livingPlaceList = livingPlaceRepository.findAll();
        assertThat(livingPlaceList).hasSize(databaseSizeBeforeUpdate);
        LivingPlace testLivingPlace = livingPlaceList.get(livingPlaceList.size() - 1);
        assertThat(testLivingPlace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLivingPlace.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the LivingPlace in Elasticsearch
        LivingPlace livingPlaceEs = livingPlaceSearchRepository.findOne(testLivingPlace.getId());
        assertThat(livingPlaceEs).isEqualToComparingFieldByField(testLivingPlace);
    }

    @Test
    @Transactional
    public void updateNonExistingLivingPlace() throws Exception {
        int databaseSizeBeforeUpdate = livingPlaceRepository.findAll().size();

        // Create the LivingPlace

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLivingPlaceMockMvc.perform(put("/api/living-places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(livingPlace)))
            .andExpect(status().isCreated());

        // Validate the LivingPlace in the database
        List<LivingPlace> livingPlaceList = livingPlaceRepository.findAll();
        assertThat(livingPlaceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLivingPlace() throws Exception {
        // Initialize the database
        livingPlaceRepository.saveAndFlush(livingPlace);
        livingPlaceSearchRepository.save(livingPlace);
        int databaseSizeBeforeDelete = livingPlaceRepository.findAll().size();

        // Get the livingPlace
        restLivingPlaceMockMvc.perform(delete("/api/living-places/{id}", livingPlace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean livingPlaceExistsInEs = livingPlaceSearchRepository.exists(livingPlace.getId());
        assertThat(livingPlaceExistsInEs).isFalse();

        // Validate the database is empty
        List<LivingPlace> livingPlaceList = livingPlaceRepository.findAll();
        assertThat(livingPlaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLivingPlace() throws Exception {
        // Initialize the database
        livingPlaceRepository.saveAndFlush(livingPlace);
        livingPlaceSearchRepository.save(livingPlace);

        // Search the livingPlace
        restLivingPlaceMockMvc.perform(get("/api/_search/living-places?query=id:" + livingPlace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livingPlace.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivingPlace.class);
        LivingPlace livingPlace1 = new LivingPlace();
        livingPlace1.setId(1L);
        LivingPlace livingPlace2 = new LivingPlace();
        livingPlace2.setId(livingPlace1.getId());
        assertThat(livingPlace1).isEqualTo(livingPlace2);
        livingPlace2.setId(2L);
        assertThat(livingPlace1).isNotEqualTo(livingPlace2);
        livingPlace1.setId(null);
        assertThat(livingPlace1).isNotEqualTo(livingPlace2);
    }
}
