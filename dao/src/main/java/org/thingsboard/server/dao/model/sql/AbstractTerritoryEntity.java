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
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.TerritoryId;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.model.SearchTextEntity;
import org.thingsboard.server.dao.util.mapping.JsonBinaryType;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
public abstract class AbstractTerritoryEntity<T extends Territory> extends BaseSqlEntity<T> implements SearchTextEntity<T> {

    @Column(name = ModelConstants.TERRITORY_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.TERRITORY_NAME_PROPERTY)
    private String name;

    public AbstractTerritoryEntity() {
        super();
    }

    public AbstractTerritoryEntity(Territory territory) {
        if (territory.getId() != null) {
            this.setUuid(territory.getUuidId());
        }
        if (territory.getTenantId() != null) {
            this.tenantId = territory.getTenantId().getId();
        }
        this.name = territory.getName();
    }

    public AbstractTerritoryEntity(TerritoryEntity territoryEntity) {
        this.setId(territoryEntity.getId());
        this.setCreatedTime(territoryEntity.getCreatedTime());
        this.tenantId = territoryEntity.getTenantId();
        this.name = territoryEntity.getName();
    }

    @Override
    public String getSearchTextSource() {
        return name;
    }

    @Override
    public void setSearchText(String searchText) {

    }

    protected Territory toTerritory() {
        Territory territory = new Territory(new TerritoryId(getUuid()));
        territory.setCreatedTime(createdTime);
        if (tenantId != null) {
            territory.setTenantId(new TenantId(tenantId));
        }
        territory.setName(name);
        return territory;
    }
}
