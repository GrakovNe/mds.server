package org.grakovne.mds.server.importer;

public class ImporterException extends RuntimeException {
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
