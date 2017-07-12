package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.ActionPlaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface ActionPlaceTypeRepository extends JpaRepository<ActionPlaceType, Integer> {
}
