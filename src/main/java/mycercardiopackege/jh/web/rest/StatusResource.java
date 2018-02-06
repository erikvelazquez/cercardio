package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Status;

import mycercardiopackege.jh.repository.StatusRepository;
import mycercardiopackege.jh.repository.search.StatusSearchRepository;
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
 * REST controller for managing Status.
 */
@RestController
@RequestMapping("/api")
public class StatusResource {

    private final Logger log = LoggerFactory.getLogger(StatusResource.class);

    private static final String ENTITY_NAME = "status";

    private final StatusRepository statusRepository;

    private final StatusSearchRepository statusSearchRepository;

    public StatusResource(StatusRepository statusRepository, StatusSearchRepository statusSearchRepository) {
        this.statusRepository = statusRepository;
        this.statusSearchRepository = statusSearchRepository;
    }

    /**
     * POST  /statuses : Create a new status.
     *
     * @param status the status to create
     * @return the ResponseEntity with status 201 (Created) and with body the new status, or with status 400 (Bad Request) if the status has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statuses")
    @Timed
    public ResponseEntity<Status> createStatus(@RequestBody Status status) throws URISyntaxException {
        log.debug("REST request to save Status : {}", status);
        if (status.getId() != null) {
            throw new BadRequestAlertException("A new status cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Status result = statusRepository.save(status);
        statusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statuses : Updates an existing status.
     *
     * @param status the status to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated status,
     * or with status 400 (Bad Request) if the status is not valid,
     * or with status 500 (Internal Server Error) if the status couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statuses")
    @Timed
    public ResponseEntity<Status> updateStatus(@RequestBody Status status) throws URISyntaxException {
        log.debug("REST request to update Status : {}", status);
        if (status.getId() == null) {
            return createStatus(status);
        }
        Status result = statusRepository.save(status);
        statusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, status.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statuses : get all the statuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of statuses in body
     */
    @GetMapping("/statuses")
    @Timed
    public List<Status> getAllStatuses() {
        log.debug("REST request to get all Statuses");
        return statusRepository.findAll();
        }

    /**
     * GET  /statuses/:id : get the "id" status.
     *
     * @param id the id of the status to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the status, or with status 404 (Not Found)
     */
    @GetMapping("/statuses/{id}")
    @Timed
    public ResponseEntity<Status> getStatus(@PathVariable Long id) {
        log.debug("REST request to get Status : {}", id);
        Status status = statusRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(status));
    }

    /**
     * DELETE  /statuses/:id : delete the "id" status.
     *
     * @param id the id of the status to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        log.debug("REST request to delete Status : {}", id);
        statusRepository.delete(id);
        statusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/statuses?query=:query : search for the status corresponding
     * to the query.
     *
     * @param query the query of the status search
     * @return the result of the search
     */
    @GetMapping("/_search/statuses")
    @Timed
    public List<Status> searchStatuses(@RequestParam String query) {
        log.debug("REST request to search Statuses for query {}", query);
        return StreamSupport
            .stream(statusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
