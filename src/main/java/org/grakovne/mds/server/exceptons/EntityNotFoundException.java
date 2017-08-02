package org.grakovne.mds.server.exceptons;

import org.grakovne.mds.server.entity.MdsEntity;

/**
 * EntityNotFoundException exception.
 */

public class EntityNotFoundException extends MdsException {

    public EntityNotFoundException(Class<? extends MdsEntity> mdsEntity) {
        super("Entity not found: " + mdsEntity.getSimpleName());
    }
}
