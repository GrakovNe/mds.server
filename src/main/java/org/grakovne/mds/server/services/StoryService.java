package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.repositories.StoryRepository;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    public Story findStory(Integer id){
        Story story = storyRepository.findOne(id);
        CheckerUtils.checkNotNull(story);
        return story;
    }

    public Page<Story> findStories(PageRequest pageRequest){
        return storyRepository.findAll(pageRequest);
    }

    public Story crateStory(Story story, File storyAudio){
        ValidationUtils.validate(story, storyAudio);

        List<Story> stories = storyRepository.findByTitleAndAuthorsAndYear(story.getTitle(), story.getAuthors(), story.getYear());

        if (stories.isEmpty()){
            throw new EntityAlreadyExistException(Story.class);

        }

        return storyRepository.save(story);
    }
}
