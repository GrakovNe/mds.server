package org.grakovne.mds.server.maintenance;

import org.grakovne.mds.server.maintenance.actions.MaintenanceAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Executes startup actions when context up.
 */

@Service
public class MaintenanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceService.class);

    @Autowired
    private List<MaintenanceAction> actions;

    /**
     * Executes all classes by interface.
     *
     * @throws Exception if something wrong
     */

    @Scheduled(fixedRate = 86_400_000)
    public void maintenanceConfigure() throws Exception {
        actions.forEach(MaintenanceAction::execute);
        LOGGER.info("Maintenance actions done.");
    }
}
