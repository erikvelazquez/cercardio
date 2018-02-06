package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Medic;
import mycercardiopackege.jh.repository.MedicRepository;
import mycercardiopackege.jh.repository.search.MedicSearchRepository;
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
 * Test class for the MedicResource REST controller.
 *
 * @see MedicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class MedicResourceIntTest {

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
    private MedicRepository medicRepository;

    @Autowired
    private MedicSearchRepository medicSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicMockMvc;

    private Medic medic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicResource medicResource = new MedicResource(medicRepository, medicSearchRepository);
        this.restMedicMockMvc = MockMvcBuilders.standaloneSetup(medicResource)
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
    public static Medic createEntity(EntityManager em) {
        Medic medic = new Medic()
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
        return medic;
    }

    @Before
    public void initTest() {
        medicSearchRepository.deleteAll();
        medic = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedic() throws Exception {
        int databaseSizeBeforeCreate = medicRepository.findAll().size();

        // Create the Medic
        restMedicMockMvc.perform(post("/api/medics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isCreated());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeCreate + 1);
        Medic testMedic = medicList.get(medicList.size() - 1);
        assertThat(testMedic.getColony()).isEqualTo(DEFAULT_COLONY);
        assertThat(testMedic.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testMedic.getStreetnumber()).isEqualTo(DEFAULT_STREETNUMBER);
        assertThat(testMedic.getSuitnumber()).isEqualTo(DEFAULT_SUITNUMBER);
        assertThat(testMedic.getPhonenumber1()).isEqualTo(DEFAULT_PHONENUMBER_1);
        assertThat(testMedic.getPhonenumber2()).isEqualTo(DEFAULT_PHONENUMBER_2);
        assertThat(testMedic.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testMedic.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testMedic.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testMedic.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testMedic.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testMedic.getSnapchat()).isEqualTo(DEFAULT_SNAPCHAT);
        assertThat(testMedic.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testMedic.getVine()).isEqualTo(DEFAULT_VINE);
        assertThat(testMedic.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);

        // Validate the Medic in Elasticsearch
        Medic medicEs = medicSearchRepository.findOne(testMedic.getId());
        assertThat(medicEs).isEqualToComparingFieldByField(testMedic);
    }

    @Test
    @Transactional
    public void createMedicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicRepository.findAll().size();

        // Create the Medic with an existing ID
        medic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicMockMvc.perform(post("/api/medics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isBadRequest());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedics() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        // Get all the medicList
        restMedicMockMvc.perform(get("/api/medics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medic.getId().intValue())))
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
    public void getMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        // Get the medic
        restMedicMockMvc.perform(get("/api/medics/{id}", medic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medic.getId().intValue()))
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
    public void getNonExistingMedic() throws Exception {
        // Get the medic
        restMedicMockMvc.perform(get("/api/medics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);
        medicSearchRepository.save(medic);
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();

        // Update the medic
        Medic updatedMedic = medicRepository.findOne(medic.getId());
        updatedMedic
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

        restMedicMockMvc.perform(put("/api/medics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedic)))
            .andExpect(status().isOk());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
        Medic testMedic = medicList.get(medicList.size() - 1);
        assertThat(testMedic.getColony()).isEqualTo(UPDATED_COLONY);
        assertThat(testMedic.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testMedic.getStreetnumber()).isEqualTo(UPDATED_STREETNUMBER);
        assertThat(testMedic.getSuitnumber()).isEqualTo(UPDATED_SUITNUMBER);
        assertThat(testMedic.getPhonenumber1()).isEqualTo(UPDATED_PHONENUMBER_1);
        assertThat(testMedic.getPhonenumber2()).isEqualTo(UPDATED_PHONENUMBER_2);
        assertThat(testMedic.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testMedic.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testMedic.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testMedic.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testMedic.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testMedic.getSnapchat()).isEqualTo(UPDATED_SNAPCHAT);
        assertThat(testMedic.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testMedic.getVine()).isEqualTo(UPDATED_VINE);
        assertThat(testMedic.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);

        // Validate the Medic in Elasticsearch
        Medic medicEs = medicSearchRepository.findOne(testMedic.getId());
        assertThat(medicEs).isEqualToComparingFieldByField(testMedic);
    }

    @Test
    @Transactional
    public void updateNonExistingMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();

        // Create the Medic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicMockMvc.perform(put("/api/medics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isCreated());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);
        medicSearchRepository.save(medic);
        int databaseSizeBeforeDelete = medicRepository.findAll().size();

        // Get the medic
        restMedicMockMvc.perform(delete("/api/medics/{id}", medic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medicExistsInEs = medicSearchRepository.exists(medic.getId());
        assertThat(medicExistsInEs).isFalse();

        // Validate the database is empty
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);
        medicSearchRepository.save(medic);

        // Search the medic
        restMedicMockMvc.perform(get("/api/_search/medics?query=id:" + medic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medic.getId().intValue())))
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
        TestUtil.equalsVerifier(Medic.class);
        Medic medic1 = new Medic();
        medic1.setId(1L);
        Medic medic2 = new Medic();
        medic2.setId(medic1.getId());
        assertThat(medic1).isEqualTo(medic2);
        medic2.setId(2L);
        assertThat(medic1).isNotEqualTo(medic2);
        medic1.setId(null);
        assertThat(medic1).isNotEqualTo(medic2);
    }
}
