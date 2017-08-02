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
     * Finds story by year and title.
     *
     * @param year  story year
     * @param title story title
     * @return Story object
     */
    Story findByYearAndTitle(Integer year, String title);
}
