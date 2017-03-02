package fr.iut.web.rest;

import fr.iut.ConcoursphotoApp;

import fr.iut.domain.UserPhotoComment;
import fr.iut.repository.UserPhotoCommentRepository;

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
 * Test class for the UserPhotoCommentResource REST controller.
 *
 * @see UserPhotoCommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcoursphotoApp.class)
public class UserPhotoCommentResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private UserPhotoCommentRepository userPhotoCommentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restUserPhotoCommentMockMvc;

    private UserPhotoComment userPhotoComment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            UserPhotoCommentResource userPhotoCommentResource = new UserPhotoCommentResource(userPhotoCommentRepository);
        this.restUserPhotoCommentMockMvc = MockMvcBuilders.standaloneSetup(userPhotoCommentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserPhotoComment createEntity(EntityManager em) {
        UserPhotoComment userPhotoComment = new UserPhotoComment()
                .comment(DEFAULT_COMMENT)
                .date(DEFAULT_DATE);
        return userPhotoComment;
    }

    @Before
    public void initTest() {
        userPhotoComment = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPhotoComment() throws Exception {
        int databaseSizeBeforeCreate = userPhotoCommentRepository.findAll().size();

        // Create the UserPhotoComment

        restUserPhotoCommentMockMvc.perform(post("/api/user-photo-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoComment)))
            .andExpect(status().isCreated());

        // Validate the UserPhotoComment in the database
        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeCreate + 1);
        UserPhotoComment testUserPhotoComment = userPhotoCommentList.get(userPhotoCommentList.size() - 1);
        assertThat(testUserPhotoComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserPhotoComment.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createUserPhotoCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPhotoCommentRepository.findAll().size();

        // Create the UserPhotoComment with an existing ID
        UserPhotoComment existingUserPhotoComment = new UserPhotoComment();
        existingUserPhotoComment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPhotoCommentMockMvc.perform(post("/api/user-photo-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserPhotoComment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPhotoCommentRepository.findAll().size();
        // set the field null
        userPhotoComment.setComment(null);

        // Create the UserPhotoComment, which fails.

        restUserPhotoCommentMockMvc.perform(post("/api/user-photo-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoComment)))
            .andExpect(status().isBadRequest());

        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPhotoCommentRepository.findAll().size();
        // set the field null
        userPhotoComment.setDate(null);

        // Create the UserPhotoComment, which fails.

        restUserPhotoCommentMockMvc.perform(post("/api/user-photo-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoComment)))
            .andExpect(status().isBadRequest());

        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserPhotoComments() throws Exception {
        // Initialize the database
        userPhotoCommentRepository.saveAndFlush(userPhotoComment);

        // Get all the userPhotoCommentList
        restUserPhotoCommentMockMvc.perform(get("/api/user-photo-comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPhotoComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getUserPhotoComment() throws Exception {
        // Initialize the database
        userPhotoCommentRepository.saveAndFlush(userPhotoComment);

        // Get the userPhotoComment
        restUserPhotoCommentMockMvc.perform(get("/api/user-photo-comments/{id}", userPhotoComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPhotoComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPhotoComment() throws Exception {
        // Get the userPhotoComment
        restUserPhotoCommentMockMvc.perform(get("/api/user-photo-comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPhotoComment() throws Exception {
        // Initialize the database
        userPhotoCommentRepository.saveAndFlush(userPhotoComment);
        int databaseSizeBeforeUpdate = userPhotoCommentRepository.findAll().size();

        // Update the userPhotoComment
        UserPhotoComment updatedUserPhotoComment = userPhotoCommentRepository.findOne(userPhotoComment.getId());
        updatedUserPhotoComment
                .comment(UPDATED_COMMENT)
                .date(UPDATED_DATE);

        restUserPhotoCommentMockMvc.perform(put("/api/user-photo-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserPhotoComment)))
            .andExpect(status().isOk());

        // Validate the UserPhotoComment in the database
        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeUpdate);
        UserPhotoComment testUserPhotoComment = userPhotoCommentList.get(userPhotoCommentList.size() - 1);
        assertThat(testUserPhotoComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserPhotoComment.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPhotoComment() throws Exception {
        int databaseSizeBeforeUpdate = userPhotoCommentRepository.findAll().size();

        // Create the UserPhotoComment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPhotoCommentMockMvc.perform(put("/api/user-photo-comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPhotoComment)))
            .andExpect(status().isCreated());

        // Validate the UserPhotoComment in the database
        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPhotoComment() throws Exception {
        // Initialize the database
        userPhotoCommentRepository.saveAndFlush(userPhotoComment);
        int databaseSizeBeforeDelete = userPhotoCommentRepository.findAll().size();

        // Get the userPhotoComment
        restUserPhotoCommentMockMvc.perform(delete("/api/user-photo-comments/{id}", userPhotoComment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPhotoComment> userPhotoCommentList = userPhotoCommentRepository.findAll();
        assertThat(userPhotoCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPhotoComment.class);
    }
}
