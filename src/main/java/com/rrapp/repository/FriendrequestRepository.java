package com.rrapp.repository;

import com.rrapp.domain.Friendrequest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Friendrequest entity.
 */
public interface FriendrequestRepository extends JpaRepository<Friendrequest,Long> {

    @Query("select friendrequest from Friendrequest friendrequest where friendrequest.requester.login = ?#{principal.username}")
    List<Friendrequest> findByRequesterIsCurrentUser();

    @Query("select friendrequest from Friendrequest friendrequest where friendrequest.requested.login = ?#{principal.username}")
    List<Friendrequest> findByRequestedIsCurrentUser();

}
