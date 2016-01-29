package com.rrapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "map_x_coordinate", nullable = false)
    private Double map_x_coordinate;

    @NotNull
    @Column(name = "map_y_coordinate", nullable = false)
    private Double map_y_coordinate;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Spot> spotss = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Meal> mealss = new HashSet<>();

    @ManyToMany(mappedBy = "restaurants_visitss")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Client> clients_visitss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMap_x_coordinate() {
        return map_x_coordinate;
    }

    public void setMap_x_coordinate(Double map_x_coordinate) {
        this.map_x_coordinate = map_x_coordinate;
    }

    public Double getMap_y_coordinate() {
        return map_y_coordinate;
    }

    public void setMap_y_coordinate(Double map_y_coordinate) {
        this.map_y_coordinate = map_y_coordinate;
    }

    public Set<Spot> getSpotss() {
        return spotss;
    }

    public void setSpotss(Set<Spot> spots) {
        this.spotss = spots;
    }

    public Set<Meal> getMealss() {
        return mealss;
    }

    public void setMealss(Set<Meal> meals) {
        this.mealss = meals;
    }

    public Set<Client> getClients_visitss() {
        return clients_visitss;
    }

    public void setClients_visitss(Set<Client> clients) {
        this.clients_visitss = clients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurant restaurant = (Restaurant) o;
        return Objects.equals(id, restaurant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", map_x_coordinate='" + map_x_coordinate + "'" +
            ", map_y_coordinate='" + map_y_coordinate + "'" +
            '}';
    }
}
