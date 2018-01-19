package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Medical_Procedures;

import mycercardiopackege.jh.repository.Medical_ProceduresRepository;
import mycercardiopackege.jh.repository.search.Medical_ProceduresSearchRepository;
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
 * REST controller for managing Medical_Procedures.
 */
@RestController
@RequestMapping("/api")
public class Medical_ProceduresResource {

    private final Logger log = LoggerFactory.getLogger(Medical_ProceduresResource.class);

    private static final String ENTITY_NAME = "medical_Procedures";

    private final Medical_ProceduresRepository medical_ProceduresRepository;

    private final Medical_ProceduresSearchRepository medical_ProceduresSearchRepository;

    public Medical_ProceduresResource(Medical_ProceduresRepository medical_ProceduresRepository, Medical_ProceduresSearchRepository medical_ProceduresSearchRepository) {
        this.medical_ProceduresRepository = medical_ProceduresRepository;
        this.medical_ProceduresSearchRepository = medical_ProceduresSearchRepository;
    }

    /**
     * POST  /medical-procedures : Create a new medical_Procedures.
     *
     * @param medical_Procedures the medical_Procedures to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medical_Procedures, or with status 400 (Bad Request) if the medical_Procedures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-procedures")
    @Timed
    public ResponseEntity<Medical_Procedures> createMedical_Procedures(@RequestBody Medical_Procedures medical_Procedures) throws URISyntaxException {
        log.debug("REST request to save Medical_Procedures : {}", medical_Procedures);
        if (medical_Procedures.getId() != null) {
            throw new BadRequestAlertException("A new medical_Procedures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medical_Procedures result = medical_ProceduresRepository.save(medical_Procedures);
        medical_ProceduresSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medical-procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-procedures : Updates an existing medical_Procedures.
     *
     * @param medical_Procedures the medical_Procedures to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medical_Procedures,
     * or with status 400 (Bad Request) if the medical_Procedures is not valid,
     * or with status 500 (Internal Server Error) if the medical_Procedures couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-procedures")
    @Timed
    public ResponseEntity<Medical_Procedures> updateMedical_Procedures(@RequestBody Medical_Procedures medical_Procedures) throws URISyntaxException {
        log.debug("REST request to update Medical_Procedures : {}", medical_Procedures);
        if (medical_Procedures.getId() == null) {
            return createMedical_Procedures(medical_Procedures);
        }
        Medical_Procedures result = medical_ProceduresRepository.save(medical_Procedures);
        medical_ProceduresSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medical_Procedures.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-procedures : get all the medical_Procedures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medical_Procedures in body
     */
    @GetMapping("/medical-procedures")
    @Timed
    public List<Medical_Procedures> getAllMedical_Procedures() {
        log.debug("REST request to get all Medical_Procedures");
        return medical_ProceduresRepository.findAll();
        }

    /**
     * GET  /medical-procedures/:id : get the "id" medical_Procedures.
     *
     * @param id the id of the medical_Procedures to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medical_Procedures, or with status 404 (Not Found)
     */
    @GetMapping("/medical-procedures/{id}")
    @Timed
    public ResponseEntity<Medical_Procedures> getMedical_Procedures(@PathVariable Long id) {
        log.debug("REST request to get Medical_Procedures : {}", id);
        Medical_Procedures medical_Procedures = medical_ProceduresRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medical_Procedures));
    }

    /**
     * DELETE  /medical-procedures/:id : delete the "id" medical_Procedures.
     *
     * @param id the id of the medical_Procedures to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-procedures/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedical_Procedures(@PathVariable Long id) {
        log.debug("REST request to delete Medical_Procedures : {}", id);
        medical_ProceduresRepository.delete(id);
        medical_ProceduresSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medical-procedures?query=:query : search for the medical_Procedures corresponding
     * to the query.
     *
     * @param query the query of the medical_Procedures search
     * @return the result of the search
     */
    @GetMapping("/_search/medical-procedures")
    @Timed
    public List<Medical_Procedures> searchMedical_Procedures(@RequestParam String query) {
        log.debug("REST request to search Medical_Procedures for query {}", query);
        return StreamSupport
            .stream(medical_ProceduresSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
