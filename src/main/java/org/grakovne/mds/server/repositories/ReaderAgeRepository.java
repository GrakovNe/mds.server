package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.ReaderAge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderAgeRepository extends JpaRepository<ReaderAge, Integer> {
}
