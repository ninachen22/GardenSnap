package com.nina.repository;

import com.nina.domain.Plant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Plant entity.
 */
@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    @Query("select plant from Plant plant where plant.user.login = ?#{principal.username}")
    List<Plant> findByUserIsCurrentUser();

    default Optional<Plant> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Plant> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Plant> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct plant from Plant plant left join fetch plant.user",
        countQuery = "select count(distinct plant) from Plant plant"
    )
    Page<Plant> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct plant from Plant plant left join fetch plant.user")
    List<Plant> findAllWithToOneRelationships();

    @Query("select plant from Plant plant left join fetch plant.user where plant.id =:id")
    Optional<Plant> findOneWithToOneRelationships(@Param("id") Long id);
}
