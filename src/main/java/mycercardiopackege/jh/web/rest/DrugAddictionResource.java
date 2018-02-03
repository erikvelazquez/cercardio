package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.DrugAddiction;

import mycercardiopackege.jh.repository.DrugAddictionRepository;
import mycercardiopackege.jh.repository.search.DrugAddictionSearchRepository;
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
 * REST controller for managing DrugAddiction.
 */
@RestController
@RequestMapping("/api")
public class DrugAddictionResource {

    private final Logger log = LoggerFactory.getLogger(DrugAddictionResource.class);

    private static final String ENTITY_NAME = "drugAddiction";

    private final DrugAddictionRepository drugAddictionRepository;

    private final DrugAddictionSearchRepository drugAddictionSearchRepository;

    public DrugAddictionResource(DrugAddictionRepository drugAddictionRepository, DrugAddictionSearchRepository drugAddictionSearchRepository) {
        this.drugAddictionRepository = drugAddictionRepository;
        this.drugAddictionSearchRepository = drugAddictionSearchRepository;
    }

    /**
     * POST  /drug-addictions : Create a new drugAddiction.
     *
     * @param drugAddiction the drugAddiction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new drugAddiction, or with status 400 (Bad Request) if the drugAddiction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drug-addictions")
    @Timed
    public ResponseEntity<DrugAddiction> createDrugAddiction(@RequestBody DrugAddiction drugAddiction) throws URISyntaxException {
        log.debug("REST request to save DrugAddiction : {}", drugAddiction);
        if (drugAddiction.getId() != null) {
            throw new BadRequestAlertException("A new drugAddiction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugAddiction result = drugAddictionRepository.save(drugAddiction);
        drugAddictionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/drug-addictions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drug-addictions : Updates an existing drugAddiction.
     *
     * @param drugAddiction the drugAddiction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated drugAddiction,
     * or with status 400 (Bad Request) if the drugAddiction is not valid,
     * or with status 500 (Internal Server Error) if the drugAddiction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drug-addictions")
    @Timed
    public ResponseEntity<DrugAddiction> updateDrugAddiction(@RequestBody DrugAddiction drugAddiction) throws URISyntaxException {
        log.debug("REST request to update DrugAddiction : {}", drugAddiction);
        if (drugAddiction.getId() == null) {
            return createDrugAddiction(drugAddiction);
        }
        DrugAddiction result = drugAddictionRepository.save(drugAddiction);
        drugAddictionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, drugAddiction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drug-addictions : get all the drugAddictions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of drugAddictions in body
     */
    @GetMapping("/drug-addictions")
    @Timed
    public List<DrugAddiction> getAllDrugAddictions() {
        log.debug("REST request to get all DrugAddictions");
        return drugAddictionRepository.findAll();
        }

    /**
     * GET  /drug-addictions/:id : get the "id" drugAddiction.
     *
     * @param id the id of the drugAddiction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the drugAddiction, or with status 404 (Not Found)
     */
    @GetMapping("/drug-addictions/{id}")
    @Timed
    public ResponseEntity<DrugAddiction> getDrugAddiction(@PathVariable Long id) {
        log.debug("REST request to get DrugAddiction : {}", id);
        DrugAddiction drugAddiction = drugAddictionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(drugAddiction));
    }

    /**
     * DELETE  /drug-addictions/:id : delete the "id" drugAddiction.
     *
     * @param id the id of the drugAddiction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drug-addictions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDrugAddiction(@PathVariable Long id) {
        log.debug("REST request to delete DrugAddiction : {}", id);
        drugAddictionRepository.delete(id);
        drugAddictionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/drug-addictions?query=:query : search for the drugAddiction corresponding
     * to the query.
     *
     * @param query the query of the drugAddiction search
     * @return the result of the search
     */
    @GetMapping("/_search/drug-addictions")
    @Timed
    public List<DrugAddiction> searchDrugAddictions(@RequestParam String query) {
        log.debug("REST request to search DrugAddictions for query {}", query);
        return StreamSupport
            .stream(drugAddictionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
