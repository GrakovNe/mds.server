package org.grakovne.mds.server.exceptons;

import org.grakovne.mds.server.entity.MdsEntity;

public class EntityAlreadyExistException extends MdsException {
    public EntityAlreadyExistException(Class<? extends MdsEntity> mdsEntity) {
        super("Entity already exists: " + mdsEntity.getSimpleName());
    }
}
