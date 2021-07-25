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
import org.thingsboard.server.common.data.Room;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractTerritoryEntity extends AbstractEntity<Territory> {

    @Column(name = ModelConstants.TERRITORY_TENANT_ID_PROPERTY)
    private UUID tenantId;

    @Column(name = ModelConstants.TERRITORY_NAME_PROPERTY)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "territoryEntity")
    @OrderBy("name DESC")
    List<BuildingEntity> buildingEntities;

    public AbstractTerritoryEntity(Territory territory) {
        super(territory);
    }

    public AbstractTerritoryEntity() {

    }
}
