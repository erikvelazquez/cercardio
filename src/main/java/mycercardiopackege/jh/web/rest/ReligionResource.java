package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Religion;

import mycercardiopackege.jh.repository.ReligionRepository;
import mycercardiopackege.jh.repository.search.ReligionSearchRepository;
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
 * REST controller for managing Religion.
 */
@RestController
@RequestMapping("/api")
public class ReligionResource {

    private final Logger log = LoggerFactory.getLogger(ReligionResource.class);

    private static final String ENTITY_NAME = "religion";

    private final ReligionRepository religionRepository;

    private final ReligionSearchRepository religionSearchRepository;

    public ReligionResource(ReligionRepository religionRepository, ReligionSearchRepository religionSearchRepository) {
        this.religionRepository = religionRepository;
        this.religionSearchRepository = religionSearchRepository;
    }

    /**
     * POST  /religions : Create a new religion.
     *
     * @param religion the religion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new religion, or with status 400 (Bad Request) if the religion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/religions")
    @Timed
    public ResponseEntity<Religion> createReligion(@RequestBody Religion religion) throws URISyntaxException {
        log.debug("REST request to save Religion : {}", religion);
        if (religion.getId() != null) {
            throw new BadRequestAlertException("A new religion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Religion result = religionRepository.save(religion);
        religionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/religions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /religions : Updates an existing religion.
     *
     * @param religion the religion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated religion,
     * or with status 400 (Bad Request) if the religion is not valid,
     * or with status 500 (Internal Server Error) if the religion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/religions")
    @Timed
    public ResponseEntity<Religion> updateReligion(@RequestBody Religion religion) throws URISyntaxException {
        log.debug("REST request to update Religion : {}", religion);
        if (religion.getId() == null) {
            return createReligion(religion);
        }
        Religion result = religionRepository.save(religion);
        religionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, religion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /religions : get all the religions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of religions in body
     */
    @GetMapping("/religions")
    @Timed
    public List<Religion> getAllReligions() {
        log.debug("REST request to get all Religions");
        return religionRepository.findAll();
        }

    /**
     * GET  /religions/:id : get the "id" religion.
     *
     * @param id the id of the religion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the religion, or with status 404 (Not Found)
     */
    @GetMapping("/religions/{id}")
    @Timed
    public ResponseEntity<Religion> getReligion(@PathVariable Long id) {
        log.debug("REST request to get Religion : {}", id);
        Religion religion = religionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(religion));
    }

    /**
     * DELETE  /religions/:id : delete the "id" religion.
     *
     * @param id the id of the religion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/religions/{id}")
    @Timed
    public ResponseEntity<Void> deleteReligion(@PathVariable Long id) {
        log.debug("REST request to delete Religion : {}", id);
        religionRepository.delete(id);
        religionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/religions?query=:query : search for the religion corresponding
     * to the query.
     *
     * @param query the query of the religion search
     * @return the result of the search
     */
    @GetMapping("/_search/religions")
    @Timed
    public List<Religion> searchReligions(@RequestParam String query) {
        log.debug("REST request to search Religions for query {}", query);
        return StreamSupport
            .stream(religionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
