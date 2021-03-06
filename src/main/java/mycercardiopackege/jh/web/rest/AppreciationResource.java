package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Appreciation;

import mycercardiopackege.jh.repository.AppreciationRepository;
import mycercardiopackege.jh.repository.search.AppreciationSearchRepository;
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
 * REST controller for managing Appreciation.
 */
@RestController
@RequestMapping("/api")
public class AppreciationResource {

    private final Logger log = LoggerFactory.getLogger(AppreciationResource.class);

    private static final String ENTITY_NAME = "appreciation";

    private final AppreciationRepository appreciationRepository;

    private final AppreciationSearchRepository appreciationSearchRepository;

    public AppreciationResource(AppreciationRepository appreciationRepository, AppreciationSearchRepository appreciationSearchRepository) {
        this.appreciationRepository = appreciationRepository;
        this.appreciationSearchRepository = appreciationSearchRepository;
    }

    /**
     * POST  /appreciations : Create a new appreciation.
     *
     * @param appreciation the appreciation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appreciation, or with status 400 (Bad Request) if the appreciation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appreciations")
    @Timed
    public ResponseEntity<Appreciation> createAppreciation(@RequestBody Appreciation appreciation) throws URISyntaxException {
        log.debug("REST request to save Appreciation : {}", appreciation);
        if (appreciation.getId() != null) {
            throw new BadRequestAlertException("A new appreciation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appreciation result = appreciationRepository.save(appreciation);
        appreciationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/appreciations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appreciations : Updates an existing appreciation.
     *
     * @param appreciation the appreciation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appreciation,
     * or with status 400 (Bad Request) if the appreciation is not valid,
     * or with status 500 (Internal Server Error) if the appreciation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appreciations")
    @Timed
    public ResponseEntity<Appreciation> updateAppreciation(@RequestBody Appreciation appreciation) throws URISyntaxException {
        log.debug("REST request to update Appreciation : {}", appreciation);
        if (appreciation.getId() == null) {
            return createAppreciation(appreciation);
        }
        Appreciation result = appreciationRepository.save(appreciation);
        appreciationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appreciation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appreciations : get all the appreciations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appreciations in body
     */
    @GetMapping("/appreciations")
    @Timed
    public List<Appreciation> getAllAppreciations() {
        log.debug("REST request to get all Appreciations");
        return appreciationRepository.findAll();
        }

    /**
     * GET  /appreciations/:id : get the "id" appreciation.
     *
     * @param id the id of the appreciation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appreciation, or with status 404 (Not Found)
     */
    @GetMapping("/appreciations/{id}")
    @Timed
    public ResponseEntity<Appreciation> getAppreciation(@PathVariable Long id) {
        log.debug("REST request to get Appreciation : {}", id);
        Appreciation appreciation = appreciationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appreciation));
    }

    /**
     * DELETE  /appreciations/:id : delete the "id" appreciation.
     *
     * @param id the id of the appreciation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appreciations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppreciation(@PathVariable Long id) {
        log.debug("REST request to delete Appreciation : {}", id);
        appreciationRepository.delete(id);
        appreciationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/appreciations?query=:query : search for the appreciation corresponding
     * to the query.
     *
     * @param query the query of the appreciation search
     * @return the result of the search
     */
    @GetMapping("/_search/appreciations")
    @Timed
    public List<Appreciation> searchAppreciations(@RequestParam String query) {
        log.debug("REST request to search Appreciations for query {}", query);
        return StreamSupport
            .stream(appreciationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
