package org.grakovne.mds.server.exceptons;

import org.grakovne.mds.server.entity.MdsEntity;

/**
 * EntityException exception.
 */

public class EntityException extends MdsException {
    public EntityException(Class<? extends MdsEntity> mdsEntity, String message) {
        super(message + " : " + mdsEntity.getSimpleName());
    }
}
