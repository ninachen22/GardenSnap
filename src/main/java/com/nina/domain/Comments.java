package com.nina.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Comments.
 */
@Entity
@Table(name = "comments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "comments" }, allowSetters = true)
    private Plant plant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Comments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Comments date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public Comments description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Comments plant(Plant plant) {
        this.setPlant(plant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comments)) {
            return false;
        }
        return id != null && id.equals(((Comments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comments{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
