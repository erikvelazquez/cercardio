package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.MedicPacient;

import mycercardiopackege.jh.repository.MedicPacientRepository;
import mycercardiopackege.jh.repository.search.MedicPacientSearchRepository;
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
 * REST controller for managing MedicPacient.
 */
@RestController
@RequestMapping("/api")
public class MedicPacientResource {

    private final Logger log = LoggerFactory.getLogger(MedicPacientResource.class);

    private static final String ENTITY_NAME = "medicPacient";

    private final MedicPacientRepository medicPacientRepository;

    private final MedicPacientSearchRepository medicPacientSearchRepository;

    public MedicPacientResource(MedicPacientRepository medicPacientRepository, MedicPacientSearchRepository medicPacientSearchRepository) {
        this.medicPacientRepository = medicPacientRepository;
        this.medicPacientSearchRepository = medicPacientSearchRepository;
    }

    /**
     * POST  /medic-pacients : Create a new medicPacient.
     *
     * @param medicPacient the medicPacient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicPacient, or with status 400 (Bad Request) if the medicPacient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medic-pacients")
    @Timed
    public ResponseEntity<MedicPacient> createMedicPacient(@RequestBody MedicPacient medicPacient) throws URISyntaxException {
        log.debug("REST request to save MedicPacient : {}", medicPacient);
        if (medicPacient.getId() != null) {
            throw new BadRequestAlertException("A new medicPacient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicPacient result = medicPacientRepository.save(medicPacient);
        medicPacientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medic-pacients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medic-pacients : Updates an existing medicPacient.
     *
     * @param medicPacient the medicPacient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicPacient,
     * or with status 400 (Bad Request) if the medicPacient is not valid,
     * or with status 500 (Internal Server Error) if the medicPacient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medic-pacients")
    @Timed
    public ResponseEntity<MedicPacient> updateMedicPacient(@RequestBody MedicPacient medicPacient) throws URISyntaxException {
        log.debug("REST request to update MedicPacient : {}", medicPacient);
        if (medicPacient.getId() == null) {
            return createMedicPacient(medicPacient);
        }
        MedicPacient result = medicPacientRepository.save(medicPacient);
        medicPacientSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicPacient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medic-pacients : get all the medicPacients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicPacients in body
     */
    @GetMapping("/medic-pacients")
    @Timed
    public List<MedicPacient> getAllMedicPacients() {
        log.debug("REST request to get all MedicPacients");
        return medicPacientRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /medic-pacients/:id : get the "id" medicPacient.
     *
     * @param id the id of the medicPacient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicPacient, or with status 404 (Not Found)
     */
    @GetMapping("/medic-pacients/{id}")
    @Timed
    public ResponseEntity<MedicPacient> getMedicPacient(@PathVariable Long id) {
        log.debug("REST request to get MedicPacient : {}", id);
        MedicPacient medicPacient = medicPacientRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicPacient));
    }

    /**
     * DELETE  /medic-pacients/:id : delete the "id" medicPacient.
     *
     * @param id the id of the medicPacient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medic-pacients/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicPacient(@PathVariable Long id) {
        log.debug("REST request to delete MedicPacient : {}", id);
        medicPacientRepository.delete(id);
        medicPacientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medic-pacients?query=:query : search for the medicPacient corresponding
     * to the query.
     *
     * @param query the query of the medicPacient search
     * @return the result of the search
     */
    @GetMapping("/_search/medic-pacients")
    @Timed
    public List<MedicPacient> searchMedicPacients(@RequestParam String query) {
        log.debug("REST request to search MedicPacients for query {}", query);
        return StreamSupport
            .stream(medicPacientSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
