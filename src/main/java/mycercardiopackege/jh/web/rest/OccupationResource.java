package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Occupation;

import mycercardiopackege.jh.repository.OccupationRepository;
import mycercardiopackege.jh.repository.search.OccupationSearchRepository;
import mycercardiopackege.jh.web.rest.errors.BadRequestAlertException;
import mycercardiopackege.jh.web.rest.util.HeaderUtil;
import mycercardiopackege.jh.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of occupations in body
     */
    @GetMapping("/occupations")
    @Timed
    public ResponseEntity<List<Occupation>> getAllOccupations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Occupations");
        Page<Occupation> page = occupationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/occupations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/occupations")
    @Timed
    public ResponseEntity<List<Occupation>> searchOccupations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Occupations for query {}", query);
        Page<Occupation> page = occupationSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/occupations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
