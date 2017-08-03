package org.grakovne.mds.server.config;

import org.grakovne.mds.server.config.actions.StartupAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartupConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupConfig.class);

    @Autowired
    private List<StartupAction> actions;

    @EventListener(ContextRefreshedEvent.class)
    public void startupConfigure() throws Exception {

        for(StartupAction action: actions){
            action.execute();
        }

        LOGGER.info("Startup actions done.");

    }
}
