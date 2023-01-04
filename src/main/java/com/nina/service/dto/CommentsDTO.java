package com.nina.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.nina.domain.Comments} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentsDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private String description;

    private PlantDTO plant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlantDTO getPlant() {
        return plant;
    }

    public void setPlant(PlantDTO plant) {
        this.plant = plant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentsDTO)) {
            return false;
        }

        CommentsDTO commentsDTO = (CommentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentsDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", plant=" + getPlant() +
            "}";
    }
}
