package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.Building;
import org.thingsboard.server.common.data.Room;
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
public abstract class AbstractRoomEntity extends AbstractEntity<Room> {
    @Column(name = ModelConstants.ROOM_TENANT_ID_PROPERTY, columnDefinition = "uuid")
    private UUID tenantId;

    @Column(name = ModelConstants.ROOM_NAME_PROPERTY)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BuildingEntity buildingEntity;

    @OneToMany(mappedBy = "roomEntity", fetch = FetchType.EAGER)
    @OrderBy("name DESC")
    private List<MegaDeviceEntity> megaDeviceEntities;

    public AbstractRoomEntity(Room room) {
        super(room);
    }

    public AbstractRoomEntity() {

    }
}
