package org.grakovne.mds.server.endpoints.rest.v1;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.grakovne.mds.server.endpoints.rest.v1.support.ApiResponse;
import org.grakovne.mds.server.entity.ListenedStory;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.services.ListenedStoryService;
import org.grakovne.mds.server.services.StoryBookmarkService;
import org.grakovne.mds.server.services.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Spring story endpoint.
 */

@RestController
@RequestMapping("/api/v1/story")
public class StoryEndpoint {

    private final Logger logger = LoggerFactory.getLogger(StoryEndpoint.class);

    @Autowired
    private StoryService storyService;

    @Autowired
    private StoryBookmarkService storyBookmarkService;

    @Autowired
    private ListenedStoryService listenedStoryService;

    /**
     * Finds stories without filters.
     *
     * @param pageNumber page number
     * @return page with stories
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<Page<Story>> findStories(
        @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

        Page<Story> stories = storyService.findStories(pageNumber);
        return new ApiResponse<>(stories);
    }

    /**
     * Finds story bi it's id.
     *
     * @param id story id
     * @return story entity
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ApiResponse<Story> findStory(@PathVariable Integer id) {
        Story story = storyService.findStory(id);
        return new ApiResponse<>(story);
    }

    /**
     * Finds audio stream of story.
     *
     * @param id       story id
     * @param response http response
     * @throws IOException when file can't be sent
     */

    @RequestMapping(value = "{id}/audio", method = RequestMethod.GET)
    public void findStoryAudio(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        InputStream fileStream = new FileInputStream(storyService.findStoryAudio(id));
        IOUtils.copy(fileStream, response.getOutputStream());
        response.setContentType("audio/mpeg");
        response.flushBuffer();
    }

    /**
     * Saves new story in db.
     *
     * @param storyString string with JSON of story entity
     * @param audio       mp3 audio file
     * @return story entity
     * @throws IOException when file can't be saved
     */

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiResponse<Story> createStory(
        @RequestPart(value = "story", required = false) String storyString,
        @RequestPart(value = "audio") MultipartFile audio) throws IOException {

        Story result;
        if (null == storyString || Strings.isNullOrEmpty(storyString)) {
            result = storyService.importStory(audio);
        } else {
            Story story = new Gson().fromJson(storyString, Story.class);
            result = storyService.createStory(story, audio);
        }

        return new ApiResponse<>(result);
    }

    /**
     * Returns stories bookmarks for user.
     *
     * @param id   story id
     * @param user user auth
     * @return list with bookmarks for story
     */

    @RequestMapping(value = "{id}/storyBookmark", method = RequestMethod.GET)
    public ApiResponse<List<StoryBookmark>> getStoryBookmark(
        @PathVariable Integer id,
        @AuthenticationPrincipal User user) {

        List<StoryBookmark> storyBookmarkList = storyBookmarkService.findStoryBookmarks(id, user.getId());
        return new ApiResponse<>(storyBookmarkList);
    }

    /**
     * Creates story bookmarks in db.
     *
     * @param storyBookmark storybookmark dto with timestamp
     * @param id            story id
     * @param user          user auth
     * @return created storybookmark
     */

    @RequestMapping(value = "{id}/storyBookmark", method = RequestMethod.POST)
    public ApiResponse<StoryBookmark> bookmarkStory(
        @RequestBody StoryBookmark storyBookmark,
        @PathVariable Integer id,
        @AuthenticationPrincipal User user) {

        StoryBookmark savedStoryBookmark = storyBookmarkService.createBookmark(id, storyBookmark, user);
        return new ApiResponse<>(savedStoryBookmark);
    }

    /**
     * Marks story as listened for user.
     *
     * @param storyId story id
     * @param user    user auth
     * @return Listened story entity
     */

    @RequestMapping(value = "{storyId}/listen", method = RequestMethod.POST)
    public ApiResponse<ListenedStory> listenStory(
        @PathVariable Integer storyId,
        @AuthenticationPrincipal User user) {

        ListenedStory listenedStory = listenedStoryService.listenStory(storyId, user);
        return new ApiResponse<ListenedStory>(listenedStory);
    }

    /**
     * Marks story as un-listened for user.
     *
     * @param storyId story id
     * @param user    user auth
     * @return Listened story entity
     */

    @RequestMapping(value = "{storyId}/listen", method = RequestMethod.DELETE)
    public ApiResponse unListenStory(
        @PathVariable Integer storyId,
        @AuthenticationPrincipal User user) {

        listenedStoryService.unListenStory(storyId, user);
        return new ApiResponse("Story has been marked as unlistened");
    }

    /**
     * Returns list of listened stories for user.
     *
     * @param user user auth
     * @return list with listened stories
     */

    @RequestMapping(value = "listen", method = RequestMethod.GET)
    public ApiResponse<List<ListenedStory>> findUsersListenedStories(
        @AuthenticationPrincipal User user) {

        List<ListenedStory> listenedStories = listenedStoryService.findUsersListenedStory(user);
        return new ApiResponse<List<ListenedStory>>(listenedStories);
    }

    /**
     * Removes storybookmark for user.
     *
     * @param id   story id
     * @param user user auth
     * @return status message
     */
    @RequestMapping(value = "storyBookmark/{id}", method = RequestMethod.DELETE)
    public ApiResponse deleteStoryBookmark(
        @PathVariable Integer id,
        @AuthenticationPrincipal User user) {

        storyBookmarkService.deleteStoryBookmark(id, user.getId());
        return new ApiResponse("Story has been deleted");
    }


    /**
     * Deletes story by it's id.
     *
     * @param id story id
     * @return status message
     */

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ApiResponse deleteStory(@PathVariable Integer id) {
        storyService.deleteStory(id);
        return new ApiResponse("Story has been deleted");
    }

}
