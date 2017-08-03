package org.grakovne.mds.server.maintenance.actions;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Removes orphan entity from DB if theirs audio files is not presented.
 */

@Service
public class DataBaseValidator implements MaintenanceAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseValidator.class);

    @Autowired
    private StoryService storyService;

    @Override
    public void execute() {
        LOGGER.info("Database validation is started...");
        removeStoriesWithoutAudio();
        LOGGER.info("Database validation is finished...");
    }

    private void removeStoriesWithoutAudio() {
        List<Story> stories = storyService.findStories();

        stories.forEach(story -> {
            try {
                storyService.findStoryAudio(story.getId());
            } catch (EntityException ex) {
                storyService.deleteStory(story.getId());
                LOGGER.warn("Story with id = " + story.getId() + " was deleted because the audio file was not found");
            }
        });

    }
}
