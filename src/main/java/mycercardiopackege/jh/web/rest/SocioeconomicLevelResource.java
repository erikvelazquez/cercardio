package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.SocioEconomicLevel;

import mycercardiopackege.jh.repository.SocioEconomicLevelRepository;
import mycercardiopackege.jh.repository.search.SocioEconomicLevelSearchRepository;
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
 * REST controller for managing SocioEconomicLevel.
 */
@RestController
@RequestMapping("/api")
public class SocioEconomicLevelResource {

    private final Logger log = LoggerFactory.getLogger(SocioEconomicLevelResource.class);

    private static final String ENTITY_NAME = "SocioEconomicLevel";

    private final SocioEconomicLevelRepository socioEconomicLevelRepository;

    private final SocioEconomicLevelSearchRepository socioEconomicLevelSearchRepository;

    public SocioEconomicLevelResource(SocioEconomicLevelRepository socioEconomicLevelRepository, SocioEconomicLevelSearchRepository socioEconomicLevelSearchRepository) {
        this.socioEconomicLevelRepository = socioEconomicLevelRepository;
        this.socioEconomicLevelSearchRepository = socioEconomicLevelSearchRepository;
    }

    /**
     * POST  /socioEconomic-levels : Create a new socioEconomicLevel.
     *
     * @param socioEconomicLevel the socioEconomicLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new socioEconomicLevel, or with status 400 (Bad Request) if the socioEconomicLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/socioEconomic-levels")
    @Timed
    public ResponseEntity<SocioEconomicLevel> createSocioEconomicLevel(@RequestBody SocioEconomicLevel socioEconomicLevel) throws URISyntaxException {
        log.debug("REST request to save SocioEconomicLevel : {}", socioEconomicLevel);
        if (socioEconomicLevel.getId() != null) {
            throw new BadRequestAlertException("A new socioEconomicLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocioEconomicLevel result = socioEconomicLevelRepository.save(socioEconomicLevel);
        socioEconomicLevelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/socio-economic-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /socioEconomic-levels : Updates an existing socioEconomicLevel.
     *
     * @param socioEconomicLevel the socioEconomicLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated socioEconomicLevel,
     * or with status 400 (Bad Request) if the socioEconomicLevel is not valid,
     * or with status 500 (Internal Server Error) if the socioEconomicLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/socio-economic-levels")
    @Timed
    public ResponseEntity<SocioEconomicLevel> updateSocioEconomicLevel(@RequestBody SocioEconomicLevel socioEconomicLevel) throws URISyntaxException {
        log.debug("REST request to update SocioEconomicLevel : {}", socioEconomicLevel);
        if (socioEconomicLevel.getId() == null) {
            return createSocioEconomicLevel(socioEconomicLevel);
        }
        SocioEconomicLevel result = socioEconomicLevelRepository.save(socioEconomicLevel);
        socioEconomicLevelSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, socioEconomicLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /socioEconomic-levels : get all the socioEconomicLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of socioEconomicLevels in body
     */
    @GetMapping("/socioEconomic-levels")
    @Timed
    public List<SocioEconomicLevel> getAllSocioEconomicLevels() {
        log.debug("REST request to get all SocioEconomicLevels");
        return socioEconomicLevelRepository.findAll();
        }

    /**
     * GET  /socioEconomic-levels/:id : get the "id" socioEconomicLevel.
     *
     * @param id the id of the socioEconomicLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the socioEconomicLevel, or with status 404 (Not Found)
     */
    @GetMapping("/socio-economic-levels/{id}")
    @Timed
    public ResponseEntity<SocioEconomicLevel> getSocioEconomicLevel(@PathVariable Long id) {
        log.debug("REST request to get SocioEconomicLevel : {}", id);
        SocioEconomicLevel socioEconomicLevel = socioEconomicLevelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(socioEconomicLevel));
    }

    /**
     * DELETE  /socioEconomic-levels/:id : delete the "id" socioEconomicLevel.
     *
     * @param id the id of the socioEconomicLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/socio-economic-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteSocioEconomicLevel(@PathVariable Long id) {
        log.debug("REST request to delete SocioEconomicLevel : {}", id);
        socioEconomicLevelRepository.delete(id);
        socioEconomicLevelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/socioEconomic-levels?query=:query : search for the socioEconomicLevel corresponding
     * to the query.
     *
     * @param query the query of the socioEconomicLevel search
     * @return the result of the search
     */
    @GetMapping("/_search/socio-economic-levels")
    @Timed
    public List<SocioEconomicLevel> searchSocioEconomicLevels(@RequestParam String query) {
        log.debug("REST request to search SocioEconomicLevels for query {}", query);
        return StreamSupport
            .stream(socioEconomicLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
