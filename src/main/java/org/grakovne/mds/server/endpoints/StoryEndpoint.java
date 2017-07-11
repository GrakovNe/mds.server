package org.grakovne.mds.server.endpoints;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/story")
public class StoryEndpoint {

    @Autowired
    private StoryService storyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Story> getStories(@RequestParam Integer pageNumber){
        return storyService.getStories(pageNumber);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Story getStory(@PathVariable Integer id){
        return storyService.getStory(id);
    }
}
