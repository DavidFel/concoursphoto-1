package fr.iut.web.rest;

import fr.iut.ConcoursphotoApp;

import fr.iut.domain.UserPhotoVote;
import fr.iut.repository.UserPhotoVoteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserPhotoVoteResource REST controller.
 *
 * @see UserPhotoVoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcoursphotoApp.class)
public class UserPhotoVoteResourceIntTest {

    private static final Integer DEFAULT_STARS = 1;
    private static final Integer UPDATED_STARS = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserPhotoVoteRepository userPhotoVoteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restUserPhotoVoteMockMvc;

    private UserPhotoVote userPhotoVote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            UserPhotoVoteResource userPhotoVoteResource = new UserPhotoVoteResource(userPhotoVoteRepository);
        this.restUserPhotoVoteMockMvc = MockMvcBuilders.standaloneSetup(userPhotoVoteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPhotoVote createEntity(EntityManager em) {
        UserPhotoVote userPhotoVote = new UserPhotoVote()
                .stars(DEFAULT_STARS)
                .date(DEFAULT_DATE);
        return userPhotoVote;
    }

    @Before
    public void initTest() {
        userPhotoVote = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPhotoVote() throws Exception {
        int databaseSizeBeforeCreate = userPhotoVoteRepository.findAll().size();

        // Create the UserPhotoVote

        restUserPhotoVoteMockMvc.perform(post("/api/user-photo-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoVote)))
            .andExpect(status().isCreated());

        // Validate the UserPhotoVote in the database
        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeCreate + 1);
        UserPhotoVote testUserPhotoVote = userPhotoVoteList.get(userPhotoVoteList.size() - 1);
        assertThat(testUserPhotoVote.getStars()).isEqualTo(DEFAULT_STARS);
        assertThat(testUserPhotoVote.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createUserPhotoVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPhotoVoteRepository.findAll().size();

        // Create the UserPhotoVote with an existing ID
        UserPhotoVote existingUserPhotoVote = new UserPhotoVote();
        existingUserPhotoVote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPhotoVoteMockMvc.perform(post("/api/user-photo-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserPhotoVote)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStarsIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPhotoVoteRepository.findAll().size();
        // set the field null
        userPhotoVote.setStars(null);

        // Create the UserPhotoVote, which fails.

        restUserPhotoVoteMockMvc.perform(post("/api/user-photo-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoVote)))
            .andExpect(status().isBadRequest());

        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPhotoVoteRepository.findAll().size();
        // set the field null
        userPhotoVote.setDate(null);

        // Create the UserPhotoVote, which fails.

        restUserPhotoVoteMockMvc.perform(post("/api/user-photo-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoVote)))
            .andExpect(status().isBadRequest());

        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserPhotoVotes() throws Exception {
        // Initialize the database
        userPhotoVoteRepository.saveAndFlush(userPhotoVote);

        // Get all the userPhotoVoteList
        restUserPhotoVoteMockMvc.perform(get("/api/user-photo-votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPhotoVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].stars").value(hasItem(DEFAULT_STARS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getUserPhotoVote() throws Exception {
        // Initialize the database
        userPhotoVoteRepository.saveAndFlush(userPhotoVote);

        // Get the userPhotoVote
        restUserPhotoVoteMockMvc.perform(get("/api/user-photo-votes/{id}", userPhotoVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPhotoVote.getId().intValue()))
            .andExpect(jsonPath("$.stars").value(DEFAULT_STARS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPhotoVote() throws Exception {
        // Get the userPhotoVote
        restUserPhotoVoteMockMvc.perform(get("/api/user-photo-votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPhotoVote() throws Exception {
        // Initialize the database
        userPhotoVoteRepository.saveAndFlush(userPhotoVote);
        int databaseSizeBeforeUpdate = userPhotoVoteRepository.findAll().size();

        // Update the userPhotoVote
        UserPhotoVote updatedUserPhotoVote = userPhotoVoteRepository.findOne(userPhotoVote.getId());
        updatedUserPhotoVote
                .stars(UPDATED_STARS)
                .date(UPDATED_DATE);

        restUserPhotoVoteMockMvc.perform(put("/api/user-photo-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPhotoVote)))
            .andExpect(status().isOk());

        // Validate the UserPhotoVote in the database
        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeUpdate);
        UserPhotoVote testUserPhotoVote = userPhotoVoteList.get(userPhotoVoteList.size() - 1);
        assertThat(testUserPhotoVote.getStars()).isEqualTo(UPDATED_STARS);
        assertThat(testUserPhotoVote.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPhotoVote() throws Exception {
        int databaseSizeBeforeUpdate = userPhotoVoteRepository.findAll().size();

        // Create the UserPhotoVote

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPhotoVoteMockMvc.perform(put("/api/user-photo-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoVote)))
            .andExpect(status().isCreated());

        // Validate the UserPhotoVote in the database
        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPhotoVote() throws Exception {
        // Initialize the database
        userPhotoVoteRepository.saveAndFlush(userPhotoVote);
        int databaseSizeBeforeDelete = userPhotoVoteRepository.findAll().size();

        // Get the userPhotoVote
        restUserPhotoVoteMockMvc.perform(delete("/api/user-photo-votes/{id}", userPhotoVote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPhotoVote> userPhotoVoteList = userPhotoVoteRepository.findAll();
        assertThat(userPhotoVoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPhotoVote.class);
    }
}
