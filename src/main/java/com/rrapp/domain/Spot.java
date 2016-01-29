package com.rrapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Spot.
 */
@Entity
@Table(name = "spot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Spot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "x_position", nullable = false)
    private Integer x_position;

    @NotNull
    @Column(name = "y_position", nullable = false)
    private Integer y_position;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

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

    public Integer getx_position() {
        return x_position;
    }

    public void setx_position(Integer x_position) {
        this.x_position = x_position;
    }

    public Integer gety_position() {
        return y_position;
    }

    public void sety_position(Integer y_position) {
        this.y_position = y_position;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Spot spot = (Spot) o;
        return Objects.equals(id, spot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Spot{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", x_position='" + x_position + "'" +
            ", y_position='" + y_position + "'" +
            '}';
    }
}
