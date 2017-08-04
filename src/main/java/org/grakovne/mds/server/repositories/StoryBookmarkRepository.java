package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Repository.
 */

@Repository
public interface StoryBookmarkRepository extends JpaRepository<StoryBookmark, Integer> {

    /**
     * Returns list with storybookmarks of story for user.
     *
     * @param user  user entity
     * @param story story entity
     * @return list with storybookmarks
     */

    List<StoryBookmark> findAllByUserAndStory(User user, Story story);

    /**
     * Returns list with storybookmarks for user by createdatetime.
     *
     * @param user           user entity
     * @param createDateTime createdatetime of storybookmark
     * @return Storybookmark entity
     */

    StoryBookmark findByUserAndCreateDateTime(User user, LocalDateTime createDateTime);
}
