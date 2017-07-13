package org.grakovne.mds.server.utils;

import org.grakovne.mds.server.entity.MdsEntity;
import org.grakovne.mds.server.entity.Story;
import org.grakovne.mds.server.exceptons.EntityNotFoundException;
public class CheckerUtils {

    public static void checkNotNull(Story story) {
        if (null == story) {
            throw new EntityNotFoundException(Story.class);
        }
    }
}
