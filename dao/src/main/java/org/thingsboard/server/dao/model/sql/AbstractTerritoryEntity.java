package org.thingsboard.server.dao.model.sql;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JacksonUtil;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class AbstractTerritoryEntity<T extends Territory> extends BaseSqlEntity<T> {

    @Column(name = ModelConstants.TERRITORY_TENANT_ID_PROPERTY)
    private UUID tenantId;

    @Column(name = ModelConstants.TERRITORY_NAME_PROPERTY)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "territoryEntity")
    @OrderBy("name DESC")
    List<BuildingEntity> buildingEntities;

    public AbstractTerritoryEntity() {
        super();
    }

    public AbstractTerritoryEntity(Territory territory) {
        if (territory.getId() != null) {
            this.setUuid(territory.getUuidId());
        }
        this.setCreatedTime(territory.getCreatedTime());
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

}
