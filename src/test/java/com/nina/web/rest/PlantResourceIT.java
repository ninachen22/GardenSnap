package com.nina.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nina.IntegrationTest;
import com.nina.domain.Plant;
import com.nina.repository.PlantRepository;
import com.nina.service.PlantService;
import com.nina.service.dto.PlantDTO;
import com.nina.service.mapper.PlantMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_PLANT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PLANT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_WATER_PER_HOUR = 1;
    private static final Integer UPDATED_WATER_PER_HOUR = 2;

    private static final String ENTITY_API_URL = "/api/plants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlantRepository plantRepository;

    @Mock
    private PlantRepository plantRepositoryMock;

    @Autowired
    private PlantMapper plantMapper;

    @Mock
    private PlantService plantServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantMockMvc;

    private Plant plant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plant createEntity(EntityManager em) {
        Plant plant = new Plant()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION)
            .datePlant(DEFAULT_DATE_PLANT)
            .waterPerHour(DEFAULT_WATER_PER_HOUR);
        return plant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plant createUpdatedEntity(EntityManager em) {
        Plant plant = new Plant()
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .datePlant(UPDATED_DATE_PLANT)
            .waterPerHour(UPDATED_WATER_PER_HOUR);
        return plant;
    }

    @BeforeEach
    public void initTest() {
        plant = createEntity(em);
    }

    @Test
    @Transactional
    void createPlant() throws Exception {
        int databaseSizeBeforeCreate = plantRepository.findAll().size();
        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);
        restPlantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plantDTO)))
            .andExpect(status().isCreated());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeCreate + 1);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPlant.getDatePlant()).isEqualTo(DEFAULT_DATE_PLANT);
        assertThat(testPlant.getWaterPerHour()).isEqualTo(DEFAULT_WATER_PER_HOUR);
    }

    @Test
    @Transactional
    void createPlantWithExistingId() throws Exception {
        // Create the Plant with an existing ID
        plant.setId(1L);
        PlantDTO plantDTO = plantMapper.toDto(plant);

        int databaseSizeBeforeCreate = plantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlants() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get all the plantList
        restPlantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].datePlant").value(hasItem(DEFAULT_DATE_PLANT.toString())))
            .andExpect(jsonPath("$.[*].waterPerHour").value(hasItem(DEFAULT_WATER_PER_HOUR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(plantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(plantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(plantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(plantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        // Get the plant
        restPlantMockMvc
            .perform(get(ENTITY_API_URL_ID, plant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.datePlant").value(DEFAULT_DATE_PLANT.toString()))
            .andExpect(jsonPath("$.waterPerHour").value(DEFAULT_WATER_PER_HOUR));
    }

    @Test
    @Transactional
    void getNonExistingPlant() throws Exception {
        // Get the plant
        restPlantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant
        Plant updatedPlant = plantRepository.findById(plant.getId()).get();
        // Disconnect from session so that the updates on updatedPlant are not directly saved in db
        em.detach(updatedPlant);
        updatedPlant.name(UPDATED_NAME).location(UPDATED_LOCATION).datePlant(UPDATED_DATE_PLANT).waterPerHour(UPDATED_WATER_PER_HOUR);
        PlantDTO plantDTO = plantMapper.toDto(updatedPlant);

        restPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPlant.getDatePlant()).isEqualTo(UPDATED_DATE_PLANT);
        assertThat(testPlant.getWaterPerHour()).isEqualTo(UPDATED_WATER_PER_HOUR);
    }

    @Test
    @Transactional
    void putNonExistingPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlantWithPatch() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant using partial update
        Plant partialUpdatedPlant = new Plant();
        partialUpdatedPlant.setId(plant.getId());

        partialUpdatedPlant.name(UPDATED_NAME).waterPerHour(UPDATED_WATER_PER_HOUR);

        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlant))
            )
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPlant.getDatePlant()).isEqualTo(DEFAULT_DATE_PLANT);
        assertThat(testPlant.getWaterPerHour()).isEqualTo(UPDATED_WATER_PER_HOUR);
    }

    @Test
    @Transactional
    void fullUpdatePlantWithPatch() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeUpdate = plantRepository.findAll().size();

        // Update the plant using partial update
        Plant partialUpdatedPlant = new Plant();
        partialUpdatedPlant.setId(plant.getId());

        partialUpdatedPlant
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .datePlant(UPDATED_DATE_PLANT)
            .waterPerHour(UPDATED_WATER_PER_HOUR);

        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlant))
            )
            .andExpect(status().isOk());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
        Plant testPlant = plantList.get(plantList.size() - 1);
        assertThat(testPlant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPlant.getDatePlant()).isEqualTo(UPDATED_DATE_PLANT);
        assertThat(testPlant.getWaterPerHour()).isEqualTo(UPDATED_WATER_PER_HOUR);
    }

    @Test
    @Transactional
    void patchNonExistingPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlant() throws Exception {
        int databaseSizeBeforeUpdate = plantRepository.findAll().size();
        plant.setId(count.incrementAndGet());

        // Create the Plant
        PlantDTO plantDTO = plantMapper.toDto(plant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plant in the database
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlant() throws Exception {
        // Initialize the database
        plantRepository.saveAndFlush(plant);

        int databaseSizeBeforeDelete = plantRepository.findAll().size();

        // Delete the plant
        restPlantMockMvc
            .perform(delete(ENTITY_API_URL_ID, plant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plant> plantList = plantRepository.findAll();
        assertThat(plantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
