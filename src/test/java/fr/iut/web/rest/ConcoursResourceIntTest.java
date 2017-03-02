package fr.iut.web.rest;

import fr.iut.ConcoursphotoApp;

import fr.iut.domain.Concours;
import fr.iut.repository.ConcoursRepository;

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
 * Test class for the ConcoursResource REST controller.
 *
 * @see ConcoursResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcoursphotoApp.class)
public class ConcoursResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ConcoursRepository concoursRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restConcoursMockMvc;

    private Concours concours;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ConcoursResource concoursResource = new ConcoursResource(concoursRepository);
        this.restConcoursMockMvc = MockMvcBuilders.standaloneSetup(concoursResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concours createEntity(EntityManager em) {
        Concours concours = new Concours()
                .name(DEFAULT_NAME)
                .startDate(DEFAULT_START_DATE)
                .endDate(DEFAULT_END_DATE)
                .description(DEFAULT_DESCRIPTION)
                .location(DEFAULT_LOCATION);
        return concours;
    }

    @Before
    public void initTest() {
        concours = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcours() throws Exception {
        int databaseSizeBeforeCreate = concoursRepository.findAll().size();

        // Create the Concours

        restConcoursMockMvc.perform(post("/api/concours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isCreated());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeCreate + 1);
        Concours testConcours = concoursList.get(concoursList.size() - 1);
        assertThat(testConcours.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConcours.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testConcours.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testConcours.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConcours.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createConcoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = concoursRepository.findAll().size();

        // Create the Concours with an existing ID
        Concours existingConcours = new Concours();
        existingConcours.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcoursMockMvc.perform(post("/api/concours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingConcours)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = concoursRepository.findAll().size();
        // set the field null
        concours.setStartDate(null);

        // Create the Concours, which fails.

        restConcoursMockMvc.perform(post("/api/concours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isBadRequest());

        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = concoursRepository.findAll().size();
        // set the field null
        concours.setEndDate(null);

        // Create the Concours, which fails.

        restConcoursMockMvc.perform(post("/api/concours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isBadRequest());

        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        // Get all the concoursList
        restConcoursMockMvc.perform(get("/api/concours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concours.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);

        // Get the concours
        restConcoursMockMvc.perform(get("/api/concours/{id}", concours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(concours.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConcours() throws Exception {
        // Get the concours
        restConcoursMockMvc.perform(get("/api/concours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();

        // Update the concours
        Concours updatedConcours = concoursRepository.findOne(concours.getId());
        updatedConcours
                .name(UPDATED_NAME)
                .startDate(UPDATED_START_DATE)
                .endDate(UPDATED_END_DATE)
                .description(UPDATED_DESCRIPTION)
                .location(UPDATED_LOCATION);

        restConcoursMockMvc.perform(put("/api/concours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcours)))
            .andExpect(status().isOk());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate);
        Concours testConcours = concoursList.get(concoursList.size() - 1);
        assertThat(testConcours.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConcours.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testConcours.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testConcours.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConcours.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingConcours() throws Exception {
        int databaseSizeBeforeUpdate = concoursRepository.findAll().size();

        // Create the Concours

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConcoursMockMvc.perform(put("/api/concours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(concours)))
            .andExpect(status().isCreated());

        // Validate the Concours in the database
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConcours() throws Exception {
        // Initialize the database
        concoursRepository.saveAndFlush(concours);
        int databaseSizeBeforeDelete = concoursRepository.findAll().size();

        // Get the concours
        restConcoursMockMvc.perform(delete("/api/concours/{id}", concours.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Concours> concoursList = concoursRepository.findAll();
        assertThat(concoursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concours.class);
    }
}
