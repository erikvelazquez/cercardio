package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Nurse;
import mycercardiopackege.jh.repository.NurseRepository;
import mycercardiopackege.jh.repository.search.NurseSearchRepository;
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
 * Test class for the NurseResource REST controller.
 *
 * @see NurseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class NurseResourceIntTest {

    private static final Integer DEFAULT_CPID = 1;
    private static final Integer UPDATED_CPID = 2;

    private static final String DEFAULT_COLONY = "AAAAAAAAAA";
    private static final String UPDATED_COLONY = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_STREETNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREETNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUITNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SUITNUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_PHONENUMBER_1 = 1;
    private static final Integer UPDATED_PHONENUMBER_1 = 2;

    private static final Integer DEFAULT_PHONENUMBER_2 = 1;
    private static final Integer UPDATED_PHONENUMBER_2 = 2;

    private static final String DEFAULT_EMAIL_1 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_SNAPCHAT = "AAAAAAAAAA";
    private static final String UPDATED_SNAPCHAT = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN = "BBBBBBBBBB";

    private static final String DEFAULT_VINE = "AAAAAAAAAA";
    private static final String UPDATED_VINE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private NurseSearchRepository nurseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNurseMockMvc;

    private Nurse nurse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NurseResource nurseResource = new NurseResource(nurseRepository, nurseSearchRepository);
        this.restNurseMockMvc = MockMvcBuilders.standaloneSetup(nurseResource)
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
    public static Nurse createEntity(EntityManager em) {
        Nurse nurse = new Nurse()
            .cpid(DEFAULT_CPID)
            .colony(DEFAULT_COLONY)
            .street(DEFAULT_STREET)
            .streetnumber(DEFAULT_STREETNUMBER)
            .suitnumber(DEFAULT_SUITNUMBER)
            .phonenumber1(DEFAULT_PHONENUMBER_1)
            .phonenumber2(DEFAULT_PHONENUMBER_2)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .instagram(DEFAULT_INSTAGRAM)
            .snapchat(DEFAULT_SNAPCHAT)
            .linkedin(DEFAULT_LINKEDIN)
            .vine(DEFAULT_VINE)
            .createdat(DEFAULT_CREATEDAT);
        return nurse;
    }

    @Before
    public void initTest() {
        nurseSearchRepository.deleteAll();
        nurse = createEntity(em);
    }

    @Test
    @Transactional
    public void createNurse() throws Exception {
        int databaseSizeBeforeCreate = nurseRepository.findAll().size();

        // Create the Nurse
        restNurseMockMvc.perform(post("/api/nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nurse)))
            .andExpect(status().isCreated());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeCreate + 1);
        Nurse testNurse = nurseList.get(nurseList.size() - 1);
        assertThat(testNurse.getCpid()).isEqualTo(DEFAULT_CPID);
        assertThat(testNurse.getColony()).isEqualTo(DEFAULT_COLONY);
        assertThat(testNurse.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testNurse.getStreetnumber()).isEqualTo(DEFAULT_STREETNUMBER);
        assertThat(testNurse.getSuitnumber()).isEqualTo(DEFAULT_SUITNUMBER);
        assertThat(testNurse.getPhonenumber1()).isEqualTo(DEFAULT_PHONENUMBER_1);
        assertThat(testNurse.getPhonenumber2()).isEqualTo(DEFAULT_PHONENUMBER_2);
        assertThat(testNurse.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testNurse.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testNurse.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testNurse.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testNurse.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testNurse.getSnapchat()).isEqualTo(DEFAULT_SNAPCHAT);
        assertThat(testNurse.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testNurse.getVine()).isEqualTo(DEFAULT_VINE);
        assertThat(testNurse.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the Nurse in Elasticsearch
        Nurse nurseEs = nurseSearchRepository.findOne(testNurse.getId());
        assertThat(nurseEs).isEqualToComparingFieldByField(testNurse);
    }

    @Test
    @Transactional
    public void createNurseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nurseRepository.findAll().size();

        // Create the Nurse with an existing ID
        nurse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNurseMockMvc.perform(post("/api/nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nurse)))
            .andExpect(status().isBadRequest());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNurses() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        // Get all the nurseList
        restNurseMockMvc.perform(get("/api/nurses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nurse.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpid").value(hasItem(DEFAULT_CPID)))
            .andExpect(jsonPath("$.[*].colony").value(hasItem(DEFAULT_COLONY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetnumber").value(hasItem(DEFAULT_STREETNUMBER.toString())))
            .andExpect(jsonPath("$.[*].suitnumber").value(hasItem(DEFAULT_SUITNUMBER.toString())))
            .andExpect(jsonPath("$.[*].phonenumber1").value(hasItem(DEFAULT_PHONENUMBER_1)))
            .andExpect(jsonPath("$.[*].phonenumber2").value(hasItem(DEFAULT_PHONENUMBER_2)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].snapchat").value(hasItem(DEFAULT_SNAPCHAT.toString())))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void getNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);

        // Get the nurse
        restNurseMockMvc.perform(get("/api/nurses/{id}", nurse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nurse.getId().intValue()))
            .andExpect(jsonPath("$.cpid").value(DEFAULT_CPID))
            .andExpect(jsonPath("$.colony").value(DEFAULT_COLONY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.streetnumber").value(DEFAULT_STREETNUMBER.toString()))
            .andExpect(jsonPath("$.suitnumber").value(DEFAULT_SUITNUMBER.toString()))
            .andExpect(jsonPath("$.phonenumber1").value(DEFAULT_PHONENUMBER_1))
            .andExpect(jsonPath("$.phonenumber2").value(DEFAULT_PHONENUMBER_2))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.snapchat").value(DEFAULT_SNAPCHAT.toString()))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN.toString()))
            .andExpect(jsonPath("$.vine").value(DEFAULT_VINE.toString()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)));
    }

    @Test
    @Transactional
    public void getNonExistingNurse() throws Exception {
        // Get the nurse
        restNurseMockMvc.perform(get("/api/nurses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);
        nurseSearchRepository.save(nurse);
        int databaseSizeBeforeUpdate = nurseRepository.findAll().size();

        // Update the nurse
        Nurse updatedNurse = nurseRepository.findOne(nurse.getId());
        updatedNurse
            .cpid(UPDATED_CPID)
            .colony(UPDATED_COLONY)
            .street(UPDATED_STREET)
            .streetnumber(UPDATED_STREETNUMBER)
            .suitnumber(UPDATED_SUITNUMBER)
            .phonenumber1(UPDATED_PHONENUMBER_1)
            .phonenumber2(UPDATED_PHONENUMBER_2)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM)
            .snapchat(UPDATED_SNAPCHAT)
            .linkedin(UPDATED_LINKEDIN)
            .vine(UPDATED_VINE)
            .createdat(UPDATED_CREATEDAT);

        restNurseMockMvc.perform(put("/api/nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNurse)))
            .andExpect(status().isOk());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeUpdate);
        Nurse testNurse = nurseList.get(nurseList.size() - 1);
        assertThat(testNurse.getCpid()).isEqualTo(UPDATED_CPID);
        assertThat(testNurse.getColony()).isEqualTo(UPDATED_COLONY);
        assertThat(testNurse.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testNurse.getStreetnumber()).isEqualTo(UPDATED_STREETNUMBER);
        assertThat(testNurse.getSuitnumber()).isEqualTo(UPDATED_SUITNUMBER);
        assertThat(testNurse.getPhonenumber1()).isEqualTo(UPDATED_PHONENUMBER_1);
        assertThat(testNurse.getPhonenumber2()).isEqualTo(UPDATED_PHONENUMBER_2);
        assertThat(testNurse.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testNurse.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testNurse.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testNurse.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testNurse.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testNurse.getSnapchat()).isEqualTo(UPDATED_SNAPCHAT);
        assertThat(testNurse.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testNurse.getVine()).isEqualTo(UPDATED_VINE);
        assertThat(testNurse.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the Nurse in Elasticsearch
        Nurse nurseEs = nurseSearchRepository.findOne(testNurse.getId());
        assertThat(nurseEs).isEqualToComparingFieldByField(testNurse);
    }

    @Test
    @Transactional
    public void updateNonExistingNurse() throws Exception {
        int databaseSizeBeforeUpdate = nurseRepository.findAll().size();

        // Create the Nurse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNurseMockMvc.perform(put("/api/nurses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nurse)))
            .andExpect(status().isCreated());

        // Validate the Nurse in the database
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);
        nurseSearchRepository.save(nurse);
        int databaseSizeBeforeDelete = nurseRepository.findAll().size();

        // Get the nurse
        restNurseMockMvc.perform(delete("/api/nurses/{id}", nurse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean nurseExistsInEs = nurseSearchRepository.exists(nurse.getId());
        assertThat(nurseExistsInEs).isFalse();

        // Validate the database is empty
        List<Nurse> nurseList = nurseRepository.findAll();
        assertThat(nurseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchNurse() throws Exception {
        // Initialize the database
        nurseRepository.saveAndFlush(nurse);
        nurseSearchRepository.save(nurse);

        // Search the nurse
        restNurseMockMvc.perform(get("/api/_search/nurses?query=id:" + nurse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nurse.getId().intValue())))
            .andExpect(jsonPath("$.[*].cpid").value(hasItem(DEFAULT_CPID)))
            .andExpect(jsonPath("$.[*].colony").value(hasItem(DEFAULT_COLONY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetnumber").value(hasItem(DEFAULT_STREETNUMBER.toString())))
            .andExpect(jsonPath("$.[*].suitnumber").value(hasItem(DEFAULT_SUITNUMBER.toString())))
            .andExpect(jsonPath("$.[*].phonenumber1").value(hasItem(DEFAULT_PHONENUMBER_1)))
            .andExpect(jsonPath("$.[*].phonenumber2").value(hasItem(DEFAULT_PHONENUMBER_2)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].snapchat").value(hasItem(DEFAULT_SNAPCHAT.toString())))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nurse.class);
        Nurse nurse1 = new Nurse();
        nurse1.setId(1L);
        Nurse nurse2 = new Nurse();
        nurse2.setId(nurse1.getId());
        assertThat(nurse1).isEqualTo(nurse2);
        nurse2.setId(2L);
        assertThat(nurse1).isNotEqualTo(nurse2);
        nurse1.setId(null);
        assertThat(nurse1).isNotEqualTo(nurse2);
    }
}
