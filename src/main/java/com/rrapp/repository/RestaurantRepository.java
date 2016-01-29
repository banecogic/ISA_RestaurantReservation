package com.rrapp.repository;

import com.rrapp.domain.Restaurant;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Restaurant entity.
 */
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
