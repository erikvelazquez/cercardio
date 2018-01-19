package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Diagnosis;

import mycercardiopackege.jh.repository.DiagnosisRepository;
import mycercardiopackege.jh.repository.search.DiagnosisSearchRepository;
import mycercardiopackege.jh.web.rest.errors.BadRequestAlertException;
import mycercardiopackege.jh.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Diagnosis.
 */
@RestController
@RequestMapping("/api")
public class DiagnosisResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosisResource.class);

    private static final String ENTITY_NAME = "diagnosis";

    private final DiagnosisRepository diagnosisRepository;

    private final DiagnosisSearchRepository diagnosisSearchRepository;

    public DiagnosisResource(DiagnosisRepository diagnosisRepository, DiagnosisSearchRepository diagnosisSearchRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.diagnosisSearchRepository = diagnosisSearchRepository;
    }

    /**
     * POST  /diagnoses : Create a new diagnosis.
     *
     * @param diagnosis the diagnosis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diagnosis, or with status 400 (Bad Request) if the diagnosis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diagnoses")
    @Timed
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) throws URISyntaxException {
        log.debug("REST request to save Diagnosis : {}", diagnosis);
        if (diagnosis.getId() != null) {
            throw new BadRequestAlertException("A new diagnosis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diagnosis result = diagnosisRepository.save(diagnosis);
        diagnosisSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/diagnoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diagnoses : Updates an existing diagnosis.
     *
     * @param diagnosis the diagnosis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diagnosis,
     * or with status 400 (Bad Request) if the diagnosis is not valid,
     * or with status 500 (Internal Server Error) if the diagnosis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diagnoses")
    @Timed
    public ResponseEntity<Diagnosis> updateDiagnosis(@RequestBody Diagnosis diagnosis) throws URISyntaxException {
        log.debug("REST request to update Diagnosis : {}", diagnosis);
        if (diagnosis.getId() == null) {
            return createDiagnosis(diagnosis);
        }
        Diagnosis result = diagnosisRepository.save(diagnosis);
        diagnosisSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diagnosis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diagnoses : get all the diagnoses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of diagnoses in body
     */
    @GetMapping("/diagnoses")
    @Timed
    public List<Diagnosis> getAllDiagnoses() {
        log.debug("REST request to get all Diagnoses");
        return diagnosisRepository.findAll();
        }

    /**
     * GET  /diagnoses/:id : get the "id" diagnosis.
     *
     * @param id the id of the diagnosis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diagnosis, or with status 404 (Not Found)
     */
    @GetMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<Diagnosis> getDiagnosis(@PathVariable Long id) {
        log.debug("REST request to get Diagnosis : {}", id);
        Diagnosis diagnosis = diagnosisRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(diagnosis));
    }

    /**
     * DELETE  /diagnoses/:id : delete the "id" diagnosis.
     *
     * @param id the id of the diagnosis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diagnoses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        log.debug("REST request to delete Diagnosis : {}", id);
        diagnosisRepository.delete(id);
        diagnosisSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/diagnoses?query=:query : search for the diagnosis corresponding
     * to the query.
     *
     * @param query the query of the diagnosis search
     * @return the result of the search
     */
    @GetMapping("/_search/diagnoses")
    @Timed
    public List<Diagnosis> searchDiagnoses(@RequestParam String query) {
        log.debug("REST request to search Diagnoses for query {}", query);
        return StreamSupport
            .stream(diagnosisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
