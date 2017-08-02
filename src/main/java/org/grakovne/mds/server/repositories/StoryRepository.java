package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository.
 */

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {
    /**
     * Finds story by it's url.
     *
     * @param url story url
     * @return story entity
     */
    Story findByUrl(String url);

    Story findByYearAndTitle(Integer year, String title);
}
