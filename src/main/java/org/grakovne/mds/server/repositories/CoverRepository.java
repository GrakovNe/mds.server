package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Cover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<Cover, Integer> {
}
