package com.rrapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rrapp.domain.Restaurant;
import com.rrapp.repository.RestaurantRepository;
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
 * REST controller for managing Restaurant.
 */
@RestController
@RequestMapping("/api")
public class RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantResource.class);
        
    @Inject
    private RestaurantRepository restaurantRepository;
    
    /**
     * POST  /restaurants -> Create a new restaurant.
     */
    @RequestMapping(value = "/restaurants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to save Restaurant : {}", restaurant);
        if (restaurant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("restaurant", "idexists", "A new restaurant cannot already have an ID")).body(null);
        }
        Restaurant result = restaurantRepository.save(restaurant);
        return ResponseEntity.created(new URI("/api/restaurants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("restaurant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restaurants -> Updates an existing restaurant.
     */
    @RequestMapping(value = "/restaurants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to update Restaurant : {}", restaurant);
        if (restaurant.getId() == null) {
            return createRestaurant(restaurant);
        }
        Restaurant result = restaurantRepository.save(restaurant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("restaurant", restaurant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restaurants -> get all the restaurants.
     */
    @RequestMapping(value = "/restaurants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Restaurant> getAllRestaurants() {
        log.debug("REST request to get all Restaurants");
        return restaurantRepository.findAll();
            }

    /**
     * GET  /restaurants/:id -> get the "id" restaurant.
     */
    @RequestMapping(value = "/restaurants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        log.debug("REST request to get Restaurant : {}", id);
        Restaurant restaurant = restaurantRepository.findOne(id);
        return Optional.ofNullable(restaurant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /restaurants/:id -> delete the "id" restaurant.
     */
    @RequestMapping(value = "/restaurants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant : {}", id);
        restaurantRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("restaurant", id.toString())).build();
    }
}
