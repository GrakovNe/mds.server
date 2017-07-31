package org.grakovne.mds.server.services;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Genre;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.Tag;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.importer.fantlab.FantLabMetaImporter;
import org.grakovne.mds.server.importer.fantlab.converters.FantLabStoryConverter;
import org.grakovne.mds.server.importer.fantlab.dto.FantLabStoryDto;
import org.grakovne.mds.server.repositories.AuthorRepository;
import org.grakovne.mds.server.repositories.StoryRepository;
import org.grakovne.mds.server.repositories.TagRepository;
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
import java.util.Set;

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
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

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

        Story savedStory = persistsStory(story);

        try {
            File savedAudioFile = fileProcessingUtils.uploadFile(
                storyAudio, filePrefix + savedStory.getId() + filePostfix);
            setAudioData(savedStory, savedAudioFile);
        } catch (IOException e) {
            throw new EntityException(Story.class, e.getMessage());
        }

        return persistsStory(savedStory);
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

    public Story importStory(MultipartFile storyAudio) throws IOException {
        File audioFile = File.createTempFile("audio_story", ".mp3");
        storyAudio.transferTo(audioFile);

        FantLabStoryDto storyDto = fantLabMetaImporter.importMetaFromAudio(audioFile);
        Story convertedStory = fantLabStoryConverter.convertFromFantLabStory(storyDto);

        return createStory(convertedStory, storyAudio);
    }

    private Story setAudioData(Story story, File file) {
        story.setUrl("/" + story.getId() + storyAudioUrlPrefix);
        story.setFileSize(audioUtils.getAudioFileSize(file));
        story.setFileQuality(audioUtils.getAudioFileBitrate(file));
        story.setLength(audioUtils.getAudioFileLength(file));
        return story;
    }

    private Story persistsStory(Story story){
        Set<Author> persistAuthors = authorService.persistAuthorList(story.getAuthors());
        story.setAuthors(persistAuthors);

        Set<Tag> persistsTags = tagService.persistTagList(story.getTags());
        story.setTags(persistsTags);

        Set<Genre> persistsGenres = genreService.persistGenreList(story.getGenres());
        story.setGenres(persistsGenres);

        return storyRepository.save(story);
    }
}
