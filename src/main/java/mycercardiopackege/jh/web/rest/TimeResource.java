package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Time;

import mycercardiopackege.jh.repository.TimeRepository;
import mycercardiopackege.jh.repository.search.TimeSearchRepository;
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
 * REST controller for managing Time.
 */
@RestController
@RequestMapping("/api")
public class TimeResource {

    private final Logger log = LoggerFactory.getLogger(TimeResource.class);

    private static final String ENTITY_NAME = "time";

    private final TimeRepository timeRepository;

    private final TimeSearchRepository timeSearchRepository;

    public TimeResource(TimeRepository timeRepository, TimeSearchRepository timeSearchRepository) {
        this.timeRepository = timeRepository;
        this.timeSearchRepository = timeSearchRepository;
    }

    /**
     * POST  /times : Create a new time.
     *
     * @param time the time to create
     * @return the ResponseEntity with status 201 (Created) and with body the new time, or with status 400 (Bad Request) if the time has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/times")
    @Timed
    public ResponseEntity<Time> createTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to save Time : {}", time);
        if (time.getId() != null) {
            throw new BadRequestAlertException("A new time cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Time result = timeRepository.save(time);
        timeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /times : Updates an existing time.
     *
     * @param time the time to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated time,
     * or with status 400 (Bad Request) if the time is not valid,
     * or with status 500 (Internal Server Error) if the time couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/times")
    @Timed
    public ResponseEntity<Time> updateTime(@RequestBody Time time) throws URISyntaxException {
        log.debug("REST request to update Time : {}", time);
        if (time.getId() == null) {
            return createTime(time);
        }
        Time result = timeRepository.save(time);
        timeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, time.getId().toString()))
            .body(result);
    }

    /**
     * GET  /times : get all the times.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of times in body
     */
    @GetMapping("/times")
    @Timed
    public List<Time> getAllTimes() {
        log.debug("REST request to get all Times");
        return timeRepository.findAll();
        }

    /**
     * GET  /times/:id : get the "id" time.
     *
     * @param id the id of the time to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the time, or with status 404 (Not Found)
     */
    @GetMapping("/times/{id}")
    @Timed
    public ResponseEntity<Time> getTime(@PathVariable Long id) {
        log.debug("REST request to get Time : {}", id);
        Time time = timeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(time));
    }

    /**
     * DELETE  /times/:id : delete the "id" time.
     *
     * @param id the id of the time to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/times/{id}")
    @Timed
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        log.debug("REST request to delete Time : {}", id);
        timeRepository.delete(id);
        timeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/times?query=:query : search for the time corresponding
     * to the query.
     *
     * @param query the query of the time search
     * @return the result of the search
     */
    @GetMapping("/_search/times")
    @Timed
    public List<Time> searchTimes(@RequestParam String query) {
        log.debug("REST request to search Times for query {}", query);
        return StreamSupport
            .stream(timeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
