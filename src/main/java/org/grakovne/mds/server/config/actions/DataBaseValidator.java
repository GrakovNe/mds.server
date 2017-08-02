package org.grakovne.mds.server.config.actions;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class DataBaseValidator implements StartupAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseValidator.class);

    private StoryService storyService;

    public DataBaseValidator(Map<String, Object> services) {
        storyService = (StoryService) services.get("StoryService");
    }

    @Override
    public void execute() {
        List<Story> stories = storyService.findStories();
        LOGGER.info("!!! " + stories.size());
    }
}
