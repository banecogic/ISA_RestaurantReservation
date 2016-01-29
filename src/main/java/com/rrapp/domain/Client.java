package com.rrapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotNull
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @NotNull
    @Pattern(regexp = ".+@{1}.+")
    @Column(name = "e_mail", nullable = false)
    private String e_mail;

    @Column(name = "date_of_birth")
    private LocalDate date_of_birth;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany()
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "FRIENDS",
    			joinColumns = @JoinColumn(name="friend1_id", referencedColumnName="id"),
    			inverseJoinColumns = @JoinColumn(name="friend2_id", referencedColumnName="id"))
    private Set<Client> friends = new HashSet<>();

    @ManyToMany()
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Restaurant> restaurants_visitss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String gete_mail() {
        return e_mail;
    }

    public void sete_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Client> getFriendss() {
        return friends;
    }

    public void setFriendss(Set<Client> clients) {
        this.friends = clients;
    }

    public Set<Restaurant> getRestaurants_visitss() {
        return restaurants_visitss;
    }

    public void setRestaurants_visitss(Set<Restaurant> restaurants) {
        this.restaurants_visitss = restaurants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", firstname='" + firstname + "'" +
            ", lastname='" + lastname + "'" +
            ", e_mail='" + e_mail + "'" +
            ", date_of_birth='" + date_of_birth + "'" +
            '}';
    }
}
