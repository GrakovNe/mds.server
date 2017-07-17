package org.grakovne.mds.server.utils;

import com.google.common.base.Strings;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ValidationUtils {
    public static void validate(Story story, MultipartFile storyAudio) {

        final String storyFileExtension = ".mp3";

        if (Strings.isNullOrEmpty(story.getTitle())){
            throw new EntityValidationException(Story.class, "story should have a name");
        }

        /*
        if (null == story.getAuthors() || story.getAuthors().isEmpty()){
            throw new EntityValidationException(Story.class, "story must have at least one author");
        }
        */

        if (null == story.getYear() || story.getYear().equals(0)){
            throw new EntityValidationException(Story.class, "story must have a year");
        }

        try {
            if (null == storyAudio || storyAudio.getBytes().length == 0){
                throw new EntityValidationException(Story.class, "story must have a audio file");
            }
        } catch (IOException e) {
            throw new EntityValidationException(Story.class, "Can't validate a story audio file");
        }

        if (!storyAudio.getOriginalFilename().endsWith(storyFileExtension)){
            throw new EntityValidationException(Story.class, "story audio file must be in MP3");
        }
    }
}
