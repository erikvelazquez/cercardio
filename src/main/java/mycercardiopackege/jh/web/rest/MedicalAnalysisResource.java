package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.MedicalAnalysis;

import mycercardiopackege.jh.repository.MedicalAnalysisRepository;
import mycercardiopackege.jh.repository.search.MedicalAnalysisSearchRepository;
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
 * REST controller for managing MedicalAnalysis.
 */
@RestController
@RequestMapping("/api")
public class MedicalAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(MedicalAnalysisResource.class);

    private static final String ENTITY_NAME = "medicalAnalysis";

    private final MedicalAnalysisRepository medicalAnalysisRepository;

    private final MedicalAnalysisSearchRepository medicalAnalysisSearchRepository;

    public MedicalAnalysisResource(MedicalAnalysisRepository medicalAnalysisRepository, MedicalAnalysisSearchRepository medicalAnalysisSearchRepository) {
        this.medicalAnalysisRepository = medicalAnalysisRepository;
        this.medicalAnalysisSearchRepository = medicalAnalysisSearchRepository;
    }

    /**
     * POST  /medical-analyses : Create a new medicalAnalysis.
     *
     * @param medicalAnalysis the medicalAnalysis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicalAnalysis, or with status 400 (Bad Request) if the medicalAnalysis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medical-analyses")
    @Timed
    public ResponseEntity<MedicalAnalysis> createMedicalAnalysis(@RequestBody MedicalAnalysis medicalAnalysis) throws URISyntaxException {
        log.debug("REST request to save MedicalAnalysis : {}", medicalAnalysis);
        if (medicalAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new medicalAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicalAnalysis result = medicalAnalysisRepository.save(medicalAnalysis);
        medicalAnalysisSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medical-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medical-analyses : Updates an existing medicalAnalysis.
     *
     * @param medicalAnalysis the medicalAnalysis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicalAnalysis,
     * or with status 400 (Bad Request) if the medicalAnalysis is not valid,
     * or with status 500 (Internal Server Error) if the medicalAnalysis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medical-analyses")
    @Timed
    public ResponseEntity<MedicalAnalysis> updateMedicalAnalysis(@RequestBody MedicalAnalysis medicalAnalysis) throws URISyntaxException {
        log.debug("REST request to update MedicalAnalysis : {}", medicalAnalysis);
        if (medicalAnalysis.getId() == null) {
            return createMedicalAnalysis(medicalAnalysis);
        }
        MedicalAnalysis result = medicalAnalysisRepository.save(medicalAnalysis);
        medicalAnalysisSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicalAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medical-analyses : get all the medicalAnalyses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicalAnalyses in body
     */
    @GetMapping("/medical-analyses")
    @Timed
    public List<MedicalAnalysis> getAllMedicalAnalyses() {
        log.debug("REST request to get all MedicalAnalyses");
        return medicalAnalysisRepository.findAll();
        }

    /**
     * GET  /medical-analyses/:id : get the "id" medicalAnalysis.
     *
     * @param id the id of the medicalAnalysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicalAnalysis, or with status 404 (Not Found)
     */
    @GetMapping("/medical-analyses/{id}")
    @Timed
    public ResponseEntity<MedicalAnalysis> getMedicalAnalysis(@PathVariable Long id) {
        log.debug("REST request to get MedicalAnalysis : {}", id);
        MedicalAnalysis medicalAnalysis = medicalAnalysisRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicalAnalysis));
    }

    /**
     * DELETE  /medical-analyses/:id : delete the "id" medicalAnalysis.
     *
     * @param id the id of the medicalAnalysis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medical-analyses/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicalAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete MedicalAnalysis : {}", id);
        medicalAnalysisRepository.delete(id);
        medicalAnalysisSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medical-analyses?query=:query : search for the medicalAnalysis corresponding
     * to the query.
     *
     * @param query the query of the medicalAnalysis search
     * @return the result of the search
     */
    @GetMapping("/_search/medical-analyses")
    @Timed
    public List<MedicalAnalysis> searchMedicalAnalyses(@RequestParam String query) {
        log.debug("REST request to search MedicalAnalyses for query {}", query);
        return StreamSupport
            .stream(medicalAnalysisSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
