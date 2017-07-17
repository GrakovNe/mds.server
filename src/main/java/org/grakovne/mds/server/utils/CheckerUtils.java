package org.grakovne.mds.server.utils;

import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityAlreadyExistException;
import org.grakovne.mds.server.exceptons.EntityNotFoundException;

import java.util.List;

public class CheckerUtils {

    public static void checkNotNull(Story story) {
        if (null == story) {
            throw new EntityNotFoundException(Story.class);
        }
    }

    public static void checkEmptyList(List<Story> storyList){
        if (!storyList.isEmpty()) {
            throw new EntityAlreadyExistException(Story.class);
        }
    }
}
