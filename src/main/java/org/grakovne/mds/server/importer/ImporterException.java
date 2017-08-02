package org.grakovne.mds.server.importer;

import org.grakovne.mds.server.exceptons.MdsException;

/**
 * Fantlab importer exception.
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
