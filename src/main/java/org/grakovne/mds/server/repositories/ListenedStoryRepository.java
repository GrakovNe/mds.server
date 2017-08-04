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
    ListenedStory findByUserAndStory(User user, Story story);

    List<ListenedStory> findAllByUser(User user);
}
