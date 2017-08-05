package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.User;
import org.springframework.cglib.core.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * Returns all listened stories.
     *
     * @param user     user entity
     * @param pageable spring pagination
     * @return page with stories
     */

    @Query("from Story s where id in (select story.id from ListenedStory ls where ls.user.id = :#{#user.id})")
    Page<Story> findUsersListenedStories(@Param("user") User user, Pageable pageable);

    /**
     * Returns all un-listened stories.
     *
     * @param user     user entity
     * @param pageable spring pagination
     * @return page with stories
     */

    @Query("from Story s where id not in (select story.id from ListenedStory ls where ls.user.id = :#{#user.id})")
    Page<Story> findUsersUnListenedStories(@Param("user") User user, Pageable pageable);

    @Query("from Story s order by random()")
    Page<Story> findRandomStory(Pageable pageable);
}
