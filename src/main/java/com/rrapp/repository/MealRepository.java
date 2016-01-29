package com.rrapp.repository;

import com.rrapp.domain.Meal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Meal entity.
 */
public interface MealRepository extends JpaRepository<Meal,Long> {

}
