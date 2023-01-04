package com.nina.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.nina.domain.Plant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlantDTO implements Serializable {

    private Long id;

    private String name;

    private String location;

    private LocalDate datePlant;

    private Integer waterPerHour;

    private UserDTO user;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDatePlant() {
        return datePlant;
    }

    public void setDatePlant(LocalDate datePlant) {
        this.datePlant = datePlant;
    }

    public Integer getWaterPerHour() {
        return waterPerHour;
    }

    public void setWaterPerHour(Integer waterPerHour) {
        this.waterPerHour = waterPerHour;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlantDTO)) {
            return false;
        }

        PlantDTO plantDTO = (PlantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", location='" + getLocation() + "'" +
            ", datePlant='" + getDatePlant() + "'" +
            ", waterPerHour=" + getWaterPerHour() +
            ", user=" + getUser() +
            "}";
    }
}
