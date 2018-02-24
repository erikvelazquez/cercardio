package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.AcademicDegree;

import mycercardiopackege.jh.repository.AcademicDegreeRepository;
import mycercardiopackege.jh.repository.search.AcademicDegreeSearchRepository;
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
 * REST controller for managing AcademicDegree.
 */
@RestController
@RequestMapping("/api")
public class AcademicDegreeResource {

    private final Logger log = LoggerFactory.getLogger(AcademicDegreeResource.class);

    private static final String ENTITY_NAME = "academicDegree";

    private final AcademicDegreeRepository academicDegreeRepository;

    private final AcademicDegreeSearchRepository academicDegreeSearchRepository;

    public AcademicDegreeResource(AcademicDegreeRepository academicDegreeRepository, AcademicDegreeSearchRepository academicDegreeSearchRepository) {
        this.academicDegreeRepository = academicDegreeRepository;
        this.academicDegreeSearchRepository = academicDegreeSearchRepository;
    }

    /**
     * POST  /academic-degrees : Create a new academicDegree.
     *
     * @param academicDegree the academicDegree to create
     * @return the ResponseEntity with status 201 (Created) and with body the new academicDegree, or with status 400 (Bad Request) if the academicDegree has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/academic-degrees")
    @Timed
    public ResponseEntity<AcademicDegree> createAcademicDegree(@RequestBody AcademicDegree academicDegree) throws URISyntaxException {
        log.debug("REST request to save AcademicDegree : {}", academicDegree);
        if (academicDegree.getId() != null) {
            throw new BadRequestAlertException("A new academicDegree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicDegree result = academicDegreeRepository.save(academicDegree);
        academicDegreeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/academic-degrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /academic-degrees : Updates an existing academicDegree.
     *
     * @param academicDegree the academicDegree to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated academicDegree,
     * or with status 400 (Bad Request) if the academicDegree is not valid,
     * or with status 500 (Internal Server Error) if the academicDegree couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/academic-degrees")
    @Timed
    public ResponseEntity<AcademicDegree> updateAcademicDegree(@RequestBody AcademicDegree academicDegree) throws URISyntaxException {
        log.debug("REST request to update AcademicDegree : {}", academicDegree);
        if (academicDegree.getId() == null) {
            return createAcademicDegree(academicDegree);
        }
        AcademicDegree result = academicDegreeRepository.save(academicDegree);
        academicDegreeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, academicDegree.getId().toString()))
            .body(result);
    }

    /**
     * GET  /academic-degrees : get all the academicDegrees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of academicDegrees in body
     */
    @GetMapping("/academic-degrees")
    @Timed
    public ResponseEntity<List<AcademicDegree>> getAllAcademicDegrees(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AcademicDegrees");
        Page<AcademicDegree> page = academicDegreeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/academic-degrees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /academic-degrees/:id : get the "id" academicDegree.
     *
     * @param id the id of the academicDegree to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the academicDegree, or with status 404 (Not Found)
     */
    @GetMapping("/academic-degrees/{id}")
    @Timed
    public ResponseEntity<AcademicDegree> getAcademicDegree(@PathVariable Long id) {
        log.debug("REST request to get AcademicDegree : {}", id);
        AcademicDegree academicDegree = academicDegreeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(academicDegree));
    }

    /**
     * DELETE  /academic-degrees/:id : delete the "id" academicDegree.
     *
     * @param id the id of the academicDegree to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/academic-degrees/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcademicDegree(@PathVariable Long id) {
        log.debug("REST request to delete AcademicDegree : {}", id);
        academicDegreeRepository.delete(id);
        academicDegreeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/academic-degrees?query=:query : search for the academicDegree corresponding
     * to the query.
     *
     * @param query the query of the academicDegree search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/academic-degrees")
    @Timed
    public ResponseEntity<List<AcademicDegree>> searchAcademicDegrees(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of AcademicDegrees for query {}", query);
        Page<AcademicDegree> page = academicDegreeSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/academic-degrees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
