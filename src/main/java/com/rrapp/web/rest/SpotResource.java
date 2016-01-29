package com.rrapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rrapp.domain.Spot;
import com.rrapp.repository.SpotRepository;
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
 * REST controller for managing Spot.
 */
@RestController
@RequestMapping("/api")
public class SpotResource {

    private final Logger log = LoggerFactory.getLogger(SpotResource.class);
        
    @Inject
    private SpotRepository spotRepository;
    
    /**
     * POST  /spots -> Create a new spot.
     */
    @RequestMapping(value = "/spots",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Spot> createSpot(@Valid @RequestBody Spot spot) throws URISyntaxException {
        log.debug("REST request to save Spot : {}", spot);
        if (spot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("spot", "idexists", "A new spot cannot already have an ID")).body(null);
        }
        Spot result = spotRepository.save(spot);
        return ResponseEntity.created(new URI("/api/spots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("spot", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spots -> Updates an existing spot.
     */
    @RequestMapping(value = "/spots",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Spot> updateSpot(@Valid @RequestBody Spot spot) throws URISyntaxException {
        log.debug("REST request to update Spot : {}", spot);
        if (spot.getId() == null) {
            return createSpot(spot);
        }
        Spot result = spotRepository.save(spot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("spot", spot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spots -> get all the spots.
     */
    @RequestMapping(value = "/spots",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Spot> getAllSpots() {
        log.debug("REST request to get all Spots");
        return spotRepository.findAll();
            }

    /**
     * GET  /spots/:id -> get the "id" spot.
     */
    @RequestMapping(value = "/spots/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Spot> getSpot(@PathVariable Long id) {
        log.debug("REST request to get Spot : {}", id);
        Spot spot = spotRepository.findOne(id);
        return Optional.ofNullable(spot)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /spots/:id -> delete the "id" spot.
     */
    @RequestMapping(value = "/spots/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSpot(@PathVariable Long id) {
        log.debug("REST request to delete Spot : {}", id);
        spotRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("spot", id.toString())).build();
    }
}
