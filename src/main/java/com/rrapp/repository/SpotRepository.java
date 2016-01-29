package com.rrapp.repository;

import com.rrapp.domain.Spot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Spot entity.
 */
public interface SpotRepository extends JpaRepository<Spot,Long> {

}
