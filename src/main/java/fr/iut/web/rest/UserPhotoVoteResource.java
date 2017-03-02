package fr.iut.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.domain.UserPhotoVote;

import fr.iut.repository.UserPhotoVoteRepository;
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
 * REST controller for managing UserPhotoVote.
 */
@RestController
@RequestMapping("/api")
public class UserPhotoVoteResource {

    private final Logger log = LoggerFactory.getLogger(UserPhotoVoteResource.class);

    private static final String ENTITY_NAME = "userPhotoVote";
        
    private final UserPhotoVoteRepository userPhotoVoteRepository;

    public UserPhotoVoteResource(UserPhotoVoteRepository userPhotoVoteRepository) {
        this.userPhotoVoteRepository = userPhotoVoteRepository;
    }

    /**
     * POST  /user-photo-votes : Create a new userPhotoVote.
     *
     * @param userPhotoVote the userPhotoVote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPhotoVote, or with status 400 (Bad Request) if the userPhotoVote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-photo-votes")
    @Timed
    public ResponseEntity<UserPhotoVote> createUserPhotoVote(@Valid @RequestBody UserPhotoVote userPhotoVote) throws URISyntaxException {
        log.debug("REST request to save UserPhotoVote : {}", userPhotoVote);
        if (userPhotoVote.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userPhotoVote cannot already have an ID")).body(null);
        }
        UserPhotoVote result = userPhotoVoteRepository.save(userPhotoVote);
        return ResponseEntity.created(new URI("/api/user-photo-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-photo-votes : Updates an existing userPhotoVote.
     *
     * @param userPhotoVote the userPhotoVote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPhotoVote,
     * or with status 400 (Bad Request) if the userPhotoVote is not valid,
     * or with status 500 (Internal Server Error) if the userPhotoVote couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-photo-votes")
    @Timed
    public ResponseEntity<UserPhotoVote> updateUserPhotoVote(@Valid @RequestBody UserPhotoVote userPhotoVote) throws URISyntaxException {
        log.debug("REST request to update UserPhotoVote : {}", userPhotoVote);
        if (userPhotoVote.getId() == null) {
            return createUserPhotoVote(userPhotoVote);
        }
        UserPhotoVote result = userPhotoVoteRepository.save(userPhotoVote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPhotoVote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-photo-votes : get all the userPhotoVotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userPhotoVotes in body
     */
    @GetMapping("/user-photo-votes")
    @Timed
    public List<UserPhotoVote> getAllUserPhotoVotes() {
        log.debug("REST request to get all UserPhotoVotes");
        List<UserPhotoVote> userPhotoVotes = userPhotoVoteRepository.findAll();
        return userPhotoVotes;
    }

    /**
     * GET  /user-photo-votes/:id : get the "id" userPhotoVote.
     *
     * @param id the id of the userPhotoVote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPhotoVote, or with status 404 (Not Found)
     */
    @GetMapping("/user-photo-votes/{id}")
    @Timed
    public ResponseEntity<UserPhotoVote> getUserPhotoVote(@PathVariable Long id) {
        log.debug("REST request to get UserPhotoVote : {}", id);
        UserPhotoVote userPhotoVote = userPhotoVoteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPhotoVote));
    }

    /**
     * DELETE  /user-photo-votes/:id : delete the "id" userPhotoVote.
     *
     * @param id the id of the userPhotoVote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-photo-votes/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPhotoVote(@PathVariable Long id) {
        log.debug("REST request to delete UserPhotoVote : {}", id);
        userPhotoVoteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
