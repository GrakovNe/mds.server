package org.grakovne.mds.server.maintenance.actions;

import org.grakovne.mds.server.services.StoryService;
import org.grakovne.mds.server.utils.FileProcessingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Removes files if they do not belong to any history.
 */

@Service
public class UploadFolderActuator implements MaintenanceAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadFolderActuator.class);

    @Autowired
    private FileProcessingUtils fileProcessingUtils;

    @Autowired
    private StoryService storyService;

    @Override
    public void execute() {
        LOGGER.info("Upload folder validation is started...");
        createUploadFolder();
        removeFilesWithoutStory();
        LOGGER.info("Upload folder validation is finished.");
    }

    private void createUploadFolder() {
        File uploadFolder = fileProcessingUtils.getUploadFolder();

        if (!uploadFolder.exists()) {
            fileProcessingUtils.createUploadDir();
            LOGGER.warn("Upload directory was re-created");
        }
    }

    private void removeFilesWithoutStory() {
        List<File> filesInUploadDirectory = fileProcessingUtils.getFiles();

        List<File> storiesFiles = new ArrayList<>();
        storyService.findStories().forEach(story -> storiesFiles.add(storyService.findStoryAudio(story.getId())));

        filesInUploadDirectory.removeAll(storiesFiles);

        filesInUploadDirectory.forEach(file -> {
            fileProcessingUtils.deleteFile(file);
            LOGGER.warn("File with name = " + file.getName() + " was deleted because the story not found in database");
        });
    }
}
