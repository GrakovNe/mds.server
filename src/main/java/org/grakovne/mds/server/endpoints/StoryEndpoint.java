package org.grakovne.mds.server.endpoints;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.grakovne.mds.server.MdsApplication;
import org.grakovne.mds.server.endpoints.support.ApiResponse;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1/story")
public class StoryEndpoint {

    @Autowired
    private StoryService storyService;

    @Autowired
    private HttpServletRequest request;

    private final Logger logger = LoggerFactory.getLogger(StoryEndpoint.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Page<Story>> findStories(@RequestParam(required = false, defaultValue = "0") Integer page) {
        Page<Story> stories = storyService.findStories(new PageRequest(page, 25));
        return new ApiResponse<>(stories);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiResponse<Story> findStory(@PathVariable Integer id) {
        Story story = storyService.findStory(id);
        return new ApiResponse<Story>(story);
    }

    @RequestMapping(value = "/audio/{id}", method = RequestMethod.GET)
    public void findStoryAudio(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        InputStream fileStream = new FileInputStream(storyService.findStoryAudio(id));
        IOUtils.copy(fileStream, response.getOutputStream());
        response.setContentType("audio/mpeg");
        response.flushBuffer();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<Story> createStory(@RequestPart(value = "story") String storyString, @RequestPart(value = "audio") MultipartFile audio) throws IOException {
        Gson gson = new Gson();
        Story story = gson.fromJson(storyString, Story.class);

        Story savedStory = storyService.createStory(story, audio);
        return new ApiResponse<>(savedStory);
    }

}
