package mycercardiopackege.jh.web.rest;

import mycercardiopackege.jh.CercardiobitiApp;

import mycercardiopackege.jh.domain.Diagnosis;
import mycercardiopackege.jh.repository.DiagnosisRepository;
import mycercardiopackege.jh.repository.search.DiagnosisSearchRepository;
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
 * Test class for the DiagnosisResource REST controller.
 *
 * @see DiagnosisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CercardiobitiApp.class)
public class DiagnosisResourceIntTest {

    private static final String DEFAULT_LETRA = "AAAAAAAAAA";
    private static final String UPDATED_LETRA = "BBBBBBBBBB";

    private static final String DEFAULT_CATALOGKEY = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGKEY = "BBBBBBBBBB";

    private static final String DEFAULT_ASTERISCO = "AAAAAAAAAA";
    private static final String UPDATED_ASTERISCO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_LSEX = "AAAAAAAAAA";
    private static final String UPDATED_LSEX = "BBBBBBBBBB";

    private static final String DEFAULT_LINF = "AAAAAAAAAA";
    private static final String UPDATED_LINF = "BBBBBBBBBB";

    private static final String DEFAULT_LSUP = "AAAAAAAAAA";
    private static final String UPDATED_LSUP = "BBBBBBBBBB";

    private static final String DEFAULT_TRIVIAL = "AAAAAAAAAA";
    private static final String UPDATED_TRIVIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ERRADICADO = "AAAAAAAAAA";
    private static final String UPDATED_ERRADICADO = "BBBBBBBBBB";

    private static final String DEFAULT_NINTER = "AAAAAAAAAA";
    private static final String UPDATED_NINTER = "BBBBBBBBBB";

    private static final String DEFAULT_NIN = "AAAAAAAAAA";
    private static final String UPDATED_NIN = "BBBBBBBBBB";

    private static final String DEFAULT_NINMTOBS = "AAAAAAAAAA";
    private static final String UPDATED_NINMTOBS = "BBBBBBBBBB";

    private static final String DEFAULT_NOCBD = "AAAAAAAAAA";
    private static final String UPDATED_NOCBD = "BBBBBBBBBB";

    private static final String DEFAULT_NOAPH = "AAAAAAAAAA";
    private static final String UPDATED_NOAPH = "BBBBBBBBBB";

    private static final String DEFAULT_FETAL = "AAAAAAAAAA";
    private static final String UPDATED_FETAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPITULO = 1;
    private static final Integer UPDATED_CAPITULO = 2;

    private static final Integer DEFAULT_LISTA_1 = 1;
    private static final Integer UPDATED_LISTA_1 = 2;

    private static final Integer DEFAULT_GRUPO_1 = 1;
    private static final Integer UPDATED_GRUPO_1 = 2;

    private static final Integer DEFAULT_LISTA_5 = 1;
    private static final Integer UPDATED_LISTA_5 = 2;

    private static final String DEFAULT_ACTUALIZACIONESCIE_10 = "AAAAAAAAAA";
    private static final String UPDATED_ACTUALIZACIONESCIE_10 = "BBBBBBBBBB";

    private static final String DEFAULT_YEARMODIFI = "AAAAAAAAAA";
    private static final String UPDATED_YEARMODIFI = "BBBBBBBBBB";

    private static final String DEFAULT_YEARAPLICACION = "AAAAAAAAAA";
    private static final String UPDATED_YEARAPLICACION = "BBBBBBBBBB";

    private static final String DEFAULT_VALID = "AAAAAAAAAA";
    private static final String UPDATED_VALID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRINMORTA = 1;
    private static final Integer UPDATED_PRINMORTA = 2;

    private static final Integer DEFAULT_PRINMORBI = 1;
    private static final Integer UPDATED_PRINMORBI = 2;

    private static final String DEFAULT_LMMORBI = "AAAAAAAAAA";
    private static final String UPDATED_LMMORBI = "BBBBBBBBBB";

    private static final String DEFAULT_LMMORTA = "AAAAAAAAAA";
    private static final String UPDATED_LMMORTA = "BBBBBBBBBB";

    private static final String DEFAULT_LGBD_165 = "AAAAAAAAAA";
    private static final String UPDATED_LGBD_165 = "BBBBBBBBBB";

    private static final Integer DEFAULT_IOMSBECK = 1;
    private static final Integer UPDATED_IOMSBECK = 2;

    private static final String DEFAULT_LGBD_190 = "AAAAAAAAAA";
    private static final String UPDATED_LGBD_190 = "BBBBBBBBBB";

    private static final String DEFAULT_NOTDIARIA = "AAAAAAAAAA";
    private static final String UPDATED_NOTDIARIA = "BBBBBBBBBB";

    private static final String DEFAULT_NOTSEMANAL = "AAAAAAAAAA";
    private static final String UPDATED_NOTSEMANAL = "BBBBBBBBBB";

    private static final String DEFAULT_SISTEMAESPECIAL = "AAAAAAAAAA";
    private static final String UPDATED_SISTEMAESPECIAL = "BBBBBBBBBB";

    private static final String DEFAULT_BIRMM = "AAAAAAAAAA";
    private static final String UPDATED_BIRMM = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAUSATYPE = 1;
    private static final Integer UPDATED_CAUSATYPE = 2;

    private static final String DEFAULT_EPIMORTA = "AAAAAAAAAA";
    private static final String UPDATED_EPIMORTA = "BBBBBBBBBB";

    private static final String DEFAULT_EDASIRAS_5_ANIOS = "AAAAAAAAAA";
    private static final String UPDATED_EDASIRAS_5_ANIOS = "BBBBBBBBBB";

    private static final String DEFAULT_CSVEMATERNASSEEDEPID = "AAAAAAAAAA";
    private static final String UPDATED_CSVEMATERNASSEEDEPID = "BBBBBBBBBB";

    private static final String DEFAULT_EPIMORTAM_5 = "AAAAAAAAAA";
    private static final String UPDATED_EPIMORTAM_5 = "BBBBBBBBBB";

