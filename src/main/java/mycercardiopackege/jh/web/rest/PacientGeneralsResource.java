package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientGenerals;

import mycercardiopackege.jh.repository.PacientGeneralsRepository;
import mycercardiopackege.jh.repository.search.PacientGeneralsSearchRepository;
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
 * REST controller for managing PacientGenerals.
 */
@RestController
@RequestMapping("/api")
public class PacientGeneralsResource {

    private final Logger log = LoggerFactory.getLogger(PacientGeneralsResource.class);

    private static final String ENTITY_NAME = "pacientGenerals";

    private final PacientGeneralsRepository pacientGeneralsRepository;

    private final PacientGeneralsSearchRepository pacientGeneralsSearchRepository;

    public PacientGeneralsResource(PacientGeneralsRepository pacientGeneralsRepository, PacientGeneralsSearchRepository pacientGeneralsSearchRepository) {
        this.pacientGeneralsRepository = pacientGeneralsRepository;
        this.pacientGeneralsSearchRepository = pacientGeneralsSearchRepository;
    }

    /**
     * POST  /pacient-generals : Create a new pacientGenerals.
     *
     * @param pacientGenerals the pacientGenerals to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientGenerals, or with status 400 (Bad Request) if the pacientGenerals has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-generals")
    @Timed
    public ResponseEntity<PacientGenerals> createPacientGenerals(@RequestBody PacientGenerals pacientGenerals) throws URISyntaxException {
        log.debug("REST request to save PacientGenerals : {}", pacientGenerals);
        if (pacientGenerals.getId() != null) {
            throw new BadRequestAlertException("A new pacientGenerals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientGenerals result = pacientGeneralsRepository.save(pacientGenerals);
        pacientGeneralsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-generals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-generals : Updates an existing pacientGenerals.
     *
     * @param pacientGenerals the pacientGenerals to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientGenerals,
     * or with status 400 (Bad Request) if the pacientGenerals is not valid,
     * or with status 500 (Internal Server Error) if the pacientGenerals couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-generals")
    @Timed
    public ResponseEntity<PacientGenerals> updatePacientGenerals(@RequestBody PacientGenerals pacientGenerals) throws URISyntaxException {
        log.debug("REST request to update PacientGenerals : {}", pacientGenerals);
        if (pacientGenerals.getId() == null) {
            return createPacientGenerals(pacientGenerals);
        }
        PacientGenerals result = pacientGeneralsRepository.save(pacientGenerals);
        pacientGeneralsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientGenerals.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-generals : get all the pacientGenerals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientGenerals in body
     */
    @GetMapping("/pacient-generals")
    @Timed
    public List<PacientGenerals> getAllPacientGenerals() {
        log.debug("REST request to get all PacientGenerals");
        return pacientGeneralsRepository.findAll();
        }

    /**
     * GET  /pacient-generals/:id : get the "id" pacientGenerals.
     *
     * @param id the id of the pacientGenerals to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientGenerals, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-generals/{id}")
    @Timed
    public ResponseEntity<PacientGenerals> getPacientGenerals(@PathVariable Long id) {
        log.debug("REST request to get PacientGenerals : {}", id);
        PacientGenerals pacientGenerals = pacientGeneralsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientGenerals));
    }

    /**
     * DELETE  /pacient-generals/:id : delete the "id" pacientGenerals.
     *
     * @param id the id of the pacientGenerals to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-generals/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientGenerals(@PathVariable Long id) {
        log.debug("REST request to delete PacientGenerals : {}", id);
        pacientGeneralsRepository.delete(id);
        pacientGeneralsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-generals?query=:query : search for the pacientGenerals corresponding
     * to the query.
     *
     * @param query the query of the pacientGenerals search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-generals")
    @Timed
    public List<PacientGenerals> searchPacientGenerals(@RequestParam String query) {
        log.debug("REST request to search PacientGenerals for query {}", query);
        return StreamSupport
            .stream(pacientGeneralsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
