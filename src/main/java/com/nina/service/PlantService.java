package com.nina.service;

import com.nina.domain.Plant;
import com.nina.repository.PlantRepository;
import com.nina.service.dto.PlantDTO;
import com.nina.service.mapper.PlantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plant}.
 */
@Service
@Transactional
public class PlantService {

    private final Logger log = LoggerFactory.getLogger(PlantService.class);

    private final PlantRepository plantRepository;

    private final PlantMapper plantMapper;

    public PlantService(PlantRepository plantRepository, PlantMapper plantMapper) {
        this.plantRepository = plantRepository;
        this.plantMapper = plantMapper;
    }

    /**
     * Save a plant.
     *
     * @param plantDTO the entity to save.
     * @return the persisted entity.
     */
    public PlantDTO save(PlantDTO plantDTO) {
        log.debug("Request to save Plant : {}", plantDTO);
        Plant plant = plantMapper.toEntity(plantDTO);
        plant = plantRepository.save(plant);
        return plantMapper.toDto(plant);
    }

    /**
     * Update a plant.
     *
     * @param plantDTO the entity to save.
     * @return the persisted entity.
     */
    public PlantDTO update(PlantDTO plantDTO) {
        log.debug("Request to update Plant : {}", plantDTO);
        Plant plant = plantMapper.toEntity(plantDTO);
        plant = plantRepository.save(plant);
        return plantMapper.toDto(plant);
    }

    /**
     * Partially update a plant.
     *
     * @param plantDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlantDTO> partialUpdate(PlantDTO plantDTO) {
        log.debug("Request to partially update Plant : {}", plantDTO);

        return plantRepository
            .findById(plantDTO.getId())
            .map(existingPlant -> {
                plantMapper.partialUpdate(existingPlant, plantDTO);

                return existingPlant;
            })
            .map(plantRepository::save)
            .map(plantMapper::toDto);
    }

    /**
     * Get all the plants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PlantDTO> findAll() {
        log.debug("Request to get all Plants");
        return plantRepository.findAll().stream().map(plantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the plants with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PlantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return plantRepository.findAllWithEagerRelationships(pageable).map(plantMapper::toDto);
    }

    /**
     * Get one plant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlantDTO> findOne(Long id) {
        log.debug("Request to get Plant : {}", id);
        return plantRepository.findOneWithEagerRelationships(id).map(plantMapper::toDto);
    }

    /**
     * Delete the plant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Plant : {}", id);
        plantRepository.deleteById(id);
    }
}
