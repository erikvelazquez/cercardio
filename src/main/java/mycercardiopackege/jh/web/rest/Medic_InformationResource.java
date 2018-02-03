package mycercardiopackege.jh.web.rest;

import com.codahale.metrics.annotation.Timed;
import mycercardiopackege.jh.domain.Medic_Information;

import mycercardiopackege.jh.repository.Medic_InformationRepository;
import mycercardiopackege.jh.repository.search.Medic_InformationSearchRepository;
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
 * REST controller for managing Medic_Information.
 */
@RestController
@RequestMapping("/api")
public class Medic_InformationResource {

    private final Logger log = LoggerFactory.getLogger(Medic_InformationResource.class);

    private static final String ENTITY_NAME = "medic_Information";

    private final Medic_InformationRepository medic_InformationRepository;

    private final Medic_InformationSearchRepository medic_InformationSearchRepository;

    public Medic_InformationResource(Medic_InformationRepository medic_InformationRepository, Medic_InformationSearchRepository medic_InformationSearchRepository) {
        this.medic_InformationRepository = medic_InformationRepository;
        this.medic_InformationSearchRepository = medic_InformationSearchRepository;
    }

    /**
     * POST  /medic-informations : Create a new medic_Information.
     *
     * @param medic_Information the medic_Information to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medic_Information, or with status 400 (Bad Request) if the medic_Information has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medic-informations")
    @Timed
    public ResponseEntity<Medic_Information> createMedic_Information(@RequestBody Medic_Information medic_Information) throws URISyntaxException {
        log.debug("REST request to save Medic_Information : {}", medic_Information);
        if (medic_Information.getId() != null) {
            throw new BadRequestAlertException("A new medic_Information cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medic_Information result = medic_InformationRepository.save(medic_Information);
        medic_InformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/medic-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medic-informations : Updates an existing medic_Information.
     *
     * @param medic_Information the medic_Information to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medic_Information,
     * or with status 400 (Bad Request) if the medic_Information is not valid,
     * or with status 500 (Internal Server Error) if the medic_Information couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medic-informations")
    @Timed
    public ResponseEntity<Medic_Information> updateMedic_Information(@RequestBody Medic_Information medic_Information) throws URISyntaxException {
        log.debug("REST request to update Medic_Information : {}", medic_Information);
        if (medic_Information.getId() == null) {
            return createMedic_Information(medic_Information);
        }
        Medic_Information result = medic_InformationRepository.save(medic_Information);
        medic_InformationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medic_Information.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medic-informations : get all the medic_Informations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medic_Informations in body
     */
    @GetMapping("/medic-informations")
    @Timed
    public List<Medic_Information> getAllMedic_Informations() {
        log.debug("REST request to get all Medic_Informations");
        return medic_InformationRepository.findAll();
        }

    /**
     * GET  /medic-informations/:id : get the "id" medic_Information.
     *
     * @param id the id of the medic_Information to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medic_Information, or with status 404 (Not Found)
     */
    @GetMapping("/medic-informations/{id}")
    @Timed
    public ResponseEntity<Medic_Information> getMedic_Information(@PathVariable Long id) {
        log.debug("REST request to get Medic_Information : {}", id);
        Medic_Information medic_Information = medic_InformationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medic_Information));
    }

    /**
     * DELETE  /medic-informations/:id : delete the "id" medic_Information.
     *
     * @param id the id of the medic_Information to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medic-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedic_Information(@PathVariable Long id) {
        log.debug("REST request to delete Medic_Information : {}", id);
        medic_InformationRepository.delete(id);
        medic_InformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medic-informations?query=:query : search for the medic_Information corresponding
     * to the query.
     *
     * @param query the query of the medic_Information search
     * @return the result of the search
     */
    @GetMapping("/_search/medic-informations")
    @Timed
    public List<Medic_Information> searchMedic_Informations(@RequestParam String query) {
        log.debug("REST request to search Medic_Informations for query {}", query);
        return StreamSupport
            .stream(medic_InformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
