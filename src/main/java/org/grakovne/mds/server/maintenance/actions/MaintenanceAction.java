package org.grakovne.mds.server.maintenance.actions;

/**
 * Common interface for startup actions.
 */
public interface MaintenanceAction {

    /**
     * executes action on startup.
     */
    void execute();
}
