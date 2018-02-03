package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Disabilities;

import mycercardiopackege.jh.repository.DisabilitiesRepository;
import mycercardiopackege.jh.repository.search.DisabilitiesSearchRepository;
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
 * REST controller for managing Disabilities.
 */
@RestController
@RequestMapping("/api")
public class DisabilitiesResource {

    private final Logger log = LoggerFactory.getLogger(DisabilitiesResource.class);

    private static final String ENTITY_NAME = "disabilities";

    private final DisabilitiesRepository disabilitiesRepository;

    private final DisabilitiesSearchRepository disabilitiesSearchRepository;

    public DisabilitiesResource(DisabilitiesRepository disabilitiesRepository, DisabilitiesSearchRepository disabilitiesSearchRepository) {
        this.disabilitiesRepository = disabilitiesRepository;
        this.disabilitiesSearchRepository = disabilitiesSearchRepository;
    }

    /**
     * POST  /disabilities : Create a new disabilities.
     *
     * @param disabilities the disabilities to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disabilities, or with status 400 (Bad Request) if the disabilities has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disabilities")
    @Timed
    public ResponseEntity<Disabilities> createDisabilities(@RequestBody Disabilities disabilities) throws URISyntaxException {
        log.debug("REST request to save Disabilities : {}", disabilities);
        if (disabilities.getId() != null) {
            throw new BadRequestAlertException("A new disabilities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disabilities result = disabilitiesRepository.save(disabilities);
        disabilitiesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/disabilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disabilities : Updates an existing disabilities.
     *
     * @param disabilities the disabilities to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disabilities,
     * or with status 400 (Bad Request) if the disabilities is not valid,
     * or with status 500 (Internal Server Error) if the disabilities couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disabilities")
    @Timed
    public ResponseEntity<Disabilities> updateDisabilities(@RequestBody Disabilities disabilities) throws URISyntaxException {
        log.debug("REST request to update Disabilities : {}", disabilities);
        if (disabilities.getId() == null) {
            return createDisabilities(disabilities);
        }
        Disabilities result = disabilitiesRepository.save(disabilities);
        disabilitiesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disabilities.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disabilities : get all the disabilities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of disabilities in body
     */
    @GetMapping("/disabilities")
    @Timed
    public List<Disabilities> getAllDisabilities() {
        log.debug("REST request to get all Disabilities");
        return disabilitiesRepository.findAll();
        }

    /**
     * GET  /disabilities/:id : get the "id" disabilities.
     *
     * @param id the id of the disabilities to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disabilities, or with status 404 (Not Found)
     */
    @GetMapping("/disabilities/{id}")
    @Timed
    public ResponseEntity<Disabilities> getDisabilities(@PathVariable Long id) {
        log.debug("REST request to get Disabilities : {}", id);
        Disabilities disabilities = disabilitiesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(disabilities));
    }

    /**
     * DELETE  /disabilities/:id : delete the "id" disabilities.
     *
     * @param id the id of the disabilities to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disabilities/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisabilities(@PathVariable Long id) {
        log.debug("REST request to delete Disabilities : {}", id);
        disabilitiesRepository.delete(id);
        disabilitiesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/disabilities?query=:query : search for the disabilities corresponding
     * to the query.
     *
     * @param query the query of the disabilities search
     * @return the result of the search
     */
    @GetMapping("/_search/disabilities")
    @Timed
    public List<Disabilities> searchDisabilities(@RequestParam String query) {
        log.debug("REST request to search Disabilities for query {}", query);
        return StreamSupport
            .stream(disabilitiesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
