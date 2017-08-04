package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.repositories.StoryBookmarkRepository;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * StoryBookmark service.
 */

@Service
public class StoryBookmarkService {

    @Autowired
    private StoryBookmarkRepository storyBookmarkRepository;

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurationUtils configurationUtils;

    /**
     * Finds storybookmarks by storyId for user.
     *
     * @param storyId story id
     * @param userId  user id
     * @return List with storybookmarks
     */

    public List<StoryBookmark> findStoryBookmarks(Integer storyId, Integer userId) {
        Story story = storyService.findStory(storyId);
        User user = userService.findUser(userId);

        return storyBookmarkRepository.findAllByUserAndStory(user, story);
    }

    /**
     * Creates new storybookmark for user.
     *
     * @param storyId       story id
     * @param storyBookmark storybookmark dto with timestamp
     * @param user          user auth
     * @return storybookmark entity
     */

    public StoryBookmark createBookmark(Integer storyId, StoryBookmark storyBookmark, User user) {
        storyBookmark.setUser(user);
        storyBookmark.setStory(storyService.findStory(storyId));
        storyBookmark.setCreateDateTime(LocalDateTime.now());

        ValidationUtils.validate(storyBookmark);
        checkNotFound(storyBookmark);

        return persistStoryBookmark(storyBookmark);
    }

    /**
     * Finds storybookmark by it's id.
     *
     * @param storyBookmarkId storybookmark id
     * @param userId          user id for security check
     * @return storybookmark entity or exception
     */

    public StoryBookmark findStoryBookmark(Integer storyBookmarkId, Integer userId) {
        StoryBookmark storyBookmark = storyBookmarkRepository.findOne(storyBookmarkId);
        User user = userService.findUser(userId);

        CheckerUtils.checkStoryBookmarkBelongsUser(storyBookmark, user);

        return storyBookmark;
    }

    /**
     * Removes storybookmark from db by it's id.
     *
     * @param storyBookmarkId storybookmark id.
     * @param userId          user id for security check
     */

    public void deleteStoryBookmark(Integer storyBookmarkId, Integer userId) {
        StoryBookmark storyBookmark = findStoryBookmark(storyBookmarkId, userId);
        storyBookmarkRepository.delete(storyBookmark.getId());
    }

    private void checkNotFound(StoryBookmark storyBookmark) {

        StoryBookmark foundStoryBookmark = storyBookmarkRepository.findByUserAndCreateDateTime(
            storyBookmark.getUser(),
            storyBookmark.getCreateDateTime()
        );

        if (null != foundStoryBookmark) {
            throw new EntityAlreadyExistException(StoryBookmark.class);
        }
    }

    private StoryBookmark persistStoryBookmark(StoryBookmark storyBookmark) {
        return storyBookmarkRepository.save(storyBookmark);
    }
}
