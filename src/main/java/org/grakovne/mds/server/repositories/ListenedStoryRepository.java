package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.ListenedStory;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository.
 */

@Repository
public interface ListenedStoryRepository extends JpaRepository<ListenedStory, Integer> {

    /**
     * Finds listened story by user and story.
     *
     * @param user  user entity
     * @param story story entity
     * @return listened story entity
     */
    ListenedStory findByUserAndStory(User user, Story story);

    /**
     * Finds all listened stories for user.
     *
     * @param user user entity
     * @return list with listened stories
     */

    List<ListenedStory> findAllByUser(User user);
}
