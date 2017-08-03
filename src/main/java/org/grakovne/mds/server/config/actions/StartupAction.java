package org.grakovne.mds.server.config.actions;

/**
 * Common interface for startup actions.
 */
public interface StartupAction {

    /**
     * executes action on startup.
     */
    void execute();
}
