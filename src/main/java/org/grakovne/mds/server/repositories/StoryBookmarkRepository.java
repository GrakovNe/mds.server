package org.grakovne.mds.server.repositories;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoryBookmarkRepository extends JpaRepository<StoryBookmark, Integer> {

    List<StoryBookmark> findAllByUserAndStory(User user, Story story);

    StoryBookmark findByUserAndCreateDateTime(User user, LocalDateTime createDateTime);
}
