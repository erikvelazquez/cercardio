package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.BloodGroup;
import mycercardiopackege.jh.repository.BloodGroupRepository;
import mycercardiopackege.jh.repository.search.BloodGroupSearchRepository;
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
 * Test class for the BloodGroupResource REST controller.
 *
 * @see BloodGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class BloodGroupResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private BloodGroupRepository bloodGroupRepository;

    @Autowired
    private BloodGroupSearchRepository bloodGroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBloodGroupMockMvc;

    private BloodGroup bloodGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BloodGroupResource bloodGroupResource = new BloodGroupResource(bloodGroupRepository, bloodGroupSearchRepository);
        this.restBloodGroupMockMvc = MockMvcBuilders.standaloneSetup(bloodGroupResource)
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
    public static BloodGroup createEntity(EntityManager em) {
        BloodGroup bloodGroup = new BloodGroup()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE);
        return bloodGroup;
    }

    @Before
    public void initTest() {
        bloodGroupSearchRepository.deleteAll();
        bloodGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createBloodGroup() throws Exception {
        int databaseSizeBeforeCreate = bloodGroupRepository.findAll().size();

        // Create the BloodGroup
        restBloodGroupMockMvc.perform(post("/api/blood-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodGroup)))
            .andExpect(status().isCreated());

        // Validate the BloodGroup in the database
        List<BloodGroup> bloodGroupList = bloodGroupRepository.findAll();
        assertThat(bloodGroupList).hasSize(databaseSizeBeforeCreate + 1);
        BloodGroup testBloodGroup = bloodGroupList.get(bloodGroupList.size() - 1);
        assertThat(testBloodGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBloodGroup.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the BloodGroup in Elasticsearch
        BloodGroup bloodGroupEs = bloodGroupSearchRepository.findOne(testBloodGroup.getId());
        assertThat(bloodGroupEs).isEqualToComparingFieldByField(testBloodGroup);
    }

    @Test
    @Transactional
    public void createBloodGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bloodGroupRepository.findAll().size();

        // Create the BloodGroup with an existing ID
        bloodGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodGroupMockMvc.perform(post("/api/blood-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodGroup)))
            .andExpect(status().isBadRequest());

        // Validate the BloodGroup in the database
        List<BloodGroup> bloodGroupList = bloodGroupRepository.findAll();
        assertThat(bloodGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBloodGroups() throws Exception {
        // Initialize the database
        bloodGroupRepository.saveAndFlush(bloodGroup);

        // Get all the bloodGroupList
        restBloodGroupMockMvc.perform(get("/api/blood-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getBloodGroup() throws Exception {
        // Initialize the database
        bloodGroupRepository.saveAndFlush(bloodGroup);

        // Get the bloodGroup
        restBloodGroupMockMvc.perform(get("/api/blood-groups/{id}", bloodGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bloodGroup.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBloodGroup() throws Exception {
        // Get the bloodGroup
        restBloodGroupMockMvc.perform(get("/api/blood-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBloodGroup() throws Exception {
        // Initialize the database
        bloodGroupRepository.saveAndFlush(bloodGroup);
        bloodGroupSearchRepository.save(bloodGroup);
        int databaseSizeBeforeUpdate = bloodGroupRepository.findAll().size();

        // Update the bloodGroup
        BloodGroup updatedBloodGroup = bloodGroupRepository.findOne(bloodGroup.getId());
        updatedBloodGroup
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE);

        restBloodGroupMockMvc.perform(put("/api/blood-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBloodGroup)))
            .andExpect(status().isOk());

        // Validate the BloodGroup in the database
        List<BloodGroup> bloodGroupList = bloodGroupRepository.findAll();
        assertThat(bloodGroupList).hasSize(databaseSizeBeforeUpdate);
        BloodGroup testBloodGroup = bloodGroupList.get(bloodGroupList.size() - 1);
        assertThat(testBloodGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBloodGroup.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the BloodGroup in Elasticsearch
        BloodGroup bloodGroupEs = bloodGroupSearchRepository.findOne(testBloodGroup.getId());
        assertThat(bloodGroupEs).isEqualToComparingFieldByField(testBloodGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingBloodGroup() throws Exception {
        int databaseSizeBeforeUpdate = bloodGroupRepository.findAll().size();

        // Create the BloodGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBloodGroupMockMvc.perform(put("/api/blood-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodGroup)))
            .andExpect(status().isCreated());

        // Validate the BloodGroup in the database
        List<BloodGroup> bloodGroupList = bloodGroupRepository.findAll();
        assertThat(bloodGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBloodGroup() throws Exception {
        // Initialize the database
        bloodGroupRepository.saveAndFlush(bloodGroup);
        bloodGroupSearchRepository.save(bloodGroup);
        int databaseSizeBeforeDelete = bloodGroupRepository.findAll().size();

        // Get the bloodGroup
        restBloodGroupMockMvc.perform(delete("/api/blood-groups/{id}", bloodGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bloodGroupExistsInEs = bloodGroupSearchRepository.exists(bloodGroup.getId());
        assertThat(bloodGroupExistsInEs).isFalse();

        // Validate the database is empty
        List<BloodGroup> bloodGroupList = bloodGroupRepository.findAll();
        assertThat(bloodGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBloodGroup() throws Exception {
        // Initialize the database
        bloodGroupRepository.saveAndFlush(bloodGroup);
        bloodGroupSearchRepository.save(bloodGroup);

        // Search the bloodGroup
        restBloodGroupMockMvc.perform(get("/api/_search/blood-groups?query=id:" + bloodGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodGroup.class);
        BloodGroup bloodGroup1 = new BloodGroup();
        bloodGroup1.setId(1L);
        BloodGroup bloodGroup2 = new BloodGroup();
        bloodGroup2.setId(bloodGroup1.getId());
        assertThat(bloodGroup1).isEqualTo(bloodGroup2);
        bloodGroup2.setId(2L);
        assertThat(bloodGroup1).isNotEqualTo(bloodGroup2);
        bloodGroup1.setId(null);
        assertThat(bloodGroup1).isNotEqualTo(bloodGroup2);
    }
}
