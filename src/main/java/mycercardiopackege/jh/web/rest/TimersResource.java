package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Timers;

import mycercardiopackege.jh.repository.TimersRepository;
import mycercardiopackege.jh.repository.search.TimersSearchRepository;
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
 * REST controller for managing Timers.
 */
@RestController
@RequestMapping("/api")
public class TimersResource {

    private final Logger log = LoggerFactory.getLogger(TimersResource.class);

    private static final String ENTITY_NAME = "timers";

    private final TimersRepository timersRepository;

    private final TimersSearchRepository timersSearchRepository;

    public TimersResource(TimersRepository timersRepository, TimersSearchRepository timersSearchRepository) {
        this.timersRepository = timersRepository;
        this.timersSearchRepository = timersSearchRepository;
    }

    /**
     * POST  /timers : Create a new timers.
     *
     * @param timers the timers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timers, or with status 400 (Bad Request) if the timers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timers")
    @Timed
    public ResponseEntity<Timers> createTimers(@RequestBody Timers timers) throws URISyntaxException {
        log.debug("REST request to save Timers : {}", timers);
        if (timers.getId() != null) {
            throw new BadRequestAlertException("A new timers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Timers result = timersRepository.save(timers);
        timersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/timers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timers : Updates an existing timers.
     *
     * @param timers the timers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timers,
     * or with status 400 (Bad Request) if the timers is not valid,
     * or with status 500 (Internal Server Error) if the timers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timers")
    @Timed
    public ResponseEntity<Timers> updateTimers(@RequestBody Timers timers) throws URISyntaxException {
        log.debug("REST request to update Timers : {}", timers);
        if (timers.getId() == null) {
            return createTimers(timers);
        }
        Timers result = timersRepository.save(timers);
        timersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timers : get all the timers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timers in body
     */
    @GetMapping("/timers")
    @Timed
    public List<Timers> getAllTimers() {
        log.debug("REST request to get all Timers");
        return timersRepository.findAll();
        }

    /**
     * GET  /timers/:id : get the "id" timers.
     *
     * @param id the id of the timers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timers, or with status 404 (Not Found)
     */
    @GetMapping("/timers/{id}")
    @Timed
    public ResponseEntity<Timers> getTimers(@PathVariable Long id) {
        log.debug("REST request to get Timers : {}", id);
        Timers timers = timersRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timers));
    }

    /**
     * DELETE  /timers/:id : delete the "id" timers.
     *
     * @param id the id of the timers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timers/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimers(@PathVariable Long id) {
        log.debug("REST request to delete Timers : {}", id);
        timersRepository.delete(id);
        timersSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/timers?query=:query : search for the timers corresponding
     * to the query.
     *
     * @param query the query of the timers search
     * @return the result of the search
     */
    @GetMapping("/_search/timers")
    @Timed
    public List<Timers> searchTimers(@RequestParam String query) {
        log.debug("REST request to search Timers for query {}", query);
        return StreamSupport
            .stream(timersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
