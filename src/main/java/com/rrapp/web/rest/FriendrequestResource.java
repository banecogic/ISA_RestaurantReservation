package com.rrapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rrapp.domain.Friendrequest;
import com.rrapp.repository.FriendrequestRepository;
import com.rrapp.service.FriendRequestException;
import com.rrapp.service.FriendrequestService;
import com.rrapp.service.UserService;
import com.rrapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Friendrequest.
 */
@RestController
@RequestMapping("/api")
public class FriendrequestResource {

    private final Logger log = LoggerFactory.getLogger(FriendrequestResource.class);
        
    @Inject
    private FriendrequestRepository friendrequestRepository;
    
    @Inject
    private FriendrequestService friendrequestService;
    
    /**
     * POST  /friendrequests -> Create a new friendrequest.
     */
    @RequestMapping(value = "/friendrequests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Friendrequest> createFriendrequest(@Valid @RequestBody Friendrequest friendrequest) throws URISyntaxException {
        log.debug("REST request to save Friendrequest : {}", friendrequest);
        if (friendrequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("friendrequest", "idexists", "A new friendrequest cannot already have an ID")).body(null);
        }

    	Friendrequest result = null;
        HttpHeaders header = new HttpHeaders();
		try {
			result = friendrequestService.createFriendRequest(friendrequest);
		} catch (FriendRequestException fre) {
			ArrayList<String> messages = new ArrayList<String>();
			messages.add(fre.getMessage());
	        header.put("Message", messages);
		}finally{
			
		}
		return Optional.ofNullable(result)
                .map(response -> new ResponseEntity<>(response,HttpStatus.OK))
                .orElse(new ResponseEntity<>(header, HttpStatus.BAD_REQUEST));
        /*return ResponseEntity.created(new URI("/api/friendrequests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("friendrequest", result.getId().toString()))
            .body(result);*/
    }

    /**
     * PUT  /friendrequests -> Updates an existing friendrequest.
     */
    @RequestMapping(value = "/friendrequests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Friendrequest> updateFriendrequest(@Valid @RequestBody Friendrequest friendrequest) throws URISyntaxException {
        log.debug("REST request to update Friendrequest : {}", friendrequest);
        if (friendrequest.getId() == null) {
            return createFriendrequest(friendrequest);
        }
        Friendrequest result = friendrequestRepository.save(friendrequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("friendrequest", friendrequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /friendrequests -> get all the friendrequests.
     */
    @RequestMapping(value = "/friendrequests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Friendrequest> getAllFriendrequests() {
        log.debug("REST request to get all Friendrequests");
        for(Friendrequest fr : friendrequestRepository.findAll()){
        	System.out.println(fr.getRequester().getLogin());
        }
        return friendrequestRepository.findAll();
            }

    /**
     * GET  /friendrequests/:id -> get the "id" friendrequest.
     */
    @RequestMapping(value = "/friendrequests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Friendrequest> getFriendrequest(@PathVariable Long id) {
        log.debug("REST request to get Friendrequest : {}", id);
        Friendrequest friendrequest = friendrequestRepository.findOne(id);
        System.out.println(friendrequest);
        return Optional.ofNullable(friendrequest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /friendrequests/:id -> delete the "id" friendrequest.
     */
    @RequestMapping(value = "/friendrequests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFriendrequest(@PathVariable Long id) {
        log.debug("REST request to delete Friendrequest : {}", id);
        friendrequestRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("friendrequest", id.toString())).build();
    }
}
