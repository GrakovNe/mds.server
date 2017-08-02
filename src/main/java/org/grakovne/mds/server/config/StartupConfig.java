package org.grakovne.mds.server.config;

import com.google.common.collect.ImmutableMap;
import org.grakovne.mds.server.config.actions.StartupAction;
import org.grakovne.mds.server.services.AuthorService;
import org.grakovne.mds.server.services.GenreService;
import org.grakovne.mds.server.services.StoryService;
import org.grakovne.mds.server.services.TagService;
import org.grakovne.mds.server.services.UserService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StartupConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupConfig.class);

    @Autowired
    private StoryService storyService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    @EventListener(ContextRefreshedEvent.class)
    public void startupConfigure() throws Exception {

        Map<String, Object> services = ImmutableMap.<String, Object>builder()
            .put(storyService.getClass().getSimpleName(), storyService)
            .put(authorService.getClass().getSimpleName(), authorService)
            .put(genreService.getClass().getSimpleName(), genreService)
            .put(tagService.getClass().getSimpleName(), tagService)
            .put(userService.getClass().getSimpleName(), userService)
            .build();

        Reflections reflections = new Reflections("org.grakovne.mds");

        for (Class<? extends StartupAction> actionClass : reflections.getSubTypesOf(StartupAction.class)) {
            actionClass.getConstructor(Map.class).newInstance(services).execute();
        }

        LOGGER.info("Startup actions done.");

    }
}
