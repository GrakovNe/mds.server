package org.grakovne.mds.server.endpoints;

import com.google.gson.Gson;
import org.grakovne.mds.server.endpoints.support.ApiResponse;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/story")
public class StoryEndpoint {

    @Autowired
    private StoryService storyService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Page<Story>> findStories(@RequestParam (required = false, defaultValue = "0") Integer page)
    {
        Page<Story> stories = storyService.findStories(new PageRequest(page, 25));
        return new ApiResponse<>(stories);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiResponse<Story> findStory(@PathVariable Integer id){
        Story story = storyService.findStory(id);
        return new ApiResponse<Story>(story);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String createStory(@RequestPart (value = "story") String storyString, @RequestPart (value = "audio") MultipartFile audio) throws IOException {
        //Story savedStory = storyService.crateStory(story, null);
        //return new ApiResponse<Story>(savedStory);

        String uploadsDir = "/uploads/";
        String realPathtoUploads =  request.getServletContext().getRealPath(uploadsDir);
        if(! new File(realPathtoUploads).exists())
        {
            new File(realPathtoUploads).mkdir();
        }


        String orgName = audio.getOriginalFilename();
        String filePath = "C:\\" + orgName;
        File dest = new File(filePath);
        audio.transferTo(dest);

        Gson gson = new Gson();
        Story story = gson.fromJson(storyString, Story.class);

        return realPathtoUploads;

        //return new ApiResponse<>(story);
    }
}
