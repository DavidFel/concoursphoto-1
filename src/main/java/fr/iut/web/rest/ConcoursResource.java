package fr.iut.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.domain.Concours;

import fr.iut.repository.ConcoursRepository;
import fr.iut.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Concours.
 */
@RestController
@RequestMapping("/api")
public class ConcoursResource {

    private final Logger log = LoggerFactory.getLogger(ConcoursResource.class);

    private static final String ENTITY_NAME = "concours";
        
    private final ConcoursRepository concoursRepository;

    public ConcoursResource(ConcoursRepository concoursRepository) {
        this.concoursRepository = concoursRepository;
    }

    /**
     * POST  /concours : Create a new concours.
     *
     * @param concours the concours to create
     * @return the ResponseEntity with status 201 (Created) and with body the new concours, or with status 400 (Bad Request) if the concours has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/concours")
    @Timed
    public ResponseEntity<Concours> createConcours(@Valid @RequestBody Concours concours) throws URISyntaxException {
        log.debug("REST request to save Concours : {}", concours);
        if (concours.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new concours cannot already have an ID")).body(null);
        }
        Concours result = concoursRepository.save(concours);
        return ResponseEntity.created(new URI("/api/concours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concours : Updates an existing concours.
     *
     * @param concours the concours to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated concours,
     * or with status 400 (Bad Request) if the concours is not valid,
     * or with status 500 (Internal Server Error) if the concours couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/concours")
    @Timed
    public ResponseEntity<Concours> updateConcours(@Valid @RequestBody Concours concours) throws URISyntaxException {
        log.debug("REST request to update Concours : {}", concours);
        if (concours.getId() == null) {
            return createConcours(concours);
        }
        Concours result = concoursRepository.save(concours);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, concours.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concours : get all the concours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of concours in body
     */
    @GetMapping("/concours")
    @Timed
    public List<Concours> getAllConcours() {
        log.debug("REST request to get all Concours");
        List<Concours> concours = concoursRepository.findAll();
        return concours;
    }

    /**
     * GET  /concours/:id : get the "id" concours.
     *
     * @param id the id of the concours to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the concours, or with status 404 (Not Found)
     */
    @GetMapping("/concours/{id}")
    @Timed
    public ResponseEntity<Concours> getConcours(@PathVariable Long id) {
        log.debug("REST request to get Concours : {}", id);
        Concours concours = concoursRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(concours));
    }

    /**
     * DELETE  /concours/:id : delete the "id" concours.
     *
     * @param id the id of the concours to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/concours/{id}")
    @Timed
    public ResponseEntity<Void> deleteConcours(@PathVariable Long id) {
        log.debug("REST request to delete Concours : {}", id);
        concoursRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
