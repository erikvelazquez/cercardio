package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientApp;

import mycercardiopackege.jh.repository.PacientAppRepository;
import mycercardiopackege.jh.repository.search.PacientAppSearchRepository;
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
 * REST controller for managing PacientApp.
 */
@RestController
@RequestMapping("/api")
public class PacientAppResource {

    private final Logger log = LoggerFactory.getLogger(PacientAppResource.class);

    private static final String ENTITY_NAME = "pacientApp";

    private final PacientAppRepository pacientAppRepository;

    private final PacientAppSearchRepository pacientAppSearchRepository;

    public PacientAppResource(PacientAppRepository pacientAppRepository, PacientAppSearchRepository pacientAppSearchRepository) {
        this.pacientAppRepository = pacientAppRepository;
        this.pacientAppSearchRepository = pacientAppSearchRepository;
    }

    /**
     * POST  /pacient-apps : Create a new pacientApp.
     *
     * @param pacientApp the pacientApp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientApp, or with status 400 (Bad Request) if the pacientApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-apps")
    @Timed
    public ResponseEntity<PacientApp> createPacientApp(@RequestBody PacientApp pacientApp) throws URISyntaxException {
        log.debug("REST request to save PacientApp : {}", pacientApp);
        if (pacientApp.getId() != null) {
            throw new BadRequestAlertException("A new pacientApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientApp result = pacientAppRepository.save(pacientApp);
        pacientAppSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-apps : Updates an existing pacientApp.
     *
     * @param pacientApp the pacientApp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientApp,
     * or with status 400 (Bad Request) if the pacientApp is not valid,
     * or with status 500 (Internal Server Error) if the pacientApp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-apps")
    @Timed
    public ResponseEntity<PacientApp> updatePacientApp(@RequestBody PacientApp pacientApp) throws URISyntaxException {
        log.debug("REST request to update PacientApp : {}", pacientApp);
        if (pacientApp.getId() == null) {
            return createPacientApp(pacientApp);
        }
        PacientApp result = pacientAppRepository.save(pacientApp);
        pacientAppSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientApp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-apps : get all the pacientApps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientApps in body
     */
    @GetMapping("/pacient-apps")
    @Timed
    public List<PacientApp> getAllPacientApps() {
        log.debug("REST request to get all PacientApps");
        return pacientAppRepository.findAll();
        }

    /**
     * GET  /pacient-apps/:id : get the "id" pacientApp.
     *
     * @param id the id of the pacientApp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientApp, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-apps/{id}")
    @Timed
    public ResponseEntity<PacientApp> getPacientApp(@PathVariable Long id) {
        log.debug("REST request to get PacientApp : {}", id);
        PacientApp pacientApp = pacientAppRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientApp));
    }

    /**
     * DELETE  /pacient-apps/:id : delete the "id" pacientApp.
     *
     * @param id the id of the pacientApp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-apps/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientApp(@PathVariable Long id) {
        log.debug("REST request to delete PacientApp : {}", id);
        pacientAppRepository.delete(id);
        pacientAppSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-apps?query=:query : search for the pacientApp corresponding
     * to the query.
     *
     * @param query the query of the pacientApp search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-apps")
    @Timed
    public List<PacientApp> searchPacientApps(@RequestParam String query) {
        log.debug("REST request to search PacientApps for query {}", query);
        return StreamSupport
            .stream(pacientAppSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
