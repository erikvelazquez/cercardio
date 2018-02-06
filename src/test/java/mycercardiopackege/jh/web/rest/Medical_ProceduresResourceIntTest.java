package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Medical_Procedures;
import mycercardiopackege.jh.repository.Medical_ProceduresRepository;
import mycercardiopackege.jh.repository.search.Medical_ProceduresSearchRepository;
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
 * Test class for the Medical_ProceduresResource REST controller.
 *
 * @see Medical_ProceduresResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class Medical_ProceduresResourceIntTest {

    private static final String DEFAULT_CATALOGKEY = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGKEY = "BBBBBBBBBB";

    private static final String DEFAULT_PRONOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_PRONOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PROCVEEDIA = "AAAAAAAAAA";
    private static final String UPDATED_PROCVEEDIA = "BBBBBBBBBB";

    private static final String DEFAULT_PROEDADIA = "AAAAAAAAAA";
    private static final String UPDATED_PROEDADIA = "BBBBBBBBBB";

    private static final String DEFAULT_PROCVEEDFA = "AAAAAAAAAA";
    private static final String UPDATED_PROCVEEDFA = "BBBBBBBBBB";

    private static final String DEFAULT_PROEDADFA = "AAAAAAAAAA";
    private static final String UPDATED_PROEDADFA = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEXTYPE = 1;
    private static final Integer UPDATED_SEXTYPE = 2;

    private static final Integer DEFAULT_PORNIVELA = 1;
    private static final Integer UPDATED_PORNIVELA = 2;

    private static final String DEFAULT_PROCEDIMIENTOTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDIMIENTOTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PROPRINCIPAL = "AAAAAAAAAA";
    private static final String UPDATED_PROPRINCIPAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROCAPITULO = 1;
    private static final Integer UPDATED_PROCAPITULO = 2;

    private static final Integer DEFAULT_PROSECCION = 1;
    private static final Integer UPDATED_PROSECCION = 2;

    private static final Integer DEFAULT_PROCATEGORIA = 1;
    private static final Integer UPDATED_PROCATEGORIA = 2;

    private static final Integer DEFAULT_PROSUBCATEG = 1;
    private static final Integer UPDATED_PROSUBCATEG = 2;

    private static final Integer DEFAULT_PROGRUPOLC = 1;
    private static final Integer UPDATED_PROGRUPOLC = 2;

    private static final Integer DEFAULT_PROESCAUSES = 1;
    private static final Integer UPDATED_PROESCAUSES = 2;

    private static final Integer DEFAULT_PRONUMCAUSES = 1;
    private static final Integer UPDATED_PRONUMCAUSES = 2;

    @Autowired
    private Medical_ProceduresRepository medical_ProceduresRepository;

    @Autowired
    private Medical_ProceduresSearchRepository medical_ProceduresSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedical_ProceduresMockMvc;

    private Medical_Procedures medical_Procedures;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Medical_ProceduresResource medical_ProceduresResource = new Medical_ProceduresResource(medical_ProceduresRepository, medical_ProceduresSearchRepository);
        this.restMedical_ProceduresMockMvc = MockMvcBuilders.standaloneSetup(medical_ProceduresResource)
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
    public static Medical_Procedures createEntity(EntityManager em) {
        Medical_Procedures medical_Procedures = new Medical_Procedures()
            .catalogkey(DEFAULT_CATALOGKEY)
            .pronombre(DEFAULT_PRONOMBRE)
            .procveedia(DEFAULT_PROCVEEDIA)
            .proedadia(DEFAULT_PROEDADIA)
            .procveedfa(DEFAULT_PROCVEEDFA)
            .proedadfa(DEFAULT_PROEDADFA)
            .sextype(DEFAULT_SEXTYPE)
            .pornivela(DEFAULT_PORNIVELA)
            .procedimientotype(DEFAULT_PROCEDIMIENTOTYPE)
            .proprincipal(DEFAULT_PROPRINCIPAL)
            .procapitulo(DEFAULT_PROCAPITULO)
            .proseccion(DEFAULT_PROSECCION)
            .procategoria(DEFAULT_PROCATEGORIA)
            .prosubcateg(DEFAULT_PROSUBCATEG)
            .progrupolc(DEFAULT_PROGRUPOLC)
            .proescauses(DEFAULT_PROESCAUSES)
            .pronumcauses(DEFAULT_PRONUMCAUSES);
        return medical_Procedures;
    }

    @Before
    public void initTest() {
        medical_ProceduresSearchRepository.deleteAll();
        medical_Procedures = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedical_Procedures() throws Exception {
        int databaseSizeBeforeCreate = medical_ProceduresRepository.findAll().size();

        // Create the Medical_Procedures
        restMedical_ProceduresMockMvc.perform(post("/api/medical-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medical_Procedures)))
            .andExpect(status().isCreated());

        // Validate the Medical_Procedures in the database
        List<Medical_Procedures> medical_ProceduresList = medical_ProceduresRepository.findAll();
        assertThat(medical_ProceduresList).hasSize(databaseSizeBeforeCreate + 1);
        Medical_Procedures testMedical_Procedures = medical_ProceduresList.get(medical_ProceduresList.size() - 1);
        assertThat(testMedical_Procedures.getCatalogkey()).isEqualTo(DEFAULT_CATALOGKEY);
        assertThat(testMedical_Procedures.getPronombre()).isEqualTo(DEFAULT_PRONOMBRE);
        assertThat(testMedical_Procedures.getProcveedia()).isEqualTo(DEFAULT_PROCVEEDIA);
        assertThat(testMedical_Procedures.getProedadia()).isEqualTo(DEFAULT_PROEDADIA);
        assertThat(testMedical_Procedures.getProcveedfa()).isEqualTo(DEFAULT_PROCVEEDFA);
        assertThat(testMedical_Procedures.getProedadfa()).isEqualTo(DEFAULT_PROEDADFA);
        assertThat(testMedical_Procedures.getSextype()).isEqualTo(DEFAULT_SEXTYPE);
        assertThat(testMedical_Procedures.getPornivela()).isEqualTo(DEFAULT_PORNIVELA);
        assertThat(testMedical_Procedures.getProcedimientotype()).isEqualTo(DEFAULT_PROCEDIMIENTOTYPE);
        assertThat(testMedical_Procedures.getProprincipal()).isEqualTo(DEFAULT_PROPRINCIPAL);
        assertThat(testMedical_Procedures.getProcapitulo()).isEqualTo(DEFAULT_PROCAPITULO);
        assertThat(testMedical_Procedures.getProseccion()).isEqualTo(DEFAULT_PROSECCION);
        assertThat(testMedical_Procedures.getProcategoria()).isEqualTo(DEFAULT_PROCATEGORIA);
        assertThat(testMedical_Procedures.getProsubcateg()).isEqualTo(DEFAULT_PROSUBCATEG);
        assertThat(testMedical_Procedures.getProgrupolc()).isEqualTo(DEFAULT_PROGRUPOLC);
        assertThat(testMedical_Procedures.getProescauses()).isEqualTo(DEFAULT_PROESCAUSES);
        assertThat(testMedical_Procedures.getPronumcauses()).isEqualTo(DEFAULT_PRONUMCAUSES);

        // Validate the Medical_Procedures in Elasticsearch
        Medical_Procedures medical_ProceduresEs = medical_ProceduresSearchRepository.findOne(testMedical_Procedures.getId());
        assertThat(medical_ProceduresEs).isEqualToComparingFieldByField(testMedical_Procedures);
    }

    @Test
    @Transactional
    public void createMedical_ProceduresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medical_ProceduresRepository.findAll().size();

        // Create the Medical_Procedures with an existing ID
        medical_Procedures.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedical_ProceduresMockMvc.perform(post("/api/medical-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medical_Procedures)))
            .andExpect(status().isBadRequest());

        // Validate the Medical_Procedures in the database
        List<Medical_Procedures> medical_ProceduresList = medical_ProceduresRepository.findAll();
        assertThat(medical_ProceduresList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedical_Procedures() throws Exception {
        // Initialize the database
        medical_ProceduresRepository.saveAndFlush(medical_Procedures);

        // Get all the medical_ProceduresList
        restMedical_ProceduresMockMvc.perform(get("/api/medical-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medical_Procedures.getId().intValue())))
            .andExpect(jsonPath("$.[*].catalogkey").value(hasItem(DEFAULT_CATALOGKEY.toString())))
            .andExpect(jsonPath("$.[*].pronombre").value(hasItem(DEFAULT_PRONOMBRE.toString())))
            .andExpect(jsonPath("$.[*].procveedia").value(hasItem(DEFAULT_PROCVEEDIA.toString())))
            .andExpect(jsonPath("$.[*].proedadia").value(hasItem(DEFAULT_PROEDADIA.toString())))
            .andExpect(jsonPath("$.[*].procveedfa").value(hasItem(DEFAULT_PROCVEEDFA.toString())))
            .andExpect(jsonPath("$.[*].proedadfa").value(hasItem(DEFAULT_PROEDADFA.toString())))
            .andExpect(jsonPath("$.[*].sextype").value(hasItem(DEFAULT_SEXTYPE)))
            .andExpect(jsonPath("$.[*].pornivela").value(hasItem(DEFAULT_PORNIVELA)))
            .andExpect(jsonPath("$.[*].procedimientotype").value(hasItem(DEFAULT_PROCEDIMIENTOTYPE.toString())))
            .andExpect(jsonPath("$.[*].proprincipal").value(hasItem(DEFAULT_PROPRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].procapitulo").value(hasItem(DEFAULT_PROCAPITULO)))
            .andExpect(jsonPath("$.[*].proseccion").value(hasItem(DEFAULT_PROSECCION)))
            .andExpect(jsonPath("$.[*].procategoria").value(hasItem(DEFAULT_PROCATEGORIA)))
            .andExpect(jsonPath("$.[*].prosubcateg").value(hasItem(DEFAULT_PROSUBCATEG)))
            .andExpect(jsonPath("$.[*].progrupolc").value(hasItem(DEFAULT_PROGRUPOLC)))
            .andExpect(jsonPath("$.[*].proescauses").value(hasItem(DEFAULT_PROESCAUSES)))
            .andExpect(jsonPath("$.[*].pronumcauses").value(hasItem(DEFAULT_PRONUMCAUSES)));
    }

    @Test
    @Transactional
    public void getMedical_Procedures() throws Exception {
        // Initialize the database
        medical_ProceduresRepository.saveAndFlush(medical_Procedures);

        // Get the medical_Procedures
        restMedical_ProceduresMockMvc.perform(get("/api/medical-procedures/{id}", medical_Procedures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medical_Procedures.getId().intValue()))
            .andExpect(jsonPath("$.catalogkey").value(DEFAULT_CATALOGKEY.toString()))
            .andExpect(jsonPath("$.pronombre").value(DEFAULT_PRONOMBRE.toString()))
            .andExpect(jsonPath("$.procveedia").value(DEFAULT_PROCVEEDIA.toString()))
            .andExpect(jsonPath("$.proedadia").value(DEFAULT_PROEDADIA.toString()))
            .andExpect(jsonPath("$.procveedfa").value(DEFAULT_PROCVEEDFA.toString()))
            .andExpect(jsonPath("$.proedadfa").value(DEFAULT_PROEDADFA.toString()))
            .andExpect(jsonPath("$.sextype").value(DEFAULT_SEXTYPE))
            .andExpect(jsonPath("$.pornivela").value(DEFAULT_PORNIVELA))
            .andExpect(jsonPath("$.procedimientotype").value(DEFAULT_PROCEDIMIENTOTYPE.toString()))
            .andExpect(jsonPath("$.proprincipal").value(DEFAULT_PROPRINCIPAL.toString()))
            .andExpect(jsonPath("$.procapitulo").value(DEFAULT_PROCAPITULO))
            .andExpect(jsonPath("$.proseccion").value(DEFAULT_PROSECCION))
            .andExpect(jsonPath("$.procategoria").value(DEFAULT_PROCATEGORIA))
            .andExpect(jsonPath("$.prosubcateg").value(DEFAULT_PROSUBCATEG))
            .andExpect(jsonPath("$.progrupolc").value(DEFAULT_PROGRUPOLC))
            .andExpect(jsonPath("$.proescauses").value(DEFAULT_PROESCAUSES))
            .andExpect(jsonPath("$.pronumcauses").value(DEFAULT_PRONUMCAUSES));
    }

    @Test
    @Transactional
    public void getNonExistingMedical_Procedures() throws Exception {
        // Get the medical_Procedures
        restMedical_ProceduresMockMvc.perform(get("/api/medical-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedical_Procedures() throws Exception {
        // Initialize the database
        medical_ProceduresRepository.saveAndFlush(medical_Procedures);
        medical_ProceduresSearchRepository.save(medical_Procedures);
        int databaseSizeBeforeUpdate = medical_ProceduresRepository.findAll().size();

        // Update the medical_Procedures
        Medical_Procedures updatedMedical_Procedures = medical_ProceduresRepository.findOne(medical_Procedures.getId());
        updatedMedical_Procedures
            .catalogkey(UPDATED_CATALOGKEY)
            .pronombre(UPDATED_PRONOMBRE)
            .procveedia(UPDATED_PROCVEEDIA)
            .proedadia(UPDATED_PROEDADIA)
            .procveedfa(UPDATED_PROCVEEDFA)
            .proedadfa(UPDATED_PROEDADFA)
            .sextype(UPDATED_SEXTYPE)
            .pornivela(UPDATED_PORNIVELA)
            .procedimientotype(UPDATED_PROCEDIMIENTOTYPE)
            .proprincipal(UPDATED_PROPRINCIPAL)
            .procapitulo(UPDATED_PROCAPITULO)
            .proseccion(UPDATED_PROSECCION)
            .procategoria(UPDATED_PROCATEGORIA)
            .prosubcateg(UPDATED_PROSUBCATEG)
            .progrupolc(UPDATED_PROGRUPOLC)
            .proescauses(UPDATED_PROESCAUSES)
            .pronumcauses(UPDATED_PRONUMCAUSES);

        restMedical_ProceduresMockMvc.perform(put("/api/medical-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedical_Procedures)))
            .andExpect(status().isOk());

        // Validate the Medical_Procedures in the database
        List<Medical_Procedures> medical_ProceduresList = medical_ProceduresRepository.findAll();
        assertThat(medical_ProceduresList).hasSize(databaseSizeBeforeUpdate);
        Medical_Procedures testMedical_Procedures = medical_ProceduresList.get(medical_ProceduresList.size() - 1);
        assertThat(testMedical_Procedures.getCatalogkey()).isEqualTo(UPDATED_CATALOGKEY);
        assertThat(testMedical_Procedures.getPronombre()).isEqualTo(UPDATED_PRONOMBRE);
        assertThat(testMedical_Procedures.getProcveedia()).isEqualTo(UPDATED_PROCVEEDIA);
        assertThat(testMedical_Procedures.getProedadia()).isEqualTo(UPDATED_PROEDADIA);
        assertThat(testMedical_Procedures.getProcveedfa()).isEqualTo(UPDATED_PROCVEEDFA);
        assertThat(testMedical_Procedures.getProedadfa()).isEqualTo(UPDATED_PROEDADFA);
        assertThat(testMedical_Procedures.getSextype()).isEqualTo(UPDATED_SEXTYPE);
        assertThat(testMedical_Procedures.getPornivela()).isEqualTo(UPDATED_PORNIVELA);
        assertThat(testMedical_Procedures.getProcedimientotype()).isEqualTo(UPDATED_PROCEDIMIENTOTYPE);
        assertThat(testMedical_Procedures.getProprincipal()).isEqualTo(UPDATED_PROPRINCIPAL);
        assertThat(testMedical_Procedures.getProcapitulo()).isEqualTo(UPDATED_PROCAPITULO);
        assertThat(testMedical_Procedures.getProseccion()).isEqualTo(UPDATED_PROSECCION);
        assertThat(testMedical_Procedures.getProcategoria()).isEqualTo(UPDATED_PROCATEGORIA);
        assertThat(testMedical_Procedures.getProsubcateg()).isEqualTo(UPDATED_PROSUBCATEG);
        assertThat(testMedical_Procedures.getProgrupolc()).isEqualTo(UPDATED_PROGRUPOLC);
        assertThat(testMedical_Procedures.getProescauses()).isEqualTo(UPDATED_PROESCAUSES);
        assertThat(testMedical_Procedures.getPronumcauses()).isEqualTo(UPDATED_PRONUMCAUSES);

        // Validate the Medical_Procedures in Elasticsearch
        Medical_Procedures medical_ProceduresEs = medical_ProceduresSearchRepository.findOne(testMedical_Procedures.getId());
        assertThat(medical_ProceduresEs).isEqualToComparingFieldByField(testMedical_Procedures);
    }

    @Test
    @Transactional
    public void updateNonExistingMedical_Procedures() throws Exception {
        int databaseSizeBeforeUpdate = medical_ProceduresRepository.findAll().size();

        // Create the Medical_Procedures

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedical_ProceduresMockMvc.perform(put("/api/medical-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medical_Procedures)))
            .andExpect(status().isCreated());

        // Validate the Medical_Procedures in the database
        List<Medical_Procedures> medical_ProceduresList = medical_ProceduresRepository.findAll();
        assertThat(medical_ProceduresList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedical_Procedures() throws Exception {
        // Initialize the database
        medical_ProceduresRepository.saveAndFlush(medical_Procedures);
        medical_ProceduresSearchRepository.save(medical_Procedures);
        int databaseSizeBeforeDelete = medical_ProceduresRepository.findAll().size();

        // Get the medical_Procedures
        restMedical_ProceduresMockMvc.perform(delete("/api/medical-procedures/{id}", medical_Procedures.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medical_ProceduresExistsInEs = medical_ProceduresSearchRepository.exists(medical_Procedures.getId());
        assertThat(medical_ProceduresExistsInEs).isFalse();

        // Validate the database is empty
        List<Medical_Procedures> medical_ProceduresList = medical_ProceduresRepository.findAll();
        assertThat(medical_ProceduresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedical_Procedures() throws Exception {
        // Initialize the database
        medical_ProceduresRepository.saveAndFlush(medical_Procedures);
        medical_ProceduresSearchRepository.save(medical_Procedures);

        // Search the medical_Procedures
        restMedical_ProceduresMockMvc.perform(get("/api/_search/medical-procedures?query=id:" + medical_Procedures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medical_Procedures.getId().intValue())))
            .andExpect(jsonPath("$.[*].catalogkey").value(hasItem(DEFAULT_CATALOGKEY.toString())))
            .andExpect(jsonPath("$.[*].pronombre").value(hasItem(DEFAULT_PRONOMBRE.toString())))
            .andExpect(jsonPath("$.[*].procveedia").value(hasItem(DEFAULT_PROCVEEDIA.toString())))
            .andExpect(jsonPath("$.[*].proedadia").value(hasItem(DEFAULT_PROEDADIA.toString())))
            .andExpect(jsonPath("$.[*].procveedfa").value(hasItem(DEFAULT_PROCVEEDFA.toString())))
            .andExpect(jsonPath("$.[*].proedadfa").value(hasItem(DEFAULT_PROEDADFA.toString())))
            .andExpect(jsonPath("$.[*].sextype").value(hasItem(DEFAULT_SEXTYPE)))
            .andExpect(jsonPath("$.[*].pornivela").value(hasItem(DEFAULT_PORNIVELA)))
            .andExpect(jsonPath("$.[*].procedimientotype").value(hasItem(DEFAULT_PROCEDIMIENTOTYPE.toString())))
            .andExpect(jsonPath("$.[*].proprincipal").value(hasItem(DEFAULT_PROPRINCIPAL.toString())))
            .andExpect(jsonPath("$.[*].procapitulo").value(hasItem(DEFAULT_PROCAPITULO)))
            .andExpect(jsonPath("$.[*].proseccion").value(hasItem(DEFAULT_PROSECCION)))
            .andExpect(jsonPath("$.[*].procategoria").value(hasItem(DEFAULT_PROCATEGORIA)))
            .andExpect(jsonPath("$.[*].prosubcateg").value(hasItem(DEFAULT_PROSUBCATEG)))
            .andExpect(jsonPath("$.[*].progrupolc").value(hasItem(DEFAULT_PROGRUPOLC)))
            .andExpect(jsonPath("$.[*].proescauses").value(hasItem(DEFAULT_PROESCAUSES)))
            .andExpect(jsonPath("$.[*].pronumcauses").value(hasItem(DEFAULT_PRONUMCAUSES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medical_Procedures.class);
        Medical_Procedures medical_Procedures1 = new Medical_Procedures();
        medical_Procedures1.setId(1L);
        Medical_Procedures medical_Procedures2 = new Medical_Procedures();
        medical_Procedures2.setId(medical_Procedures1.getId());
        assertThat(medical_Procedures1).isEqualTo(medical_Procedures2);
        medical_Procedures2.setId(2L);
        assertThat(medical_Procedures1).isNotEqualTo(medical_Procedures2);
        medical_Procedures1.setId(null);
        assertThat(medical_Procedures1).isNotEqualTo(medical_Procedures2);
    }
}
