package com.nina.repository;

import com.nina.domain.Comments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Comments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {}
