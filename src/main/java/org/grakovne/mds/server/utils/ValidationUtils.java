package org.grakovne.mds.server.utils;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Genre;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.exceptons.EntityValidationException;

/**
 * Common class which can validate every entity.
 */

public class ValidationUtils {

    /**
     * Validates story.
     *
     * @param story      story entity
     */

    public static void validate(Story story) {

        if (Strings.isNullOrEmpty(story.getTitle())) {
            throw new EntityValidationException(Story.class, "story should have a name");
        }

        if (null == story.getAuthors() || story.getAuthors().isEmpty()){
            throw new EntityValidationException(Story.class, "story must have at least one author");
        }

        if (null == story.getYear() || story.getYear().equals(0)) {
            throw new EntityValidationException(Story.class, "story must have a year");
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

    /**
     * Validates storyBookmark.
     *
     * @param storyBookmark storyBookmark entity
     */

    public static void validate(StoryBookmark storyBookmark) {
        if (null == storyBookmark) {
            throw new EntityValidationException(StoryBookmark.class, "story bookmark is null");
        }

        if (null == storyBookmark.getCreateDateTime()) {
            throw new EntityValidationException(StoryBookmark.class, "story bookmark must have create datetime");
        }

        if (null == storyBookmark.getTimestamp()) {
            throw new EntityValidationException(StoryBookmark.class, "story bookmark must have timestamp");
        }

        if (null == storyBookmark.getStory()) {
            throw new EntityValidationException(StoryBookmark.class, "story bookmark must have story entity");
        }

        if (null == storyBookmark.getUser()) {
            throw new EntityValidationException(StoryBookmark.class, "story bookmark must have user");
        }
    }
}
