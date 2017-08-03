package org.grakovne.mds.server.utils;

import org.grakovne.mds.server.entity.Author;
import org.grakovne.mds.server.entity.Genre;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.entity.StoryBookmark;
import org.grakovne.mds.server.entity.User;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.exceptons.EntityException;
import org.grakovne.mds.server.exceptons.EntityNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Common class which provides some simply checks.
 */

public class CheckerUtils {

    /**
     * Checks that entity isn't null.
     *
     * @param story entity
     */

    public static void checkNotNull(Story story) {
        if (null == story) {
            throw new EntityNotFoundException(Story.class);
        }
    }

    /**
     * Checks that entity isn't null.
     *
     * @param genre entity
     */

    public static void checkNotNull(Genre genre) {
        if (null == genre) {
            throw new EntityNotFoundException(Genre.class);
        }
    }

    /**
     * Checks that entity isn't null.
     *
     * @param author entity
     */

    public static void checkNotNull(Author author) {
        if (null == author) {
            throw new EntityNotFoundException(Author.class);
        }
    }

    /**
     * Checks that entity is null.
     *
     * @param genre entity
     */

    public static void checkNull(Genre genre) {
        if (null != genre) {
            throw new EntityAlreadyExistException(Genre.class);
        }
    }

    /**
     * Checks that entity is null.
     *
     * @param story entity
     */

    public static void checkNull(Story story) {
        if (null != story) {
            throw new EntityAlreadyExistException(Story.class);
        }
    }

    /**
     * Checks that entity is null.
     *
     * @param author entity
     */

    public static void checkNull(Author author) {
        if (null != author) {
            throw new EntityAlreadyExistException(Author.class);
        }
    }

    /**
     * Checks that file is exists on a disk.
     *
     * @param file file object
     * @throws FileNotFoundException when file isn't found
     */

    public static void checkFileExists(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File Not found!");
        }
    }

    public static void checkNotNull(User user) {
        if (null == user) {
            throw new EntityNotFoundException(User.class);
        }
    }

    public static void checkStoryBookmarkBelongsUser(StoryBookmark storyBookmark, User user) {
        if (!storyBookmark.getUser().getId().equals(user.getId())) {
            throw new EntityException(StoryBookmark.class, "storybookmark is not belongs to user");
        }
    }
}
