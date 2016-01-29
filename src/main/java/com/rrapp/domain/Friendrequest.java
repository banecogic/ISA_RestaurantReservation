package com.rrapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Friendrequest.
 */
@Entity
@Table(name = "friendrequest")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Friendrequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "sent_date_and_time", nullable = false)
    private ZonedDateTime sentDateAndTime;

    @NotNull
    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    @Column(name = "accepted_date_and_time")
    private ZonedDateTime acceptedDateAndTime;

    @JsonBackReference(value="requester")
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @JsonBackReference(value = "requested")
    @ManyToOne
    @JoinColumn(name = "requested_id")
    private User requested;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSentDateAndTime() {
        return sentDateAndTime;
    }

    public void setSentDateAndTime(ZonedDateTime sentDateAndTime) {
        this.sentDateAndTime = sentDateAndTime;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public ZonedDateTime getAcceptedDateAndTime() {
        return acceptedDateAndTime;
    }

    public void setAcceptedDateAndTime(ZonedDateTime acceptedDateAndTime) {
        this.acceptedDateAndTime = acceptedDateAndTime;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User user) {
        this.requester = user;
    }

    public User getRequested() {
        return requested;
    }

    public void setRequested(User user) {
        this.requested = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Friendrequest friendrequest = (Friendrequest) o;
        return Objects.equals(id, friendrequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Friendrequest{" +
            "id=" + id +
            ", requester='" + this.getRequester() + "'" +
            ", requested='" + this.getRequested() + "'" +
            ", sentDateAndTime='" + sentDateAndTime + "'" +
            ", isAccepted='" + isAccepted + "'" +
            ", acceptedDateAndTime='" + acceptedDateAndTime + "'" +
            '}';
    }
}
