package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.repositories.StoryRepository;
import org.grakovne.mds.server.utils.AudioUtils;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.FileProcessingUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Story service.
 */

@Service
public class StoryService {
    private final String storyAudioUrlPrefix = "/audio/";
    private final String filePrefix = "story_";
    private final String filePostfix = ".mp3";

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private FileProcessingUtils fileProcessingUtils;

    @Autowired
    private AudioUtils audioUtils;

    @Autowired
    private ConfigurationUtils configurationUtils;

    /**
     * Finds story by it's id.
     *
     * @param id id
     * @return Story entity
     */
    public Story findStory(Integer id) {
        Story story = storyRepository.findOne(id);
        CheckerUtils.checkNotNull(story);
        return story;
    }

    /**
     * Finds stories.
     *
     * @param pageNumber page number
     * @return Page with stories
     */

    public Page<Story> findStories(Integer pageNumber) {
        return storyRepository.findAll(new PageRequest(pageNumber, configurationUtils.getPageSize()));
    }

    /**
     * Saves new story in DB.
     *
     * @param story      story entity
     * @param storyAudio audio file
     * @return saved story
     */

    public Story createStory(Story story, MultipartFile storyAudio) {
        ValidationUtils.validate(story, storyAudio);

        Story savedStory = storyRepository.save(story);

        try {
            File savedAudioFile = fileProcessingUtils.uploadFile(
                storyAudio, filePrefix + savedStory.getId() + filePostfix);
            setAudioData(savedStory, savedAudioFile);
        } catch (IOException e) {
            throw new EntityException(Story.class, e.getMessage());
        }

        return storyRepository.save(story);
    }

    /**
     * Finds story audio file by story id.
     *
     * @param storyId story id
     * @return file with mp3
     */

    public File findStoryAudio(Integer storyId) {
        String fileName = filePrefix + storyId + filePostfix;

        try {
            return fileProcessingUtils.getFile(fileName);
        } catch (FileNotFoundException ex) {
            throw new EntityException(Story.class, "Audio file is not found");
        }

    }

    /**
     * Deletes story by it's id.
     *
     * @param storyId story id
     */

    public void deleteStory(Integer storyId) {
        Story story = storyRepository.findOne(storyId);
        CheckerUtils.checkNotNull(story);

        try {
            fileProcessingUtils.deleteFile(filePrefix + storyId + filePostfix);
        } catch (FileNotFoundException e) {
            throw new EntityException(Story.class, "Audio file can't de deleted");
        }

        storyRepository.delete(storyId);
    }

    private Story setAudioData(Story story, File file) {
        story.setUrl(storyAudioUrlPrefix + story.getId());
        story.setFileSize(audioUtils.getAudioFileSize(file));
        story.setFileQuality(audioUtils.getAudioFileBitrate(file));
        story.setLength(audioUtils.getAudioFileLength(file));
        return story;
    }
}
