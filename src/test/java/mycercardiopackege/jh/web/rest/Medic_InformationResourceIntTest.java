package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Medic_Information;
import mycercardiopackege.jh.repository.Medic_InformationRepository;
import mycercardiopackege.jh.repository.search.Medic_InformationSearchRepository;
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
 * Test class for the Medic_InformationResource REST controller.
 *
 * @see Medic_InformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class Medic_InformationResourceIntTest {

    private static final Integer DEFAULT_CP = 1;
    private static final Integer UPDATED_CP = 2;

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_EXTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EXTNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INTNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INTNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_1 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_2 = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBBBBBBB";

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

    private static final String DEFAULT_RECOMENDED = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDED = "BBBBBBBBBB";

    private static final String DEFAULT_CVUCONACYT = "AAAAAAAAAA";
    private static final String UPDATED_CVUCONACYT = "BBBBBBBBBB";

    private static final String DEFAULT_CEDULA = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA = "BBBBBBBBBB";

    private static final String DEFAULT_CEDULAESP = "AAAAAAAAAA";
    private static final String UPDATED_CEDULAESP = "BBBBBBBBBB";

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_RFC = "AAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EXTERNALHOSPITALS = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNALHOSPITALS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUBID = 1;
    private static final Integer UPDATED_SUBID = 2;

    private static final Integer DEFAULT_HIGHID = 1;
    private static final Integer UPDATED_HIGHID = 2;

    private static final Integer DEFAULT_MEDICALINSURANCE = 1;
    private static final Integer UPDATED_MEDICALINSURANCE = 2;

    private static final Integer DEFAULT_SOCIALINSURANCE = 1;
    private static final Integer UPDATED_SOCIALINSURANCE = 2;

    private static final String DEFAULT_RESPONSIBILITYINSURANCE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBILITYINSURANCE = "BBBBBBBBBB";

    private static final String DEFAULT_S_I_NCONACYT = "AAAAAAAAAA";
    private static final String UPDATED_S_I_NCONACYT = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICALINNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MEDICALINNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SOCIALINNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SOCIALINNUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBILITYINNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBILITYINNUMBER = "BBBBBBBBBB";

    @Autowired
    private Medic_InformationRepository medic_InformationRepository;

    @Autowired
    private Medic_InformationSearchRepository medic_InformationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedic_InformationMockMvc;

    private Medic_Information medic_Information;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Medic_InformationResource medic_InformationResource = new Medic_InformationResource(medic_InformationRepository, medic_InformationSearchRepository);
        this.restMedic_InformationMockMvc = MockMvcBuilders.standaloneSetup(medic_InformationResource)
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
    public static Medic_Information createEntity(EntityManager em) {
        Medic_Information medic_Information = new Medic_Information()
            .cp(DEFAULT_CP)
            .street(DEFAULT_STREET)
            .extnumber(DEFAULT_EXTNUMBER)
            .intnumber(DEFAULT_INTNUMBER)
            .phone1(DEFAULT_PHONE_1)
            .phone2(DEFAULT_PHONE_2)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .instagram(DEFAULT_INSTAGRAM)
            .snapchat(DEFAULT_SNAPCHAT)
            .linkedin(DEFAULT_LINKEDIN)
            .vine(DEFAULT_VINE)
            .recomended(DEFAULT_RECOMENDED)
            .cvuconacyt(DEFAULT_CVUCONACYT)
            .cedula(DEFAULT_CEDULA)
            .cedulaesp(DEFAULT_CEDULAESP)
            .curp(DEFAULT_CURP)
            .rfc(DEFAULT_RFC)
            .birthday(DEFAULT_BIRTHDAY)
            .externalhospitals(DEFAULT_EXTERNALHOSPITALS)
            .subid(DEFAULT_SUBID)
            .highid(DEFAULT_HIGHID)
            .medicalinsurance(DEFAULT_MEDICALINSURANCE)
            .socialinsurance(DEFAULT_SOCIALINSURANCE)
            .responsibilityinsurance(DEFAULT_RESPONSIBILITYINSURANCE)
            .sINconacyt(DEFAULT_S_I_NCONACYT)
            .medicalinnumber(DEFAULT_MEDICALINNUMBER)
            .socialinnumber(DEFAULT_SOCIALINNUMBER)
            .responsibilityinnumber(DEFAULT_RESPONSIBILITYINNUMBER);
        return medic_Information;
    }

    @Before
    public void initTest() {
        medic_InformationSearchRepository.deleteAll();
        medic_Information = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedic_Information() throws Exception {
        int databaseSizeBeforeCreate = medic_InformationRepository.findAll().size();

        // Create the Medic_Information
        restMedic_InformationMockMvc.perform(post("/api/medic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medic_Information)))
            .andExpect(status().isCreated());

        // Validate the Medic_Information in the database
        List<Medic_Information> medic_InformationList = medic_InformationRepository.findAll();
        assertThat(medic_InformationList).hasSize(databaseSizeBeforeCreate + 1);
        Medic_Information testMedic_Information = medic_InformationList.get(medic_InformationList.size() - 1);
        assertThat(testMedic_Information.getCp()).isEqualTo(DEFAULT_CP);
        assertThat(testMedic_Information.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testMedic_Information.getExtnumber()).isEqualTo(DEFAULT_EXTNUMBER);
        assertThat(testMedic_Information.getIntnumber()).isEqualTo(DEFAULT_INTNUMBER);
        assertThat(testMedic_Information.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testMedic_Information.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testMedic_Information.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testMedic_Information.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
        assertThat(testMedic_Information.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testMedic_Information.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testMedic_Information.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testMedic_Information.getSnapchat()).isEqualTo(DEFAULT_SNAPCHAT);
        assertThat(testMedic_Information.getLinkedin()).isEqualTo(DEFAULT_LINKEDIN);
        assertThat(testMedic_Information.getVine()).isEqualTo(DEFAULT_VINE);
        assertThat(testMedic_Information.getRecomended()).isEqualTo(DEFAULT_RECOMENDED);
        assertThat(testMedic_Information.getCvuconacyt()).isEqualTo(DEFAULT_CVUCONACYT);
        assertThat(testMedic_Information.getCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testMedic_Information.getCedulaesp()).isEqualTo(DEFAULT_CEDULAESP);
        assertThat(testMedic_Information.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testMedic_Information.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testMedic_Information.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testMedic_Information.getExternalhospitals()).isEqualTo(DEFAULT_EXTERNALHOSPITALS);
        assertThat(testMedic_Information.getSubid()).isEqualTo(DEFAULT_SUBID);
        assertThat(testMedic_Information.getHighid()).isEqualTo(DEFAULT_HIGHID);
        assertThat(testMedic_Information.getMedicalinsurance()).isEqualTo(DEFAULT_MEDICALINSURANCE);
        assertThat(testMedic_Information.getSocialinsurance()).isEqualTo(DEFAULT_SOCIALINSURANCE);
        assertThat(testMedic_Information.getResponsibilityinsurance()).isEqualTo(DEFAULT_RESPONSIBILITYINSURANCE);
        assertThat(testMedic_Information.getsINconacyt()).isEqualTo(DEFAULT_S_I_NCONACYT);
        assertThat(testMedic_Information.getMedicalinnumber()).isEqualTo(DEFAULT_MEDICALINNUMBER);
        assertThat(testMedic_Information.getSocialinnumber()).isEqualTo(DEFAULT_SOCIALINNUMBER);
        assertThat(testMedic_Information.getResponsibilityinnumber()).isEqualTo(DEFAULT_RESPONSIBILITYINNUMBER);

        // Validate the Medic_Information in Elasticsearch
        Medic_Information medic_InformationEs = medic_InformationSearchRepository.findOne(testMedic_Information.getId());
        assertThat(medic_InformationEs).isEqualToComparingFieldByField(testMedic_Information);
    }

    @Test
    @Transactional
    public void createMedic_InformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medic_InformationRepository.findAll().size();

        // Create the Medic_Information with an existing ID
        medic_Information.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedic_InformationMockMvc.perform(post("/api/medic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medic_Information)))
            .andExpect(status().isBadRequest());

        // Validate the Medic_Information in the database
        List<Medic_Information> medic_InformationList = medic_InformationRepository.findAll();
        assertThat(medic_InformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedic_Informations() throws Exception {
        // Initialize the database
        medic_InformationRepository.saveAndFlush(medic_Information);

        // Get all the medic_InformationList
        restMedic_InformationMockMvc.perform(get("/api/medic-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medic_Information.getId().intValue())))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].extnumber").value(hasItem(DEFAULT_EXTNUMBER.toString())))
            .andExpect(jsonPath("$.[*].intnumber").value(hasItem(DEFAULT_INTNUMBER.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].snapchat").value(hasItem(DEFAULT_SNAPCHAT.toString())))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())))
            .andExpect(jsonPath("$.[*].recomended").value(hasItem(DEFAULT_RECOMENDED.toString())))
            .andExpect(jsonPath("$.[*].cvuconacyt").value(hasItem(DEFAULT_CVUCONACYT.toString())))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA.toString())))
            .andExpect(jsonPath("$.[*].cedulaesp").value(hasItem(DEFAULT_CEDULAESP.toString())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].externalhospitals").value(hasItem(DEFAULT_EXTERNALHOSPITALS.toString())))
            .andExpect(jsonPath("$.[*].subid").value(hasItem(DEFAULT_SUBID)))
            .andExpect(jsonPath("$.[*].highid").value(hasItem(DEFAULT_HIGHID)))
            .andExpect(jsonPath("$.[*].medicalinsurance").value(hasItem(DEFAULT_MEDICALINSURANCE)))
            .andExpect(jsonPath("$.[*].socialinsurance").value(hasItem(DEFAULT_SOCIALINSURANCE)))
            .andExpect(jsonPath("$.[*].responsibilityinsurance").value(hasItem(DEFAULT_RESPONSIBILITYINSURANCE.toString())))
            .andExpect(jsonPath("$.[*].sINconacyt").value(hasItem(DEFAULT_S_I_NCONACYT.toString())))
            .andExpect(jsonPath("$.[*].medicalinnumber").value(hasItem(DEFAULT_MEDICALINNUMBER.toString())))
            .andExpect(jsonPath("$.[*].socialinnumber").value(hasItem(DEFAULT_SOCIALINNUMBER.toString())))
            .andExpect(jsonPath("$.[*].responsibilityinnumber").value(hasItem(DEFAULT_RESPONSIBILITYINNUMBER.toString())));
    }

    @Test
    @Transactional
    public void getMedic_Information() throws Exception {
        // Initialize the database
        medic_InformationRepository.saveAndFlush(medic_Information);

        // Get the medic_Information
        restMedic_InformationMockMvc.perform(get("/api/medic-informations/{id}", medic_Information.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medic_Information.getId().intValue()))
            .andExpect(jsonPath("$.cp").value(DEFAULT_CP))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.extnumber").value(DEFAULT_EXTNUMBER.toString()))
            .andExpect(jsonPath("$.intnumber").value(DEFAULT_INTNUMBER.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.snapchat").value(DEFAULT_SNAPCHAT.toString()))
            .andExpect(jsonPath("$.linkedin").value(DEFAULT_LINKEDIN.toString()))
            .andExpect(jsonPath("$.vine").value(DEFAULT_VINE.toString()))
            .andExpect(jsonPath("$.recomended").value(DEFAULT_RECOMENDED.toString()))
            .andExpect(jsonPath("$.cvuconacyt").value(DEFAULT_CVUCONACYT.toString()))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA.toString()))
            .andExpect(jsonPath("$.cedulaesp").value(DEFAULT_CEDULAESP.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.externalhospitals").value(DEFAULT_EXTERNALHOSPITALS.toString()))
            .andExpect(jsonPath("$.subid").value(DEFAULT_SUBID))
            .andExpect(jsonPath("$.highid").value(DEFAULT_HIGHID))
            .andExpect(jsonPath("$.medicalinsurance").value(DEFAULT_MEDICALINSURANCE))
            .andExpect(jsonPath("$.socialinsurance").value(DEFAULT_SOCIALINSURANCE))
            .andExpect(jsonPath("$.responsibilityinsurance").value(DEFAULT_RESPONSIBILITYINSURANCE.toString()))
            .andExpect(jsonPath("$.sINconacyt").value(DEFAULT_S_I_NCONACYT.toString()))
            .andExpect(jsonPath("$.medicalinnumber").value(DEFAULT_MEDICALINNUMBER.toString()))
            .andExpect(jsonPath("$.socialinnumber").value(DEFAULT_SOCIALINNUMBER.toString()))
            .andExpect(jsonPath("$.responsibilityinnumber").value(DEFAULT_RESPONSIBILITYINNUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedic_Information() throws Exception {
        // Get the medic_Information
        restMedic_InformationMockMvc.perform(get("/api/medic-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedic_Information() throws Exception {
        // Initialize the database
        medic_InformationRepository.saveAndFlush(medic_Information);
        medic_InformationSearchRepository.save(medic_Information);
        int databaseSizeBeforeUpdate = medic_InformationRepository.findAll().size();

        // Update the medic_Information
        Medic_Information updatedMedic_Information = medic_InformationRepository.findOne(medic_Information.getId());
        updatedMedic_Information
            .cp(UPDATED_CP)
            .street(UPDATED_STREET)
            .extnumber(UPDATED_EXTNUMBER)
            .intnumber(UPDATED_INTNUMBER)
            .phone1(UPDATED_PHONE_1)
            .phone2(UPDATED_PHONE_2)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM)
            .snapchat(UPDATED_SNAPCHAT)
            .linkedin(UPDATED_LINKEDIN)
            .vine(UPDATED_VINE)
            .recomended(UPDATED_RECOMENDED)
            .cvuconacyt(UPDATED_CVUCONACYT)
            .cedula(UPDATED_CEDULA)
            .cedulaesp(UPDATED_CEDULAESP)
            .curp(UPDATED_CURP)
            .rfc(UPDATED_RFC)
            .birthday(UPDATED_BIRTHDAY)
            .externalhospitals(UPDATED_EXTERNALHOSPITALS)
            .subid(UPDATED_SUBID)
            .highid(UPDATED_HIGHID)
            .medicalinsurance(UPDATED_MEDICALINSURANCE)
            .socialinsurance(UPDATED_SOCIALINSURANCE)
            .responsibilityinsurance(UPDATED_RESPONSIBILITYINSURANCE)
            .sINconacyt(UPDATED_S_I_NCONACYT)
            .medicalinnumber(UPDATED_MEDICALINNUMBER)
            .socialinnumber(UPDATED_SOCIALINNUMBER)
            .responsibilityinnumber(UPDATED_RESPONSIBILITYINNUMBER);

        restMedic_InformationMockMvc.perform(put("/api/medic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedic_Information)))
            .andExpect(status().isOk());

        // Validate the Medic_Information in the database
        List<Medic_Information> medic_InformationList = medic_InformationRepository.findAll();
        assertThat(medic_InformationList).hasSize(databaseSizeBeforeUpdate);
        Medic_Information testMedic_Information = medic_InformationList.get(medic_InformationList.size() - 1);
        assertThat(testMedic_Information.getCp()).isEqualTo(UPDATED_CP);
        assertThat(testMedic_Information.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testMedic_Information.getExtnumber()).isEqualTo(UPDATED_EXTNUMBER);
        assertThat(testMedic_Information.getIntnumber()).isEqualTo(UPDATED_INTNUMBER);
        assertThat(testMedic_Information.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testMedic_Information.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testMedic_Information.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testMedic_Information.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
        assertThat(testMedic_Information.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testMedic_Information.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testMedic_Information.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testMedic_Information.getSnapchat()).isEqualTo(UPDATED_SNAPCHAT);
        assertThat(testMedic_Information.getLinkedin()).isEqualTo(UPDATED_LINKEDIN);
        assertThat(testMedic_Information.getVine()).isEqualTo(UPDATED_VINE);
        assertThat(testMedic_Information.getRecomended()).isEqualTo(UPDATED_RECOMENDED);
        assertThat(testMedic_Information.getCvuconacyt()).isEqualTo(UPDATED_CVUCONACYT);
        assertThat(testMedic_Information.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testMedic_Information.getCedulaesp()).isEqualTo(UPDATED_CEDULAESP);
        assertThat(testMedic_Information.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testMedic_Information.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testMedic_Information.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testMedic_Information.getExternalhospitals()).isEqualTo(UPDATED_EXTERNALHOSPITALS);
        assertThat(testMedic_Information.getSubid()).isEqualTo(UPDATED_SUBID);
        assertThat(testMedic_Information.getHighid()).isEqualTo(UPDATED_HIGHID);
        assertThat(testMedic_Information.getMedicalinsurance()).isEqualTo(UPDATED_MEDICALINSURANCE);
        assertThat(testMedic_Information.getSocialinsurance()).isEqualTo(UPDATED_SOCIALINSURANCE);
        assertThat(testMedic_Information.getResponsibilityinsurance()).isEqualTo(UPDATED_RESPONSIBILITYINSURANCE);
        assertThat(testMedic_Information.getsINconacyt()).isEqualTo(UPDATED_S_I_NCONACYT);
        assertThat(testMedic_Information.getMedicalinnumber()).isEqualTo(UPDATED_MEDICALINNUMBER);
        assertThat(testMedic_Information.getSocialinnumber()).isEqualTo(UPDATED_SOCIALINNUMBER);
        assertThat(testMedic_Information.getResponsibilityinnumber()).isEqualTo(UPDATED_RESPONSIBILITYINNUMBER);

        // Validate the Medic_Information in Elasticsearch
        Medic_Information medic_InformationEs = medic_InformationSearchRepository.findOne(testMedic_Information.getId());
        assertThat(medic_InformationEs).isEqualToComparingFieldByField(testMedic_Information);
    }

    @Test
    @Transactional
    public void updateNonExistingMedic_Information() throws Exception {
        int databaseSizeBeforeUpdate = medic_InformationRepository.findAll().size();

        // Create the Medic_Information

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedic_InformationMockMvc.perform(put("/api/medic-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medic_Information)))
            .andExpect(status().isCreated());

        // Validate the Medic_Information in the database
        List<Medic_Information> medic_InformationList = medic_InformationRepository.findAll();
        assertThat(medic_InformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedic_Information() throws Exception {
        // Initialize the database
        medic_InformationRepository.saveAndFlush(medic_Information);
        medic_InformationSearchRepository.save(medic_Information);
        int databaseSizeBeforeDelete = medic_InformationRepository.findAll().size();

        // Get the medic_Information
        restMedic_InformationMockMvc.perform(delete("/api/medic-informations/{id}", medic_Information.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medic_InformationExistsInEs = medic_InformationSearchRepository.exists(medic_Information.getId());
        assertThat(medic_InformationExistsInEs).isFalse();

        // Validate the database is empty
        List<Medic_Information> medic_InformationList = medic_InformationRepository.findAll();
        assertThat(medic_InformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedic_Information() throws Exception {
        // Initialize the database
        medic_InformationRepository.saveAndFlush(medic_Information);
        medic_InformationSearchRepository.save(medic_Information);

        // Search the medic_Information
        restMedic_InformationMockMvc.perform(get("/api/_search/medic-informations?query=id:" + medic_Information.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medic_Information.getId().intValue())))
            .andExpect(jsonPath("$.[*].cp").value(hasItem(DEFAULT_CP)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
            .andExpect(jsonPath("$.[*].extnumber").value(hasItem(DEFAULT_EXTNUMBER.toString())))
            .andExpect(jsonPath("$.[*].intnumber").value(hasItem(DEFAULT_INTNUMBER.toString())))
            .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
            .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].snapchat").value(hasItem(DEFAULT_SNAPCHAT.toString())))
            .andExpect(jsonPath("$.[*].linkedin").value(hasItem(DEFAULT_LINKEDIN.toString())))
            .andExpect(jsonPath("$.[*].vine").value(hasItem(DEFAULT_VINE.toString())))
            .andExpect(jsonPath("$.[*].recomended").value(hasItem(DEFAULT_RECOMENDED.toString())))
            .andExpect(jsonPath("$.[*].cvuconacyt").value(hasItem(DEFAULT_CVUCONACYT.toString())))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA.toString())))
            .andExpect(jsonPath("$.[*].cedulaesp").value(hasItem(DEFAULT_CEDULAESP.toString())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
            .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].externalhospitals").value(hasItem(DEFAULT_EXTERNALHOSPITALS.toString())))
            .andExpect(jsonPath("$.[*].subid").value(hasItem(DEFAULT_SUBID)))
            .andExpect(jsonPath("$.[*].highid").value(hasItem(DEFAULT_HIGHID)))
            .andExpect(jsonPath("$.[*].medicalinsurance").value(hasItem(DEFAULT_MEDICALINSURANCE)))
            .andExpect(jsonPath("$.[*].socialinsurance").value(hasItem(DEFAULT_SOCIALINSURANCE)))
            .andExpect(jsonPath("$.[*].responsibilityinsurance").value(hasItem(DEFAULT_RESPONSIBILITYINSURANCE.toString())))
            .andExpect(jsonPath("$.[*].sINconacyt").value(hasItem(DEFAULT_S_I_NCONACYT.toString())))
            .andExpect(jsonPath("$.[*].medicalinnumber").value(hasItem(DEFAULT_MEDICALINNUMBER.toString())))
            .andExpect(jsonPath("$.[*].socialinnumber").value(hasItem(DEFAULT_SOCIALINNUMBER.toString())))
            .andExpect(jsonPath("$.[*].responsibilityinnumber").value(hasItem(DEFAULT_RESPONSIBILITYINNUMBER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medic_Information.class);
        Medic_Information medic_Information1 = new Medic_Information();
        medic_Information1.setId(1L);
        Medic_Information medic_Information2 = new Medic_Information();
        medic_Information2.setId(medic_Information1.getId());
        assertThat(medic_Information1).isEqualTo(medic_Information2);
        medic_Information2.setId(2L);
        assertThat(medic_Information1).isNotEqualTo(medic_Information2);
        medic_Information1.setId(null);
        assertThat(medic_Information1).isNotEqualTo(medic_Information2);
    }
}
