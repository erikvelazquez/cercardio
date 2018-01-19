package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PacientContact;

import mycercardiopackege.jh.repository.PacientContactRepository;
import mycercardiopackege.jh.repository.search.PacientContactSearchRepository;
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
 * REST controller for managing PacientContact.
 */
@RestController
@RequestMapping("/api")
public class PacientContactResource {

    private final Logger log = LoggerFactory.getLogger(PacientContactResource.class);

    private static final String ENTITY_NAME = "pacientContact";

    private final PacientContactRepository pacientContactRepository;

    private final PacientContactSearchRepository pacientContactSearchRepository;

    public PacientContactResource(PacientContactRepository pacientContactRepository, PacientContactSearchRepository pacientContactSearchRepository) {
        this.pacientContactRepository = pacientContactRepository;
        this.pacientContactSearchRepository = pacientContactSearchRepository;
    }

    /**
     * POST  /pacient-contacts : Create a new pacientContact.
     *
     * @param pacientContact the pacientContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacientContact, or with status 400 (Bad Request) if the pacientContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacient-contacts")
    @Timed
    public ResponseEntity<PacientContact> createPacientContact(@RequestBody PacientContact pacientContact) throws URISyntaxException {
        log.debug("REST request to save PacientContact : {}", pacientContact);
        if (pacientContact.getId() != null) {
            throw new BadRequestAlertException("A new pacientContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PacientContact result = pacientContactRepository.save(pacientContact);
        pacientContactSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pacient-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacient-contacts : Updates an existing pacientContact.
     *
     * @param pacientContact the pacientContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacientContact,
     * or with status 400 (Bad Request) if the pacientContact is not valid,
     * or with status 500 (Internal Server Error) if the pacientContact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacient-contacts")
    @Timed
    public ResponseEntity<PacientContact> updatePacientContact(@RequestBody PacientContact pacientContact) throws URISyntaxException {
        log.debug("REST request to update PacientContact : {}", pacientContact);
        if (pacientContact.getId() == null) {
            return createPacientContact(pacientContact);
        }
        PacientContact result = pacientContactRepository.save(pacientContact);
        pacientContactSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacientContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacient-contacts : get all the pacientContacts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientContacts in body
     */
    @GetMapping("/pacient-contacts")
    @Timed
    public List<PacientContact> getAllPacientContacts() {
        log.debug("REST request to get all PacientContacts");
        return pacientContactRepository.findAll();
        }

    /**
     * GET  /pacient-contacts/:id : get the "id" pacientContact.
     *
     * @param id the id of the pacientContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacientContact, or with status 404 (Not Found)
     */
    @GetMapping("/pacient-contacts/{id}")
    @Timed
    public ResponseEntity<PacientContact> getPacientContact(@PathVariable Long id) {
        log.debug("REST request to get PacientContact : {}", id);
        PacientContact pacientContact = pacientContactRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacientContact));
    }

    /**
     * DELETE  /pacient-contacts/:id : delete the "id" pacientContact.
     *
     * @param id the id of the pacientContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacient-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deletePacientContact(@PathVariable Long id) {
        log.debug("REST request to delete PacientContact : {}", id);
        pacientContactRepository.delete(id);
        pacientContactSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pacient-contacts?query=:query : search for the pacientContact corresponding
     * to the query.
     *
     * @param query the query of the pacientContact search
     * @return the result of the search
     */
    @GetMapping("/_search/pacient-contacts")
    @Timed
    public List<PacientContact> searchPacientContacts(@RequestParam String query) {
        log.debug("REST request to search PacientContacts for query {}", query);
        return StreamSupport
            .stream(pacientContactSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
