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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JsonBinaryType;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name = ModelConstants.BUILDING_COLUMN_FAMILY_NAME)
public class BuildingEntity {
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Column(name = ModelConstants.ID_PROPERTY)
    private Integer id;

    @Column(name = ModelConstants.CREATED_TIME_PROPERTY)
    private long createdTime;

    @Column(name = ModelConstants.BUILDING_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.BUILDING_NAME_PROPERTY)
    private String name;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "territory_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Territory territory;

    @OneToMany(mappedBy = "buildingEntity", fetch = FetchType.EAGER)
    @OrderBy("name DESC")
    private List<RoomEntity> roomEntityList;

    public BuildingEntity() {
    }

    public BuildingEntity(long createdTime, UUID tenantId, String name) {
        this.createdTime = createdTime;
        this.tenantId = tenantId;
        this.name = name;
    }

    public void setCreatedTime(long createdTime) {
        if (createdTime > 0) {
            this.createdTime = createdTime;
        }
    }
}
