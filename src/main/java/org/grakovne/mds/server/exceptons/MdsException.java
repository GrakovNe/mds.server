package org.grakovne.mds.server.exceptons;

/**
 * MdsException exception.
 */

public abstract class MdsException extends RuntimeException {
    public MdsException(String message) {
        super(message);
    }
}
