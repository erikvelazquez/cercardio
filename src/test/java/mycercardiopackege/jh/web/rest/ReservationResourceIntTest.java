package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Reservation;
import mycercardiopackege.jh.repository.ReservationRepository;
import mycercardiopackege.jh.repository.search.ReservationSearchRepository;
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
 * Test class for the ReservationResource REST controller.
 *
 * @see ReservationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class ReservationResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEAT = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_CREATEDAT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDAT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_SYMTOMS = "AAAAAAAAAA";
    private static final String UPDATED_SYMTOMS = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAMENTS = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_COST = 1;
    private static final Integer UPDATED_COST = 2;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationSearchRepository reservationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReservationResource reservationResource = new ReservationResource(reservationRepository, reservationSearchRepository);
        this.restReservationMockMvc = MockMvcBuilders.standaloneSetup(reservationResource)
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
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .title(DEFAULT_TITLE)
            .note(DEFAULT_NOTE)
            .dateat(DEFAULT_DATEAT)
            .createdat(DEFAULT_CREATEDAT)
            .symtoms(DEFAULT_SYMTOMS)
            .medicaments(DEFAULT_MEDICAMENTS)
            .cost(DEFAULT_COST);
        return reservation;
    }

    @Before
    public void initTest() {
        reservationSearchRepository.deleteAll();
        reservation = createEntity(em);
    }

    @Test
    @Transactional
    public void createReservation() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate + 1);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testReservation.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testReservation.getDateat()).isEqualTo(DEFAULT_DATEAT);
        assertThat(testReservation.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testReservation.getSymtoms()).isEqualTo(DEFAULT_SYMTOMS);
        assertThat(testReservation.getMedicaments()).isEqualTo(DEFAULT_MEDICAMENTS);
        assertThat(testReservation.getCost()).isEqualTo(DEFAULT_COST);

        // Validate the Reservation in Elasticsearch
        Reservation reservationEs = reservationSearchRepository.findOne(testReservation.getId());
        assertThat(reservationEs).isEqualToComparingFieldByField(testReservation);
    }

    @Test
    @Transactional
    public void createReservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reservationRepository.findAll().size();

        // Create the Reservation with an existing ID
        reservation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc.perform(post("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReservations() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc.perform(get("/api/reservations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].dateat").value(hasItem(DEFAULT_DATEAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].symtoms").value(hasItem(DEFAULT_SYMTOMS.toString())))
            .andExpect(jsonPath("$.[*].medicaments").value(hasItem(DEFAULT_MEDICAMENTS.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)));
    }

    @Test
    @Transactional
    public void getReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.dateat").value(DEFAULT_DATEAT.toString()))
            .andExpect(jsonPath("$.createdat").value(sameInstant(DEFAULT_CREATEDAT)))
            .andExpect(jsonPath("$.symtoms").value(DEFAULT_SYMTOMS.toString()))
            .andExpect(jsonPath("$.medicaments").value(DEFAULT_MEDICAMENTS.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST));
    }

    @Test
    @Transactional
    public void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get("/api/reservations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);
        reservationSearchRepository.save(reservation);
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findOne(reservation.getId());
        updatedReservation
            .title(UPDATED_TITLE)
            .note(UPDATED_NOTE)
            .dateat(UPDATED_DATEAT)
            .createdat(UPDATED_CREATEDAT)
            .symtoms(UPDATED_SYMTOMS)
            .medicaments(UPDATED_MEDICAMENTS)
            .cost(UPDATED_COST);

        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReservation)))
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate);
        Reservation testReservation = reservationList.get(reservationList.size() - 1);
        assertThat(testReservation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testReservation.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testReservation.getDateat()).isEqualTo(UPDATED_DATEAT);
        assertThat(testReservation.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testReservation.getSymtoms()).isEqualTo(UPDATED_SYMTOMS);
        assertThat(testReservation.getMedicaments()).isEqualTo(UPDATED_MEDICAMENTS);
        assertThat(testReservation.getCost()).isEqualTo(UPDATED_COST);

        // Validate the Reservation in Elasticsearch
        Reservation reservationEs = reservationSearchRepository.findOne(testReservation.getId());
        assertThat(reservationEs).isEqualToComparingFieldByField(testReservation);
    }

    @Test
    @Transactional
    public void updateNonExistingReservation() throws Exception {
        int databaseSizeBeforeUpdate = reservationRepository.findAll().size();

        // Create the Reservation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReservationMockMvc.perform(put("/api/reservations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reservation)))
            .andExpect(status().isCreated());

        // Validate the Reservation in the database
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);
        reservationSearchRepository.save(reservation);
        int databaseSizeBeforeDelete = reservationRepository.findAll().size();

        // Get the reservation
        restReservationMockMvc.perform(delete("/api/reservations/{id}", reservation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reservationExistsInEs = reservationSearchRepository.exists(reservation.getId());
        assertThat(reservationExistsInEs).isFalse();

        // Validate the database is empty
        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReservation() throws Exception {
        // Initialize the database
        reservationRepository.saveAndFlush(reservation);
        reservationSearchRepository.save(reservation);

        // Search the reservation
        restReservationMockMvc.perform(get("/api/_search/reservations?query=id:" + reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].dateat").value(hasItem(DEFAULT_DATEAT.toString())))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(sameInstant(DEFAULT_CREATEDAT))))
            .andExpect(jsonPath("$.[*].symtoms").value(hasItem(DEFAULT_SYMTOMS.toString())))
            .andExpect(jsonPath("$.[*].medicaments").value(hasItem(DEFAULT_MEDICAMENTS.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservation.class);
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(reservation1.getId());
        assertThat(reservation1).isEqualTo(reservation2);
        reservation2.setId(2L);
        assertThat(reservation1).isNotEqualTo(reservation2);
        reservation1.setId(null);
        assertThat(reservation1).isNotEqualTo(reservation2);
    }
}
