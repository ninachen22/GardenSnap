package com.nina.service;

import com.nina.domain.Comments;
import com.nina.repository.CommentsRepository;
import com.nina.service.dto.CommentsDTO;
import com.nina.service.mapper.CommentsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Comments}.
 */
@Service
@Transactional
public class CommentsService {

    private final Logger log = LoggerFactory.getLogger(CommentsService.class);

    private final CommentsRepository commentsRepository;

    private final CommentsMapper commentsMapper;

    public CommentsService(CommentsRepository commentsRepository, CommentsMapper commentsMapper) {
        this.commentsRepository = commentsRepository;
        this.commentsMapper = commentsMapper;
    }

    /**
     * Save a comments.
     *
     * @param commentsDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentsDTO save(CommentsDTO commentsDTO) {
        log.debug("Request to save Comments : {}", commentsDTO);
        Comments comments = commentsMapper.toEntity(commentsDTO);
        comments = commentsRepository.save(comments);
        return commentsMapper.toDto(comments);
    }

    /**
     * Update a comments.
     *
     * @param commentsDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentsDTO update(CommentsDTO commentsDTO) {
        log.debug("Request to update Comments : {}", commentsDTO);
        Comments comments = commentsMapper.toEntity(commentsDTO);
        comments = commentsRepository.save(comments);
        return commentsMapper.toDto(comments);
    }

    /**
     * Partially update a comments.
     *
     * @param commentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommentsDTO> partialUpdate(CommentsDTO commentsDTO) {
        log.debug("Request to partially update Comments : {}", commentsDTO);

        return commentsRepository
            .findById(commentsDTO.getId())
            .map(existingComments -> {
                commentsMapper.partialUpdate(existingComments, commentsDTO);

                return existingComments;
            })
            .map(commentsRepository::save)
            .map(commentsMapper::toDto);
    }

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CommentsDTO> findAll() {
        log.debug("Request to get all Comments");
        return commentsRepository.findAll().stream().map(commentsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one comments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommentsDTO> findOne(Long id) {
        log.debug("Request to get Comments : {}", id);
        return commentsRepository.findById(id).map(commentsMapper::toDto);
    }

    /**
     * Delete the comments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Comments : {}", id);
        commentsRepository.deleteById(id);
    }
}
