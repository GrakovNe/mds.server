package org.grakovne.mds.server.services;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.Tag;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.importer.fantlab.FantLabMetaImporter;
import org.grakovne.mds.server.importer.fantlab.converters.FantLabStoryConverter;
import org.grakovne.mds.server.importer.fantlab.dto.FantLabStoryDto;
import org.grakovne.mds.server.repositories.StoryRepository;
import org.grakovne.mds.server.utils.AudioUtils;
import org.grakovne.mds.server.utils.CheckerUtils;
import org.grakovne.mds.server.utils.ConfigurationUtils;
import org.grakovne.mds.server.utils.FileProcessingUtils;
import org.grakovne.mds.server.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Story service.
 */

@Service
public class StoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoryService.class);

    private final String storyAudioUrlPrefix = "/audio/";
    private final String filePrefix = "story_";
    private final String filePostfix = ".mp3";

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagService tagService;

    @Autowired
    private FileProcessingUtils fileProcessingUtils;

    @Autowired
    private AudioUtils audioUtils;

    @Autowired
    private ConfigurationUtils configurationUtils;

    @Autowired
    private FantLabMetaImporter fantLabMetaImporter;

    @Autowired
    private FantLabStoryConverter fantLabStoryConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private ListenedStoryService listenedStoryService;

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
     * Returns all listened stories.
     *
     * @param user       user entity
     * @param pageNumber page number
     * @return page with stories
     */

    public Page<Story> findListenedStories(User user, Integer pageNumber) {
        return storyRepository.findUsersListenedStories(
            user,
            new PageRequest(pageNumber, configurationUtils.getPageSize())
        );
    }

    /**
     * Returns all un-listened stories.
     *
     * @param user       user entity
     * @param pageNumber page number
     * @return page with stories
     */

    public Page<Story> findUnListenedStories(User user, Integer pageNumber) {
        return storyRepository.findUsersUnListenedStories(
            user,
            new PageRequest(pageNumber,
                configurationUtils.getPageSize())
        );
    }

    /**
     * Returns all stories from db.
     *
     * @return list with stories.
     */
    public List<Story> findStories() {
        return storyRepository.findAll();
    }


    /**
     * Saves new story in DB.
     *
     * @param story      story entity
     * @param storyAudio audio file
     * @return saved story
     * @throws IOException if something wrong with file upload
     */

    public Story createStory(Story story, MultipartFile storyAudio) throws IOException {
        File audioFile = createTempStoryAudioFile(storyAudio);

        return persistsStory(story, audioFile);
    }

    /**
     * Imports story from 3rd party service.
     *
     * @param storyAudio File with ID3 tags
     * @return saved story
     * @throws IOException if something wrong with file upload
     */

    public Story importStory(MultipartFile storyAudio) throws IOException {
        File audioFile = createTempStoryAudioFile(storyAudio);

        FantLabStoryDto storyDto = fantLabMetaImporter.importMetaFromAudio(audioFile);
        Story convertedStory = fantLabStoryConverter.convertFromFantLabStory(storyDto);

        return persistsStory(convertedStory, audioFile);
    }

    /**
     * Finds story audio file by story id.
     *
     * @param storyId story id
     * @return file with mp3
     */

    public File findStoryAudio(Integer storyId) {
        try {
            return fileProcessingUtils.getFile(getFileName(storyId));
        } catch (FileNotFoundException ex) {
            throw new EntityException(Story.class, "Audio file is not found");
        }

    }

    private String getFileName(Integer storyId) {
        return filePrefix + storyId + filePostfix;
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
            LOGGER.warn("story with id = " + storyId + " was deleted but storyFile was not processed");
        }

        storyRepository.delete(storyId);
    }

    /**
     * Updates story meta in db.
     *
     * @param storyId  story id
     * @param newStory new story meta DTO
     * @return updated story object
     */

    public Story updateStory(Integer storyId, Story newStory) {
        Story persistStory = findStory(storyId);

        if (!Strings.isNullOrEmpty(newStory.getTitle())) {
            persistStory.setTitle(newStory.getTitle());
        }

        if (null != newStory.getYear()) {
            persistStory.setYear(newStory.getYear());
        }

        if (null != newStory.getAuthors() && !newStory.getAuthors().isEmpty()) {
            Set<Author> authors = authorService.persistAuthorList(newStory.getAuthors());
            persistStory.setAuthors(authors);
        }

        if (null != newStory.getRating() && null != newStory.getRating().getValue()) {
            if (null != persistStory.getRating()) {
                persistStory.getRating().setValue(newStory.getRating().getValue());
                persistStory.getRating().setVoters(newStory.getRating().getVoters());
            } else {
                persistStory.setRating(newStory.getRating());
            }
        }

        if (null != newStory.getCover() && !Strings.isNullOrEmpty(newStory.getCover().getBase64EncodedCover())) {
            if (null == persistStory.getCover()) {
                persistStory.setCover(newStory.getCover());
            } else {
                persistStory.getCover().setBase64EncodedCover(newStory.getCover().getBase64EncodedCover());
            }
        }

        if (null != newStory.getAnnotation() && !Strings.isNullOrEmpty(newStory.getAnnotation())) {
            persistStory.setAnnotation(newStory.getAnnotation());
        }

        if (null != newStory.getTags()) {
            if (null != persistStory.getTags()) {
                persistStory.getTags().addAll(newStory.getTags());
            } else {
                persistStory.setTags(newStory.getTags());
            }
        }

        return persistsStory(persistStory);
    }

    private void setAudioData(Story story, File file) {
        story.setUrl(story.getId() + storyAudioUrlPrefix);
        story.setFileSize(audioUtils.getAudioFileSize(file));
        story.setFileQuality(audioUtils.getAudioFileBitrate(file));
        story.setLength(audioUtils.getAudioFileLength(file));
    }

    private Story persistsStory(Story story) {
        Set<Author> persistAuthors = authorService.persistAuthorList(story.getAuthors());
        story.setAuthors(persistAuthors);

        Set<Tag> persistsTags = tagService.persistTagList(story.getTags());
        story.setTags(persistsTags);

        return storyRepository.save(story);
    }

    private void checkNotFound(Story story) {
        Story foundStory = storyRepository.findByYearAndTitle(story.getYear(), story.getTitle());

        if (null != foundStory) {
            throw new EntityAlreadyExistException(Story.class);
        }
    }

    private File uploadStoryFile(Integer storyId, File storyAudio) {
        try {
            return fileProcessingUtils.uploadFile(
                storyAudio, filePrefix + storyId + filePostfix);
        } catch (IOException e) {
            throw new EntityException(Story.class, e.getMessage());
        }
    }

    private File createTempStoryAudioFile(MultipartFile storyAudio) throws IOException {
        File audioFile = File.createTempFile("audio_story", ".mp3");
        storyAudio.transferTo(audioFile);

        return audioFile;
    }

    private Story persistsStory(Story story, File audioFile) {

        ValidationUtils.validate(story);
        checkNotFound(story);

        Story savedStory = persistsStory(story);

        File savedStoryAudioFile = uploadStoryFile(story.getId(), audioFile);
        setAudioData(savedStory, savedStoryAudioFile);

        return persistsStory(savedStory);
    }

    public Story findRandomStory() {
        return storyRepository.findRandomStory(new PageRequest(1, 1)).getContent().get(0);
    }
}
