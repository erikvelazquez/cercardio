package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.SocioeconomicLevel;

import mycercardiopackege.jh.repository.SocioeconomicLevelRepository;
import mycercardiopackege.jh.repository.search.SocioeconomicLevelSearchRepository;
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
 * REST controller for managing SocioeconomicLevel.
 */
@RestController
@RequestMapping("/api")
public class SocioeconomicLevelResource {

    private final Logger log = LoggerFactory.getLogger(SocioeconomicLevelResource.class);

    private static final String ENTITY_NAME = "socioeconomicLevel";

    private final SocioeconomicLevelRepository socioeconomicLevelRepository;

    private final SocioeconomicLevelSearchRepository socioeconomicLevelSearchRepository;

    public SocioeconomicLevelResource(SocioeconomicLevelRepository socioeconomicLevelRepository, SocioeconomicLevelSearchRepository socioeconomicLevelSearchRepository) {
        this.socioeconomicLevelRepository = socioeconomicLevelRepository;
        this.socioeconomicLevelSearchRepository = socioeconomicLevelSearchRepository;
    }

    /**
     * POST  /socioeconomic-levels : Create a new socioeconomicLevel.
     *
     * @param socioeconomicLevel the socioeconomicLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socioeconomicLevel, or with status 400 (Bad Request) if the socioeconomicLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/socioeconomic-levels")
    @Timed
    public ResponseEntity<SocioeconomicLevel> createSocioeconomicLevel(@RequestBody SocioeconomicLevel socioeconomicLevel) throws URISyntaxException {
        log.debug("REST request to save SocioeconomicLevel : {}", socioeconomicLevel);
        if (socioeconomicLevel.getId() != null) {
            throw new BadRequestAlertException("A new socioeconomicLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocioeconomicLevel result = socioeconomicLevelRepository.save(socioeconomicLevel);
        socioeconomicLevelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/socioeconomic-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /socioeconomic-levels : Updates an existing socioeconomicLevel.
     *
     * @param socioeconomicLevel the socioeconomicLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socioeconomicLevel,
     * or with status 400 (Bad Request) if the socioeconomicLevel is not valid,
     * or with status 500 (Internal Server Error) if the socioeconomicLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/socioeconomic-levels")
    @Timed
    public ResponseEntity<SocioeconomicLevel> updateSocioeconomicLevel(@RequestBody SocioeconomicLevel socioeconomicLevel) throws URISyntaxException {
        log.debug("REST request to update SocioeconomicLevel : {}", socioeconomicLevel);
        if (socioeconomicLevel.getId() == null) {
            return createSocioeconomicLevel(socioeconomicLevel);
        }
        SocioeconomicLevel result = socioeconomicLevelRepository.save(socioeconomicLevel);
        socioeconomicLevelSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socioeconomicLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /socioeconomic-levels : get all the socioeconomicLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socioeconomicLevels in body
     */
    @GetMapping("/socioeconomic-levels")
    @Timed
    public List<SocioeconomicLevel> getAllSocioeconomicLevels() {
        log.debug("REST request to get all SocioeconomicLevels");
        return socioeconomicLevelRepository.findAll();
        }

    /**
     * GET  /socioeconomic-levels/:id : get the "id" socioeconomicLevel.
     *
     * @param id the id of the socioeconomicLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socioeconomicLevel, or with status 404 (Not Found)
     */
    @GetMapping("/socioeconomic-levels/{id}")
    @Timed
    public ResponseEntity<SocioeconomicLevel> getSocioeconomicLevel(@PathVariable Long id) {
        log.debug("REST request to get SocioeconomicLevel : {}", id);
        SocioeconomicLevel socioeconomicLevel = socioeconomicLevelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(socioeconomicLevel));
    }

    /**
     * DELETE  /socioeconomic-levels/:id : delete the "id" socioeconomicLevel.
     *
     * @param id the id of the socioeconomicLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/socioeconomic-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocioeconomicLevel(@PathVariable Long id) {
        log.debug("REST request to delete SocioeconomicLevel : {}", id);
        socioeconomicLevelRepository.delete(id);
        socioeconomicLevelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/socioeconomic-levels?query=:query : search for the socioeconomicLevel corresponding
     * to the query.
     *
     * @param query the query of the socioeconomicLevel search
     * @return the result of the search
     */
    @GetMapping("/_search/socioeconomic-levels")
    @Timed
    public List<SocioeconomicLevel> searchSocioeconomicLevels(@RequestParam String query) {
        log.debug("REST request to search SocioeconomicLevels for query {}", query);
        return StreamSupport
            .stream(socioeconomicLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