    private static final String DEFAULT_EPIMORBI = "AAAAAAAAAA";
    private static final String UPDATED_EPIMORBI = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFMATERNAS = 1;
    private static final Integer UPDATED_DEFMATERNAS = 2;

    private static final String DEFAULT_ESCAUSES = "AAAAAAAAAA";
    private static final String UPDATED_ESCAUSES = "BBBBBBBBBB";

    private static final String DEFAULT_NUMCAUSES = "AAAAAAAAAA";
    private static final String UPDATED_NUMCAUSES = "BBBBBBBBBB";

    private static final String DEFAULT_ESSUIVEMORTA = "AAAAAAAAAA";
    private static final String UPDATED_ESSUIVEMORTA = "BBBBBBBBBB";

    private static final String DEFAULT_ESSUIVEMORB = "AAAAAAAAAA";
    private static final String UPDATED_ESSUIVEMORB = "BBBBBBBBBB";

    private static final String DEFAULT_EPICLAVE = "AAAAAAAAAA";
    private static final String UPDATED_EPICLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_EPICLAVEDESC = "AAAAAAAAAA";
    private static final String UPDATED_EPICLAVEDESC = "BBBBBBBBBB";

    private static final String DEFAULT_ESSUIVENOTIN = "AAAAAAAAAA";
    private static final String UPDATED_ESSUIVENOTIN = "BBBBBBBBBB";

    private static final String DEFAULT_ESSUIVEESTEPI = "AAAAAAAAAA";
    private static final String UPDATED_ESSUIVEESTEPI = "BBBBBBBBBB";

    private static final String DEFAULT_ESSUIVEESTBROTE = "AAAAAAAAAA";
    private static final String UPDATED_ESSUIVEESTBROTE = "BBBBBBBBBB";

    private static final String DEFAULT_SINAC = "AAAAAAAAAA";
    private static final String UPDATED_SINAC = "BBBBBBBBBB";

    private static final String DEFAULT_DAGA = "AAAAAAAAAA";
    private static final String UPDATED_DAGA = "BBBBBBBBBB";

    private static final String DEFAULT_MANIFESTAENFER = "AAAAAAAAAA";
    private static final String UPDATED_MANIFESTAENFER = "BBBBBBBBBB";

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private DiagnosisSearchRepository diagnosisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiagnosisMockMvc;

    private Diagnosis diagnosis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiagnosisResource diagnosisResource = new DiagnosisResource(diagnosisRepository, diagnosisSearchRepository);
        this.restDiagnosisMockMvc = MockMvcBuilders.standaloneSetup(diagnosisResource)
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
    public static Diagnosis createEntity(EntityManager em) {
        Diagnosis diagnosis = new Diagnosis()
            .letra(DEFAULT_LETRA)
            .catalogkey(DEFAULT_CATALOGKEY)
            .asterisco(DEFAULT_ASTERISCO)
            .nombre(DEFAULT_NOMBRE)
            .lsex(DEFAULT_LSEX)
            .linf(DEFAULT_LINF)
            .lsup(DEFAULT_LSUP)
            .trivial(DEFAULT_TRIVIAL)
            .erradicado(DEFAULT_ERRADICADO)
            .ninter(DEFAULT_NINTER)
            .nin(DEFAULT_NIN)
            .ninmtobs(DEFAULT_NINMTOBS)
            .nocbd(DEFAULT_NOCBD)
            .noaph(DEFAULT_NOAPH)
            .fetal(DEFAULT_FETAL)
            .capitulo(DEFAULT_CAPITULO)
            .lista1(DEFAULT_LISTA_1)
            .grupo1(DEFAULT_GRUPO_1)
            .lista5(DEFAULT_LISTA_5)
            .actualizacionescie10(DEFAULT_ACTUALIZACIONESCIE_10)
            .yearmodifi(DEFAULT_YEARMODIFI)
            .yearaplicacion(DEFAULT_YEARAPLICACION)
            .valid(DEFAULT_VALID)
            .prinmorta(DEFAULT_PRINMORTA)
            .prinmorbi(DEFAULT_PRINMORBI)
            .lmmorbi(DEFAULT_LMMORBI)
            .lmmorta(DEFAULT_LMMORTA)
            .lgbd165(DEFAULT_LGBD_165)
            .iomsbeck(DEFAULT_IOMSBECK)
            .lgbd190(DEFAULT_LGBD_190)
            .notdiaria(DEFAULT_NOTDIARIA)
            .notsemanal(DEFAULT_NOTSEMANAL)
            .sistemaespecial(DEFAULT_SISTEMAESPECIAL)
            .birmm(DEFAULT_BIRMM)
            .causatype(DEFAULT_CAUSATYPE)
            .epimorta(DEFAULT_EPIMORTA)
            .edasiras5Anios(DEFAULT_EDASIRAS_5_ANIOS)
            .csvematernasseedepid(DEFAULT_CSVEMATERNASSEEDEPID)
            .epimortam5(DEFAULT_EPIMORTAM_5)
            .epimorbi(DEFAULT_EPIMORBI)
            .defmaternas(DEFAULT_DEFMATERNAS)
            .escauses(DEFAULT_ESCAUSES)
            .numcauses(DEFAULT_NUMCAUSES)
            .essuivemorta(DEFAULT_ESSUIVEMORTA)
            .essuivemorb(DEFAULT_ESSUIVEMORB)
            .epiclave(DEFAULT_EPICLAVE)
            .epiclavedesc(DEFAULT_EPICLAVEDESC)
            .essuivenotin(DEFAULT_ESSUIVENOTIN)
            .essuiveestepi(DEFAULT_ESSUIVEESTEPI)
            .essuiveestbrote(DEFAULT_ESSUIVEESTBROTE)
            .sinac(DEFAULT_SINAC)
            .daga(DEFAULT_DAGA)
            .manifestaenfer(DEFAULT_MANIFESTAENFER);
        return diagnosis;
    }

