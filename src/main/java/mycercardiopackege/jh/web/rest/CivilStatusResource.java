package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.CivilStatus;

import mycercardiopackege.jh.repository.CivilStatusRepository;
import mycercardiopackege.jh.repository.search.CivilStatusSearchRepository;
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
 * REST controller for managing CivilStatus.
 */
@RestController
@RequestMapping("/api")
public class CivilStatusResource {

    private final Logger log = LoggerFactory.getLogger(CivilStatusResource.class);

    private static final String ENTITY_NAME = "civilStatus";

    private final CivilStatusRepository civilStatusRepository;

    private final CivilStatusSearchRepository civilStatusSearchRepository;

    public CivilStatusResource(CivilStatusRepository civilStatusRepository, CivilStatusSearchRepository civilStatusSearchRepository) {
        this.civilStatusRepository = civilStatusRepository;
        this.civilStatusSearchRepository = civilStatusSearchRepository;
    }

    /**
     * POST  /civil-statuses : Create a new civilStatus.
     *
     * @param civilStatus the civilStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new civilStatus, or with status 400 (Bad Request) if the civilStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/civil-statuses")
    @Timed
    public ResponseEntity<CivilStatus> createCivilStatus(@RequestBody CivilStatus civilStatus) throws URISyntaxException {
        log.debug("REST request to save CivilStatus : {}", civilStatus);
        if (civilStatus.getId() != null) {
            throw new BadRequestAlertException("A new civilStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CivilStatus result = civilStatusRepository.save(civilStatus);
        civilStatusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/civil-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /civil-statuses : Updates an existing civilStatus.
     *
     * @param civilStatus the civilStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated civilStatus,
     * or with status 400 (Bad Request) if the civilStatus is not valid,
     * or with status 500 (Internal Server Error) if the civilStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/civil-statuses")
    @Timed
    public ResponseEntity<CivilStatus> updateCivilStatus(@RequestBody CivilStatus civilStatus) throws URISyntaxException {
        log.debug("REST request to update CivilStatus : {}", civilStatus);
        if (civilStatus.getId() == null) {
            return createCivilStatus(civilStatus);
        }
        CivilStatus result = civilStatusRepository.save(civilStatus);
        civilStatusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, civilStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /civil-statuses : get all the civilStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of civilStatuses in body
     */
    @GetMapping("/civil-statuses")
    @Timed
    public List<CivilStatus> getAllCivilStatuses() {
        log.debug("REST request to get all CivilStatuses");
        return civilStatusRepository.findAll();
        }

    /**
     * GET  /civil-statuses/:id : get the "id" civilStatus.
     *
     * @param id the id of the civilStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the civilStatus, or with status 404 (Not Found)
     */
    @GetMapping("/civil-statuses/{id}")
    @Timed
    public ResponseEntity<CivilStatus> getCivilStatus(@PathVariable Long id) {
        log.debug("REST request to get CivilStatus : {}", id);
        CivilStatus civilStatus = civilStatusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(civilStatus));
    }

    /**
     * DELETE  /civil-statuses/:id : delete the "id" civilStatus.
     *
     * @param id the id of the civilStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/civil-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCivilStatus(@PathVariable Long id) {
        log.debug("REST request to delete CivilStatus : {}", id);
        civilStatusRepository.delete(id);
        civilStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/civil-statuses?query=:query : search for the civilStatus corresponding
     * to the query.
     *
     * @param query the query of the civilStatus search
     * @return the result of the search
     */
    @GetMapping("/_search/civil-statuses")
    @Timed
    public List<CivilStatus> searchCivilStatuses(@RequestParam String query) {
        log.debug("REST request to search CivilStatuses for query {}", query);
        return StreamSupport
            .stream(civilStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
