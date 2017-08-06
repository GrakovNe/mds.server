package org.grakovne.mds.server.importers;

import org.grakovne.mds.server.exceptons.MdsException;

/**
 * Fantlab importers exception.
 */

public class ImporterException extends MdsException {
    private String message;

    public ImporterException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
