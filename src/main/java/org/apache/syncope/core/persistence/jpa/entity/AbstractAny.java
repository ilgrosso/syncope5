/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.core.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class AbstractAny extends AbstractGeneratedKeyEntity {

    private static final long serialVersionUID = -2666540708092702810L;

    /**
     * Username of the user that has created the related instance.
     */
    private String creator;

    /**
     * Creation date.
     */
    private OffsetDateTime creationDate;

    /**
     * Context information about create.
     */
    private String creationContext;

    /**
     * Username of the user that has performed the last modification to the related instance.
     */
    private String lastModifier;

    private OffsetDateTime lastChangeDate;

    /**
     * Context information about last update.
     */
    private String lastChangeContext;

    @Column(nullable = true)
    private String status;

    public String getCreator() {
        return creator;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationContext() {
        return creationContext;
    }

    public void setCreationContext(final String creationContext) {
        this.creationContext = creationContext;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(final String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public OffsetDateTime getLastChangeDate() {
        if (lastChangeDate != null) {
            return lastChangeDate;
        } else if (creationDate != null) {
            return creationDate;
        }

        return null;
    }

    public void setLastChangeDate(final OffsetDateTime lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getLastChangeContext() {
        return lastChangeContext;
    }

    public void setLastChangeContext(final String lastChangeContext) {
        this.lastChangeContext = lastChangeContext;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
