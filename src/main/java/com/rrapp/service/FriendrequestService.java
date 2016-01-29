package com.rrapp.service;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rrapp.domain.Friendrequest;
import com.rrapp.domain.User;
import com.rrapp.repository.FriendrequestRepository;
import com.rrapp.repository.UserRepository;

@Service
@Transactional
public class FriendrequestService{
	
    private final Logger log = LoggerFactory.getLogger(FriendrequestService.class);
    
    @Inject
    private FriendrequestRepository friendRequestRepository;
    
    @Inject
    private MailService mailService;
    
    public Friendrequest createFriendRequest(Friendrequest newFriendRequest) throws FriendRequestException {
    	if(newFriendRequest.getRequested().getLogin().equals(newFriendRequest.getRequester().getLogin())){
    		throw new FriendRequestException("Korisnik ne moze slati zahtev za prijateljstvo samom sebi.");
    	}
    	if(newFriendRequest.getRequested() == null || newFriendRequest.getRequester() == null || newFriendRequest.getSentDateAndTime() == null){
    		throw new FriendRequestException("Niste uneli sve potrebne podatke.");
    	}
    	
    	List<Friendrequest> alreadySentByUser = friendRequestRepository.findByRequesterIsCurrentUser();
    	for(Friendrequest friendrequest : alreadySentByUser){
    		User requested = friendrequest.getRequested();
    		if(requested.getLogin().equals(newFriendRequest.getRequested().getLogin())){
    			ZonedDateTime timeOfExistantRequest = friendrequest.getSentDateAndTime();
    			ZonedDateTime timeOfNewRequest = newFriendRequest.getSentDateAndTime();
    			System.out.println("/n/n/n/n/nNOVI REQUEST EPOHA = " + timeOfNewRequest.toEpochSecond());
    			System.out.println("/nSTARI REQUEST EPOHA = " + timeOfExistantRequest.toEpochSecond() + "/n/n/n/n/n");
    			long razlikaVremenaUSekundama = timeOfNewRequest.toEpochSecond()-timeOfExistantRequest.toEpochSecond();
    			if(razlikaVremenaUSekundama < 60*60*24){
    				throw new FriendRequestException("Vec ste poslali zahtev ovom korisniku. Morate sacekati 24 casa da bi ponovo mogli poslati zahtev istom korisniku");
    			}
    		}
    	}
    	
    	Friendrequest friendRequest = friendRequestRepository.save(newFriendRequest);
    	return friendRequest;
    }
}
