package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Indigenous_Languages;

import mycercardiopackege.jh.repository.Indigenous_LanguagesRepository;
import mycercardiopackege.jh.repository.search.Indigenous_LanguagesSearchRepository;
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
 * REST controller for managing Indigenous_Languages.
 */
@RestController
@RequestMapping("/api")
public class Indigenous_LanguagesResource {

    private final Logger log = LoggerFactory.getLogger(Indigenous_LanguagesResource.class);

    private static final String ENTITY_NAME = "indigenous_Languages";

    private final Indigenous_LanguagesRepository indigenous_LanguagesRepository;

    private final Indigenous_LanguagesSearchRepository indigenous_LanguagesSearchRepository;

    public Indigenous_LanguagesResource(Indigenous_LanguagesRepository indigenous_LanguagesRepository, Indigenous_LanguagesSearchRepository indigenous_LanguagesSearchRepository) {
        this.indigenous_LanguagesRepository = indigenous_LanguagesRepository;
        this.indigenous_LanguagesSearchRepository = indigenous_LanguagesSearchRepository;
    }

    /**
     * POST  /indigenous-languages : Create a new indigenous_Languages.
     *
     * @param indigenous_Languages the indigenous_Languages to create
     * @return the ResponseEntity with status 201 (Created) and with body the new indigenous_Languages, or with status 400 (Bad Request) if the indigenous_Languages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/indigenous-languages")
    @Timed
    public ResponseEntity<Indigenous_Languages> createIndigenous_Languages(@RequestBody Indigenous_Languages indigenous_Languages) throws URISyntaxException {
        log.debug("REST request to save Indigenous_Languages : {}", indigenous_Languages);
        if (indigenous_Languages.getId() != null) {
            throw new BadRequestAlertException("A new indigenous_Languages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Indigenous_Languages result = indigenous_LanguagesRepository.save(indigenous_Languages);
        indigenous_LanguagesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/indigenous-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /indigenous-languages : Updates an existing indigenous_Languages.
     *
     * @param indigenous_Languages the indigenous_Languages to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated indigenous_Languages,
     * or with status 400 (Bad Request) if the indigenous_Languages is not valid,
     * or with status 500 (Internal Server Error) if the indigenous_Languages couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/indigenous-languages")
    @Timed
    public ResponseEntity<Indigenous_Languages> updateIndigenous_Languages(@RequestBody Indigenous_Languages indigenous_Languages) throws URISyntaxException {
        log.debug("REST request to update Indigenous_Languages : {}", indigenous_Languages);
        if (indigenous_Languages.getId() == null) {
            return createIndigenous_Languages(indigenous_Languages);
        }
        Indigenous_Languages result = indigenous_LanguagesRepository.save(indigenous_Languages);
        indigenous_LanguagesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, indigenous_Languages.getId().toString()))
            .body(result);
    }

    /**
     * GET  /indigenous-languages : get all the indigenous_Languages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of indigenous_Languages in body
     */
    @GetMapping("/indigenous-languages")
    @Timed
    public List<Indigenous_Languages> getAllIndigenous_Languages() {
        log.debug("REST request to get all Indigenous_Languages");
        return indigenous_LanguagesRepository.findAll();
        }

    /**
     * GET  /indigenous-languages/:id : get the "id" indigenous_Languages.
     *
     * @param id the id of the indigenous_Languages to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the indigenous_Languages, or with status 404 (Not Found)
     */
    @GetMapping("/indigenous-languages/{id}")
    @Timed
    public ResponseEntity<Indigenous_Languages> getIndigenous_Languages(@PathVariable Long id) {
        log.debug("REST request to get Indigenous_Languages : {}", id);
        Indigenous_Languages indigenous_Languages = indigenous_LanguagesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(indigenous_Languages));
    }

    /**
     * DELETE  /indigenous-languages/:id : delete the "id" indigenous_Languages.
     *
     * @param id the id of the indigenous_Languages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/indigenous-languages/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndigenous_Languages(@PathVariable Long id) {
        log.debug("REST request to delete Indigenous_Languages : {}", id);
        indigenous_LanguagesRepository.delete(id);
        indigenous_LanguagesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/indigenous-languages?query=:query : search for the indigenous_Languages corresponding
     * to the query.
     *
     * @param query the query of the indigenous_Languages search
     * @return the result of the search
     */
    @GetMapping("/_search/indigenous-languages")
    @Timed
    public List<Indigenous_Languages> searchIndigenous_Languages(@RequestParam String query) {
        log.debug("REST request to search Indigenous_Languages for query {}", query);
        return StreamSupport
            .stream(indigenous_LanguagesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
