package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.LivingPlace;

import mycercardiopackege.jh.repository.LivingPlaceRepository;
import mycercardiopackege.jh.repository.search.LivingPlaceSearchRepository;
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
 * REST controller for managing LivingPlace.
 */
@RestController
@RequestMapping("/api")
public class LivingPlaceResource {

    private final Logger log = LoggerFactory.getLogger(LivingPlaceResource.class);

    private static final String ENTITY_NAME = "livingPlace";

    private final LivingPlaceRepository livingPlaceRepository;

    private final LivingPlaceSearchRepository livingPlaceSearchRepository;

    public LivingPlaceResource(LivingPlaceRepository livingPlaceRepository, LivingPlaceSearchRepository livingPlaceSearchRepository) {
        this.livingPlaceRepository = livingPlaceRepository;
        this.livingPlaceSearchRepository = livingPlaceSearchRepository;
    }

    /**
     * POST  /living-places : Create a new livingPlace.
     *
     * @param livingPlace the livingPlace to create
     * @return the ResponseEntity with status 201 (Created) and with body the new livingPlace, or with status 400 (Bad Request) if the livingPlace has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/living-places")
    @Timed
    public ResponseEntity<LivingPlace> createLivingPlace(@RequestBody LivingPlace livingPlace) throws URISyntaxException {
        log.debug("REST request to save LivingPlace : {}", livingPlace);
        if (livingPlace.getId() != null) {
            throw new BadRequestAlertException("A new livingPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LivingPlace result = livingPlaceRepository.save(livingPlace);
        livingPlaceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/living-places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /living-places : Updates an existing livingPlace.
     *
     * @param livingPlace the livingPlace to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated livingPlace,
     * or with status 400 (Bad Request) if the livingPlace is not valid,
     * or with status 500 (Internal Server Error) if the livingPlace couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/living-places")
    @Timed
    public ResponseEntity<LivingPlace> updateLivingPlace(@RequestBody LivingPlace livingPlace) throws URISyntaxException {
        log.debug("REST request to update LivingPlace : {}", livingPlace);
        if (livingPlace.getId() == null) {
            return createLivingPlace(livingPlace);
        }
        LivingPlace result = livingPlaceRepository.save(livingPlace);
        livingPlaceSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, livingPlace.getId().toString()))
            .body(result);
    }

    /**
     * GET  /living-places : get all the livingPlaces.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of livingPlaces in body
     */
    @GetMapping("/living-places")
    @Timed
    public List<LivingPlace> getAllLivingPlaces() {
        log.debug("REST request to get all LivingPlaces");
        return livingPlaceRepository.findAll();
        }

    /**
     * GET  /living-places/:id : get the "id" livingPlace.
     *
     * @param id the id of the livingPlace to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the livingPlace, or with status 404 (Not Found)
     */
    @GetMapping("/living-places/{id}")
    @Timed
    public ResponseEntity<LivingPlace> getLivingPlace(@PathVariable Long id) {
        log.debug("REST request to get LivingPlace : {}", id);
        LivingPlace livingPlace = livingPlaceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(livingPlace));
    }

    /**
     * DELETE  /living-places/:id : delete the "id" livingPlace.
     *
     * @param id the id of the livingPlace to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/living-places/{id}")
    @Timed
    public ResponseEntity<Void> deleteLivingPlace(@PathVariable Long id) {
        log.debug("REST request to delete LivingPlace : {}", id);
        livingPlaceRepository.delete(id);
        livingPlaceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/living-places?query=:query : search for the livingPlace corresponding
     * to the query.
     *
     * @param query the query of the livingPlace search
     * @return the result of the search
     */
    @GetMapping("/_search/living-places")
    @Timed
    public List<LivingPlace> searchLivingPlaces(@RequestParam String query) {
        log.debug("REST request to search LivingPlaces for query {}", query);
        return StreamSupport
            .stream(livingPlaceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
