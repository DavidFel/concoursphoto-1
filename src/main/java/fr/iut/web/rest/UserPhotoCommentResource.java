package fr.iut.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.domain.UserPhotoComment;

import fr.iut.repository.UserPhotoCommentRepository;
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
 * REST controller for managing UserPhotoComment.
 */
@RestController
@RequestMapping("/api")
public class UserPhotoCommentResource {

    private final Logger log = LoggerFactory.getLogger(UserPhotoCommentResource.class);

    private static final String ENTITY_NAME = "userPhotoComment";
        
    private final UserPhotoCommentRepository userPhotoCommentRepository;

    public UserPhotoCommentResource(UserPhotoCommentRepository userPhotoCommentRepository) {
        this.userPhotoCommentRepository = userPhotoCommentRepository;
    }

    /**
     * POST  /user-photo-comments : Create a new userPhotoComment.
     *
     * @param userPhotoComment the userPhotoComment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPhotoComment, or with status 400 (Bad Request) if the userPhotoComment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-photo-comments")
    @Timed
    public ResponseEntity<UserPhotoComment> createUserPhotoComment(@Valid @RequestBody UserPhotoComment userPhotoComment) throws URISyntaxException {
        log.debug("REST request to save UserPhotoComment : {}", userPhotoComment);
        if (userPhotoComment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userPhotoComment cannot already have an ID")).body(null);
        }
        UserPhotoComment result = userPhotoCommentRepository.save(userPhotoComment);
        return ResponseEntity.created(new URI("/api/user-photo-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-photo-comments : Updates an existing userPhotoComment.
     *
     * @param userPhotoComment the userPhotoComment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPhotoComment,
     * or with status 400 (Bad Request) if the userPhotoComment is not valid,
     * or with status 500 (Internal Server Error) if the userPhotoComment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-photo-comments")
    @Timed
    public ResponseEntity<UserPhotoComment> updateUserPhotoComment(@Valid @RequestBody UserPhotoComment userPhotoComment) throws URISyntaxException {
        log.debug("REST request to update UserPhotoComment : {}", userPhotoComment);
        if (userPhotoComment.getId() == null) {
            return createUserPhotoComment(userPhotoComment);
        }
        UserPhotoComment result = userPhotoCommentRepository.save(userPhotoComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPhotoComment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-photo-comments : get all the userPhotoComments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userPhotoComments in body
     */
    @GetMapping("/user-photo-comments")
    @Timed
    public List<UserPhotoComment> getAllUserPhotoComments() {
        log.debug("REST request to get all UserPhotoComments");
        List<UserPhotoComment> userPhotoComments = userPhotoCommentRepository.findAll();
        return userPhotoComments;
    }

    /**
     * GET  /user-photo-comments/:id : get the "id" userPhotoComment.
     *
     * @param id the id of the userPhotoComment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPhotoComment, or with status 404 (Not Found)
     */
    @GetMapping("/user-photo-comments/{id}")
    @Timed
    public ResponseEntity<UserPhotoComment> getUserPhotoComment(@PathVariable Long id) {
        log.debug("REST request to get UserPhotoComment : {}", id);
        UserPhotoComment userPhotoComment = userPhotoCommentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPhotoComment));
    }

    /**
     * DELETE  /user-photo-comments/:id : delete the "id" userPhotoComment.
     *
     * @param id the id of the userPhotoComment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-photo-comments/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPhotoComment(@PathVariable Long id) {
        log.debug("REST request to delete UserPhotoComment : {}", id);
        userPhotoCommentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
