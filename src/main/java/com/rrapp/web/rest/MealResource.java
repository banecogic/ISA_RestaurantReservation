package com.rrapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rrapp.domain.Meal;
import com.rrapp.repository.MealRepository;
import com.rrapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Meal.
 */
@RestController
@RequestMapping("/api")
public class MealResource {

    private final Logger log = LoggerFactory.getLogger(MealResource.class);
        
    @Inject
    private MealRepository mealRepository;
    
    /**
     * POST  /meals -> Create a new meal.
     */
    @RequestMapping(value = "/meals",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Meal> createMeal(@Valid @RequestBody Meal meal) throws URISyntaxException {
        log.debug("REST request to save Meal : {}", meal);
        if (meal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("meal", "idexists", "A new meal cannot already have an ID")).body(null);
        }
        Meal result = mealRepository.save(meal);
        return ResponseEntity.created(new URI("/api/meals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("meal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meals -> Updates an existing meal.
     */
    @RequestMapping(value = "/meals",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Meal> updateMeal(@Valid @RequestBody Meal meal) throws URISyntaxException {
        log.debug("REST request to update Meal : {}", meal);
        if (meal.getId() == null) {
            return createMeal(meal);
        }
        Meal result = mealRepository.save(meal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("meal", meal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meals -> get all the meals.
     */
    @RequestMapping(value = "/meals",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Meal> getAllMeals() {
        log.debug("REST request to get all Meals");
        return mealRepository.findAll();
            }

    /**
     * GET  /meals/:id -> get the "id" meal.
     */
    @RequestMapping(value = "/meals/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Meal> getMeal(@PathVariable Long id) {
        log.debug("REST request to get Meal : {}", id);
        Meal meal = mealRepository.findOne(id);
        return Optional.ofNullable(meal)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /meals/:id -> delete the "id" meal.
     */
    @RequestMapping(value = "/meals/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        log.debug("REST request to delete Meal : {}", id);
        mealRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("meal", id.toString())).build();
    }
}
