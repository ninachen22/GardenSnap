package com.nina.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plant.
 */
@Entity
@Table(name = "plant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "date_plant")
    private LocalDate datePlant;

    @Column(name = "water_per_hour")
    private Integer waterPerHour;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "plant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plant" }, allowSetters = true)
    private Set<Comments> comments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Plant name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public Plant location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDatePlant() {
        return this.datePlant;
    }

    public Plant datePlant(LocalDate datePlant) {
        this.setDatePlant(datePlant);
        return this;
    }

    public void setDatePlant(LocalDate datePlant) {
        this.datePlant = datePlant;
    }

    public Integer getWaterPerHour() {
        return this.waterPerHour;
    }

    public Plant waterPerHour(Integer waterPerHour) {
        this.setWaterPerHour(waterPerHour);
        return this;
    }

    public void setWaterPerHour(Integer waterPerHour) {
        this.waterPerHour = waterPerHour;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plant user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Comments> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comments> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setPlant(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setPlant(this));
        }
        this.comments = comments;
    }

    public Plant comments(Set<Comments> comments) {
        this.setComments(comments);
        return this;
    }

    public Plant addComments(Comments comments) {
        this.comments.add(comments);
        comments.setPlant(this);
        return this;
    }

    public Plant removeComments(Comments comments) {
        this.comments.remove(comments);
        comments.setPlant(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plant)) {
            return false;
        }
        return id != null && id.equals(((Plant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", datePlant='" + getDatePlant() + "'" +
            ", waterPerHour=" + getWaterPerHour() +
            "}";
    }
}
