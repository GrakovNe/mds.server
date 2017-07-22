package org.grakovne.mds.server;

import org.grakovne.mds.server.importer.FantLabMetaImporter;
import org.grakovne.mds.server.importer.dto.FantLabStoryDto;
import org.grakovne.mds.server.utils.AudioUtils;

import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\GrakovNe\\Desktop\\Роберт Шекли - Опека.mp3");
        FantLabStoryDto dto = new FantLabMetaImporter().importMetaFromAudio(file);
        System.out.print(dto);
    }
}
