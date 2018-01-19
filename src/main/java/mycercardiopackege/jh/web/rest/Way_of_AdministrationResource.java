package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Way_of_Administration;

import mycercardiopackege.jh.repository.Way_of_AdministrationRepository;
import mycercardiopackege.jh.repository.search.Way_of_AdministrationSearchRepository;
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
 * REST controller for managing Way_of_Administration.
 */
@RestController
@RequestMapping("/api")
public class Way_of_AdministrationResource {

    private final Logger log = LoggerFactory.getLogger(Way_of_AdministrationResource.class);

    private static final String ENTITY_NAME = "way_of_Administration";

    private final Way_of_AdministrationRepository way_of_AdministrationRepository;

    private final Way_of_AdministrationSearchRepository way_of_AdministrationSearchRepository;

    public Way_of_AdministrationResource(Way_of_AdministrationRepository way_of_AdministrationRepository, Way_of_AdministrationSearchRepository way_of_AdministrationSearchRepository) {
        this.way_of_AdministrationRepository = way_of_AdministrationRepository;
        this.way_of_AdministrationSearchRepository = way_of_AdministrationSearchRepository;
    }

    /**
     * POST  /way-of-administrations : Create a new way_of_Administration.
     *
     * @param way_of_Administration the way_of_Administration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new way_of_Administration, or with status 400 (Bad Request) if the way_of_Administration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/way-of-administrations")
    @Timed
    public ResponseEntity<Way_of_Administration> createWay_of_Administration(@RequestBody Way_of_Administration way_of_Administration) throws URISyntaxException {
        log.debug("REST request to save Way_of_Administration : {}", way_of_Administration);
        if (way_of_Administration.getId() != null) {
            throw new BadRequestAlertException("A new way_of_Administration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Way_of_Administration result = way_of_AdministrationRepository.save(way_of_Administration);
        way_of_AdministrationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/way-of-administrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /way-of-administrations : Updates an existing way_of_Administration.
     *
     * @param way_of_Administration the way_of_Administration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated way_of_Administration,
     * or with status 400 (Bad Request) if the way_of_Administration is not valid,
     * or with status 500 (Internal Server Error) if the way_of_Administration couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/way-of-administrations")
    @Timed
    public ResponseEntity<Way_of_Administration> updateWay_of_Administration(@RequestBody Way_of_Administration way_of_Administration) throws URISyntaxException {
        log.debug("REST request to update Way_of_Administration : {}", way_of_Administration);
        if (way_of_Administration.getId() == null) {
            return createWay_of_Administration(way_of_Administration);
        }
        Way_of_Administration result = way_of_AdministrationRepository.save(way_of_Administration);
        way_of_AdministrationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, way_of_Administration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /way-of-administrations : get all the way_of_Administrations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of way_of_Administrations in body
     */
    @GetMapping("/way-of-administrations")
    @Timed
    public List<Way_of_Administration> getAllWay_of_Administrations() {
        log.debug("REST request to get all Way_of_Administrations");
        return way_of_AdministrationRepository.findAll();
        }

    /**
     * GET  /way-of-administrations/:id : get the "id" way_of_Administration.
     *
     * @param id the id of the way_of_Administration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the way_of_Administration, or with status 404 (Not Found)
     */
    @GetMapping("/way-of-administrations/{id}")
    @Timed
    public ResponseEntity<Way_of_Administration> getWay_of_Administration(@PathVariable Long id) {
        log.debug("REST request to get Way_of_Administration : {}", id);
        Way_of_Administration way_of_Administration = way_of_AdministrationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(way_of_Administration));
    }

    /**
     * DELETE  /way-of-administrations/:id : delete the "id" way_of_Administration.
     *
     * @param id the id of the way_of_Administration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/way-of-administrations/{id}")
    @Timed
    public ResponseEntity<Void> deleteWay_of_Administration(@PathVariable Long id) {
        log.debug("REST request to delete Way_of_Administration : {}", id);
        way_of_AdministrationRepository.delete(id);
        way_of_AdministrationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/way-of-administrations?query=:query : search for the way_of_Administration corresponding
     * to the query.
     *
     * @param query the query of the way_of_Administration search
     * @return the result of the search
     */
    @GetMapping("/_search/way-of-administrations")
    @Timed
    public List<Way_of_Administration> searchWay_of_Administrations(@RequestParam String query) {
        log.debug("REST request to search Way_of_Administrations for query {}", query);
        return StreamSupport
            .stream(way_of_AdministrationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
