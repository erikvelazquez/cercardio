package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientLaboratoy;

import mycercardiopackege.jh.repository.PacientLaboratoyRepository;
import mycercardiopackege.jh.repository.search.PacientLaboratoySearchRepository;
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
 * REST controller for managing PacientLaboratoy.
 */
@RestController
@RequestMapping("/api")
public class PacientLaboratoyResource {

    private final Logger log = LoggerFactory.getLogger(PacientLaboratoyResource.class);

    private static final String ENTITY_NAME = "pacientLaboratoy";

    private final PacientLaboratoyRepository pacientLaboratoyRepository;

    private final PacientLaboratoySearchRepository pacientLaboratoySearchRepository;

    public PacientLaboratoyResource(PacientLaboratoyRepository pacientLaboratoyRepository, PacientLaboratoySearchRepository pacientLaboratoySearchRepository) {
        this.pacientLaboratoyRepository = pacientLaboratoyRepository;
        this.pacientLaboratoySearchRepository = pacientLaboratoySearchRepository;
    }

    /**
     * POST  /pacient-laboratoys : Create a new pacientLaboratoy.
     *
     * @param pacientLaboratoy the pacientLaboratoy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientLaboratoy, or with status 400 (Bad Request) if the pacientLaboratoy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-laboratoys")
    @Timed
    public ResponseEntity<PacientLaboratoy> createPacientLaboratoy(@RequestBody PacientLaboratoy pacientLaboratoy) throws URISyntaxException {
        log.debug("REST request to save PacientLaboratoy : {}", pacientLaboratoy);
        if (pacientLaboratoy.getId() != null) {
            throw new BadRequestAlertException("A new pacientLaboratoy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientLaboratoy result = pacientLaboratoyRepository.save(pacientLaboratoy);
        pacientLaboratoySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-laboratoys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-laboratoys : Updates an existing pacientLaboratoy.
     *
     * @param pacientLaboratoy the pacientLaboratoy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientLaboratoy,
     * or with status 400 (Bad Request) if the pacientLaboratoy is not valid,
     * or with status 500 (Internal Server Error) if the pacientLaboratoy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-laboratoys")
    @Timed
    public ResponseEntity<PacientLaboratoy> updatePacientLaboratoy(@RequestBody PacientLaboratoy pacientLaboratoy) throws URISyntaxException {
        log.debug("REST request to update PacientLaboratoy : {}", pacientLaboratoy);
        if (pacientLaboratoy.getId() == null) {
            return createPacientLaboratoy(pacientLaboratoy);
        }
        PacientLaboratoy result = pacientLaboratoyRepository.save(pacientLaboratoy);
        pacientLaboratoySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientLaboratoy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-laboratoys : get all the pacientLaboratoys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientLaboratoys in body
     */
    @GetMapping("/pacient-laboratoys")
    @Timed
    public List<PacientLaboratoy> getAllPacientLaboratoys() {
        log.debug("REST request to get all PacientLaboratoys");
        return pacientLaboratoyRepository.findAll();
        }

    /**
     * GET  /pacient-laboratoys/:id : get the "id" pacientLaboratoy.
     *
     * @param id the id of the pacientLaboratoy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientLaboratoy, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-laboratoys/{id}")
    @Timed
    public ResponseEntity<PacientLaboratoy> getPacientLaboratoy(@PathVariable Long id) {
        log.debug("REST request to get PacientLaboratoy : {}", id);
        PacientLaboratoy pacientLaboratoy = pacientLaboratoyRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientLaboratoy));
    }

    /**
     * DELETE  /pacient-laboratoys/:id : delete the "id" pacientLaboratoy.
     *
     * @param id the id of the pacientLaboratoy to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-laboratoys/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientLaboratoy(@PathVariable Long id) {
        log.debug("REST request to delete PacientLaboratoy : {}", id);
        pacientLaboratoyRepository.delete(id);
        pacientLaboratoySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-laboratoys?query=:query : search for the pacientLaboratoy corresponding
     * to the query.
     *
     * @param query the query of the pacientLaboratoy search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-laboratoys")
    @Timed
    public List<PacientLaboratoy> searchPacientLaboratoys(@RequestParam String query) {
        log.debug("REST request to search PacientLaboratoys for query {}", query);
        return StreamSupport
            .stream(pacientLaboratoySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
