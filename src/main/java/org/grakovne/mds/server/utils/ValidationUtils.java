package org.grakovne.mds.server.utils;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Genre;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Common class which can validate every entity.
 */

public class ValidationUtils {

    /**
     * Validates story.
     *
     * @param story      story entity
     * @param storyAudio audio file
     */

    public static void validate(Story story, MultipartFile storyAudio) {

        final String storyFileExtension = ".mp3";

        if (Strings.isNullOrEmpty(story.getTitle())) {
            throw new EntityValidationException(Story.class, "story should have a name");
        }

        /*
        if (null == story.getAuthors() || story.getAuthors().isEmpty()){
            throw new EntityValidationException(Story.class, "story must have at least one author");
        }
        */

        if (null == story.getYear() || story.getYear().equals(0)) {
            throw new EntityValidationException(Story.class, "story must have a year");
        }

        try {
            if (null == storyAudio || storyAudio.getBytes().length == 0) {
                throw new EntityValidationException(Story.class, "story must have a audio file");
            }
        } catch (IOException e) {
            throw new EntityValidationException(Story.class, "Can't validate a story audio file");
        }

        if (!storyAudio.getOriginalFilename().endsWith(storyFileExtension)) {
            throw new EntityValidationException(Story.class, "story audio file must be in MP3");
        }
    }

    /**
     * Validates genre.
     *
     * @param genre genre entity
     */

    public static void validate(Genre genre) {
        if (null == genre) {
            throw new EntityValidationException(Genre.class, "genre is null");
        }

        if (Strings.isNullOrEmpty(genre.getValue())) {
            throw new EntityValidationException(Genre.class, "genre must have a value");
        }
    }

    /**
     * Validates author.
     *
     * @param author author entity
     */

    public static void validate(Author author) {
        if (null == author) {
            throw new EntityValidationException(Author.class, "author is null");
        }

        if (Strings.isNullOrEmpty(author.getName())) {
            throw new EntityValidationException(Author.class, "author must have name");
        }

    }
}
