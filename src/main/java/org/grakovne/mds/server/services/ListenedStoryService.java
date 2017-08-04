package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.ListenedStory;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.repositories.ListenedStoryRepository;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ListenedStory service.
 */

@Service
public class ListenedStoryService {

    @Autowired
    private ListenedStoryRepository listenedStoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    public ListenedStory listenStory(Integer storyId, User user) {
        Story story = storyService.findStory(storyId);

        ListenedStory listenedStory = new ListenedStory();
        listenedStory.setCreateDateTime(LocalDateTime.now());
        listenedStory.setStory(story);
        listenedStory.setUser(user);

        return persistsListenedStory(listenedStory);
    }

    public void unListenStory(Integer storyId, User user) {
        Story story = storyService.findStory(storyId);

        ListenedStory listenedStory = listenedStoryRepository.findByUserAndStory(user, story);
        CheckerUtils.checkListenedStoryBelongsUser(listenedStory, user);

        listenedStoryRepository.delete(listenedStory.getId());
    }

    public List<ListenedStory> findUsersListenedStory(User user) {
        return listenedStoryRepository.findAllByUser(user);
    }

    private ListenedStory persistsListenedStory(ListenedStory listenedStory) {
        checkNotFound(listenedStory);

        return listenedStoryRepository.save(listenedStory);
    }

    private void checkNotFound(ListenedStory listenedStory) {
        ListenedStory foundListenedStory = listenedStoryRepository.findByUserAndStory(
            listenedStory.getUser(),
            listenedStory.getStory());

        if (null != foundListenedStory) {
            throw new EntityAlreadyExistException(ListenedStory.class);
        }
    }
}
