package com.rrapp.web.rest;

import com.rrapp.Application;
import com.rrapp.domain.Spot;
import com.rrapp.repository.SpotRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SpotResource REST controller.
 *
 * @see SpotResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SpotResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_X_POSITION = 1;
    private static final Integer UPDATED_X_POSITION = 2;

    private static final Integer DEFAULT_Y_POSITION = 1;
    private static final Integer UPDATED_Y_POSITION = 2;

    @Inject
    private SpotRepository spotRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSpotMockMvc;

    private Spot spot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SpotResource spotResource = new SpotResource();
        ReflectionTestUtils.setField(spotResource, "spotRepository", spotRepository);
        this.restSpotMockMvc = MockMvcBuilders.standaloneSetup(spotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        spot = new Spot();
        spot.setName(DEFAULT_NAME);
        spot.setx_position(DEFAULT_X_POSITION);
        spot.sety_position(DEFAULT_Y_POSITION);
    }

    @Test
    @Transactional
    public void createSpot() throws Exception {
        int databaseSizeBeforeCreate = spotRepository.findAll().size();

        // Create the Spot

        restSpotMockMvc.perform(post("/api/spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spot)))
                .andExpect(status().isCreated());

        // Validate the Spot in the database
        List<Spot> spots = spotRepository.findAll();
        assertThat(spots).hasSize(databaseSizeBeforeCreate + 1);
        Spot testSpot = spots.get(spots.size() - 1);
        assertThat(testSpot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpot.getx_position()).isEqualTo(DEFAULT_X_POSITION);
        assertThat(testSpot.gety_position()).isEqualTo(DEFAULT_Y_POSITION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = spotRepository.findAll().size();
        // set the field null
        spot.setName(null);

        // Create the Spot, which fails.

        restSpotMockMvc.perform(post("/api/spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spot)))
                .andExpect(status().isBadRequest());

        List<Spot> spots = spotRepository.findAll();
        assertThat(spots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkx_positionIsRequired() throws Exception {
        int databaseSizeBeforeTest = spotRepository.findAll().size();
        // set the field null
        spot.setx_position(null);

        // Create the Spot, which fails.

        restSpotMockMvc.perform(post("/api/spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spot)))
                .andExpect(status().isBadRequest());

        List<Spot> spots = spotRepository.findAll();
        assertThat(spots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checky_positionIsRequired() throws Exception {
        int databaseSizeBeforeTest = spotRepository.findAll().size();
        // set the field null
        spot.sety_position(null);

        // Create the Spot, which fails.

        restSpotMockMvc.perform(post("/api/spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spot)))
                .andExpect(status().isBadRequest());

        List<Spot> spots = spotRepository.findAll();
        assertThat(spots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpots() throws Exception {
        // Initialize the database
        spotRepository.saveAndFlush(spot);

        // Get all the spots
        restSpotMockMvc.perform(get("/api/spots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(spot.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].x_position").value(hasItem(DEFAULT_X_POSITION)))
                .andExpect(jsonPath("$.[*].y_position").value(hasItem(DEFAULT_Y_POSITION)));
    }

    @Test
    @Transactional
    public void getSpot() throws Exception {
        // Initialize the database
        spotRepository.saveAndFlush(spot);

        // Get the spot
        restSpotMockMvc.perform(get("/api/spots/{id}", spot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(spot.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.x_position").value(DEFAULT_X_POSITION))
            .andExpect(jsonPath("$.y_position").value(DEFAULT_Y_POSITION));
    }

    @Test
    @Transactional
    public void getNonExistingSpot() throws Exception {
        // Get the spot
        restSpotMockMvc.perform(get("/api/spots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpot() throws Exception {
        // Initialize the database
        spotRepository.saveAndFlush(spot);

		int databaseSizeBeforeUpdate = spotRepository.findAll().size();

        // Update the spot
        spot.setName(UPDATED_NAME);
        spot.setx_position(UPDATED_X_POSITION);
        spot.sety_position(UPDATED_Y_POSITION);

        restSpotMockMvc.perform(put("/api/spots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(spot)))
                .andExpect(status().isOk());

        // Validate the Spot in the database
        List<Spot> spots = spotRepository.findAll();
        assertThat(spots).hasSize(databaseSizeBeforeUpdate);
        Spot testSpot = spots.get(spots.size() - 1);
        assertThat(testSpot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpot.getx_position()).isEqualTo(UPDATED_X_POSITION);
        assertThat(testSpot.gety_position()).isEqualTo(UPDATED_Y_POSITION);
    }

    @Test
    @Transactional
    public void deleteSpot() throws Exception {
        // Initialize the database
        spotRepository.saveAndFlush(spot);

		int databaseSizeBeforeDelete = spotRepository.findAll().size();

        // Get the spot
        restSpotMockMvc.perform(delete("/api/spots/{id}", spot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Spot> spots = spotRepository.findAll();
        assertThat(spots).hasSize(databaseSizeBeforeDelete - 1);
    }
}
