package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.ModelConstants;
import org.thingsboard.server.dao.util.mapping.JsonBinaryType;
import org.thingsboard.server.dao.util.mapping.JsonStringType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Table(name = ModelConstants.TERRITORY_COLUMN_FAMILY_NAME)
public class TerritoryEntity extends AbstractTerritoryEntity<Territory> {

    public TerritoryEntity() {
        super();
    }

    public TerritoryEntity(Territory territory) {
        super(territory);
    }

    @Override
    public Territory toData() {
        return super.toTerritory();
    }
}
