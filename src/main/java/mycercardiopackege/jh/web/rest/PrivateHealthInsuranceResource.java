package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.PrivateHealthInsurance;

import mycercardiopackege.jh.repository.PrivateHealthInsuranceRepository;
import mycercardiopackege.jh.repository.search.PrivateHealthInsuranceSearchRepository;
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
 * REST controller for managing PrivateHealthInsurance.
 */
@RestController
@RequestMapping("/api")
public class PrivateHealthInsuranceResource {

    private final Logger log = LoggerFactory.getLogger(PrivateHealthInsuranceResource.class);

    private static final String ENTITY_NAME = "privateHealthInsurance";

    private final PrivateHealthInsuranceRepository privateHealthInsuranceRepository;

    private final PrivateHealthInsuranceSearchRepository privateHealthInsuranceSearchRepository;

    public PrivateHealthInsuranceResource(PrivateHealthInsuranceRepository privateHealthInsuranceRepository, PrivateHealthInsuranceSearchRepository privateHealthInsuranceSearchRepository) {
        this.privateHealthInsuranceRepository = privateHealthInsuranceRepository;
        this.privateHealthInsuranceSearchRepository = privateHealthInsuranceSearchRepository;
    }

    /**
     * POST  /private-health-insurances : Create a new privateHealthInsurance.
     *
     * @param privateHealthInsurance the privateHealthInsurance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new privateHealthInsurance, or with status 400 (Bad Request) if the privateHealthInsurance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/private-health-insurances")
    @Timed
    public ResponseEntity<PrivateHealthInsurance> createPrivateHealthInsurance(@RequestBody PrivateHealthInsurance privateHealthInsurance) throws URISyntaxException {
        log.debug("REST request to save PrivateHealthInsurance : {}", privateHealthInsurance);
        if (privateHealthInsurance.getId() != null) {
            throw new BadRequestAlertException("A new privateHealthInsurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrivateHealthInsurance result = privateHealthInsuranceRepository.save(privateHealthInsurance);
        privateHealthInsuranceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/private-health-insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /private-health-insurances : Updates an existing privateHealthInsurance.
     *
     * @param privateHealthInsurance the privateHealthInsurance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated privateHealthInsurance,
     * or with status 400 (Bad Request) if the privateHealthInsurance is not valid,
     * or with status 500 (Internal Server Error) if the privateHealthInsurance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/private-health-insurances")
    @Timed
    public ResponseEntity<PrivateHealthInsurance> updatePrivateHealthInsurance(@RequestBody PrivateHealthInsurance privateHealthInsurance) throws URISyntaxException {
        log.debug("REST request to update PrivateHealthInsurance : {}", privateHealthInsurance);
        if (privateHealthInsurance.getId() == null) {
            return createPrivateHealthInsurance(privateHealthInsurance);
        }
        PrivateHealthInsurance result = privateHealthInsuranceRepository.save(privateHealthInsurance);
        privateHealthInsuranceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, privateHealthInsurance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /private-health-insurances : get all the privateHealthInsurances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of privateHealthInsurances in body
     */
    @GetMapping("/private-health-insurances")
    @Timed
    public List<PrivateHealthInsurance> getAllPrivateHealthInsurances() {
        log.debug("REST request to get all PrivateHealthInsurances");
        return privateHealthInsuranceRepository.findAll();
        }

    /**
     * GET  /private-health-insurances/:id : get the "id" privateHealthInsurance.
     *
     * @param id the id of the privateHealthInsurance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the privateHealthInsurance, or with status 404 (Not Found)
     */
    @GetMapping("/private-health-insurances/{id}")
    @Timed
    public ResponseEntity<PrivateHealthInsurance> getPrivateHealthInsurance(@PathVariable Long id) {
        log.debug("REST request to get PrivateHealthInsurance : {}", id);
        PrivateHealthInsurance privateHealthInsurance = privateHealthInsuranceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(privateHealthInsurance));
    }

    /**
     * DELETE  /private-health-insurances/:id : delete the "id" privateHealthInsurance.
     *
     * @param id the id of the privateHealthInsurance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/private-health-insurances/{id}")
    @Timed
    public ResponseEntity<Void> deletePrivateHealthInsurance(@PathVariable Long id) {
        log.debug("REST request to delete PrivateHealthInsurance : {}", id);
        privateHealthInsuranceRepository.delete(id);
        privateHealthInsuranceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/private-health-insurances?query=:query : search for the privateHealthInsurance corresponding
     * to the query.
     *
     * @param query the query of the privateHealthInsurance search
     * @return the result of the search
     */
    @GetMapping("/_search/private-health-insurances")
    @Timed
    public List<PrivateHealthInsurance> searchPrivateHealthInsurances(@RequestParam String query) {
        log.debug("REST request to search PrivateHealthInsurances for query {}", query);
        return StreamSupport
            .stream(privateHealthInsuranceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
