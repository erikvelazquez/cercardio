package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Domicile;

import mycercardiopackege.jh.repository.DomicileRepository;
import mycercardiopackege.jh.repository.search.DomicileSearchRepository;
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
 * REST controller for managing Domicile.
 */
@RestController
@RequestMapping("/api")
public class DomicileResource {

    private final Logger log = LoggerFactory.getLogger(DomicileResource.class);

    private static final String ENTITY_NAME = "domicile";

    private final DomicileRepository domicileRepository;

    private final DomicileSearchRepository domicileSearchRepository;

    public DomicileResource(DomicileRepository domicileRepository, DomicileSearchRepository domicileSearchRepository) {
        this.domicileRepository = domicileRepository;
        this.domicileSearchRepository = domicileSearchRepository;
    }

    /**
     * POST  /domiciles : Create a new domicile.
     *
     * @param domicile the domicile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new domicile, or with status 400 (Bad Request) if the domicile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/domiciles")
    @Timed
    public ResponseEntity<Domicile> createDomicile(@RequestBody Domicile domicile) throws URISyntaxException {
        log.debug("REST request to save Domicile : {}", domicile);
        if (domicile.getId() != null) {
            throw new BadRequestAlertException("A new domicile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Domicile result = domicileRepository.save(domicile);
        domicileSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/domiciles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /domiciles : Updates an existing domicile.
     *
     * @param domicile the domicile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated domicile,
     * or with status 400 (Bad Request) if the domicile is not valid,
     * or with status 500 (Internal Server Error) if the domicile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/domiciles")
    @Timed
    public ResponseEntity<Domicile> updateDomicile(@RequestBody Domicile domicile) throws URISyntaxException {
        log.debug("REST request to update Domicile : {}", domicile);
        if (domicile.getId() == null) {
            return createDomicile(domicile);
        }
        Domicile result = domicileRepository.save(domicile);
        domicileSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, domicile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /domiciles : get all the domiciles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of domiciles in body
     */
    @GetMapping("/domiciles")
    @Timed
    public List<Domicile> getAllDomiciles() {
        log.debug("REST request to get all Domiciles");
        return domicileRepository.findAll();
        }

    /**
     * GET  /domiciles/:id : get the "id" domicile.
     *
     * @param id the id of the domicile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domicile, or with status 404 (Not Found)
     */
    @GetMapping("/domiciles/{id}")
    @Timed
    public ResponseEntity<Domicile> getDomicile(@PathVariable Long id) {
        log.debug("REST request to get Domicile : {}", id);
        Domicile domicile = domicileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(domicile));
    }

    /**
     * DELETE  /domiciles/:id : delete the "id" domicile.
     *
     * @param id the id of the domicile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/domiciles/{id}")
    @Timed
    public ResponseEntity<Void> deleteDomicile(@PathVariable Long id) {
        log.debug("REST request to delete Domicile : {}", id);
        domicileRepository.delete(id);
        domicileSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/domiciles?query=:query : search for the domicile corresponding
     * to the query.
     *
     * @param query the query of the domicile search
     * @return the result of the search
     */
    @GetMapping("/_search/domiciles")
    @Timed
    public List<Domicile> searchDomiciles(@RequestParam String query) {
        log.debug("REST request to search Domiciles for query {}", query);
        return StreamSupport
            .stream(domicileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
