package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Occupation;

import mycercardiopackege.jh.repository.OccupationRepository;
import mycercardiopackege.jh.repository.search.OccupationSearchRepository;
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
 * REST controller for managing Occupation.
 */
@RestController
@RequestMapping("/api")
public class OccupationResource {

    private final Logger log = LoggerFactory.getLogger(OccupationResource.class);

    private static final String ENTITY_NAME = "occupation";

    private final OccupationRepository occupationRepository;

    private final OccupationSearchRepository occupationSearchRepository;

    public OccupationResource(OccupationRepository occupationRepository, OccupationSearchRepository occupationSearchRepository) {
        this.occupationRepository = occupationRepository;
        this.occupationSearchRepository = occupationSearchRepository;
    }

    /**
     * POST  /occupations : Create a new occupation.
     *
     * @param occupation the occupation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new occupation, or with status 400 (Bad Request) if the occupation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/occupations")
    @Timed
    public ResponseEntity<Occupation> createOccupation(@RequestBody Occupation occupation) throws URISyntaxException {
        log.debug("REST request to save Occupation : {}", occupation);
        if (occupation.getId() != null) {
            throw new BadRequestAlertException("A new occupation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Occupation result = occupationRepository.save(occupation);
        occupationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/occupations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /occupations : Updates an existing occupation.
     *
     * @param occupation the occupation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated occupation,
     * or with status 400 (Bad Request) if the occupation is not valid,
     * or with status 500 (Internal Server Error) if the occupation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/occupations")
    @Timed
    public ResponseEntity<Occupation> updateOccupation(@RequestBody Occupation occupation) throws URISyntaxException {
        log.debug("REST request to update Occupation : {}", occupation);
        if (occupation.getId() == null) {
            return createOccupation(occupation);
        }
        Occupation result = occupationRepository.save(occupation);
        occupationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, occupation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /occupations : get all the occupations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of occupations in body
     */
    @GetMapping("/occupations")
    @Timed
    public List<Occupation> getAllOccupations() {
        log.debug("REST request to get all Occupations");
        return occupationRepository.findAll();
        }

    /**
     * GET  /occupations/:id : get the "id" occupation.
     *
     * @param id the id of the occupation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the occupation, or with status 404 (Not Found)
     */
    @GetMapping("/occupations/{id}")
    @Timed
    public ResponseEntity<Occupation> getOccupation(@PathVariable Long id) {
        log.debug("REST request to get Occupation : {}", id);
        Occupation occupation = occupationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(occupation));
    }

    /**
     * DELETE  /occupations/:id : delete the "id" occupation.
     *
     * @param id the id of the occupation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/occupations/{id}")
    @Timed
    public ResponseEntity<Void> deleteOccupation(@PathVariable Long id) {
        log.debug("REST request to delete Occupation : {}", id);
        occupationRepository.delete(id);
        occupationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/occupations?query=:query : search for the occupation corresponding
     * to the query.
     *
     * @param query the query of the occupation search
     * @return the result of the search
     */
    @GetMapping("/_search/occupations")
    @Timed
    public List<Occupation> searchOccupations(@RequestParam String query) {
        log.debug("REST request to search Occupations for query {}", query);
        return StreamSupport
            .stream(occupationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
