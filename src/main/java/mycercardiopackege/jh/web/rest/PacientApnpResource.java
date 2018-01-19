package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientApnp;

import mycercardiopackege.jh.repository.PacientApnpRepository;
import mycercardiopackege.jh.repository.search.PacientApnpSearchRepository;
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
 * REST controller for managing PacientApnp.
 */
@RestController
@RequestMapping("/api")
public class PacientApnpResource {

    private final Logger log = LoggerFactory.getLogger(PacientApnpResource.class);

    private static final String ENTITY_NAME = "pacientApnp";

    private final PacientApnpRepository pacientApnpRepository;

    private final PacientApnpSearchRepository pacientApnpSearchRepository;

    public PacientApnpResource(PacientApnpRepository pacientApnpRepository, PacientApnpSearchRepository pacientApnpSearchRepository) {
        this.pacientApnpRepository = pacientApnpRepository;
        this.pacientApnpSearchRepository = pacientApnpSearchRepository;
    }

    /**
     * POST  /pacient-apnps : Create a new pacientApnp.
     *
     * @param pacientApnp the pacientApnp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientApnp, or with status 400 (Bad Request) if the pacientApnp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-apnps")
    @Timed
    public ResponseEntity<PacientApnp> createPacientApnp(@RequestBody PacientApnp pacientApnp) throws URISyntaxException {
        log.debug("REST request to save PacientApnp : {}", pacientApnp);
        if (pacientApnp.getId() != null) {
            throw new BadRequestAlertException("A new pacientApnp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientApnp result = pacientApnpRepository.save(pacientApnp);
        pacientApnpSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-apnps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-apnps : Updates an existing pacientApnp.
     *
     * @param pacientApnp the pacientApnp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientApnp,
     * or with status 400 (Bad Request) if the pacientApnp is not valid,
     * or with status 500 (Internal Server Error) if the pacientApnp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-apnps")
    @Timed
    public ResponseEntity<PacientApnp> updatePacientApnp(@RequestBody PacientApnp pacientApnp) throws URISyntaxException {
        log.debug("REST request to update PacientApnp : {}", pacientApnp);
        if (pacientApnp.getId() == null) {
            return createPacientApnp(pacientApnp);
        }
        PacientApnp result = pacientApnpRepository.save(pacientApnp);
        pacientApnpSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientApnp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-apnps : get all the pacientApnps.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientApnps in body
     */
    @GetMapping("/pacient-apnps")
    @Timed
    public List<PacientApnp> getAllPacientApnps() {
        log.debug("REST request to get all PacientApnps");
        return pacientApnpRepository.findAll();
        }

    /**
     * GET  /pacient-apnps/:id : get the "id" pacientApnp.
     *
     * @param id the id of the pacientApnp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientApnp, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-apnps/{id}")
    @Timed
    public ResponseEntity<PacientApnp> getPacientApnp(@PathVariable Long id) {
        log.debug("REST request to get PacientApnp : {}", id);
        PacientApnp pacientApnp = pacientApnpRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientApnp));
    }

    /**
     * DELETE  /pacient-apnps/:id : delete the "id" pacientApnp.
     *
     * @param id the id of the pacientApnp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-apnps/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientApnp(@PathVariable Long id) {
        log.debug("REST request to delete PacientApnp : {}", id);
        pacientApnpRepository.delete(id);
        pacientApnpSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-apnps?query=:query : search for the pacientApnp corresponding
     * to the query.
     *
     * @param query the query of the pacientApnp search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-apnps")
    @Timed
    public List<PacientApnp> searchPacientApnps(@RequestParam String query) {
        log.debug("REST request to search PacientApnps for query {}", query);
        return StreamSupport
            .stream(pacientApnpSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
