package fr.iut.web.rest;

import fr.iut.ConcoursphotoApp;

import fr.iut.domain.SiteUser;
import fr.iut.repository.SiteUserRepository;

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

import fr.iut.domain.enumeration.UserType;
/**
 * Test class for the SiteUserResource REST controller.
 *
 * @see SiteUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcoursphotoApp.class)
public class SiteUserResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHARTE = false;
    private static final Boolean UPDATED_CHARTE = true;

    private static final UserType DEFAULT_TYPE = UserType.VOTANT;
    private static final UserType UPDATED_TYPE = UserType.PARTICIPANT;

    @Autowired
    private SiteUserRepository siteUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restSiteUserMockMvc;

    private SiteUser siteUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            SiteUserResource siteUserResource = new SiteUserResource(siteUserRepository);
        this.restSiteUserMockMvc = MockMvcBuilders.standaloneSetup(siteUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteUser createEntity(EntityManager em) {
        SiteUser siteUser = new SiteUser()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .email(DEFAULT_EMAIL)
                .address(DEFAULT_ADDRESS)
                .jobTitle(DEFAULT_JOB_TITLE)
                .charte(DEFAULT_CHARTE)
                .type(DEFAULT_TYPE);
        return siteUser;
    }

    @Before
    public void initTest() {
        siteUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createSiteUser() throws Exception {
        int databaseSizeBeforeCreate = siteUserRepository.findAll().size();

        // Create the SiteUser

        restSiteUserMockMvc.perform(post("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteUser)))
            .andExpect(status().isCreated());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeCreate + 1);
        SiteUser testSiteUser = siteUserList.get(siteUserList.size() - 1);
        assertThat(testSiteUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSiteUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testSiteUser.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testSiteUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSiteUser.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSiteUser.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testSiteUser.isCharte()).isEqualTo(DEFAULT_CHARTE);
        assertThat(testSiteUser.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createSiteUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = siteUserRepository.findAll().size();

        // Create the SiteUser with an existing ID
        SiteUser existingSiteUser = new SiteUser();
        existingSiteUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteUserMockMvc.perform(post("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSiteUser)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUserRepository.findAll().size();
        // set the field null
        siteUser.setFirstName(null);

        // Create the SiteUser, which fails.

        restSiteUserMockMvc.perform(post("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteUser)))
            .andExpect(status().isBadRequest());

        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUserRepository.findAll().size();
        // set the field null
        siteUser.setLastName(null);

        // Create the SiteUser, which fails.

        restSiteUserMockMvc.perform(post("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteUser)))
            .andExpect(status().isBadRequest());

        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = siteUserRepository.findAll().size();
        // set the field null
        siteUser.setEmail(null);

        // Create the SiteUser, which fails.

        restSiteUserMockMvc.perform(post("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteUser)))
            .andExpect(status().isBadRequest());

        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSiteUsers() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get all the siteUserList
        restSiteUserMockMvc.perform(get("/api/site-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].charte").value(hasItem(DEFAULT_CHARTE.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getSiteUser() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);

        // Get the siteUser
        restSiteUserMockMvc.perform(get("/api/site-users/{id}", siteUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(siteUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.charte").value(DEFAULT_CHARTE.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSiteUser() throws Exception {
        // Get the siteUser
        restSiteUserMockMvc.perform(get("/api/site-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSiteUser() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);
        int databaseSizeBeforeUpdate = siteUserRepository.findAll().size();

        // Update the siteUser
        SiteUser updatedSiteUser = siteUserRepository.findOne(siteUser.getId());
        updatedSiteUser
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .birthDate(UPDATED_BIRTH_DATE)
                .email(UPDATED_EMAIL)
                .address(UPDATED_ADDRESS)
                .jobTitle(UPDATED_JOB_TITLE)
                .charte(UPDATED_CHARTE)
                .type(UPDATED_TYPE);

        restSiteUserMockMvc.perform(put("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSiteUser)))
            .andExpect(status().isOk());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeUpdate);
        SiteUser testSiteUser = siteUserList.get(siteUserList.size() - 1);
        assertThat(testSiteUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSiteUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testSiteUser.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testSiteUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSiteUser.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSiteUser.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testSiteUser.isCharte()).isEqualTo(UPDATED_CHARTE);
        assertThat(testSiteUser.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSiteUser() throws Exception {
        int databaseSizeBeforeUpdate = siteUserRepository.findAll().size();

        // Create the SiteUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSiteUserMockMvc.perform(put("/api/site-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(siteUser)))
            .andExpect(status().isCreated());

        // Validate the SiteUser in the database
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSiteUser() throws Exception {
        // Initialize the database
        siteUserRepository.saveAndFlush(siteUser);
        int databaseSizeBeforeDelete = siteUserRepository.findAll().size();

        // Get the siteUser
        restSiteUserMockMvc.perform(delete("/api/site-users/{id}", siteUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SiteUser> siteUserList = siteUserRepository.findAll();
        assertThat(siteUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteUser.class);
    }
}
