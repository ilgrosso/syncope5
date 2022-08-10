package org.apache.syncope.core.persistence.api.entity;

import java.time.OffsetDateTime;

public interface Any extends Entity {

    OffsetDateTime getCreationDate();

    String getCreator();

    String getCreationContext();

    OffsetDateTime getLastChangeDate();

    String getLastModifier();

    String getLastChangeContext();

    void setCreationDate(OffsetDateTime creationDate);

    void setCreator(String creator);

    void setCreationContext(String context);

    void setLastChangeDate(OffsetDateTime lastChangeDate);

    void setLastModifier(String lastModifier);

    void setLastChangeContext(String context);

    String getStatus();

    void setStatus(String status);
}
