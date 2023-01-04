package com.nina.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlantMapperTest {

    private PlantMapper plantMapper;

    @BeforeEach
    public void setUp() {
        plantMapper = new PlantMapperImpl();
    }
}
