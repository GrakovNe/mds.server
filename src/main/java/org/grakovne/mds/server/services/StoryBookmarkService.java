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

import java.util.List;

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

    public List<StoryBookmark> findStoryBookmarks(Integer storyId, Integer userId) {
        Story story = storyService.findStory(storyId);
        User user = userService.findUser(userId);

        return storyBookmarkRepository.findAllByUserAndStory(user, story);
    }

    public StoryBookmark createBookmark(Integer storyId, StoryBookmark storyBookmark, User user) {
        storyBookmark.setUser(user);
        storyBookmark.setStory(storyService.findStory(storyId));

        ValidationUtils.validate(storyBookmark);
        checkNotFound(storyBookmark);

        return persistStoryBookmark(storyBookmark);
    }

    public StoryBookmark findStoryBookmark(Integer storyBookmarkId, Integer userId){
        StoryBookmark storyBookmark = storyBookmarkRepository.findOne(storyBookmarkId);
        User user = userService.findUser(userId);

        CheckerUtils.checkStoryBookmarkBelongsUser(storyBookmark, user);

        return storyBookmark;
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

    public void deleteStoryBookmark(Integer storyBookmarkId, Integer userId){
        StoryBookmark storyBookmark = findStoryBookmark(storyBookmarkId, userId);
        storyBookmarkRepository.delete(storyBookmarkId);
    }
}
