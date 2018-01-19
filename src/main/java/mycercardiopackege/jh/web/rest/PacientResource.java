package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Pacient;

import mycercardiopackege.jh.repository.PacientRepository;
import mycercardiopackege.jh.repository.search.PacientSearchRepository;
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
 * REST controller for managing Pacient.
 */
@RestController
@RequestMapping("/api")
public class PacientResource {

    private final Logger log = LoggerFactory.getLogger(PacientResource.class);

    private static final String ENTITY_NAME = "pacient";

    private final PacientRepository pacientRepository;

    private final PacientSearchRepository pacientSearchRepository;

    public PacientResource(PacientRepository pacientRepository, PacientSearchRepository pacientSearchRepository) {
        this.pacientRepository = pacientRepository;
        this.pacientSearchRepository = pacientSearchRepository;
    }

    /**
     * POST  /pacients : Create a new pacient.
     *
     * @param pacient the pacient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacient, or with status 400 (Bad Request) if the pacient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacients")
    @Timed
    public ResponseEntity<Pacient> createPacient(@RequestBody Pacient pacient) throws URISyntaxException {
        log.debug("REST request to save Pacient : {}", pacient);
        if (pacient.getId() != null) {
            throw new BadRequestAlertException("A new pacient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pacient result = pacientRepository.save(pacient);
        pacientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacients : Updates an existing pacient.
     *
     * @param pacient the pacient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacient,
     * or with status 400 (Bad Request) if the pacient is not valid,
     * or with status 500 (Internal Server Error) if the pacient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacients")
    @Timed
    public ResponseEntity<Pacient> updatePacient(@RequestBody Pacient pacient) throws URISyntaxException {
        log.debug("REST request to update Pacient : {}", pacient);
        if (pacient.getId() == null) {
            return createPacient(pacient);
        }
        Pacient result = pacientRepository.save(pacient);
        pacientSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacients : get all the pacients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacients in body
     */
    @GetMapping("/pacients")
    @Timed
    public List<Pacient> getAllPacients() {
        log.debug("REST request to get all Pacients");
        return pacientRepository.findAll();
        }

    /**
     * GET  /pacients/:id : get the "id" pacient.
     *
     * @param id the id of the pacient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacient, or with status 404 (Not Found)
     */
    @GetMapping("/pacients/{id}")
    @Timed
    public ResponseEntity<Pacient> getPacient(@PathVariable Long id) {
        log.debug("REST request to get Pacient : {}", id);
        Pacient pacient = pacientRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacient));
    }

    /**
     * DELETE  /pacients/:id : delete the "id" pacient.
     *
     * @param id the id of the pacient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacients/{id}")
    @Timed
    public ResponseEntity<Void> deletePacient(@PathVariable Long id) {
        log.debug("REST request to delete Pacient : {}", id);
        pacientRepository.delete(id);
        pacientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacients?query=:query : search for the pacient corresponding
     * to the query.
     *
     * @param query the query of the pacient search
     * @return the result of the search
     */
    @GetMapping("/_search/pacients")
    @Timed
    public List<Pacient> searchPacients(@RequestParam String query) {
        log.debug("REST request to search Pacients for query {}", query);
        return StreamSupport
            .stream(pacientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
