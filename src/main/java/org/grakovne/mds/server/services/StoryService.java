package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.repositories.StoryRepository;
import org.grakovne.mds.server.utils.AudioUtils;
import org.grakovne.mds.server.utils.CheckerUtils;
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

@Service
public class StoryService {
    private final String STORY_AUDIO_URL_PREFIX = "/audio/";
    private final String FILE_PREFIX = "story_";
    private final String FILE_POSTFIX = ".mp3";
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private FileProcessingUtils fileProcessingUtils;
    @Autowired
    private AudioUtils audioUtils;

    public Story findStory(Integer id) {
        Story story = storyRepository.findOne(id);
        CheckerUtils.checkNotNull(story);
        return story;
    }

    public Page<Story> findStories(PageRequest pageRequest) {
        return storyRepository.findAll(pageRequest);
    }

    public Story createStory(Story story, MultipartFile storyAudio) {
        ValidationUtils.validate(story, storyAudio);
        CheckerUtils.checkEmptyList(storyRepository.findByUrl(story.getUrl()));

        Story savedStory = storyRepository.save(story);

        try {
            File savedAudioFile = fileProcessingUtils.uploadFile(storyAudio, FILE_PREFIX + savedStory.getId() + FILE_POSTFIX);
            setAudioData(savedStory, savedAudioFile);
        } catch (IOException e) {
            throw new EntityException(Story.class, "Can't upload a story");
        }

        return storyRepository.save(story);
    }

    public File findStoryAudio(Integer storyId) {
        String fileName = FILE_PREFIX + storyId + FILE_POSTFIX;

        try {
            return fileProcessingUtils.getFile(fileName);
        } catch (FileNotFoundException ex) {
            throw new EntityException(Story.class, "Audio file is not found");
        }

    }

    public void deleteStory(Integer storyId) {
        Story story = storyRepository.findOne(storyId);
        CheckerUtils.checkNotNull(story);

        try {
            fileProcessingUtils.deleteFile(FILE_PREFIX + storyId + FILE_POSTFIX);
        } catch (FileNotFoundException e) {
            throw new EntityException(Story.class, "Audio file can't de deleted");
        }

        storyRepository.delete(storyId);
    }

    private Story setAudioData(Story story, File file) {
        story.setUrl(STORY_AUDIO_URL_PREFIX + story.getId());
        story.setFileSize(audioUtils.getAudioFileSize(file));
        story.setFileQuality(audioUtils.getAudioFileBitrate(file));
        story.setLength(audioUtils.getAudioFileLength(file));
        return story;
    }
}
