package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.UserBD;

import mycercardiopackege.jh.repository.UserBDRepository;
import mycercardiopackege.jh.repository.search.UserBDSearchRepository;
import mycercardiopackege.jh.web.rest.errors.BadRequestAlertException;
import mycercardiopackege.jh.web.rest.util.HeaderUtil;
import mycercardiopackege.jh.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing UserBD.
 */
@RestController
@RequestMapping("/api")
public class UserBDResource {

    private final Logger log = LoggerFactory.getLogger(UserBDResource.class);

    private static final String ENTITY_NAME = "userBD";

    private final UserBDRepository userBDRepository;

    private final UserBDSearchRepository userBDSearchRepository;

    public UserBDResource(UserBDRepository userBDRepository, UserBDSearchRepository userBDSearchRepository) {
        this.userBDRepository = userBDRepository;
        this.userBDSearchRepository = userBDSearchRepository;
    }

    /**
     * POST  /user-bds : Create a new userBD.
     *
     * @param userBD the userBD to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userBD, or with status 400 (Bad Request) if the userBD has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-bds")
    @Timed
    public ResponseEntity<UserBD> createUserBD(@RequestBody UserBD userBD) throws URISyntaxException {
        log.debug("REST request to save UserBD : {}", userBD);
        if (userBD.getId() != null) {
            throw new BadRequestAlertException("A new userBD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBD result = userBDRepository.save(userBD);
        userBDSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-bds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-bds : Updates an existing userBD.
     *
     * @param userBD the userBD to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userBD,
     * or with status 400 (Bad Request) if the userBD is not valid,
     * or with status 500 (Internal Server Error) if the userBD couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-bds")
    @Timed
    public ResponseEntity<UserBD> updateUserBD(@RequestBody UserBD userBD) throws URISyntaxException {
        log.debug("REST request to update UserBD : {}", userBD);
        if (userBD.getId() == null) {
            return createUserBD(userBD);
        }
        UserBD result = userBDRepository.save(userBD);
        userBDSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userBD.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-bds : get all the userBDS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userBDS in body
     */
    @GetMapping("/user-bds")
    @Timed
    public ResponseEntity<List<UserBD>> getAllUserBDS(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserBDS");
        Page<UserBD> page = userBDRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-bds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-bds/:id : get the "id" userBD.
     *
     * @param id the id of the userBD to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userBD, or with status 404 (Not Found)
     */
    @GetMapping("/user-bds/{id}")
    @Timed
    public ResponseEntity<UserBD> getUserBD(@PathVariable Long id) {
        log.debug("REST request to get UserBD : {}", id);
        UserBD userBD = userBDRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userBD));
    }

    /**
     * DELETE  /user-bds/:id : delete the "id" userBD.
     *
     * @param id the id of the userBD to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-bds/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserBD(@PathVariable Long id) {
        log.debug("REST request to delete UserBD : {}", id);
        userBDRepository.delete(id);
        userBDSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-bds?query=:query : search for the userBD corresponding
     * to the query.
     *
     * @param query the query of the userBD search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-bds")
    @Timed
    public ResponseEntity<List<UserBD>> searchUserBDS(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of UserBDS for query {}", query);
        Page<UserBD> page = userBDSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-bds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
