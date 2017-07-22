package org.grakovne.mds.server.importer;

import org.grakovne.mds.server.importer.dto.search.AudioMetaData;

public interface AudioMetaImporter {
    AudioMetaData importMetaFromAudio(String title, String artist);
}
