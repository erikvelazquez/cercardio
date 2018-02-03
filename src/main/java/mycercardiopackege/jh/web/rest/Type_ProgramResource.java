package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Type_Program;

import mycercardiopackege.jh.repository.Type_ProgramRepository;
import mycercardiopackege.jh.repository.search.Type_ProgramSearchRepository;
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
 * REST controller for managing Type_Program.
 */
@RestController
@RequestMapping("/api")
public class Type_ProgramResource {

    private final Logger log = LoggerFactory.getLogger(Type_ProgramResource.class);

    private static final String ENTITY_NAME = "type_Program";

    private final Type_ProgramRepository type_ProgramRepository;

    private final Type_ProgramSearchRepository type_ProgramSearchRepository;

    public Type_ProgramResource(Type_ProgramRepository type_ProgramRepository, Type_ProgramSearchRepository type_ProgramSearchRepository) {
        this.type_ProgramRepository = type_ProgramRepository;
        this.type_ProgramSearchRepository = type_ProgramSearchRepository;
    }

    /**
     * POST  /type-programs : Create a new type_Program.
     *
     * @param type_Program the type_Program to create
     * @return the ResponseEntity with status 201 (Created) and with body the new type_Program, or with status 400 (Bad Request) if the type_Program has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-programs")
    @Timed
    public ResponseEntity<Type_Program> createType_Program(@RequestBody Type_Program type_Program) throws URISyntaxException {
        log.debug("REST request to save Type_Program : {}", type_Program);
        if (type_Program.getId() != null) {
            throw new BadRequestAlertException("A new type_Program cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Type_Program result = type_ProgramRepository.save(type_Program);
        type_ProgramSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/type-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-programs : Updates an existing type_Program.
     *
     * @param type_Program the type_Program to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated type_Program,
     * or with status 400 (Bad Request) if the type_Program is not valid,
     * or with status 500 (Internal Server Error) if the type_Program couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-programs")
    @Timed
    public ResponseEntity<Type_Program> updateType_Program(@RequestBody Type_Program type_Program) throws URISyntaxException {
        log.debug("REST request to update Type_Program : {}", type_Program);
        if (type_Program.getId() == null) {
            return createType_Program(type_Program);
        }
        Type_Program result = type_ProgramRepository.save(type_Program);
        type_ProgramSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, type_Program.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-programs : get all the type_Programs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of type_Programs in body
     */
    @GetMapping("/type-programs")
    @Timed
    public List<Type_Program> getAllType_Programs() {
        log.debug("REST request to get all Type_Programs");
        return type_ProgramRepository.findAll();
        }

    /**
     * GET  /type-programs/:id : get the "id" type_Program.
     *
     * @param id the id of the type_Program to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the type_Program, or with status 404 (Not Found)
     */
    @GetMapping("/type-programs/{id}")
    @Timed
    public ResponseEntity<Type_Program> getType_Program(@PathVariable Long id) {
        log.debug("REST request to get Type_Program : {}", id);
        Type_Program type_Program = type_ProgramRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(type_Program));
    }

    /**
     * DELETE  /type-programs/:id : delete the "id" type_Program.
     *
     * @param id the id of the type_Program to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-programs/{id}")
    @Timed
    public ResponseEntity<Void> deleteType_Program(@PathVariable Long id) {
        log.debug("REST request to delete Type_Program : {}", id);
        type_ProgramRepository.delete(id);
        type_ProgramSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-programs?query=:query : search for the type_Program corresponding
     * to the query.
     *
     * @param query the query of the type_Program search
     * @return the result of the search
     */
    @GetMapping("/_search/type-programs")
    @Timed
    public List<Type_Program> searchType_Programs(@RequestParam String query) {
        log.debug("REST request to search Type_Programs for query {}", query);
        return StreamSupport
            .stream(type_ProgramSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
