package org.grakovne.mds.server.services;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public Page<Story> getStories(Integer pageNumber){
        return storyRepository.findAll(new PageRequest(pageNumber, 25, Sort.Direction.DESC, "id"));
    }

    public Story getStory(Integer id){
        return storyRepository.findOne(id);
    }

}
