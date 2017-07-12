package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
