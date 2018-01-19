package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientMedicalAnalysis;

import mycercardiopackege.jh.repository.PacientMedicalAnalysisRepository;
import mycercardiopackege.jh.repository.search.PacientMedicalAnalysisSearchRepository;
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
 * REST controller for managing PacientMedicalAnalysis.
 */
@RestController
@RequestMapping("/api")
public class PacientMedicalAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(PacientMedicalAnalysisResource.class);

    private static final String ENTITY_NAME = "pacientMedicalAnalysis";

    private final PacientMedicalAnalysisRepository pacientMedicalAnalysisRepository;

    private final PacientMedicalAnalysisSearchRepository pacientMedicalAnalysisSearchRepository;

    public PacientMedicalAnalysisResource(PacientMedicalAnalysisRepository pacientMedicalAnalysisRepository, PacientMedicalAnalysisSearchRepository pacientMedicalAnalysisSearchRepository) {
        this.pacientMedicalAnalysisRepository = pacientMedicalAnalysisRepository;
        this.pacientMedicalAnalysisSearchRepository = pacientMedicalAnalysisSearchRepository;
    }

    /**
     * POST  /pacient-medical-analyses : Create a new pacientMedicalAnalysis.
     *
     * @param pacientMedicalAnalysis the pacientMedicalAnalysis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientMedicalAnalysis, or with status 400 (Bad Request) if the pacientMedicalAnalysis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-medical-analyses")
    @Timed
    public ResponseEntity<PacientMedicalAnalysis> createPacientMedicalAnalysis(@RequestBody PacientMedicalAnalysis pacientMedicalAnalysis) throws URISyntaxException {
        log.debug("REST request to save PacientMedicalAnalysis : {}", pacientMedicalAnalysis);
        if (pacientMedicalAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new pacientMedicalAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientMedicalAnalysis result = pacientMedicalAnalysisRepository.save(pacientMedicalAnalysis);
        pacientMedicalAnalysisSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-medical-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-medical-analyses : Updates an existing pacientMedicalAnalysis.
     *
     * @param pacientMedicalAnalysis the pacientMedicalAnalysis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientMedicalAnalysis,
     * or with status 400 (Bad Request) if the pacientMedicalAnalysis is not valid,
     * or with status 500 (Internal Server Error) if the pacientMedicalAnalysis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-medical-analyses")
    @Timed
    public ResponseEntity<PacientMedicalAnalysis> updatePacientMedicalAnalysis(@RequestBody PacientMedicalAnalysis pacientMedicalAnalysis) throws URISyntaxException {
        log.debug("REST request to update PacientMedicalAnalysis : {}", pacientMedicalAnalysis);
        if (pacientMedicalAnalysis.getId() == null) {
            return createPacientMedicalAnalysis(pacientMedicalAnalysis);
        }
        PacientMedicalAnalysis result = pacientMedicalAnalysisRepository.save(pacientMedicalAnalysis);
        pacientMedicalAnalysisSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientMedicalAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-medical-analyses : get all the pacientMedicalAnalyses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientMedicalAnalyses in body
     */
    @GetMapping("/pacient-medical-analyses")
    @Timed
    public List<PacientMedicalAnalysis> getAllPacientMedicalAnalyses() {
        log.debug("REST request to get all PacientMedicalAnalyses");
        return pacientMedicalAnalysisRepository.findAll();
        }

    /**
     * GET  /pacient-medical-analyses/:id : get the "id" pacientMedicalAnalysis.
     *
     * @param id the id of the pacientMedicalAnalysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientMedicalAnalysis, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-medical-analyses/{id}")
    @Timed
    public ResponseEntity<PacientMedicalAnalysis> getPacientMedicalAnalysis(@PathVariable Long id) {
        log.debug("REST request to get PacientMedicalAnalysis : {}", id);
        PacientMedicalAnalysis pacientMedicalAnalysis = pacientMedicalAnalysisRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientMedicalAnalysis));
    }

    /**
     * DELETE  /pacient-medical-analyses/:id : delete the "id" pacientMedicalAnalysis.
     *
     * @param id the id of the pacientMedicalAnalysis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-medical-analyses/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientMedicalAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete PacientMedicalAnalysis : {}", id);
        pacientMedicalAnalysisRepository.delete(id);
        pacientMedicalAnalysisSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-medical-analyses?query=:query : search for the pacientMedicalAnalysis corresponding
     * to the query.
     *
     * @param query the query of the pacientMedicalAnalysis search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-medical-analyses")
    @Timed
    public List<PacientMedicalAnalysis> searchPacientMedicalAnalyses(@RequestParam String query) {
        log.debug("REST request to search PacientMedicalAnalyses for query {}", query);
        return StreamSupport
            .stream(pacientMedicalAnalysisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
