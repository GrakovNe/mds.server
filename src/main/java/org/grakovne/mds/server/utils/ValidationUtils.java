package org.grakovne.mds.server.utils;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityValidationException;

import java.io.File;

public class ValidationUtils {
    public static void validate(Story story, File storyAudio) {

        if (Strings.isNullOrEmpty(story.getTitle())){
            throw new EntityValidationException(Story.class, "story should have a name");
        }

        if (null == story.getAuthors() || story.getAuthors().isEmpty()){
            throw new EntityValidationException(Story.class, "story must have at least one author");
        }

        if (null == story.getYear() || story.getYear().equals(0)){
            throw new EntityValidationException(Story.class, "story must have a year");
        }

        if (null == storyAudio || storyAudio.length() == 0){
            throw new EntityValidationException(Story.class, "story must have a audio file");
        }
    }
}
