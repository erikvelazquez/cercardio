package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.EthnicGroup;
import mycercardiopackege.jh.repository.EthnicGroupRepository;
import mycercardiopackege.jh.repository.search.EthnicGroupSearchRepository;
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
 * Test class for the EthnicGroupResource REST controller.
 *
 * @see EthnicGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class EthnicGroupResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISACTIVE = false;
    private static final Boolean UPDATED_ISACTIVE = true;

    @Autowired
    private EthnicGroupRepository ethnicGroupRepository;

    @Autowired
    private EthnicGroupSearchRepository ethnicGroupSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEthnicGroupMockMvc;

    private EthnicGroup ethnicGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EthnicGroupResource ethnicGroupResource = new EthnicGroupResource(ethnicGroupRepository, ethnicGroupSearchRepository);
        this.restEthnicGroupMockMvc = MockMvcBuilders.standaloneSetup(ethnicGroupResource)
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
    public static EthnicGroup createEntity(EntityManager em) {
        EthnicGroup ethnicGroup = new EthnicGroup()
            .description(DEFAULT_DESCRIPTION)
            .isactive(DEFAULT_ISACTIVE);
        return ethnicGroup;
    }

    @Before
    public void initTest() {
        ethnicGroupSearchRepository.deleteAll();
        ethnicGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createEthnicGroup() throws Exception {
        int databaseSizeBeforeCreate = ethnicGroupRepository.findAll().size();

        // Create the EthnicGroup
        restEthnicGroupMockMvc.perform(post("/api/ethnic-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicGroup)))
            .andExpect(status().isCreated());

        // Validate the EthnicGroup in the database
        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findAll();
        assertThat(ethnicGroupList).hasSize(databaseSizeBeforeCreate + 1);
        EthnicGroup testEthnicGroup = ethnicGroupList.get(ethnicGroupList.size() - 1);
        assertThat(testEthnicGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEthnicGroup.isIsactive()).isEqualTo(DEFAULT_ISACTIVE);

        // Validate the EthnicGroup in Elasticsearch
        EthnicGroup ethnicGroupEs = ethnicGroupSearchRepository.findOne(testEthnicGroup.getId());
        assertThat(ethnicGroupEs).isEqualToComparingFieldByField(testEthnicGroup);
    }

    @Test
    @Transactional
    public void createEthnicGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ethnicGroupRepository.findAll().size();

        // Create the EthnicGroup with an existing ID
        ethnicGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEthnicGroupMockMvc.perform(post("/api/ethnic-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicGroup)))
            .andExpect(status().isBadRequest());

        // Validate the EthnicGroup in the database
        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findAll();
        assertThat(ethnicGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEthnicGroups() throws Exception {
        // Initialize the database
        ethnicGroupRepository.saveAndFlush(ethnicGroup);

        // Get all the ethnicGroupList
        restEthnicGroupMockMvc.perform(get("/api/ethnic-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ethnicGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void getEthnicGroup() throws Exception {
        // Initialize the database
        ethnicGroupRepository.saveAndFlush(ethnicGroup);

        // Get the ethnicGroup
        restEthnicGroupMockMvc.perform(get("/api/ethnic-groups/{id}", ethnicGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ethnicGroup.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isactive").value(DEFAULT_ISACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEthnicGroup() throws Exception {
        // Get the ethnicGroup
        restEthnicGroupMockMvc.perform(get("/api/ethnic-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEthnicGroup() throws Exception {
        // Initialize the database
        ethnicGroupRepository.saveAndFlush(ethnicGroup);
        ethnicGroupSearchRepository.save(ethnicGroup);
        int databaseSizeBeforeUpdate = ethnicGroupRepository.findAll().size();

        // Update the ethnicGroup
        EthnicGroup updatedEthnicGroup = ethnicGroupRepository.findOne(ethnicGroup.getId());
        updatedEthnicGroup
            .description(UPDATED_DESCRIPTION)
            .isactive(UPDATED_ISACTIVE);

        restEthnicGroupMockMvc.perform(put("/api/ethnic-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEthnicGroup)))
            .andExpect(status().isOk());

        // Validate the EthnicGroup in the database
        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findAll();
        assertThat(ethnicGroupList).hasSize(databaseSizeBeforeUpdate);
        EthnicGroup testEthnicGroup = ethnicGroupList.get(ethnicGroupList.size() - 1);
        assertThat(testEthnicGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEthnicGroup.isIsactive()).isEqualTo(UPDATED_ISACTIVE);

        // Validate the EthnicGroup in Elasticsearch
        EthnicGroup ethnicGroupEs = ethnicGroupSearchRepository.findOne(testEthnicGroup.getId());
        assertThat(ethnicGroupEs).isEqualToComparingFieldByField(testEthnicGroup);
    }

    @Test
    @Transactional
    public void updateNonExistingEthnicGroup() throws Exception {
        int databaseSizeBeforeUpdate = ethnicGroupRepository.findAll().size();

        // Create the EthnicGroup

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEthnicGroupMockMvc.perform(put("/api/ethnic-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ethnicGroup)))
            .andExpect(status().isCreated());

        // Validate the EthnicGroup in the database
        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findAll();
        assertThat(ethnicGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEthnicGroup() throws Exception {
        // Initialize the database
        ethnicGroupRepository.saveAndFlush(ethnicGroup);
        ethnicGroupSearchRepository.save(ethnicGroup);
        int databaseSizeBeforeDelete = ethnicGroupRepository.findAll().size();

        // Get the ethnicGroup
        restEthnicGroupMockMvc.perform(delete("/api/ethnic-groups/{id}", ethnicGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ethnicGroupExistsInEs = ethnicGroupSearchRepository.exists(ethnicGroup.getId());
        assertThat(ethnicGroupExistsInEs).isFalse();

        // Validate the database is empty
        List<EthnicGroup> ethnicGroupList = ethnicGroupRepository.findAll();
        assertThat(ethnicGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEthnicGroup() throws Exception {
        // Initialize the database
        ethnicGroupRepository.saveAndFlush(ethnicGroup);
        ethnicGroupSearchRepository.save(ethnicGroup);

        // Search the ethnicGroup
        restEthnicGroupMockMvc.perform(get("/api/_search/ethnic-groups?query=id:" + ethnicGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ethnicGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isactive").value(hasItem(DEFAULT_ISACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EthnicGroup.class);
        EthnicGroup ethnicGroup1 = new EthnicGroup();
        ethnicGroup1.setId(1L);
        EthnicGroup ethnicGroup2 = new EthnicGroup();
        ethnicGroup2.setId(ethnicGroup1.getId());
        assertThat(ethnicGroup1).isEqualTo(ethnicGroup2);
        ethnicGroup2.setId(2L);
        assertThat(ethnicGroup1).isNotEqualTo(ethnicGroup2);
        ethnicGroup1.setId(null);
        assertThat(ethnicGroup1).isNotEqualTo(ethnicGroup2);
    }
}
