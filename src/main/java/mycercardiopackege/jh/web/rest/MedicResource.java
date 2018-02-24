package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Medic;

import mycercardiopackege.jh.repository.MedicRepository;
import mycercardiopackege.jh.repository.search.MedicSearchRepository;
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
 * REST controller for managing Medic.
 */
@RestController
@RequestMapping("/api")
public class MedicResource {

    private final Logger log = LoggerFactory.getLogger(MedicResource.class);

    private static final String ENTITY_NAME = "medic";

    private final MedicRepository medicRepository;

    private final MedicSearchRepository medicSearchRepository;

    public MedicResource(MedicRepository medicRepository, MedicSearchRepository medicSearchRepository) {
        this.medicRepository = medicRepository;
        this.medicSearchRepository = medicSearchRepository;
    }

    /**
     * POST  /medics : Create a new medic.
     *
     * @param medic the medic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medic, or with status 400 (Bad Request) if the medic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medics")
    @Timed
    public ResponseEntity<Medic> createMedic(@RequestBody Medic medic) throws URISyntaxException {
        log.debug("REST request to save Medic : {}", medic);
        if (medic.getId() != null) {
            throw new BadRequestAlertException("A new medic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medic result = medicRepository.save(medic);
        medicSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medics : Updates an existing medic.
     *
     * @param medic the medic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medic,
     * or with status 400 (Bad Request) if the medic is not valid,
     * or with status 500 (Internal Server Error) if the medic couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medics")
    @Timed
    public ResponseEntity<Medic> updateMedic(@RequestBody Medic medic) throws URISyntaxException {
        log.debug("REST request to update Medic : {}", medic);
        if (medic.getId() == null) {
            return createMedic(medic);
        }
        Medic result = medicRepository.save(medic);
        medicSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medics : get all the medics.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medics in body
     */
    @GetMapping("/medics")
    @Timed
    public ResponseEntity<List<Medic>> getAllMedics(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Medics");
        Page<Medic> page = medicRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medics/:id : get the "id" medic.
     *
     * @param id the id of the medic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medic, or with status 404 (Not Found)
     */
    @GetMapping("/medics/{id}")
    @Timed
    public ResponseEntity<Medic> getMedic(@PathVariable Long id) {
        log.debug("REST request to get Medic : {}", id);
        Medic medic = medicRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medic));
    }

    /**
     * DELETE  /medics/:id : delete the "id" medic.
     *
     * @param id the id of the medic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medics/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {
        log.debug("REST request to delete Medic : {}", id);
        medicRepository.delete(id);
        medicSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medics?query=:query : search for the medic corresponding
     * to the query.
     *
     * @param query the query of the medic search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/medics")
    @Timed
    public ResponseEntity<List<Medic>> searchMedics(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Medics for query {}", query);
        Page<Medic> page = medicSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/medics");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
