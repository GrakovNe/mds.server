package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.ActionTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTimeTypeRepository extends JpaRepository<ActionTimeType, Integer> {
}
