/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.thingsboard.server.common.data.id.DeviceCredentialsId;
import org.thingsboard.server.common.data.id.DeviceId;
import org.thingsboard.server.common.data.id.TerritoryCredentialsId;
import org.thingsboard.server.common.data.id.TerritoryId;
import org.thingsboard.server.common.data.security.DeviceCredentials;
import org.thingsboard.server.common.data.security.DeviceCredentialsType;
import org.thingsboard.server.common.data.security.TerritoryCredentials;
import org.thingsboard.server.common.data.security.TerritoryCredentialsType;
import org.thingsboard.server.dao.model.BaseEntity;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import javax.persistence.*;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.TERRITORY_CREDENTIALS_COLUMN_FAMILY_NAME)
public final class TerritoryCredentialsEntity extends BaseSqlEntity<TerritoryCredentials> implements BaseEntity<TerritoryCredentials> {

    @Column(name = ModelConstants.TERRITORY_CREDENTIALS_TERRITORY_ID_PROPERTY)
    private UUID territoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.TERRITORY_CREDENTIALS_CREDENTIALS_TYPE_PROPERTY)
    private TerritoryCredentialsType credentialsType;

    @Column(name = ModelConstants.TERRITORY_CREDENTIALS_CREDENTIALS_ID_PROPERTY)
    private String credentialsId;

    @Column(name = ModelConstants.TERRITORY_CREDENTIALS_CREDENTIALS_VALUE_PROPERTY)
    private String credentialsValue;

    public TerritoryCredentialsEntity() {
        super();
    }

    public TerritoryCredentialsEntity(TerritoryCredentials territoryCredentials) {
        if (territoryCredentials.getId() != null) {
            this.setUuid(territoryCredentials.getId().getId());
        }
        this.setCreatedTime(territoryCredentials.getCreatedTime());
        if (territoryCredentials.getTerritoryId() != null) {
            this.territoryId = territoryCredentials.getTerritoryId().getId();
        }
        this.credentialsType = territoryCredentials.getCredentialsType();
        this.credentialsId = territoryCredentials.getCredentialsId();
        this.credentialsValue = territoryCredentials.getCredentialsValue();
    }

    @Override
    public TerritoryCredentials toData() {
        TerritoryCredentials territoryCredentials = new TerritoryCredentials(new TerritoryCredentialsId(this.getUuid()));
        territoryCredentials.setCreatedTime(createdTime);
        if (territoryId != null) {
            territoryCredentials.setTerritoryId(new TerritoryId(territoryId));
        }
        territoryCredentials.setCredentialsType(credentialsType);
        territoryCredentials.setCredentialsId(credentialsId);
        territoryCredentials.setCredentialsValue(credentialsValue);
        return territoryCredentials;
    }

}
