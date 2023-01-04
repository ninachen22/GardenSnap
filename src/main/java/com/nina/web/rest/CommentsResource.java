package com.nina.web.rest;

import com.nina.repository.CommentsRepository;
import com.nina.service.CommentsService;
import com.nina.service.dto.CommentsDTO;
import com.nina.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.nina.domain.Comments}.
 */
@RestController
@RequestMapping("/api")
public class CommentsResource {

    private final Logger log = LoggerFactory.getLogger(CommentsResource.class);

    private static final String ENTITY_NAME = "comments";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentsService commentsService;

    private final CommentsRepository commentsRepository;

    public CommentsResource(CommentsService commentsService, CommentsRepository commentsRepository) {
        this.commentsService = commentsService;
        this.commentsRepository = commentsRepository;
    }

    /**
     * {@code POST  /comments} : Create a new comments.
     *
     * @param commentsDTO the commentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentsDTO, or with status {@code 400 (Bad Request)} if the comments has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comments")
    public ResponseEntity<CommentsDTO> createComments(@RequestBody CommentsDTO commentsDTO) throws URISyntaxException {
        log.debug("REST request to save Comments : {}", commentsDTO);
        if (commentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new comments cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentsDTO result = commentsService.save(commentsDTO);
        return ResponseEntity
            .created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comments/:id} : Updates an existing comments.
     *
     * @param id the id of the commentsDTO to save.
     * @param commentsDTO the commentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsDTO,
     * or with status {@code 400 (Bad Request)} if the commentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentsDTO> updateComments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentsDTO commentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Comments : {}, {}", id, commentsDTO);
        if (commentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentsDTO result = commentsService.update(commentsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comments/:id} : Partial updates given fields of an existing comments, field will ignore if it is null
     *
     * @param id the id of the commentsDTO to save.
     * @param commentsDTO the commentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentsDTO,
     * or with status {@code 400 (Bad Request)} if the commentsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the commentsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the commentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentsDTO> partialUpdateComments(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommentsDTO commentsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Comments partially : {}, {}", id, commentsDTO);
        if (commentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentsDTO> result = commentsService.partialUpdate(commentsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commentsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comments in body.
     */
    @GetMapping("/comments")
    public List<CommentsDTO> getAllComments() {
        log.debug("REST request to get all Comments");
        return commentsService.findAll();
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comments.
     *
     * @param id the id of the commentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Long id) {
        log.debug("REST request to get Comments : {}", id);
        Optional<CommentsDTO> commentsDTO = commentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentsDTO);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comments.
     *
     * @param id the id of the commentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable Long id) {
        log.debug("REST request to delete Comments : {}", id);
        commentsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
