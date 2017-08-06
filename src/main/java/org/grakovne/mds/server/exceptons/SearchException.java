package org.grakovne.mds.server.exceptons;

public class SearchException extends MdsException {
    public SearchException(String message) {
        super("Can't search: " + message);
    }
}
