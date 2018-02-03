package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.SocialHealthInsurance;

import mycercardiopackege.jh.repository.SocialHealthInsuranceRepository;
import mycercardiopackege.jh.repository.search.SocialHealthInsuranceSearchRepository;
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
 * REST controller for managing SocialHealthInsurance.
 */
@RestController
@RequestMapping("/api")
public class SocialHealthInsuranceResource {

    private final Logger log = LoggerFactory.getLogger(SocialHealthInsuranceResource.class);

    private static final String ENTITY_NAME = "socialHealthInsurance";

    private final SocialHealthInsuranceRepository socialHealthInsuranceRepository;

    private final SocialHealthInsuranceSearchRepository socialHealthInsuranceSearchRepository;

    public SocialHealthInsuranceResource(SocialHealthInsuranceRepository socialHealthInsuranceRepository, SocialHealthInsuranceSearchRepository socialHealthInsuranceSearchRepository) {
        this.socialHealthInsuranceRepository = socialHealthInsuranceRepository;
        this.socialHealthInsuranceSearchRepository = socialHealthInsuranceSearchRepository;
    }

    /**
     * POST  /social-health-insurances : Create a new socialHealthInsurance.
     *
     * @param socialHealthInsurance the socialHealthInsurance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socialHealthInsurance, or with status 400 (Bad Request) if the socialHealthInsurance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/social-health-insurances")
    @Timed
    public ResponseEntity<SocialHealthInsurance> createSocialHealthInsurance(@RequestBody SocialHealthInsurance socialHealthInsurance) throws URISyntaxException {
        log.debug("REST request to save SocialHealthInsurance : {}", socialHealthInsurance);
        if (socialHealthInsurance.getId() != null) {
            throw new BadRequestAlertException("A new socialHealthInsurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialHealthInsurance result = socialHealthInsuranceRepository.save(socialHealthInsurance);
        socialHealthInsuranceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/social-health-insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /social-health-insurances : Updates an existing socialHealthInsurance.
     *
     * @param socialHealthInsurance the socialHealthInsurance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socialHealthInsurance,
     * or with status 400 (Bad Request) if the socialHealthInsurance is not valid,
     * or with status 500 (Internal Server Error) if the socialHealthInsurance couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/social-health-insurances")
    @Timed
    public ResponseEntity<SocialHealthInsurance> updateSocialHealthInsurance(@RequestBody SocialHealthInsurance socialHealthInsurance) throws URISyntaxException {
        log.debug("REST request to update SocialHealthInsurance : {}", socialHealthInsurance);
        if (socialHealthInsurance.getId() == null) {
            return createSocialHealthInsurance(socialHealthInsurance);
        }
        SocialHealthInsurance result = socialHealthInsuranceRepository.save(socialHealthInsurance);
        socialHealthInsuranceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socialHealthInsurance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /social-health-insurances : get all the socialHealthInsurances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socialHealthInsurances in body
     */
    @GetMapping("/social-health-insurances")
    @Timed
    public List<SocialHealthInsurance> getAllSocialHealthInsurances() {
        log.debug("REST request to get all SocialHealthInsurances");
        return socialHealthInsuranceRepository.findAll();
        }

    /**
     * GET  /social-health-insurances/:id : get the "id" socialHealthInsurance.
     *
     * @param id the id of the socialHealthInsurance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socialHealthInsurance, or with status 404 (Not Found)
     */
    @GetMapping("/social-health-insurances/{id}")
    @Timed
    public ResponseEntity<SocialHealthInsurance> getSocialHealthInsurance(@PathVariable Long id) {
        log.debug("REST request to get SocialHealthInsurance : {}", id);
        SocialHealthInsurance socialHealthInsurance = socialHealthInsuranceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(socialHealthInsurance));
    }

    /**
     * DELETE  /social-health-insurances/:id : delete the "id" socialHealthInsurance.
     *
     * @param id the id of the socialHealthInsurance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/social-health-insurances/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocialHealthInsurance(@PathVariable Long id) {
        log.debug("REST request to delete SocialHealthInsurance : {}", id);
        socialHealthInsuranceRepository.delete(id);
        socialHealthInsuranceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/social-health-insurances?query=:query : search for the socialHealthInsurance corresponding
     * to the query.
     *
     * @param query the query of the socialHealthInsurance search
     * @return the result of the search
     */
    @GetMapping("/_search/social-health-insurances")
    @Timed
    public List<SocialHealthInsurance> searchSocialHealthInsurances(@RequestParam String query) {
        log.debug("REST request to search SocialHealthInsurances for query {}", query);
        return StreamSupport
            .stream(socialHealthInsuranceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
