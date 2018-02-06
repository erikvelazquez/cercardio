package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.EthnicGroup;

import mycercardiopackege.jh.repository.EthnicGroupRepository;
import mycercardiopackege.jh.repository.search.EthnicGroupSearchRepository;
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
 * REST controller for managing EthnicGroup.
 */
@RestController
@RequestMapping("/api")
public class EthnicGroupResource {

    private final Logger log = LoggerFactory.getLogger(EthnicGroupResource.class);

    private static final String ENTITY_NAME = "ethnicGroup";

    private final EthnicGroupRepository ethnicGroupRepository;

    private final EthnicGroupSearchRepository ethnicGroupSearchRepository;

    public EthnicGroupResource(EthnicGroupRepository ethnicGroupRepository, EthnicGroupSearchRepository ethnicGroupSearchRepository) {
        this.ethnicGroupRepository = ethnicGroupRepository;
        this.ethnicGroupSearchRepository = ethnicGroupSearchRepository;
    }

    /**
     * POST  /ethnic-groups : Create a new ethnicGroup.
     *
     * @param ethnicGroup the ethnicGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ethnicGroup, or with status 400 (Bad Request) if the ethnicGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ethnic-groups")
    @Timed
    public ResponseEntity<EthnicGroup> createEthnicGroup(@RequestBody EthnicGroup ethnicGroup) throws URISyntaxException {
        log.debug("REST request to save EthnicGroup : {}", ethnicGroup);
        if (ethnicGroup.getId() != null) {
            throw new BadRequestAlertException("A new ethnicGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EthnicGroup result = ethnicGroupRepository.save(ethnicGroup);
        ethnicGroupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ethnic-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ethnic-groups : Updates an existing ethnicGroup.
     *
     * @param ethnicGroup the ethnicGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ethnicGroup,
     * or with status 400 (Bad Request) if the ethnicGroup is not valid,
     * or with status 500 (Internal Server Error) if the ethnicGroup couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ethnic-groups")
    @Timed
    public ResponseEntity<EthnicGroup> updateEthnicGroup(@RequestBody EthnicGroup ethnicGroup) throws URISyntaxException {
        log.debug("REST request to update EthnicGroup : {}", ethnicGroup);
        if (ethnicGroup.getId() == null) {
            return createEthnicGroup(ethnicGroup);
        }
        EthnicGroup result = ethnicGroupRepository.save(ethnicGroup);
        ethnicGroupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ethnicGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ethnic-groups : get all the ethnicGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ethnicGroups in body
     */
    @GetMapping("/ethnic-groups")
    @Timed
    public List<EthnicGroup> getAllEthnicGroups() {
        log.debug("REST request to get all EthnicGroups");
        return ethnicGroupRepository.findAll();
        }

    /**
     * GET  /ethnic-groups/:id : get the "id" ethnicGroup.
     *
     * @param id the id of the ethnicGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ethnicGroup, or with status 404 (Not Found)
     */
    @GetMapping("/ethnic-groups/{id}")
    @Timed
    public ResponseEntity<EthnicGroup> getEthnicGroup(@PathVariable Long id) {
        log.debug("REST request to get EthnicGroup : {}", id);
        EthnicGroup ethnicGroup = ethnicGroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ethnicGroup));
    }

    /**
     * DELETE  /ethnic-groups/:id : delete the "id" ethnicGroup.
     *
     * @param id the id of the ethnicGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ethnic-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteEthnicGroup(@PathVariable Long id) {
        log.debug("REST request to delete EthnicGroup : {}", id);
        ethnicGroupRepository.delete(id);
        ethnicGroupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ethnic-groups?query=:query : search for the ethnicGroup corresponding
     * to the query.
     *
     * @param query the query of the ethnicGroup search
     * @return the result of the search
     */
    @GetMapping("/_search/ethnic-groups")
    @Timed
    public List<EthnicGroup> searchEthnicGroups(@RequestParam String query) {
        log.debug("REST request to search EthnicGroups for query {}", query);
        return StreamSupport
            .stream(ethnicGroupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
