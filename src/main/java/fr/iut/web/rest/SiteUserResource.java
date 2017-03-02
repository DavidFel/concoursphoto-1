package fr.iut.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.domain.SiteUser;

import fr.iut.repository.SiteUserRepository;
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
 * REST controller for managing SiteUser.
 */
@RestController
@RequestMapping("/api")
public class SiteUserResource {

    private final Logger log = LoggerFactory.getLogger(SiteUserResource.class);

    private static final String ENTITY_NAME = "siteUser";
        
    private final SiteUserRepository siteUserRepository;

    public SiteUserResource(SiteUserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
    }

    /**
     * POST  /site-users : Create a new siteUser.
     *
     * @param siteUser the siteUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siteUser, or with status 400 (Bad Request) if the siteUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/site-users")
    @Timed
    public ResponseEntity<SiteUser> createSiteUser(@Valid @RequestBody SiteUser siteUser) throws URISyntaxException {
        log.debug("REST request to save SiteUser : {}", siteUser);
        if (siteUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new siteUser cannot already have an ID")).body(null);
        }
        SiteUser result = siteUserRepository.save(siteUser);
        return ResponseEntity.created(new URI("/api/site-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /site-users : Updates an existing siteUser.
     *
     * @param siteUser the siteUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siteUser,
     * or with status 400 (Bad Request) if the siteUser is not valid,
     * or with status 500 (Internal Server Error) if the siteUser couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/site-users")
    @Timed
    public ResponseEntity<SiteUser> updateSiteUser(@Valid @RequestBody SiteUser siteUser) throws URISyntaxException {
        log.debug("REST request to update SiteUser : {}", siteUser);
        if (siteUser.getId() == null) {
            return createSiteUser(siteUser);
        }
        SiteUser result = siteUserRepository.save(siteUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siteUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /site-users : get all the siteUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of siteUsers in body
     */
    @GetMapping("/site-users")
    @Timed
    public List<SiteUser> getAllSiteUsers() {
        log.debug("REST request to get all SiteUsers");
        List<SiteUser> siteUsers = siteUserRepository.findAll();
        return siteUsers;
    }

    /**
     * GET  /site-users/:id : get the "id" siteUser.
     *
     * @param id the id of the siteUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siteUser, or with status 404 (Not Found)
     */
    @GetMapping("/site-users/{id}")
    @Timed
    public ResponseEntity<SiteUser> getSiteUser(@PathVariable Long id) {
        log.debug("REST request to get SiteUser : {}", id);
        SiteUser siteUser = siteUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(siteUser));
    }

    /**
     * DELETE  /site-users/:id : delete the "id" siteUser.
     *
     * @param id the id of the siteUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/site-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteSiteUser(@PathVariable Long id) {
        log.debug("REST request to delete SiteUser : {}", id);
        siteUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
