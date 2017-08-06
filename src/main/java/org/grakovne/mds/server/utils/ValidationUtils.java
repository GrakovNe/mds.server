package org.grakovne.mds.server.utils;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.exceptons.EntityValidationException;
import org.grakovne.mds.server.exceptons.SearchException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Common class which can validate every entity.
 */

public class ValidationUtils {

    /**
     * Validates story.
     *
     * @param story story entity
     */

    public static void validate(Story story) {

        if (Strings.isNullOrEmpty(story.getTitle())) {
            throw new EntityValidationException(Story.class, "story should have a name");
        }

        if (null == story.getAuthors() || story.getAuthors().isEmpty()) {
            throw new EntityValidationException(Story.class, "story must have at least one author");
        }

        if (null == story.getYear() || story.getYear().equals(0)) {
            throw new EntityValidationException(Story.class, "story must have a year");
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

    public static void validate(User user){
        if (null == user){
            throw new EntityException(User.class, "user is null");
        }

        if (Strings.isNullOrEmpty(user.getUsername())){
            throw new EntityException(User.class, "user must have username");
        }

        if (Strings.isNullOrEmpty(user.getPassword())){
            throw new EntityException(User.class, "user must have password");
        }
    }

    /**
     * Validates search parameters.
     *
     * @param searchParams search parameters
     */

    public static void validate(Map<String, String> searchParams) {

        if (null == searchParams) {
            throw new SearchException("search parameters is not presented");
        }

        List<String> allowedOrderByFields = Arrays.asList("id", "title", "year", "rating");
        List<String> allowedListenedTypes = Arrays.asList("both", "listened", "unlistened");

        String listenedType = searchParams.getOrDefault("listenedType", "both");
        String orderBy = searchParams.getOrDefault("orderBy", "title");
        String userId = searchParams.get("userId");

        if (listenedType.equals("listened") || listenedType.equals("unlistened")) {
            if (Strings.isNullOrEmpty(userId)) {
                throw new SearchException("user should be logged in for search in " + listenedType + " stories");
            }
        }

        if (!allowedOrderByFields.contains(orderBy)) {
            throw new SearchException("invalid orderBy field");
        }

        if (!allowedListenedTypes.contains(listenedType)) {
            throw new SearchException("invalid listened type");
        }

    }
}
