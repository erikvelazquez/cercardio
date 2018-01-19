package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Pacient;
import mycercardiopackege.jh.repository.PacientRepository;
import mycercardiopackege.jh.repository.search.PacientSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mycercardiopackege.jh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PacientResource REST controller.
 *
 * @see PacientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class PacientResourceIntTest {

    private static final Integer DEFAULT_CP = 1;
    private static final Integer UPDATED_CP = 2;

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

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEOFBIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEOFBIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACEOFBIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACEOFBIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVATENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRIVATENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIALNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SOCIALNUMBER = "BBBBBBBBBB";

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
    private PacientRepository pacientRepository;

    @Autowired
    private PacientSearchRepository pacientSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPacientMockMvc;

    private Pacient pacient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PacientResource pacientResource = new PacientResource(pacientRepository, pacientSearchRepository);
        this.restPacientMockMvc = MockMvcBuilders.standaloneSetup(pacientResource)
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
    public static Pacient createEntity(EntityManager em) {
        Pacient pacient = new Pacient()
            .cp(DEFAULT_CP)
            .colony(DEFAULT_COLONY)
            .street(DEFAULT_STREET)
            .streetnumber(DEFAULT_STREETNUMBER)
            .suitnumber(DEFAULT_SUITNUMBER)
            .phonenumber1(DEFAULT_PHONENUMBER_1)
            .phonenumber2(DEFAULT_PHONENUMBER_2)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .curp(DEFAULT_CURP)
            .rfc(DEFAULT_RFC)
            .dateofbirth(DEFAULT_DATEOFBIRTH)
            .placeofbirth(DEFAULT_PLACEOFBIRTH)
            .privatenumber(DEFAULT_PRIVATENUMBER)
            .socialnumber(DEFAULT_SOCIALNUMBER)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .instagram(DEFAULT_INSTAGRAM)
            .snapchat(DEFAULT_SNAPCHAT)
            .linkedin(DEFAULT_LINKEDIN)
            .vine(DEFAULT_VINE);
        return pacient;
    }

    @Before
    public void initTest() {
        pacientSearchRepository.deleteAll();
        pacient = createEntity(em);
    }

    @Test
    @Transactional
    public void createPacient() throws Exception {
        int databaseSizeBeforeCreate = pacientRepository.findAll().size();

        // Create the Pacient
        restPacientMockMvc.perform(post("/api/pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacient)))
            .andExpect(status().isCreated());

        // Validate the Pacient in the database
        List<Pacient> pacientList = pacientRepository.findAll();
        assertThat(pacientList).hasSize(databaseSizeBeforeCreate + 1);
        Pacient testPacient = pacientList.get(pacientList.size() - 1);
        assertThat(testPacient.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testPacient.getColony()).isEqualTo(DEFAULT_COLONY);
        assertThat(testPacient.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testPacient.getStreetnumber()).isEqualTo(DEFAULT_STREETNUMBER);
        assertThat(testPacient.getSuitnumber()).isEqualTo(DEFAULT_SUITNUMBER);
        assertThat(testPacient.getPhonenumber1()).isEqualTo(DEFAULT_PHONENUMBER_1);
        assertThat(testPacient.getPhonenumber2()).isEqualTo(DEFAULT_PHONENUMBER_2);
        assertThat(testPacient.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testPacient.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testPacient.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testPacient.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testPacient.getDateofbirth()).isEqualTo(DEFAULT_DATEOFBIRTH);
        assertThat(testPacient.getPlaceofbirth()).isEqualTo(DEFAULT_PLACEOFBIRTH);
        assertThat(testPacient.getPrivatenumber()).isEqualTo(DEFAULT_PRIVATENUMBER);
        assertThat(testPacient.getSocialnumber()).isEqualTo(DEFAULT_SOCIALNUMBER);
        assertThat(testPacient.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testPacient.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testPacient.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testPacient.getSnapchat()).isEqualTo(DEFAULT_SNAPCHAT);
        assertThat(testPacient.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testPacient.getVine()).isEqualTo(DEFAULT_VINE);

        // Validate the Pacient in Elasticsearch
        Pacient pacientEs = pacientSearchRepository.findOne(testPacient.getId());
        assertThat(pacientEs).isEqualToComparingFieldByField(testPacient);
    }

    @Test
    @Transactional
    public void createPacientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pacientRepository.findAll().size();

        // Create the Pacient with an existing ID
        pacient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPacientMockMvc.perform(post("/api/pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacient)))
            .andExpect(status().isBadRequest());

        // Validate the Pacient in the database
        List<Pacient> pacientList = pacientRepository.findAll();
        assertThat(pacientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPacients() throws Exception {
        // Initialize the database
        pacientRepository.saveAndFlush(pacient);

        // Get all the pacientList
        restPacientMockMvc.perform(get("/api/pacients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacient.getId().intValue())))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].colony").value(hasItem(DEFAULT_COLONY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetnumber").value(hasItem(DEFAULT_STREETNUMBER.toString())))
            .andExpect(jsonPath("$.[*].suitnumber").value(hasItem(DEFAULT_SUITNUMBER.toString())))
            .andExpect(jsonPath("$.[*].phonenumber1").value(hasItem(DEFAULT_PHONENUMBER_1)))
            .andExpect(jsonPath("$.[*].phonenumber2").value(hasItem(DEFAULT_PHONENUMBER_2)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeofbirth").value(hasItem(DEFAULT_PLACEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].privatenumber").value(hasItem(DEFAULT_PRIVATENUMBER.toString())))
            .andExpect(jsonPath("$.[*].socialnumber").value(hasItem(DEFAULT_SOCIALNUMBER.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].snapchat").value(hasItem(DEFAULT_SNAPCHAT.toString())))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())));
    }

    @Test
    @Transactional
    public void getPacient() throws Exception {
        // Initialize the database
        pacientRepository.saveAndFlush(pacient);

        // Get the pacient
        restPacientMockMvc.perform(get("/api/pacients/{id}", pacient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pacient.getId().intValue()))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP))
            .andExpect(jsonPath("$.colony").value(DEFAULT_COLONY.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.streetnumber").value(DEFAULT_STREETNUMBER.toString()))
            .andExpect(jsonPath("$.suitnumber").value(DEFAULT_SUITNUMBER.toString()))
            .andExpect(jsonPath("$.phonenumber1").value(DEFAULT_PHONENUMBER_1))
            .andExpect(jsonPath("$.phonenumber2").value(DEFAULT_PHONENUMBER_2))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.dateofbirth").value(DEFAULT_DATEOFBIRTH.toString()))
            .andExpect(jsonPath("$.placeofbirth").value(DEFAULT_PLACEOFBIRTH.toString()))
            .andExpect(jsonPath("$.privatenumber").value(DEFAULT_PRIVATENUMBER.toString()))
            .andExpect(jsonPath("$.socialnumber").value(DEFAULT_SOCIALNUMBER.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.snapchat").value(DEFAULT_SNAPCHAT.toString()))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN.toString()))
            .andExpect(jsonPath("$.vine").value(DEFAULT_VINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPacient() throws Exception {
        // Get the pacient
        restPacientMockMvc.perform(get("/api/pacients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePacient() throws Exception {
        // Initialize the database
        pacientRepository.saveAndFlush(pacient);
        pacientSearchRepository.save(pacient);
        int databaseSizeBeforeUpdate = pacientRepository.findAll().size();

        // Update the pacient
        Pacient updatedPacient = pacientRepository.findOne(pacient.getId());
        updatedPacient
            .cp(UPDATED_CP)
            .colony(UPDATED_COLONY)
            .street(UPDATED_STREET)
            .streetnumber(UPDATED_STREETNUMBER)
            .suitnumber(UPDATED_SUITNUMBER)
            .phonenumber1(UPDATED_PHONENUMBER_1)
            .phonenumber2(UPDATED_PHONENUMBER_2)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .curp(UPDATED_CURP)
            .rfc(UPDATED_RFC)
            .dateofbirth(UPDATED_DATEOFBIRTH)
            .placeofbirth(UPDATED_PLACEOFBIRTH)
            .privatenumber(UPDATED_PRIVATENUMBER)
            .socialnumber(UPDATED_SOCIALNUMBER)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM)
            .snapchat(UPDATED_SNAPCHAT)
            .linkedin(UPDATED_LINKEDIN)
            .vine(UPDATED_VINE);

        restPacientMockMvc.perform(put("/api/pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPacient)))
            .andExpect(status().isOk());

        // Validate the Pacient in the database
        List<Pacient> pacientList = pacientRepository.findAll();
        assertThat(pacientList).hasSize(databaseSizeBeforeUpdate);
        Pacient testPacient = pacientList.get(pacientList.size() - 1);
        assertThat(testPacient.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testPacient.getColony()).isEqualTo(UPDATED_COLONY);
        assertThat(testPacient.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testPacient.getStreetnumber()).isEqualTo(UPDATED_STREETNUMBER);
        assertThat(testPacient.getSuitnumber()).isEqualTo(UPDATED_SUITNUMBER);
        assertThat(testPacient.getPhonenumber1()).isEqualTo(UPDATED_PHONENUMBER_1);
        assertThat(testPacient.getPhonenumber2()).isEqualTo(UPDATED_PHONENUMBER_2);
        assertThat(testPacient.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testPacient.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testPacient.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testPacient.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testPacient.getDateofbirth()).isEqualTo(UPDATED_DATEOFBIRTH);
        assertThat(testPacient.getPlaceofbirth()).isEqualTo(UPDATED_PLACEOFBIRTH);
        assertThat(testPacient.getPrivatenumber()).isEqualTo(UPDATED_PRIVATENUMBER);
        assertThat(testPacient.getSocialnumber()).isEqualTo(UPDATED_SOCIALNUMBER);
        assertThat(testPacient.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testPacient.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testPacient.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testPacient.getSnapchat()).isEqualTo(UPDATED_SNAPCHAT);
        assertThat(testPacient.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testPacient.getVine()).isEqualTo(UPDATED_VINE);

        // Validate the Pacient in Elasticsearch
        Pacient pacientEs = pacientSearchRepository.findOne(testPacient.getId());
        assertThat(pacientEs).isEqualToComparingFieldByField(testPacient);
    }

    @Test
    @Transactional
    public void updateNonExistingPacient() throws Exception {
        int databaseSizeBeforeUpdate = pacientRepository.findAll().size();

        // Create the Pacient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPacientMockMvc.perform(put("/api/pacients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pacient)))
            .andExpect(status().isCreated());

        // Validate the Pacient in the database
        List<Pacient> pacientList = pacientRepository.findAll();
        assertThat(pacientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePacient() throws Exception {
        // Initialize the database
        pacientRepository.saveAndFlush(pacient);
        pacientSearchRepository.save(pacient);
        int databaseSizeBeforeDelete = pacientRepository.findAll().size();

        // Get the pacient
        restPacientMockMvc.perform(delete("/api/pacients/{id}", pacient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pacientExistsInEs = pacientSearchRepository.exists(pacient.getId());
        assertThat(pacientExistsInEs).isFalse();

        // Validate the database is empty
        List<Pacient> pacientList = pacientRepository.findAll();
        assertThat(pacientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPacient() throws Exception {
        // Initialize the database
        pacientRepository.saveAndFlush(pacient);
        pacientSearchRepository.save(pacient);

        // Search the pacient
        restPacientMockMvc.perform(get("/api/_search/pacients?query=id:" + pacient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pacient.getId().intValue())))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].colony").value(hasItem(DEFAULT_COLONY.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].streetnumber").value(hasItem(DEFAULT_STREETNUMBER.toString())))
            .andExpect(jsonPath("$.[*].suitnumber").value(hasItem(DEFAULT_SUITNUMBER.toString())))
            .andExpect(jsonPath("$.[*].phonenumber1").value(hasItem(DEFAULT_PHONENUMBER_1)))
            .andExpect(jsonPath("$.[*].phonenumber2").value(hasItem(DEFAULT_PHONENUMBER_2)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].dateofbirth").value(hasItem(DEFAULT_DATEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].placeofbirth").value(hasItem(DEFAULT_PLACEOFBIRTH.toString())))
            .andExpect(jsonPath("$.[*].privatenumber").value(hasItem(DEFAULT_PRIVATENUMBER.toString())))
            .andExpect(jsonPath("$.[*].socialnumber").value(hasItem(DEFAULT_SOCIALNUMBER.toString())))
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
        TestUtil.equalsVerifier(Pacient.class);
        Pacient pacient1 = new Pacient();
        pacient1.setId(1L);
        Pacient pacient2 = new Pacient();
        pacient2.setId(pacient1.getId());
        assertThat(pacient1).isEqualTo(pacient2);
        pacient2.setId(2L);
        assertThat(pacient1).isNotEqualTo(pacient2);
        pacient1.setId(null);
        assertThat(pacient1).isNotEqualTo(pacient2);
    }
}
