package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    /**
     * Returns Tag entity by value.
     *
     * @param value tag name
     * @return Tag object
     */

    Tag findAllByValue(String value);
}
