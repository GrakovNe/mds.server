package org.grakovne.mds.server.exceptons;

public abstract class MdsException extends RuntimeException {
    public MdsException(String message) {
        super(message);
    }
}
