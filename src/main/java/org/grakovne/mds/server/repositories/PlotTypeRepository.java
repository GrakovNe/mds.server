package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.PlotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface PlotTypeRepository extends JpaRepository<PlotType, Integer> {
}
