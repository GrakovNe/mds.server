package org.grakovne.mds.server.importers.fantlab.dto.search;

import java.util.List;

/**
 * Fantlab DTO.
 */

public class SearchResult {
    private List<AudioMatches> matches;

    public SearchResult() {
    }

    public List<AudioMatches> getMatches() {
        return matches;
    }

    public void setMatches(List<AudioMatches> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
            "matches=" + matches +
            '}';
    }
}
