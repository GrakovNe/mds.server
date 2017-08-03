package org.grakovne.mds.server.config;

import org.grakovne.mds.server.config.actions.StartupAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Executes startup actions when context up.
 */

@Service
public class StartupConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupConfig.class);

    @Autowired
    private List<StartupAction> actions;

    /**
     * Executes all classes by interface.
     *
     * @throws Exception if something wrong
     */

    @EventListener(ContextRefreshedEvent.class)
    public void startupConfigure() throws Exception {
        actions.forEach(StartupAction::execute);
        LOGGER.info("Startup actions done.");
    }
}
