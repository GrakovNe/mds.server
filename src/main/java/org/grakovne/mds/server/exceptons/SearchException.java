package org.grakovne.mds.server.exceptons;

/**
 * SearchException exception.
 */

public class SearchException extends MdsException {
    public SearchException(String message) {
        super("Can't search: " + message);
    }
}
