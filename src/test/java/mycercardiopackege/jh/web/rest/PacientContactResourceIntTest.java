package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.PacientContact;
import mycercardiopackege.jh.repository.PacientContactRepository;
import mycercardiopackege.jh.repository.search.PacientContactSearchRepository;
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
 * Test class for the PacientContactResource REST controller.
 *
 * @see PacientContactResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientContactResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

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

    @Autowired
    private PacientContactRepository pacientContactRepository;

    @Autowired
    private PacientContactSearchRepository pacientContactSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientContactMockMvc;

    private PacientContact pacientContact;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientContactResource pacientContactResource = new PacientContactResource(pacientContactRepository, pacientContactSearchRepository);
        this.restPacientContactMockMvc = MockMvcBuilders.standaloneSetup(pacientContactResource)
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
    public static PacientContact createEntity(EntityManager em) {
        PacientContact pacientContact = new PacientContact()
            .name(DEFAULT_NAME)
            .lastname(DEFAULT_LASTNAME)
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
            .vine(DEFAULT_VINE);
        return pacientContact;
    }

    @Before
    public void initTest() {
        pacientContactSearchRepository.deleteAll();
        pacientContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacientContact() throws Exception {
        int databaseSizeBeforeCreate = pacientContactRepository.findAll().size();

        // Create the PacientContact
        restPacientContactMockMvc.perform(post("/api/pacient-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientContact)))
            .andExpect(status().isCreated());

        // Validate the PacientContact in the database
        List<PacientContact> pacientContactList = pacientContactRepository.findAll();
        assertThat(pacientContactList).hasSize(databaseSizeBeforeCreate + 1);
        PacientContact testPacientContact = pacientContactList.get(pacientContactList.size() - 1);
        assertThat(testPacientContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPacientContact.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPacientContact.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testPacientContact.getStreetnumber()).isEqualTo(DEFAULT_STREETNUMBER);
        assertThat(testPacientContact.getSuitnumber()).isEqualTo(DEFAULT_SUITNUMBER);
        assertThat(testPacientContact.getPhonenumber1()).isEqualTo(DEFAULT_PHONENUMBER_1);
        assertThat(testPacientContact.getPhonenumber2()).isEqualTo(DEFAULT_PHONENUMBER_2);
        assertThat(testPacientContact.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testPacientContact.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testPacientContact.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testPacientContact.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testPacientContact.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testPacientContact.getSnapchat()).isEqualTo(DEFAULT_SNAPCHAT);
        assertThat(testPacientContact.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testPacientContact.getVine()).isEqualTo(DEFAULT_VINE);

        // Validate the PacientContact in Elasticsearch
        PacientContact pacientContactEs = pacientContactSearchRepository.findOne(testPacientContact.getId());
        assertThat(pacientContactEs).isEqualToComparingFieldByField(testPacientContact);
    }

    @Test
    @Transactional
    public void createPacientContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientContactRepository.findAll().size();

        // Create the PacientContact with an existing ID
        pacientContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientContactMockMvc.perform(post("/api/pacient-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientContact)))
            .andExpect(status().isBadRequest());

        // Validate the PacientContact in the database
        List<PacientContact> pacientContactList = pacientContactRepository.findAll();
        assertThat(pacientContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacientContacts() throws Exception {
        // Initialize the database
        pacientContactRepository.saveAndFlush(pacientContact);

        // Get all the pacientContactList
        restPacientContactMockMvc.perform(get("/api/pacient-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
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
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())));
    }

    @Test
    @Transactional
    public void getPacientContact() throws Exception {
        // Initialize the database
        pacientContactRepository.saveAndFlush(pacientContact);

        // Get the pacientContact
        restPacientContactMockMvc.perform(get("/api/pacient-contacts/{id}", pacientContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacientContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
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
            .andExpect(jsonPath("$.vine").value(DEFAULT_VINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPacientContact() throws Exception {
        // Get the pacientContact
        restPacientContactMockMvc.perform(get("/api/pacient-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacientContact() throws Exception {
        // Initialize the database
        pacientContactRepository.saveAndFlush(pacientContact);
        pacientContactSearchRepository.save(pacientContact);
        int databaseSizeBeforeUpdate = pacientContactRepository.findAll().size();

        // Update the pacientContact
        PacientContact updatedPacientContact = pacientContactRepository.findOne(pacientContact.getId());
        updatedPacientContact
            .name(UPDATED_NAME)
            .lastname(UPDATED_LASTNAME)
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
            .vine(UPDATED_VINE);

        restPacientContactMockMvc.perform(put("/api/pacient-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacientContact)))
            .andExpect(status().isOk());

        // Validate the PacientContact in the database
        List<PacientContact> pacientContactList = pacientContactRepository.findAll();
        assertThat(pacientContactList).hasSize(databaseSizeBeforeUpdate);
        PacientContact testPacientContact = pacientContactList.get(pacientContactList.size() - 1);
        assertThat(testPacientContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPacientContact.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPacientContact.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testPacientContact.getStreetnumber()).isEqualTo(UPDATED_STREETNUMBER);
        assertThat(testPacientContact.getSuitnumber()).isEqualTo(UPDATED_SUITNUMBER);
        assertThat(testPacientContact.getPhonenumber1()).isEqualTo(UPDATED_PHONENUMBER_1);
        assertThat(testPacientContact.getPhonenumber2()).isEqualTo(UPDATED_PHONENUMBER_2);
        assertThat(testPacientContact.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testPacientContact.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testPacientContact.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testPacientContact.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testPacientContact.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testPacientContact.getSnapchat()).isEqualTo(UPDATED_SNAPCHAT);
        assertThat(testPacientContact.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testPacientContact.getVine()).isEqualTo(UPDATED_VINE);

        // Validate the PacientContact in Elasticsearch
        PacientContact pacientContactEs = pacientContactSearchRepository.findOne(testPacientContact.getId());
        assertThat(pacientContactEs).isEqualToComparingFieldByField(testPacientContact);
    }

    @Test
    @Transactional
    public void updateNonExistingPacientContact() throws Exception {
        int databaseSizeBeforeUpdate = pacientContactRepository.findAll().size();

        // Create the PacientContact

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientContactMockMvc.perform(put("/api/pacient-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacientContact)))
            .andExpect(status().isCreated());

        // Validate the PacientContact in the database
        List<PacientContact> pacientContactList = pacientContactRepository.findAll();
        assertThat(pacientContactList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacientContact() throws Exception {
        // Initialize the database
        pacientContactRepository.saveAndFlush(pacientContact);
        pacientContactSearchRepository.save(pacientContact);
        int databaseSizeBeforeDelete = pacientContactRepository.findAll().size();

        // Get the pacientContact
        restPacientContactMockMvc.perform(delete("/api/pacient-contacts/{id}", pacientContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientContactExistsInEs = pacientContactSearchRepository.exists(pacientContact.getId());
        assertThat(pacientContactExistsInEs).isFalse();

        // Validate the database is empty
        List<PacientContact> pacientContactList = pacientContactRepository.findAll();
        assertThat(pacientContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacientContact() throws Exception {
        // Initialize the database
        pacientContactRepository.saveAndFlush(pacientContact);
        pacientContactSearchRepository.save(pacientContact);

        // Search the pacientContact
        restPacientContactMockMvc.perform(get("/api/_search/pacient-contacts?query=id:" + pacientContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacientContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
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
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacientContact.class);
        PacientContact pacientContact1 = new PacientContact();
        pacientContact1.setId(1L);
        PacientContact pacientContact2 = new PacientContact();
        pacientContact2.setId(pacientContact1.getId());
        assertThat(pacientContact1).isEqualTo(pacientContact2);
        pacientContact2.setId(2L);
        assertThat(pacientContact1).isNotEqualTo(pacientContact2);
        pacientContact1.setId(null);
        assertThat(pacientContact1).isNotEqualTo(pacientContact2);
    }
}
