package org.grakovne.mds.server.importer.dto.search;

import java.util.List;

public class AudioMetaData {
    private List<AudioMatches> matches;

    public AudioMetaData() {
    }

    public List<AudioMatches> getMatches() {
        return matches;
    }

    public void setMatches(List<AudioMatches> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return "AudioMetaData{" +
            "matches=" + matches +
            '}';
    }
}
