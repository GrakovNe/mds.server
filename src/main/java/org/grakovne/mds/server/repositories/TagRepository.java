package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findAllByValue(String value);
}
