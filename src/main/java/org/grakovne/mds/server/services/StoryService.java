package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.exceptons.MdsException;
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
import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private FileProcessingUtils fileProcessingUtils;

    @Autowired
    private AudioUtils audioUtils;

    private final String STORY_AUDIO_URL_PREFIX = "/audio/";
    private final String FILE_PREFIX = "story_";
    private final String FILE_POSTFIX = ".mp3";

    public Story findStory(Integer id){
        Story story = storyRepository.findOne(id);
        CheckerUtils.checkNotNull(story);
        return story;
    }

    public Page<Story> findStories(PageRequest pageRequest){
        return storyRepository.findAll(pageRequest);
    }

    public Story createStory(Story story, MultipartFile storyAudio){
        ValidationUtils.validate(story, storyAudio);

        List<Story> stories = storyRepository.findByUrl(story.getUrl());

        if (!stories.isEmpty()){
            throw new EntityAlreadyExistException(Story.class);

        }

        Story savedStory = storyRepository.save(story);

        File savedAudioFile = null;

        try {
            String fileName = FILE_PREFIX + savedStory.getId() + FILE_POSTFIX;
            savedAudioFile = fileProcessingUtils.uploadFile(storyAudio, fileName);
            savedStory.setUrl(STORY_AUDIO_URL_PREFIX + savedStory.getId());
        } catch (IOException e) {
            throw new MdsException("Can't upload a story");
        }

        fillAudioFileMeta(savedStory, savedAudioFile);
        savedStory = storyRepository.save(story);

        return savedStory;
    }

    public File findStoryAudio(Integer storyId){
        String fileName = FILE_PREFIX + storyId + FILE_POSTFIX;

        try{
            return fileProcessingUtils.getFile(fileName);
        } catch (FileNotFoundException ex){
            throw new MdsException("Audio file is not found");
        }

    }

    private Story fillAudioFileMeta(Story story, File file){
        story.setFileSize(audioUtils.getAudioFileSize(file));
        story.setFileQuality(audioUtils.getAudioFileBitrate(file));
        story.setLength(audioUtils.getAudioFileLength(file));
        return story;
    }
}
