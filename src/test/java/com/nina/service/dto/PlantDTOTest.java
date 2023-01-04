package com.nina.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nina.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantDTO.class);
        PlantDTO plantDTO1 = new PlantDTO();
        plantDTO1.setId(1L);
        PlantDTO plantDTO2 = new PlantDTO();
        assertThat(plantDTO1).isNotEqualTo(plantDTO2);
        plantDTO2.setId(plantDTO1.getId());
        assertThat(plantDTO1).isEqualTo(plantDTO2);
        plantDTO2.setId(2L);
        assertThat(plantDTO1).isNotEqualTo(plantDTO2);
        plantDTO1.setId(null);
        assertThat(plantDTO1).isNotEqualTo(plantDTO2);
    }
}
