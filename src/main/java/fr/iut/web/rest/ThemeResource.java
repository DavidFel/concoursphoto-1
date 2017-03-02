package fr.iut.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.domain.Theme;

import fr.iut.repository.ThemeRepository;
import fr.iut.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Theme.
 */
@RestController
@RequestMapping("/api")
public class ThemeResource {

    private final Logger log = LoggerFactory.getLogger(ThemeResource.class);

    private static final String ENTITY_NAME = "theme";
        
    private final ThemeRepository themeRepository;

    public ThemeResource(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    /**
     * POST  /themes : Create a new theme.
     *
     * @param theme the theme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new theme, or with status 400 (Bad Request) if the theme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/themes")
    @Timed
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to save Theme : {}", theme);
        if (theme.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new theme cannot already have an ID")).body(null);
        }
        Theme result = themeRepository.save(theme);
        return ResponseEntity.created(new URI("/api/themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /themes : Updates an existing theme.
     *
     * @param theme the theme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated theme,
     * or with status 400 (Bad Request) if the theme is not valid,
     * or with status 500 (Internal Server Error) if the theme couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/themes")
    @Timed
    public ResponseEntity<Theme> updateTheme(@RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to update Theme : {}", theme);
        if (theme.getId() == null) {
            return createTheme(theme);
        }
        Theme result = themeRepository.save(theme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, theme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /themes : get all the themes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of themes in body
     */
    @GetMapping("/themes")
    @Timed
    public List<Theme> getAllThemes() {
        log.debug("REST request to get all Themes");
        List<Theme> themes = themeRepository.findAllWithEagerRelationships();
        return themes;
    }

    /**
     * GET  /themes/:id : get the "id" theme.
     *
     * @param id the id of the theme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the theme, or with status 404 (Not Found)
     */
    @GetMapping("/themes/{id}")
    @Timed
    public ResponseEntity<Theme> getTheme(@PathVariable Long id) {
        log.debug("REST request to get Theme : {}", id);
        Theme theme = themeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(theme));
    }

    /**
     * DELETE  /themes/:id : delete the "id" theme.
     *
     * @param id the id of the theme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/themes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        log.debug("REST request to delete Theme : {}", id);
        themeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
