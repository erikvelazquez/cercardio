package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Background;

import mycercardiopackege.jh.repository.BackgroundRepository;
import mycercardiopackege.jh.repository.search.BackgroundSearchRepository;
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
 * REST controller for managing Background.
 */
@RestController
@RequestMapping("/api")
public class BackgroundResource {

    private final Logger log = LoggerFactory.getLogger(BackgroundResource.class);

    private static final String ENTITY_NAME = "background";

    private final BackgroundRepository backgroundRepository;

    private final BackgroundSearchRepository backgroundSearchRepository;

    public BackgroundResource(BackgroundRepository backgroundRepository, BackgroundSearchRepository backgroundSearchRepository) {
        this.backgroundRepository = backgroundRepository;
        this.backgroundSearchRepository = backgroundSearchRepository;
    }

    /**
     * POST  /backgrounds : Create a new background.
     *
     * @param background the background to create
     * @return the ResponseEntity with status 201 (Created) and with body the new background, or with status 400 (Bad Request) if the background has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/backgrounds")
    @Timed
    public ResponseEntity<Background> createBackground(@RequestBody Background background) throws URISyntaxException {
        log.debug("REST request to save Background : {}", background);
        if (background.getId() != null) {
            throw new BadRequestAlertException("A new background cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Background result = backgroundRepository.save(background);
        backgroundSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/backgrounds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /backgrounds : Updates an existing background.
     *
     * @param background the background to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated background,
     * or with status 400 (Bad Request) if the background is not valid,
     * or with status 500 (Internal Server Error) if the background couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/backgrounds")
    @Timed
    public ResponseEntity<Background> updateBackground(@RequestBody Background background) throws URISyntaxException {
        log.debug("REST request to update Background : {}", background);
        if (background.getId() == null) {
            return createBackground(background);
        }
        Background result = backgroundRepository.save(background);
        backgroundSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, background.getId().toString()))
            .body(result);
    }

    /**
     * GET  /backgrounds : get all the backgrounds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of backgrounds in body
     */
    @GetMapping("/backgrounds")
    @Timed
    public List<Background> getAllBackgrounds() {
        log.debug("REST request to get all Backgrounds");
        return backgroundRepository.findAll();
        }

    /**
     * GET  /backgrounds/:id : get the "id" background.
     *
     * @param id the id of the background to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the background, or with status 404 (Not Found)
     */
    @GetMapping("/backgrounds/{id}")
    @Timed
    public ResponseEntity<Background> getBackground(@PathVariable Long id) {
        log.debug("REST request to get Background : {}", id);
        Background background = backgroundRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(background));
    }

    /**
     * DELETE  /backgrounds/:id : delete the "id" background.
     *
     * @param id the id of the background to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/backgrounds/{id}")
    @Timed
    public ResponseEntity<Void> deleteBackground(@PathVariable Long id) {
        log.debug("REST request to delete Background : {}", id);
        backgroundRepository.delete(id);
        backgroundSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/backgrounds?query=:query : search for the background corresponding
     * to the query.
     *
     * @param query the query of the background search
     * @return the result of the search
     */
    @GetMapping("/_search/backgrounds")
    @Timed
    public List<Background> searchBackgrounds(@RequestParam String query) {
        log.debug("REST request to search Backgrounds for query {}", query);
        return StreamSupport
            .stream(backgroundSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
