package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.MedicNurse;

import mycercardiopackege.jh.repository.MedicNurseRepository;
import mycercardiopackege.jh.repository.search.MedicNurseSearchRepository;
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
 * REST controller for managing MedicNurse.
 */
@RestController
@RequestMapping("/api")
public class MedicNurseResource {

    private final Logger log = LoggerFactory.getLogger(MedicNurseResource.class);

    private static final String ENTITY_NAME = "medicNurse";

    private final MedicNurseRepository medicNurseRepository;

    private final MedicNurseSearchRepository medicNurseSearchRepository;

    public MedicNurseResource(MedicNurseRepository medicNurseRepository, MedicNurseSearchRepository medicNurseSearchRepository) {
        this.medicNurseRepository = medicNurseRepository;
        this.medicNurseSearchRepository = medicNurseSearchRepository;
    }

    /**
     * POST  /medic-nurses : Create a new medicNurse.
     *
     * @param medicNurse the medicNurse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicNurse, or with status 400 (Bad Request) if the medicNurse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medic-nurses")
    @Timed
    public ResponseEntity<MedicNurse> createMedicNurse(@RequestBody MedicNurse medicNurse) throws URISyntaxException {
        log.debug("REST request to save MedicNurse : {}", medicNurse);
        if (medicNurse.getId() != null) {
            throw new BadRequestAlertException("A new medicNurse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicNurse result = medicNurseRepository.save(medicNurse);
        medicNurseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medic-nurses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medic-nurses : Updates an existing medicNurse.
     *
     * @param medicNurse the medicNurse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicNurse,
     * or with status 400 (Bad Request) if the medicNurse is not valid,
     * or with status 500 (Internal Server Error) if the medicNurse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medic-nurses")
    @Timed
    public ResponseEntity<MedicNurse> updateMedicNurse(@RequestBody MedicNurse medicNurse) throws URISyntaxException {
        log.debug("REST request to update MedicNurse : {}", medicNurse);
        if (medicNurse.getId() == null) {
            return createMedicNurse(medicNurse);
        }
        MedicNurse result = medicNurseRepository.save(medicNurse);
        medicNurseSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicNurse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medic-nurses : get all the medicNurses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicNurses in body
     */
    @GetMapping("/medic-nurses")
    @Timed
    public List<MedicNurse> getAllMedicNurses() {
        log.debug("REST request to get all MedicNurses");
        return medicNurseRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /medic-nurses/:id : get the "id" medicNurse.
     *
     * @param id the id of the medicNurse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicNurse, or with status 404 (Not Found)
     */
    @GetMapping("/medic-nurses/{id}")
    @Timed
    public ResponseEntity<MedicNurse> getMedicNurse(@PathVariable Long id) {
        log.debug("REST request to get MedicNurse : {}", id);
        MedicNurse medicNurse = medicNurseRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicNurse));
    }

    /**
     * DELETE  /medic-nurses/:id : delete the "id" medicNurse.
     *
     * @param id the id of the medicNurse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medic-nurses/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedicNurse(@PathVariable Long id) {
        log.debug("REST request to delete MedicNurse : {}", id);
        medicNurseRepository.delete(id);
        medicNurseSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medic-nurses?query=:query : search for the medicNurse corresponding
     * to the query.
     *
     * @param query the query of the medicNurse search
     * @return the result of the search
     */
    @GetMapping("/_search/medic-nurses")
    @Timed
    public List<MedicNurse> searchMedicNurses(@RequestParam String query) {
        log.debug("REST request to search MedicNurses for query {}", query);
        return StreamSupport
            .stream(medicNurseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
