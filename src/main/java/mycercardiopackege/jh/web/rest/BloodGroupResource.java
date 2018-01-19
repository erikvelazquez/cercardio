package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.BloodGroup;

import mycercardiopackege.jh.repository.BloodGroupRepository;
import mycercardiopackege.jh.repository.search.BloodGroupSearchRepository;
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
 * REST controller for managing BloodGroup.
 */
@RestController
@RequestMapping("/api")
public class BloodGroupResource {

    private final Logger log = LoggerFactory.getLogger(BloodGroupResource.class);

    private static final String ENTITY_NAME = "bloodGroup";

    private final BloodGroupRepository bloodGroupRepository;

    private final BloodGroupSearchRepository bloodGroupSearchRepository;

    public BloodGroupResource(BloodGroupRepository bloodGroupRepository, BloodGroupSearchRepository bloodGroupSearchRepository) {
        this.bloodGroupRepository = bloodGroupRepository;
        this.bloodGroupSearchRepository = bloodGroupSearchRepository;
    }

    /**
     * POST  /blood-groups : Create a new bloodGroup.
     *
     * @param bloodGroup the bloodGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bloodGroup, or with status 400 (Bad Request) if the bloodGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blood-groups")
    @Timed
    public ResponseEntity<BloodGroup> createBloodGroup(@RequestBody BloodGroup bloodGroup) throws URISyntaxException {
        log.debug("REST request to save BloodGroup : {}", bloodGroup);
        if (bloodGroup.getId() != null) {
            throw new BadRequestAlertException("A new bloodGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodGroup result = bloodGroupRepository.save(bloodGroup);
        bloodGroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/blood-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blood-groups : Updates an existing bloodGroup.
     *
     * @param bloodGroup the bloodGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bloodGroup,
     * or with status 400 (Bad Request) if the bloodGroup is not valid,
     * or with status 500 (Internal Server Error) if the bloodGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blood-groups")
    @Timed
    public ResponseEntity<BloodGroup> updateBloodGroup(@RequestBody BloodGroup bloodGroup) throws URISyntaxException {
        log.debug("REST request to update BloodGroup : {}", bloodGroup);
        if (bloodGroup.getId() == null) {
            return createBloodGroup(bloodGroup);
        }
        BloodGroup result = bloodGroupRepository.save(bloodGroup);
        bloodGroupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bloodGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blood-groups : get all the bloodGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bloodGroups in body
     */
    @GetMapping("/blood-groups")
    @Timed
    public List<BloodGroup> getAllBloodGroups() {
        log.debug("REST request to get all BloodGroups");
        return bloodGroupRepository.findAll();
        }

    /**
     * GET  /blood-groups/:id : get the "id" bloodGroup.
     *
     * @param id the id of the bloodGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bloodGroup, or with status 404 (Not Found)
     */
    @GetMapping("/blood-groups/{id}")
    @Timed
    public ResponseEntity<BloodGroup> getBloodGroup(@PathVariable Long id) {
        log.debug("REST request to get BloodGroup : {}", id);
        BloodGroup bloodGroup = bloodGroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bloodGroup));
    }

    /**
     * DELETE  /blood-groups/:id : delete the "id" bloodGroup.
     *
     * @param id the id of the bloodGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blood-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteBloodGroup(@PathVariable Long id) {
        log.debug("REST request to delete BloodGroup : {}", id);
        bloodGroupRepository.delete(id);
        bloodGroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/blood-groups?query=:query : search for the bloodGroup corresponding
     * to the query.
     *
     * @param query the query of the bloodGroup search
     * @return the result of the search
     */
    @GetMapping("/_search/blood-groups")
    @Timed
    public List<BloodGroup> searchBloodGroups(@RequestParam String query) {
        log.debug("REST request to search BloodGroups for query {}", query);
        return StreamSupport
            .stream(bloodGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
