package com.rrapp.web.rest;

import com.rrapp.Application;
import com.rrapp.domain.Meal;
import com.rrapp.repository.MealRepository;

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
 * Test class for the MealResource REST controller.
 *
 * @see MealResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MealResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Inject
    private MealRepository mealRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMealMockMvc;

    private Meal meal;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MealResource mealResource = new MealResource();
        ReflectionTestUtils.setField(mealResource, "mealRepository", mealRepository);
        this.restMealMockMvc = MockMvcBuilders.standaloneSetup(mealResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        meal = new Meal();
        meal.setName(DEFAULT_NAME);
        meal.setDescription(DEFAULT_DESCRIPTION);
        meal.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createMeal() throws Exception {
        int databaseSizeBeforeCreate = mealRepository.findAll().size();

        // Create the Meal

        restMealMockMvc.perform(post("/api/meals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meal)))
                .andExpect(status().isCreated());

        // Validate the Meal in the database
        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(databaseSizeBeforeCreate + 1);
        Meal testMeal = meals.get(meals.size() - 1);
        assertThat(testMeal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMeal.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mealRepository.findAll().size();
        // set the field null
        meal.setName(null);

        // Create the Meal, which fails.

        restMealMockMvc.perform(post("/api/meals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meal)))
                .andExpect(status().isBadRequest());

        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mealRepository.findAll().size();
        // set the field null
        meal.setDescription(null);

        // Create the Meal, which fails.

        restMealMockMvc.perform(post("/api/meals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meal)))
                .andExpect(status().isBadRequest());

        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = mealRepository.findAll().size();
        // set the field null
        meal.setPrice(null);

        // Create the Meal, which fails.

        restMealMockMvc.perform(post("/api/meals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meal)))
                .andExpect(status().isBadRequest());

        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeals() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        // Get all the meals
        restMealMockMvc.perform(get("/api/meals?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(meal.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

        // Get the meal
        restMealMockMvc.perform(get("/api/meals/{id}", meal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(meal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeal() throws Exception {
        // Get the meal
        restMealMockMvc.perform(get("/api/meals/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

		int databaseSizeBeforeUpdate = mealRepository.findAll().size();

        // Update the meal
        meal.setName(UPDATED_NAME);
        meal.setDescription(UPDATED_DESCRIPTION);
        meal.setPrice(UPDATED_PRICE);

        restMealMockMvc.perform(put("/api/meals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(meal)))
                .andExpect(status().isOk());

        // Validate the Meal in the database
        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(databaseSizeBeforeUpdate);
        Meal testMeal = meals.get(meals.size() - 1);
        assertThat(testMeal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMeal.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deleteMeal() throws Exception {
        // Initialize the database
        mealRepository.saveAndFlush(meal);

		int databaseSizeBeforeDelete = mealRepository.findAll().size();

        // Get the meal
        restMealMockMvc.perform(delete("/api/meals/{id}", meal.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Meal> meals = mealRepository.findAll();
        assertThat(meals).hasSize(databaseSizeBeforeDelete - 1);
    }
}
