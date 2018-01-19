package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientMedicalData;

import mycercardiopackege.jh.repository.PacientMedicalDataRepository;
import mycercardiopackege.jh.repository.search.PacientMedicalDataSearchRepository;
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
 * REST controller for managing PacientMedicalData.
 */
@RestController
@RequestMapping("/api")
public class PacientMedicalDataResource {

    private final Logger log = LoggerFactory.getLogger(PacientMedicalDataResource.class);

    private static final String ENTITY_NAME = "pacientMedicalData";

    private final PacientMedicalDataRepository pacientMedicalDataRepository;

    private final PacientMedicalDataSearchRepository pacientMedicalDataSearchRepository;

    public PacientMedicalDataResource(PacientMedicalDataRepository pacientMedicalDataRepository, PacientMedicalDataSearchRepository pacientMedicalDataSearchRepository) {
        this.pacientMedicalDataRepository = pacientMedicalDataRepository;
        this.pacientMedicalDataSearchRepository = pacientMedicalDataSearchRepository;
    }

    /**
     * POST  /pacient-medical-data : Create a new pacientMedicalData.
     *
     * @param pacientMedicalData the pacientMedicalData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientMedicalData, or with status 400 (Bad Request) if the pacientMedicalData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-medical-data")
    @Timed
    public ResponseEntity<PacientMedicalData> createPacientMedicalData(@RequestBody PacientMedicalData pacientMedicalData) throws URISyntaxException {
        log.debug("REST request to save PacientMedicalData : {}", pacientMedicalData);
        if (pacientMedicalData.getId() != null) {
            throw new BadRequestAlertException("A new pacientMedicalData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientMedicalData result = pacientMedicalDataRepository.save(pacientMedicalData);
        pacientMedicalDataSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-medical-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-medical-data : Updates an existing pacientMedicalData.
     *
     * @param pacientMedicalData the pacientMedicalData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientMedicalData,
     * or with status 400 (Bad Request) if the pacientMedicalData is not valid,
     * or with status 500 (Internal Server Error) if the pacientMedicalData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-medical-data")
    @Timed
    public ResponseEntity<PacientMedicalData> updatePacientMedicalData(@RequestBody PacientMedicalData pacientMedicalData) throws URISyntaxException {
        log.debug("REST request to update PacientMedicalData : {}", pacientMedicalData);
        if (pacientMedicalData.getId() == null) {
            return createPacientMedicalData(pacientMedicalData);
        }
        PacientMedicalData result = pacientMedicalDataRepository.save(pacientMedicalData);
        pacientMedicalDataSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientMedicalData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-medical-data : get all the pacientMedicalData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientMedicalData in body
     */
    @GetMapping("/pacient-medical-data")
    @Timed
    public List<PacientMedicalData> getAllPacientMedicalData() {
        log.debug("REST request to get all PacientMedicalData");
        return pacientMedicalDataRepository.findAll();
        }

    /**
     * GET  /pacient-medical-data/:id : get the "id" pacientMedicalData.
     *
     * @param id the id of the pacientMedicalData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientMedicalData, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-medical-data/{id}")
    @Timed
    public ResponseEntity<PacientMedicalData> getPacientMedicalData(@PathVariable Long id) {
        log.debug("REST request to get PacientMedicalData : {}", id);
        PacientMedicalData pacientMedicalData = pacientMedicalDataRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientMedicalData));
    }

    /**
     * DELETE  /pacient-medical-data/:id : delete the "id" pacientMedicalData.
     *
     * @param id the id of the pacientMedicalData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-medical-data/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientMedicalData(@PathVariable Long id) {
        log.debug("REST request to delete PacientMedicalData : {}", id);
        pacientMedicalDataRepository.delete(id);
        pacientMedicalDataSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-medical-data?query=:query : search for the pacientMedicalData corresponding
     * to the query.
     *
     * @param query the query of the pacientMedicalData search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-medical-data")
    @Timed
    public List<PacientMedicalData> searchPacientMedicalData(@RequestParam String query) {
        log.debug("REST request to search PacientMedicalData for query {}", query);
        return StreamSupport
            .stream(pacientMedicalDataSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
