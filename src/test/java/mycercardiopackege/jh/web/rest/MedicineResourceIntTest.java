package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Medicine;
import mycercardiopackege.jh.repository.MedicineRepository;
import mycercardiopackege.jh.repository.search.MedicineSearchRepository;
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
 * Test class for the MedicineResource REST controller.
 *
 * @see MedicineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class MedicineResourceIntTest {

    private static final String DEFAULT_TIPOINSUMO = "AAAAAAAAAA";
    private static final String UPDATED_TIPOINSUMO = "BBBBBBBBBB";

    private static final String DEFAULT_DENTROFUERACUADRO = "AAAAAAAAAA";
    private static final String UPDATED_DENTROFUERACUADRO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOGRUPOTERAPEUTICO = 1;
    private static final Integer UPDATED_NOGRUPOTERAPEUTICO = 2;

    private static final String DEFAULT_NOMBREGRUPOTERAPEUTICO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBREGRUPOTERAPEUTICO = "BBBBBBBBBB";

    private static final String DEFAULT_NIVELATENCION = "AAAAAAAAAA";
    private static final String UPDATED_NIVELATENCION = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVECODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CLAVECODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_SUBCLAVECODIGO = "AAAAAAAAAA";
    private static final String UPDATED_SUBCLAVECODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBREGENERICO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBREGENERICO = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAFARMACEUTICA = "AAAAAAAAAA";
    private static final String UPDATED_FORMAFARMACEUTICA = "BBBBBBBBBB";

    private static final String DEFAULT_CONCENTRACION = "AAAAAAAAAA";
    private static final String UPDATED_CONCENTRACION = "BBBBBBBBBB";

    private static final String DEFAULT_PRESENTACION = "AAAAAAAAAA";
    private static final String UPDATED_PRESENTACION = "BBBBBBBBBB";

    private static final String DEFAULT_PRINCIPALINDICACION = "AAAAAAAAAA";
    private static final String UPDATED_PRINCIPALINDICACION = "BBBBBBBBBB";

    private static final String DEFAULT_DEMASINDICACIONES = "AAAAAAAAAA";
    private static final String UPDATED_DEMASINDICACIONES = "BBBBBBBBBB";

    private static final String DEFAULT_TIPOACTUALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_TIPOACTUALIZACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOACTUALIZACION = "AAAAAAAAAA";
    private static final String UPDATED_NOACTUALIZACION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineSearchRepository medicineSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicineMockMvc;

    private Medicine medicine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicineResource medicineResource = new MedicineResource(medicineRepository, medicineSearchRepository);
        this.restMedicineMockMvc = MockMvcBuilders.standaloneSetup(medicineResource)
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
    public static Medicine createEntity(EntityManager em) {
        Medicine medicine = new Medicine()
            .tipoinsumo(DEFAULT_TIPOINSUMO)
            .dentrofueracuadro(DEFAULT_DENTROFUERACUADRO)
            .nogrupoterapeutico(DEFAULT_NOGRUPOTERAPEUTICO)
            .nombregrupoterapeutico(DEFAULT_NOMBREGRUPOTERAPEUTICO)
            .nivelatencion(DEFAULT_NIVELATENCION)
            .clavecodigo(DEFAULT_CLAVECODIGO)
            .subclavecodigo(DEFAULT_SUBCLAVECODIGO)
            .nombregenerico(DEFAULT_NOMBREGENERICO)
            .formafarmaceutica(DEFAULT_FORMAFARMACEUTICA)
            .concentracion(DEFAULT_CONCENTRACION)
            .presentacion(DEFAULT_PRESENTACION)
            .principalindicacion(DEFAULT_PRINCIPALINDICACION)
            .demasindicaciones(DEFAULT_DEMASINDICACIONES)
            .tipoactualizacion(DEFAULT_TIPOACTUALIZACION)
            .noactualizacion(DEFAULT_NOACTUALIZACION)
            .descripcion(DEFAULT_DESCRIPCION);
        return medicine;
    }

    @Before
    public void initTest() {
        medicineSearchRepository.deleteAll();
        medicine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicine() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // Create the Medicine
        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isCreated());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate + 1);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getTipoinsumo()).isEqualTo(DEFAULT_TIPOINSUMO);
        assertThat(testMedicine.getDentrofueracuadro()).isEqualTo(DEFAULT_DENTROFUERACUADRO);
        assertThat(testMedicine.getNogrupoterapeutico()).isEqualTo(DEFAULT_NOGRUPOTERAPEUTICO);
        assertThat(testMedicine.getNombregrupoterapeutico()).isEqualTo(DEFAULT_NOMBREGRUPOTERAPEUTICO);
        assertThat(testMedicine.getNivelatencion()).isEqualTo(DEFAULT_NIVELATENCION);
        assertThat(testMedicine.getClavecodigo()).isEqualTo(DEFAULT_CLAVECODIGO);
        assertThat(testMedicine.getSubclavecodigo()).isEqualTo(DEFAULT_SUBCLAVECODIGO);
        assertThat(testMedicine.getNombregenerico()).isEqualTo(DEFAULT_NOMBREGENERICO);
        assertThat(testMedicine.getFormafarmaceutica()).isEqualTo(DEFAULT_FORMAFARMACEUTICA);
        assertThat(testMedicine.getConcentracion()).isEqualTo(DEFAULT_CONCENTRACION);
        assertThat(testMedicine.getPresentacion()).isEqualTo(DEFAULT_PRESENTACION);
        assertThat(testMedicine.getPrincipalindicacion()).isEqualTo(DEFAULT_PRINCIPALINDICACION);
        assertThat(testMedicine.getDemasindicaciones()).isEqualTo(DEFAULT_DEMASINDICACIONES);
        assertThat(testMedicine.getTipoactualizacion()).isEqualTo(DEFAULT_TIPOACTUALIZACION);
        assertThat(testMedicine.getNoactualizacion()).isEqualTo(DEFAULT_NOACTUALIZACION);
        assertThat(testMedicine.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the Medicine in Elasticsearch
        Medicine medicineEs = medicineSearchRepository.findOne(testMedicine.getId());
        assertThat(medicineEs).isEqualToComparingFieldByField(testMedicine);
    }

    @Test
    @Transactional
    public void createMedicineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // Create the Medicine with an existing ID
        medicine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicines() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList
        restMedicineMockMvc.perform(get("/api/medicines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoinsumo").value(hasItem(DEFAULT_TIPOINSUMO.toString())))
            .andExpect(jsonPath("$.[*].dentrofueracuadro").value(hasItem(DEFAULT_DENTROFUERACUADRO.toString())))
            .andExpect(jsonPath("$.[*].nogrupoterapeutico").value(hasItem(DEFAULT_NOGRUPOTERAPEUTICO)))
            .andExpect(jsonPath("$.[*].nombregrupoterapeutico").value(hasItem(DEFAULT_NOMBREGRUPOTERAPEUTICO.toString())))
            .andExpect(jsonPath("$.[*].nivelatencion").value(hasItem(DEFAULT_NIVELATENCION.toString())))
            .andExpect(jsonPath("$.[*].clavecodigo").value(hasItem(DEFAULT_CLAVECODIGO.toString())))
            .andExpect(jsonPath("$.[*].subclavecodigo").value(hasItem(DEFAULT_SUBCLAVECODIGO.toString())))
            .andExpect(jsonPath("$.[*].nombregenerico").value(hasItem(DEFAULT_NOMBREGENERICO.toString())))
            .andExpect(jsonPath("$.[*].formafarmaceutica").value(hasItem(DEFAULT_FORMAFARMACEUTICA.toString())))
            .andExpect(jsonPath("$.[*].concentracion").value(hasItem(DEFAULT_CONCENTRACION.toString())))
            .andExpect(jsonPath("$.[*].presentacion").value(hasItem(DEFAULT_PRESENTACION.toString())))
            .andExpect(jsonPath("$.[*].principalindicacion").value(hasItem(DEFAULT_PRINCIPALINDICACION.toString())))
            .andExpect(jsonPath("$.[*].demasindicaciones").value(hasItem(DEFAULT_DEMASINDICACIONES.toString())))
            .andExpect(jsonPath("$.[*].tipoactualizacion").value(hasItem(DEFAULT_TIPOACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].noactualizacion").value(hasItem(DEFAULT_NOACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get the medicine
        restMedicineMockMvc.perform(get("/api/medicines/{id}", medicine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicine.getId().intValue()))
            .andExpect(jsonPath("$.tipoinsumo").value(DEFAULT_TIPOINSUMO.toString()))
            .andExpect(jsonPath("$.dentrofueracuadro").value(DEFAULT_DENTROFUERACUADRO.toString()))
            .andExpect(jsonPath("$.nogrupoterapeutico").value(DEFAULT_NOGRUPOTERAPEUTICO))
            .andExpect(jsonPath("$.nombregrupoterapeutico").value(DEFAULT_NOMBREGRUPOTERAPEUTICO.toString()))
            .andExpect(jsonPath("$.nivelatencion").value(DEFAULT_NIVELATENCION.toString()))
            .andExpect(jsonPath("$.clavecodigo").value(DEFAULT_CLAVECODIGO.toString()))
            .andExpect(jsonPath("$.subclavecodigo").value(DEFAULT_SUBCLAVECODIGO.toString()))
            .andExpect(jsonPath("$.nombregenerico").value(DEFAULT_NOMBREGENERICO.toString()))
            .andExpect(jsonPath("$.formafarmaceutica").value(DEFAULT_FORMAFARMACEUTICA.toString()))
            .andExpect(jsonPath("$.concentracion").value(DEFAULT_CONCENTRACION.toString()))
            .andExpect(jsonPath("$.presentacion").value(DEFAULT_PRESENTACION.toString()))
            .andExpect(jsonPath("$.principalindicacion").value(DEFAULT_PRINCIPALINDICACION.toString()))
            .andExpect(jsonPath("$.demasindicaciones").value(DEFAULT_DEMASINDICACIONES.toString()))
            .andExpect(jsonPath("$.tipoactualizacion").value(DEFAULT_TIPOACTUALIZACION.toString()))
            .andExpect(jsonPath("$.noactualizacion").value(DEFAULT_NOACTUALIZACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicine() throws Exception {
        // Get the medicine
        restMedicineMockMvc.perform(get("/api/medicines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);
        medicineSearchRepository.save(medicine);
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Update the medicine
        Medicine updatedMedicine = medicineRepository.findOne(medicine.getId());
        updatedMedicine
            .tipoinsumo(UPDATED_TIPOINSUMO)
            .dentrofueracuadro(UPDATED_DENTROFUERACUADRO)
            .nogrupoterapeutico(UPDATED_NOGRUPOTERAPEUTICO)
            .nombregrupoterapeutico(UPDATED_NOMBREGRUPOTERAPEUTICO)
            .nivelatencion(UPDATED_NIVELATENCION)
            .clavecodigo(UPDATED_CLAVECODIGO)
            .subclavecodigo(UPDATED_SUBCLAVECODIGO)
            .nombregenerico(UPDATED_NOMBREGENERICO)
            .formafarmaceutica(UPDATED_FORMAFARMACEUTICA)
            .concentracion(UPDATED_CONCENTRACION)
            .presentacion(UPDATED_PRESENTACION)
            .principalindicacion(UPDATED_PRINCIPALINDICACION)
            .demasindicaciones(UPDATED_DEMASINDICACIONES)
            .tipoactualizacion(UPDATED_TIPOACTUALIZACION)
            .noactualizacion(UPDATED_NOACTUALIZACION)
            .descripcion(UPDATED_DESCRIPCION);

        restMedicineMockMvc.perform(put("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicine)))
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getTipoinsumo()).isEqualTo(UPDATED_TIPOINSUMO);
        assertThat(testMedicine.getDentrofueracuadro()).isEqualTo(UPDATED_DENTROFUERACUADRO);
        assertThat(testMedicine.getNogrupoterapeutico()).isEqualTo(UPDATED_NOGRUPOTERAPEUTICO);
        assertThat(testMedicine.getNombregrupoterapeutico()).isEqualTo(UPDATED_NOMBREGRUPOTERAPEUTICO);
        assertThat(testMedicine.getNivelatencion()).isEqualTo(UPDATED_NIVELATENCION);
        assertThat(testMedicine.getClavecodigo()).isEqualTo(UPDATED_CLAVECODIGO);
        assertThat(testMedicine.getSubclavecodigo()).isEqualTo(UPDATED_SUBCLAVECODIGO);
        assertThat(testMedicine.getNombregenerico()).isEqualTo(UPDATED_NOMBREGENERICO);
        assertThat(testMedicine.getFormafarmaceutica()).isEqualTo(UPDATED_FORMAFARMACEUTICA);
        assertThat(testMedicine.getConcentracion()).isEqualTo(UPDATED_CONCENTRACION);
        assertThat(testMedicine.getPresentacion()).isEqualTo(UPDATED_PRESENTACION);
        assertThat(testMedicine.getPrincipalindicacion()).isEqualTo(UPDATED_PRINCIPALINDICACION);
        assertThat(testMedicine.getDemasindicaciones()).isEqualTo(UPDATED_DEMASINDICACIONES);
        assertThat(testMedicine.getTipoactualizacion()).isEqualTo(UPDATED_TIPOACTUALIZACION);
        assertThat(testMedicine.getNoactualizacion()).isEqualTo(UPDATED_NOACTUALIZACION);
        assertThat(testMedicine.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the Medicine in Elasticsearch
        Medicine medicineEs = medicineSearchRepository.findOne(testMedicine.getId());
        assertThat(medicineEs).isEqualToComparingFieldByField(testMedicine);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Create the Medicine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicineMockMvc.perform(put("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isCreated());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);
        medicineSearchRepository.save(medicine);
        int databaseSizeBeforeDelete = medicineRepository.findAll().size();

        // Get the medicine
        restMedicineMockMvc.perform(delete("/api/medicines/{id}", medicine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medicineExistsInEs = medicineSearchRepository.exists(medicine.getId());
        assertThat(medicineExistsInEs).isFalse();

        // Validate the database is empty
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);
        medicineSearchRepository.save(medicine);

        // Search the medicine
        restMedicineMockMvc.perform(get("/api/_search/medicines?query=id:" + medicine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoinsumo").value(hasItem(DEFAULT_TIPOINSUMO.toString())))
            .andExpect(jsonPath("$.[*].dentrofueracuadro").value(hasItem(DEFAULT_DENTROFUERACUADRO.toString())))
            .andExpect(jsonPath("$.[*].nogrupoterapeutico").value(hasItem(DEFAULT_NOGRUPOTERAPEUTICO)))
            .andExpect(jsonPath("$.[*].nombregrupoterapeutico").value(hasItem(DEFAULT_NOMBREGRUPOTERAPEUTICO.toString())))
            .andExpect(jsonPath("$.[*].nivelatencion").value(hasItem(DEFAULT_NIVELATENCION.toString())))
            .andExpect(jsonPath("$.[*].clavecodigo").value(hasItem(DEFAULT_CLAVECODIGO.toString())))
            .andExpect(jsonPath("$.[*].subclavecodigo").value(hasItem(DEFAULT_SUBCLAVECODIGO.toString())))
            .andExpect(jsonPath("$.[*].nombregenerico").value(hasItem(DEFAULT_NOMBREGENERICO.toString())))
            .andExpect(jsonPath("$.[*].formafarmaceutica").value(hasItem(DEFAULT_FORMAFARMACEUTICA.toString())))
            .andExpect(jsonPath("$.[*].concentracion").value(hasItem(DEFAULT_CONCENTRACION.toString())))
            .andExpect(jsonPath("$.[*].presentacion").value(hasItem(DEFAULT_PRESENTACION.toString())))
            .andExpect(jsonPath("$.[*].principalindicacion").value(hasItem(DEFAULT_PRINCIPALINDICACION.toString())))
            .andExpect(jsonPath("$.[*].demasindicaciones").value(hasItem(DEFAULT_DEMASINDICACIONES.toString())))
            .andExpect(jsonPath("$.[*].tipoactualizacion").value(hasItem(DEFAULT_TIPOACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].noactualizacion").value(hasItem(DEFAULT_NOACTUALIZACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicine.class);
        Medicine medicine1 = new Medicine();
        medicine1.setId(1L);
        Medicine medicine2 = new Medicine();
        medicine2.setId(medicine1.getId());
        assertThat(medicine1).isEqualTo(medicine2);
        medicine2.setId(2L);
        assertThat(medicine1).isNotEqualTo(medicine2);
        medicine1.setId(null);
        assertThat(medicine1).isNotEqualTo(medicine2);
    }
}