    @Before
    public void initTest() {
        diagnosisSearchRepository.deleteAll();
        diagnosis = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiagnosis() throws Exception {
        int databaseSizeBeforeCreate = diagnosisRepository.findAll().size();

        // Create the Diagnosis
        restDiagnosisMockMvc.perform(post("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isCreated());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeCreate + 1);
        Diagnosis testDiagnosis = diagnosisList.get(diagnosisList.size() - 1);
        assertThat(testDiagnosis.getLetra()).isEqualTo(DEFAULT_LETRA);
        assertThat(testDiagnosis.getCatalogkey()).isEqualTo(DEFAULT_CATALOGKEY);
        assertThat(testDiagnosis.getAsterisco()).isEqualTo(DEFAULT_ASTERISCO);
        assertThat(testDiagnosis.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDiagnosis.getLsex()).isEqualTo(DEFAULT_LSEX);
        assertThat(testDiagnosis.getLinf()).isEqualTo(DEFAULT_LINF);
        assertThat(testDiagnosis.getLsup()).isEqualTo(DEFAULT_LSUP);
        assertThat(testDiagnosis.getTrivial()).isEqualTo(DEFAULT_TRIVIAL);
        assertThat(testDiagnosis.getErradicado()).isEqualTo(DEFAULT_ERRADICADO);
        assertThat(testDiagnosis.getNinter()).isEqualTo(DEFAULT_NINTER);
        assertThat(testDiagnosis.getNin()).isEqualTo(DEFAULT_NIN);
        assertThat(testDiagnosis.getNinmtobs()).isEqualTo(DEFAULT_NINMTOBS);
        assertThat(testDiagnosis.getNocbd()).isEqualTo(DEFAULT_NOCBD);
        assertThat(testDiagnosis.getNoaph()).isEqualTo(DEFAULT_NOAPH);
        assertThat(testDiagnosis.getFetal()).isEqualTo(DEFAULT_FETAL);
        assertThat(testDiagnosis.getCapitulo()).isEqualTo(DEFAULT_CAPITULO);
        assertThat(testDiagnosis.getLista1()).isEqualTo(DEFAULT_LISTA_1);
        assertThat(testDiagnosis.getGrupo1()).isEqualTo(DEFAULT_GRUPO_1);
        assertThat(testDiagnosis.getLista5()).isEqualTo(DEFAULT_LISTA_5);
        assertThat(testDiagnosis.getActualizacionescie10()).isEqualTo(DEFAULT_ACTUALIZACIONESCIE_10);
        assertThat(testDiagnosis.getYearmodifi()).isEqualTo(DEFAULT_YEARMODIFI);
        assertThat(testDiagnosis.getYearaplicacion()).isEqualTo(DEFAULT_YEARAPLICACION);
        assertThat(testDiagnosis.getValid()).isEqualTo(DEFAULT_VALID);
        assertThat(testDiagnosis.getPrinmorta()).isEqualTo(DEFAULT_PRINMORTA);
        assertThat(testDiagnosis.getPrinmorbi()).isEqualTo(DEFAULT_PRINMORBI);
        assertThat(testDiagnosis.getLmmorbi()).isEqualTo(DEFAULT_LMMORBI);
        assertThat(testDiagnosis.getLmmorta()).isEqualTo(DEFAULT_LMMORTA);
        assertThat(testDiagnosis.getLgbd165()).isEqualTo(DEFAULT_LGBD_165);
        assertThat(testDiagnosis.getIomsbeck()).isEqualTo(DEFAULT_IOMSBECK);
        assertThat(testDiagnosis.getLgbd190()).isEqualTo(DEFAULT_LGBD_190);
        assertThat(testDiagnosis.getNotdiaria()).isEqualTo(DEFAULT_NOTDIARIA);
        assertThat(testDiagnosis.getNotsemanal()).isEqualTo(DEFAULT_NOTSEMANAL);
        assertThat(testDiagnosis.getSistemaespecial()).isEqualTo(DEFAULT_SISTEMAESPECIAL);
        assertThat(testDiagnosis.getBirmm()).isEqualTo(DEFAULT_BIRMM);
        assertThat(testDiagnosis.getCausatype()).isEqualTo(DEFAULT_CAUSATYPE);
        assertThat(testDiagnosis.getEpimorta()).isEqualTo(DEFAULT_EPIMORTA);
        assertThat(testDiagnosis.getEdasiras5Anios()).isEqualTo(DEFAULT_EDASIRAS_5_ANIOS);
        assertThat(testDiagnosis.getCsvematernasseedepid()).isEqualTo(DEFAULT_CSVEMATERNASSEEDEPID);
        assertThat(testDiagnosis.getEpimortam5()).isEqualTo(DEFAULT_EPIMORTAM_5);
        assertThat(testDiagnosis.getEpimorbi()).isEqualTo(DEFAULT_EPIMORBI);
        assertThat(testDiagnosis.getDefmaternas()).isEqualTo(DEFAULT_DEFMATERNAS);
        assertThat(testDiagnosis.getEscauses()).isEqualTo(DEFAULT_ESCAUSES);
        assertThat(testDiagnosis.getNumcauses()).isEqualTo(DEFAULT_NUMCAUSES);
        assertThat(testDiagnosis.getEssuivemorta()).isEqualTo(DEFAULT_ESSUIVEMORTA);
        assertThat(testDiagnosis.getEssuivemorb()).isEqualTo(DEFAULT_ESSUIVEMORB);
        assertThat(testDiagnosis.getEpiclave()).isEqualTo(DEFAULT_EPICLAVE);
        assertThat(testDiagnosis.getEpiclavedesc()).isEqualTo(DEFAULT_EPICLAVEDESC);
        assertThat(testDiagnosis.getEssuivenotin()).isEqualTo(DEFAULT_ESSUIVENOTIN);
        assertThat(testDiagnosis.getEssuiveestepi()).isEqualTo(DEFAULT_ESSUIVEESTEPI);
        assertThat(testDiagnosis.getEssuiveestbrote()).isEqualTo(DEFAULT_ESSUIVEESTBROTE);
        assertThat(testDiagnosis.getSinac()).isEqualTo(DEFAULT_SINAC);
        assertThat(testDiagnosis.getDaga()).isEqualTo(DEFAULT_DAGA);
        assertThat(testDiagnosis.getManifestaenfer()).isEqualTo(DEFAULT_MANIFESTAENFER);

        // Validate the Diagnosis in Elasticsearch
        Diagnosis diagnosisEs = diagnosisSearchRepository.findOne(testDiagnosis.getId());
        assertThat(diagnosisEs).isEqualToComparingFieldByField(testDiagnosis);
    }

    @Test
    @Transactional
    public void createDiagnosisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diagnosisRepository.findAll().size();

        // Create the Diagnosis with an existing ID
        diagnosis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosisMockMvc.perform(post("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiagnoses() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        // Get all the diagnosisList
        restDiagnosisMockMvc.perform(get("/api/diagnoses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosis.getId().intValue())))
            .andExpect(jsonPath("$.[*].letra").value(hasItem(DEFAULT_LETRA.toString())))
            .andExpect(jsonPath("$.[*].catalogkey").value(hasItem(DEFAULT_CATALOGKEY.toString())))
            .andExpect(jsonPath("$.[*].asterisco").value(hasItem(DEFAULT_ASTERISCO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].lsex").value(hasItem(DEFAULT_LSEX.toString())))
            .andExpect(jsonPath("$.[*].linf").value(hasItem(DEFAULT_LINF.toString())))
            .andExpect(jsonPath("$.[*].lsup").value(hasItem(DEFAULT_LSUP.toString())))
            .andExpect(jsonPath("$.[*].trivial").value(hasItem(DEFAULT_TRIVIAL.toString())))
            .andExpect(jsonPath("$.[*].erradicado").value(hasItem(DEFAULT_ERRADICADO.toString())))
            .andExpect(jsonPath("$.[*].ninter").value(hasItem(DEFAULT_NINTER.toString())))
            .andExpect(jsonPath("$.[*].nin").value(hasItem(DEFAULT_NIN.toString())))
            .andExpect(jsonPath("$.[*].ninmtobs").value(hasItem(DEFAULT_NINMTOBS.toString())))
            .andExpect(jsonPath("$.[*].nocbd").value(hasItem(DEFAULT_NOCBD.toString())))
            .andExpect(jsonPath("$.[*].noaph").value(hasItem(DEFAULT_NOAPH.toString())))
            .andExpect(jsonPath("$.[*].fetal").value(hasItem(DEFAULT_FETAL.toString())))
            .andExpect(jsonPath("$.[*].capitulo").value(hasItem(DEFAULT_CAPITULO)))
            .andExpect(jsonPath("$.[*].lista1").value(hasItem(DEFAULT_LISTA_1)))
            .andExpect(jsonPath("$.[*].grupo1").value(hasItem(DEFAULT_GRUPO_1)))
            .andExpect(jsonPath("$.[*].lista5").value(hasItem(DEFAULT_LISTA_5)))
            .andExpect(jsonPath("$.[*].actualizacionescie10").value(hasItem(DEFAULT_ACTUALIZACIONESCIE_10.toString())))
            .andExpect(jsonPath("$.[*].yearmodifi").value(hasItem(DEFAULT_YEARMODIFI.toString())))
            .andExpect(jsonPath("$.[*].yearaplicacion").value(hasItem(DEFAULT_YEARAPLICACION.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.toString())))
            .andExpect(jsonPath("$.[*].prinmorta").value(hasItem(DEFAULT_PRINMORTA)))
            .andExpect(jsonPath("$.[*].prinmorbi").value(hasItem(DEFAULT_PRINMORBI)))
            .andExpect(jsonPath("$.[*].lmmorbi").value(hasItem(DEFAULT_LMMORBI.toString())))
            .andExpect(jsonPath("$.[*].lmmorta").value(hasItem(DEFAULT_LMMORTA.toString())))
            .andExpect(jsonPath("$.[*].lgbd165").value(hasItem(DEFAULT_LGBD_165.toString())))
            .andExpect(jsonPath("$.[*].iomsbeck").value(hasItem(DEFAULT_IOMSBECK)))
            .andExpect(jsonPath("$.[*].lgbd190").value(hasItem(DEFAULT_LGBD_190.toString())))
            .andExpect(jsonPath("$.[*].notdiaria").value(hasItem(DEFAULT_NOTDIARIA.toString())))
            .andExpect(jsonPath("$.[*].notsemanal").value(hasItem(DEFAULT_NOTSEMANAL.toString())))
            .andExpect(jsonPath("$.[*].sistemaespecial").value(hasItem(DEFAULT_SISTEMAESPECIAL.toString())))
            .andExpect(jsonPath("$.[*].birmm").value(hasItem(DEFAULT_BIRMM.toString())))
            .andExpect(jsonPath("$.[*].causatype").value(hasItem(DEFAULT_CAUSATYPE)))
            .andExpect(jsonPath("$.[*].epimorta").value(hasItem(DEFAULT_EPIMORTA.toString())))
            .andExpect(jsonPath("$.[*].edasiras5Anios").value(hasItem(DEFAULT_EDASIRAS_5_ANIOS.toString())))
            .andExpect(jsonPath("$.[*].csvematernasseedepid").value(hasItem(DEFAULT_CSVEMATERNASSEEDEPID.toString())))
            .andExpect(jsonPath("$.[*].epimortam5").value(hasItem(DEFAULT_EPIMORTAM_5.toString())))
            .andExpect(jsonPath("$.[*].epimorbi").value(hasItem(DEFAULT_EPIMORBI.toString())))
            .andExpect(jsonPath("$.[*].defmaternas").value(hasItem(DEFAULT_DEFMATERNAS)))
            .andExpect(jsonPath("$.[*].escauses").value(hasItem(DEFAULT_ESCAUSES.toString())))
            .andExpect(jsonPath("$.[*].numcauses").value(hasItem(DEFAULT_NUMCAUSES.toString())))
            .andExpect(jsonPath("$.[*].essuivemorta").value(hasItem(DEFAULT_ESSUIVEMORTA.toString())))
            .andExpect(jsonPath("$.[*].essuivemorb").value(hasItem(DEFAULT_ESSUIVEMORB.toString())))
            .andExpect(jsonPath("$.[*].epiclave").value(hasItem(DEFAULT_EPICLAVE.toString())))
            .andExpect(jsonPath("$.[*].epiclavedesc").value(hasItem(DEFAULT_EPICLAVEDESC.toString())))
            .andExpect(jsonPath("$.[*].essuivenotin").value(hasItem(DEFAULT_ESSUIVENOTIN.toString())))
            .andExpect(jsonPath("$.[*].essuiveestepi").value(hasItem(DEFAULT_ESSUIVEESTEPI.toString())))
            .andExpect(jsonPath("$.[*].essuiveestbrote").value(hasItem(DEFAULT_ESSUIVEESTBROTE.toString())))
            .andExpect(jsonPath("$.[*].sinac").value(hasItem(DEFAULT_SINAC.toString())))
            .andExpect(jsonPath("$.[*].daga").value(hasItem(DEFAULT_DAGA.toString())))
            .andExpect(jsonPath("$.[*].manifestaenfer").value(hasItem(DEFAULT_MANIFESTAENFER.toString())));
    }

    @Test
    @Transactional
    public void getDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        // Get the diagnosis
        restDiagnosisMockMvc.perform(get("/api/diagnoses/{id}", diagnosis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diagnosis.getId().intValue()))
            .andExpect(jsonPath("$.letra").value(DEFAULT_LETRA.toString()))
            .andExpect(jsonPath("$.catalogkey").value(DEFAULT_CATALOGKEY.toString()))
            .andExpect(jsonPath("$.asterisco").value(DEFAULT_ASTERISCO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.lsex").value(DEFAULT_LSEX.toString()))
            .andExpect(jsonPath("$.linf").value(DEFAULT_LINF.toString()))
            .andExpect(jsonPath("$.lsup").value(DEFAULT_LSUP.toString()))
            .andExpect(jsonPath("$.trivial").value(DEFAULT_TRIVIAL.toString()))
            .andExpect(jsonPath("$.erradicado").value(DEFAULT_ERRADICADO.toString()))
            .andExpect(jsonPath("$.ninter").value(DEFAULT_NINTER.toString()))
            .andExpect(jsonPath("$.nin").value(DEFAULT_NIN.toString()))
            .andExpect(jsonPath("$.ninmtobs").value(DEFAULT_NINMTOBS.toString()))
            .andExpect(jsonPath("$.nocbd").value(DEFAULT_NOCBD.toString()))
            .andExpect(jsonPath("$.noaph").value(DEFAULT_NOAPH.toString()))
            .andExpect(jsonPath("$.fetal").value(DEFAULT_FETAL.toString()))
            .andExpect(jsonPath("$.capitulo").value(DEFAULT_CAPITULO))
            .andExpect(jsonPath("$.lista1").value(DEFAULT_LISTA_1))
            .andExpect(jsonPath("$.grupo1").value(DEFAULT_GRUPO_1))
            .andExpect(jsonPath("$.lista5").value(DEFAULT_LISTA_5))
            .andExpect(jsonPath("$.actualizacionescie10").value(DEFAULT_ACTUALIZACIONESCIE_10.toString()))
            .andExpect(jsonPath("$.yearmodifi").value(DEFAULT_YEARMODIFI.toString()))
            .andExpect(jsonPath("$.yearaplicacion").value(DEFAULT_YEARAPLICACION.toString()))
            .andExpect(jsonPath("$.valid").value(DEFAULT_VALID.toString()))
            .andExpect(jsonPath("$.prinmorta").value(DEFAULT_PRINMORTA))
            .andExpect(jsonPath("$.prinmorbi").value(DEFAULT_PRINMORBI))
            .andExpect(jsonPath("$.lmmorbi").value(DEFAULT_LMMORBI.toString()))
            .andExpect(jsonPath("$.lmmorta").value(DEFAULT_LMMORTA.toString()))
            .andExpect(jsonPath("$.lgbd165").value(DEFAULT_LGBD_165.toString()))
            .andExpect(jsonPath("$.iomsbeck").value(DEFAULT_IOMSBECK))
            .andExpect(jsonPath("$.lgbd190").value(DEFAULT_LGBD_190.toString()))
            .andExpect(jsonPath("$.notdiaria").value(DEFAULT_NOTDIARIA.toString()))
            .andExpect(jsonPath("$.notsemanal").value(DEFAULT_NOTSEMANAL.toString()))
            .andExpect(jsonPath("$.sistemaespecial").value(DEFAULT_SISTEMAESPECIAL.toString()))
            .andExpect(jsonPath("$.birmm").value(DEFAULT_BIRMM.toString()))
            .andExpect(jsonPath("$.causatype").value(DEFAULT_CAUSATYPE))
            .andExpect(jsonPath("$.epimorta").value(DEFAULT_EPIMORTA.toString()))
            .andExpect(jsonPath("$.edasiras5Anios").value(DEFAULT_EDASIRAS_5_ANIOS.toString()))
            .andExpect(jsonPath("$.csvematernasseedepid").value(DEFAULT_CSVEMATERNASSEEDEPID.toString()))
            .andExpect(jsonPath("$.epimortam5").value(DEFAULT_EPIMORTAM_5.toString()))
            .andExpect(jsonPath("$.epimorbi").value(DEFAULT_EPIMORBI.toString()))
            .andExpect(jsonPath("$.defmaternas").value(DEFAULT_DEFMATERNAS))
            .andExpect(jsonPath("$.escauses").value(DEFAULT_ESCAUSES.toString()))
            .andExpect(jsonPath("$.numcauses").value(DEFAULT_NUMCAUSES.toString()))
            .andExpect(jsonPath("$.essuivemorta").value(DEFAULT_ESSUIVEMORTA.toString()))
            .andExpect(jsonPath("$.essuivemorb").value(DEFAULT_ESSUIVEMORB.toString()))
            .andExpect(jsonPath("$.epiclave").value(DEFAULT_EPICLAVE.toString()))
            .andExpect(jsonPath("$.epiclavedesc").value(DEFAULT_EPICLAVEDESC.toString()))
            .andExpect(jsonPath("$.essuivenotin").value(DEFAULT_ESSUIVENOTIN.toString()))
            .andExpect(jsonPath("$.essuiveestepi").value(DEFAULT_ESSUIVEESTEPI.toString()))
            .andExpect(jsonPath("$.essuiveestbrote").value(DEFAULT_ESSUIVEESTBROTE.toString()))
            .andExpect(jsonPath("$.sinac").value(DEFAULT_SINAC.toString()))
            .andExpect(jsonPath("$.daga").value(DEFAULT_DAGA.toString()))
            .andExpect(jsonPath("$.manifestaenfer").value(DEFAULT_MANIFESTAENFER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiagnosis() throws Exception {
        // Get the diagnosis
        restDiagnosisMockMvc.perform(get("/api/diagnoses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);
        diagnosisSearchRepository.save(diagnosis);
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();

        // Update the diagnosis
        Diagnosis updatedDiagnosis = diagnosisRepository.findOne(diagnosis.getId());
        updatedDiagnosis
            .letra(UPDATED_LETRA)
            .catalogkey(UPDATED_CATALOGKEY)
            .asterisco(UPDATED_ASTERISCO)
            .nombre(UPDATED_NOMBRE)
            .lsex(UPDATED_LSEX)
            .linf(UPDATED_LINF)
            .lsup(UPDATED_LSUP)
            .trivial(UPDATED_TRIVIAL)
            .erradicado(UPDATED_ERRADICADO)
            .ninter(UPDATED_NINTER)
            .nin(UPDATED_NIN)
            .ninmtobs(UPDATED_NINMTOBS)
            .nocbd(UPDATED_NOCBD)
            .noaph(UPDATED_NOAPH)
            .fetal(UPDATED_FETAL)
            .capitulo(UPDATED_CAPITULO)
            .lista1(UPDATED_LISTA_1)
            .grupo1(UPDATED_GRUPO_1)
            .lista5(UPDATED_LISTA_5)
            .actualizacionescie10(UPDATED_ACTUALIZACIONESCIE_10)
            .yearmodifi(UPDATED_YEARMODIFI)
            .yearaplicacion(UPDATED_YEARAPLICACION)
            .valid(UPDATED_VALID)
            .prinmorta(UPDATED_PRINMORTA)
            .prinmorbi(UPDATED_PRINMORBI)
            .lmmorbi(UPDATED_LMMORBI)
            .lmmorta(UPDATED_LMMORTA)
            .lgbd165(UPDATED_LGBD_165)
            .iomsbeck(UPDATED_IOMSBECK)
            .lgbd190(UPDATED_LGBD_190)
            .notdiaria(UPDATED_NOTDIARIA)
            .notsemanal(UPDATED_NOTSEMANAL)
            .sistemaespecial(UPDATED_SISTEMAESPECIAL)
            .birmm(UPDATED_BIRMM)
            .causatype(UPDATED_CAUSATYPE)
            .epimorta(UPDATED_EPIMORTA)
            .edasiras5Anios(UPDATED_EDASIRAS_5_ANIOS)
            .csvematernasseedepid(UPDATED_CSVEMATERNASSEEDEPID)
            .epimortam5(UPDATED_EPIMORTAM_5)
            .epimorbi(UPDATED_EPIMORBI)
            .defmaternas(UPDATED_DEFMATERNAS)
            .escauses(UPDATED_ESCAUSES)
            .numcauses(UPDATED_NUMCAUSES)
            .essuivemorta(UPDATED_ESSUIVEMORTA)
            .essuivemorb(UPDATED_ESSUIVEMORB)
            .epiclave(UPDATED_EPICLAVE)
            .epiclavedesc(UPDATED_EPICLAVEDESC)
            .essuivenotin(UPDATED_ESSUIVENOTIN)
            .essuiveestepi(UPDATED_ESSUIVEESTEPI)
            .essuiveestbrote(UPDATED_ESSUIVEESTBROTE)
            .sinac(UPDATED_SINAC)
            .daga(UPDATED_DAGA)
            .manifestaenfer(UPDATED_MANIFESTAENFER);

        restDiagnosisMockMvc.perform(put("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiagnosis)))
            .andExpect(status().isOk());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
        Diagnosis testDiagnosis = diagnosisList.get(diagnosisList.size() - 1);
        assertThat(testDiagnosis.getLetra()).isEqualTo(UPDATED_LETRA);
        assertThat(testDiagnosis.getCatalogkey()).isEqualTo(UPDATED_CATALOGKEY);
        assertThat(testDiagnosis.getAsterisco()).isEqualTo(UPDATED_ASTERISCO);
        assertThat(testDiagnosis.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDiagnosis.getLsex()).isEqualTo(UPDATED_LSEX);
        assertThat(testDiagnosis.getLinf()).isEqualTo(UPDATED_LINF);
        assertThat(testDiagnosis.getLsup()).isEqualTo(UPDATED_LSUP);
        assertThat(testDiagnosis.getTrivial()).isEqualTo(UPDATED_TRIVIAL);
        assertThat(testDiagnosis.getErradicado()).isEqualTo(UPDATED_ERRADICADO);
        assertThat(testDiagnosis.getNinter()).isEqualTo(UPDATED_NINTER);
        assertThat(testDiagnosis.getNin()).isEqualTo(UPDATED_NIN);
        assertThat(testDiagnosis.getNinmtobs()).isEqualTo(UPDATED_NINMTOBS);
        assertThat(testDiagnosis.getNocbd()).isEqualTo(UPDATED_NOCBD);
        assertThat(testDiagnosis.getNoaph()).isEqualTo(UPDATED_NOAPH);
        assertThat(testDiagnosis.getFetal()).isEqualTo(UPDATED_FETAL);
        assertThat(testDiagnosis.getCapitulo()).isEqualTo(UPDATED_CAPITULO);
        assertThat(testDiagnosis.getLista1()).isEqualTo(UPDATED_LISTA_1);
        assertThat(testDiagnosis.getGrupo1()).isEqualTo(UPDATED_GRUPO_1);
        assertThat(testDiagnosis.getLista5()).isEqualTo(UPDATED_LISTA_5);
        assertThat(testDiagnosis.getActualizacionescie10()).isEqualTo(UPDATED_ACTUALIZACIONESCIE_10);
        assertThat(testDiagnosis.getYearmodifi()).isEqualTo(UPDATED_YEARMODIFI);
        assertThat(testDiagnosis.getYearaplicacion()).isEqualTo(UPDATED_YEARAPLICACION);
        assertThat(testDiagnosis.getValid()).isEqualTo(UPDATED_VALID);
        assertThat(testDiagnosis.getPrinmorta()).isEqualTo(UPDATED_PRINMORTA);
        assertThat(testDiagnosis.getPrinmorbi()).isEqualTo(UPDATED_PRINMORBI);
        assertThat(testDiagnosis.getLmmorbi()).isEqualTo(UPDATED_LMMORBI);
        assertThat(testDiagnosis.getLmmorta()).isEqualTo(UPDATED_LMMORTA);
        assertThat(testDiagnosis.getLgbd165()).isEqualTo(UPDATED_LGBD_165);
        assertThat(testDiagnosis.getIomsbeck()).isEqualTo(UPDATED_IOMSBECK);
        assertThat(testDiagnosis.getLgbd190()).isEqualTo(UPDATED_LGBD_190);
        assertThat(testDiagnosis.getNotdiaria()).isEqualTo(UPDATED_NOTDIARIA);
        assertThat(testDiagnosis.getNotsemanal()).isEqualTo(UPDATED_NOTSEMANAL);
        assertThat(testDiagnosis.getSistemaespecial()).isEqualTo(UPDATED_SISTEMAESPECIAL);
        assertThat(testDiagnosis.getBirmm()).isEqualTo(UPDATED_BIRMM);
        assertThat(testDiagnosis.getCausatype()).isEqualTo(UPDATED_CAUSATYPE);
        assertThat(testDiagnosis.getEpimorta()).isEqualTo(UPDATED_EPIMORTA);
        assertThat(testDiagnosis.getEdasiras5Anios()).isEqualTo(UPDATED_EDASIRAS_5_ANIOS);
        assertThat(testDiagnosis.getCsvematernasseedepid()).isEqualTo(UPDATED_CSVEMATERNASSEEDEPID);
        assertThat(testDiagnosis.getEpimortam5()).isEqualTo(UPDATED_EPIMORTAM_5);
        assertThat(testDiagnosis.getEpimorbi()).isEqualTo(UPDATED_EPIMORBI);
        assertThat(testDiagnosis.getDefmaternas()).isEqualTo(UPDATED_DEFMATERNAS);
        assertThat(testDiagnosis.getEscauses()).isEqualTo(UPDATED_ESCAUSES);
        assertThat(testDiagnosis.getNumcauses()).isEqualTo(UPDATED_NUMCAUSES);
        assertThat(testDiagnosis.getEssuivemorta()).isEqualTo(UPDATED_ESSUIVEMORTA);
        assertThat(testDiagnosis.getEssuivemorb()).isEqualTo(UPDATED_ESSUIVEMORB);
        assertThat(testDiagnosis.getEpiclave()).isEqualTo(UPDATED_EPICLAVE);
        assertThat(testDiagnosis.getEpiclavedesc()).isEqualTo(UPDATED_EPICLAVEDESC);
        assertThat(testDiagnosis.getEssuivenotin()).isEqualTo(UPDATED_ESSUIVENOTIN);
        assertThat(testDiagnosis.getEssuiveestepi()).isEqualTo(UPDATED_ESSUIVEESTEPI);
        assertThat(testDiagnosis.getEssuiveestbrote()).isEqualTo(UPDATED_ESSUIVEESTBROTE);
        assertThat(testDiagnosis.getSinac()).isEqualTo(UPDATED_SINAC);
        assertThat(testDiagnosis.getDaga()).isEqualTo(UPDATED_DAGA);
        assertThat(testDiagnosis.getManifestaenfer()).isEqualTo(UPDATED_MANIFESTAENFER);

        // Validate the Diagnosis in Elasticsearch
        Diagnosis diagnosisEs = diagnosisSearchRepository.findOne(testDiagnosis.getId());
        assertThat(diagnosisEs).isEqualToComparingFieldByField(testDiagnosis);
    }

    @Test
    @Transactional
    public void updateNonExistingDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();

        // Create the Diagnosis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiagnosisMockMvc.perform(put("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isCreated());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);
        diagnosisSearchRepository.save(diagnosis);
        int databaseSizeBeforeDelete = diagnosisRepository.findAll().size();

        // Get the diagnosis
        restDiagnosisMockMvc.perform(delete("/api/diagnoses/{id}", diagnosis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean diagnosisExistsInEs = diagnosisSearchRepository.exists(diagnosis.getId());
        assertThat(diagnosisExistsInEs).isFalse();

        // Validate the database is empty
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);
        diagnosisSearchRepository.save(diagnosis);

        // Search the diagnosis
        restDiagnosisMockMvc.perform(get("/api/_search/diagnoses?query=id:" + diagnosis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosis.getId().intValue())))
            .andExpect(jsonPath("$.[*].letra").value(hasItem(DEFAULT_LETRA.toString())))
            .andExpect(jsonPath("$.[*].catalogkey").value(hasItem(DEFAULT_CATALOGKEY.toString())))
            .andExpect(jsonPath("$.[*].asterisco").value(hasItem(DEFAULT_ASTERISCO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].lsex").value(hasItem(DEFAULT_LSEX.toString())))
            .andExpect(jsonPath("$.[*].linf").value(hasItem(DEFAULT_LINF.toString())))
            .andExpect(jsonPath("$.[*].lsup").value(hasItem(DEFAULT_LSUP.toString())))
            .andExpect(jsonPath("$.[*].trivial").value(hasItem(DEFAULT_TRIVIAL.toString())))
            .andExpect(jsonPath("$.[*].erradicado").value(hasItem(DEFAULT_ERRADICADO.toString())))
            .andExpect(jsonPath("$.[*].ninter").value(hasItem(DEFAULT_NINTER.toString())))
            .andExpect(jsonPath("$.[*].nin").value(hasItem(DEFAULT_NIN.toString())))
            .andExpect(jsonPath("$.[*].ninmtobs").value(hasItem(DEFAULT_NINMTOBS.toString())))
            .andExpect(jsonPath("$.[*].nocbd").value(hasItem(DEFAULT_NOCBD.toString())))
            .andExpect(jsonPath("$.[*].noaph").value(hasItem(DEFAULT_NOAPH.toString())))
            .andExpect(jsonPath("$.[*].fetal").value(hasItem(DEFAULT_FETAL.toString())))
            .andExpect(jsonPath("$.[*].capitulo").value(hasItem(DEFAULT_CAPITULO)))
            .andExpect(jsonPath("$.[*].lista1").value(hasItem(DEFAULT_LISTA_1)))
            .andExpect(jsonPath("$.[*].grupo1").value(hasItem(DEFAULT_GRUPO_1)))
            .andExpect(jsonPath("$.[*].lista5").value(hasItem(DEFAULT_LISTA_5)))
            .andExpect(jsonPath("$.[*].actualizacionescie10").value(hasItem(DEFAULT_ACTUALIZACIONESCIE_10.toString())))
            .andExpect(jsonPath("$.[*].yearmodifi").value(hasItem(DEFAULT_YEARMODIFI.toString())))
            .andExpect(jsonPath("$.[*].yearaplicacion").value(hasItem(DEFAULT_YEARAPLICACION.toString())))
            .andExpect(jsonPath("$.[*].valid").value(hasItem(DEFAULT_VALID.toString())))
            .andExpect(jsonPath("$.[*].prinmorta").value(hasItem(DEFAULT_PRINMORTA)))
            .andExpect(jsonPath("$.[*].prinmorbi").value(hasItem(DEFAULT_PRINMORBI)))
            .andExpect(jsonPath("$.[*].lmmorbi").value(hasItem(DEFAULT_LMMORBI.toString())))
            .andExpect(jsonPath("$.[*].lmmorta").value(hasItem(DEFAULT_LMMORTA.toString())))
            .andExpect(jsonPath("$.[*].lgbd165").value(hasItem(DEFAULT_LGBD_165.toString())))
            .andExpect(jsonPath("$.[*].iomsbeck").value(hasItem(DEFAULT_IOMSBECK)))
            .andExpect(jsonPath("$.[*].lgbd190").value(hasItem(DEFAULT_LGBD_190.toString())))
            .andExpect(jsonPath("$.[*].notdiaria").value(hasItem(DEFAULT_NOTDIARIA.toString())))
            .andExpect(jsonPath("$.[*].notsemanal").value(hasItem(DEFAULT_NOTSEMANAL.toString())))
            .andExpect(jsonPath("$.[*].sistemaespecial").value(hasItem(DEFAULT_SISTEMAESPECIAL.toString())))
            .andExpect(jsonPath("$.[*].birmm").value(hasItem(DEFAULT_BIRMM.toString())))
            .andExpect(jsonPath("$.[*].causatype").value(hasItem(DEFAULT_CAUSATYPE)))
            .andExpect(jsonPath("$.[*].epimorta").value(hasItem(DEFAULT_EPIMORTA.toString())))
            .andExpect(jsonPath("$.[*].edasiras5Anios").value(hasItem(DEFAULT_EDASIRAS_5_ANIOS.toString())))
            .andExpect(jsonPath("$.[*].csvematernasseedepid").value(hasItem(DEFAULT_CSVEMATERNASSEEDEPID.toString())))
            .andExpect(jsonPath("$.[*].epimortam5").value(hasItem(DEFAULT_EPIMORTAM_5.toString())))
            .andExpect(jsonPath("$.[*].epimorbi").value(hasItem(DEFAULT_EPIMORBI.toString())))
            .andExpect(jsonPath("$.[*].defmaternas").value(hasItem(DEFAULT_DEFMATERNAS)))
            .andExpect(jsonPath("$.[*].escauses").value(hasItem(DEFAULT_ESCAUSES.toString())))
            .andExpect(jsonPath("$.[*].numcauses").value(hasItem(DEFAULT_NUMCAUSES.toString())))
            .andExpect(jsonPath("$.[*].essuivemorta").value(hasItem(DEFAULT_ESSUIVEMORTA.toString())))
            .andExpect(jsonPath("$.[*].essuivemorb").value(hasItem(DEFAULT_ESSUIVEMORB.toString())))
            .andExpect(jsonPath("$.[*].epiclave").value(hasItem(DEFAULT_EPICLAVE.toString())))
            .andExpect(jsonPath("$.[*].epiclavedesc").value(hasItem(DEFAULT_EPICLAVEDESC.toString())))
            .andExpect(jsonPath("$.[*].essuivenotin").value(hasItem(DEFAULT_ESSUIVENOTIN.toString())))
            .andExpect(jsonPath("$.[*].essuiveestepi").value(hasItem(DEFAULT_ESSUIVEESTEPI.toString())))
            .andExpect(jsonPath("$.[*].essuiveestbrote").value(hasItem(DEFAULT_ESSUIVEESTBROTE.toString())))
            .andExpect(jsonPath("$.[*].sinac").value(hasItem(DEFAULT_SINAC.toString())))
            .andExpect(jsonPath("$.[*].daga").value(hasItem(DEFAULT_DAGA.toString())))
            .andExpect(jsonPath("$.[*].manifestaenfer").value(hasItem(DEFAULT_MANIFESTAENFER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diagnosis.class);
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setId(1L);
        Diagnosis diagnosis2 = new Diagnosis();
        diagnosis2.setId(diagnosis1.getId());
        assertThat(diagnosis1).isEqualTo(diagnosis2);
        diagnosis2.setId(2L);
        assertThat(diagnosis1).isNotEqualTo(diagnosis2);
        diagnosis1.setId(null);
        assertThat(diagnosis1).isNotEqualTo(diagnosis2);
    }
}
