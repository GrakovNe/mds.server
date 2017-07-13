package org.grakovne.mds.server.exceptons;

import org.grakovne.mds.server.entity.MdsEntity;

public class EntityValidationException extends MdsException {
    public EntityValidationException(Class<? extends MdsEntity> mdsEntity, String validationIssue) {
        super(mdsEntity.getSimpleName() + " : " + validationIssue);
    }
}
