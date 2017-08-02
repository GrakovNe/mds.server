package org.grakovne.mds.server.config;

import org.grakovne.mds.server.config.actions.StartupAction;
import org.grakovne.mds.server.services.StoryService;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StartupConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupConfig.class);

    @Autowired
    private StoryService storyService;

    @EventListener(ContextRefreshedEvent.class)
    public void startupConfigure() throws Exception {

        Map<String, Object> services = new HashMap<>();
        services.putIfAbsent(storyService.getClass().getSimpleName(), storyService);

        Reflections reflections = new Reflections("org.grakovne.mds");

        for (Class<? extends StartupAction> actionClass : reflections.getSubTypesOf(StartupAction.class)) {
            actionClass.getConstructor(Map.class).newInstance(services).execute();
        }

        LOGGER.info("Startup actions done.");

    }
}
