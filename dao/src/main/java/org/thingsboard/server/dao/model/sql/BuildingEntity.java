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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.Building;
import org.thingsboard.server.common.data.Device;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JsonBinaryType;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name = ModelConstants.BUILDING_COLUMN_FAMILY_NAME)
public class BuildingEntity extends AbstractBuildingEntity {
    public BuildingEntity() {
        super();
    }

    public BuildingEntity(Building building) {
        super(building);
    }

    @Override
    public Building toData() {
        return super.toData();
    }
}
