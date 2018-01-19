package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Programs;

import mycercardiopackege.jh.repository.ProgramsRepository;
import mycercardiopackege.jh.repository.search.ProgramsSearchRepository;
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
 * REST controller for managing Programs.
 */
@RestController
@RequestMapping("/api")
public class ProgramsResource {

    private final Logger log = LoggerFactory.getLogger(ProgramsResource.class);

    private static final String ENTITY_NAME = "programs";

    private final ProgramsRepository programsRepository;

    private final ProgramsSearchRepository programsSearchRepository;

    public ProgramsResource(ProgramsRepository programsRepository, ProgramsSearchRepository programsSearchRepository) {
        this.programsRepository = programsRepository;
        this.programsSearchRepository = programsSearchRepository;
    }

    /**
     * POST  /programs : Create a new programs.
     *
     * @param programs the programs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programs, or with status 400 (Bad Request) if the programs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/programs")
    @Timed
    public ResponseEntity<Programs> createPrograms(@RequestBody Programs programs) throws URISyntaxException {
        log.debug("REST request to save Programs : {}", programs);
        if (programs.getId() != null) {
            throw new BadRequestAlertException("A new programs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Programs result = programsRepository.save(programs);
        programsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /programs : Updates an existing programs.
     *
     * @param programs the programs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programs,
     * or with status 400 (Bad Request) if the programs is not valid,
     * or with status 500 (Internal Server Error) if the programs couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/programs")
    @Timed
    public ResponseEntity<Programs> updatePrograms(@RequestBody Programs programs) throws URISyntaxException {
        log.debug("REST request to update Programs : {}", programs);
        if (programs.getId() == null) {
            return createPrograms(programs);
        }
        Programs result = programsRepository.save(programs);
        programsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, programs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /programs : get all the programs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of programs in body
     */
    @GetMapping("/programs")
    @Timed
    public List<Programs> getAllPrograms() {
        log.debug("REST request to get all Programs");
        return programsRepository.findAll();
        }

    /**
     * GET  /programs/:id : get the "id" programs.
     *
     * @param id the id of the programs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programs, or with status 404 (Not Found)
     */
    @GetMapping("/programs/{id}")
    @Timed
    public ResponseEntity<Programs> getPrograms(@PathVariable Long id) {
        log.debug("REST request to get Programs : {}", id);
        Programs programs = programsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(programs));
    }

    /**
     * DELETE  /programs/:id : delete the "id" programs.
     *
     * @param id the id of the programs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/programs/{id}")
    @Timed
    public ResponseEntity<Void> deletePrograms(@PathVariable Long id) {
        log.debug("REST request to delete Programs : {}", id);
        programsRepository.delete(id);
        programsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/programs?query=:query : search for the programs corresponding
     * to the query.
     *
     * @param query the query of the programs search
     * @return the result of the search
     */
    @GetMapping("/_search/programs")
    @Timed
    public List<Programs> searchPrograms(@RequestParam String query) {
        log.debug("REST request to search Programs for query {}", query);
        return StreamSupport
            .stream(programsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
