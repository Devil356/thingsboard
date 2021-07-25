package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.Building;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JsonBinaryType;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@MappedSuperclass
public abstract class AbstractBuildingEntity extends AbstractEntity<Building> {
    @Column(name = ModelConstants.BUILDING_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.BUILDING_NAME_PROPERTY)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "territory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TerritoryEntity territoryEntity;

    @OneToMany(mappedBy = "buildingEntity", fetch = FetchType.EAGER)
    @OrderBy("name DESC")
    private List<RoomEntity> roomEntityList;

    public AbstractBuildingEntity(Building building) {
        super(building);
    }

    public AbstractBuildingEntity() {

    }
}
