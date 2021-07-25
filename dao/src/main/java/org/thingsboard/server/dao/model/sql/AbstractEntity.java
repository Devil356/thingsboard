package org.thingsboard.server.dao.model.sql;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.thingsboard.server.common.data.AbstractObject;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.BaseSqlEntity;
import org.thingsboard.server.dao.model.ModelConstants;
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
public abstract class AbstractEntity<T extends AbstractObject> extends BaseSqlEntity<T> {
    private UUID tenantId;

    private String name;

    public AbstractEntity() {
        super();
    }

    public AbstractEntity(AbstractObject abstractObject) {
        if (abstractObject.getId() != null) {
            this.setUuid(abstractObject.getUuidId());
        }
        this.setCreatedTime(abstractObject.getCreatedTime());
        if (abstractObject.getTenantId() != null) {
            this.tenantId = abstractObject.getTenantId().getId();
        }
        this.name = abstractObject.getName();
    }

    public AbstractEntity(AbstractEntity abstractEntity) {
        this.setId(abstractEntity.getId());
        this.setCreatedTime(abstractEntity.getCreatedTime());
        this.tenantId = abstractEntity.getTenantId();
        this.name = abstractEntity.getName();
    }
    @Override
    public T toData() {
        return null;
    }
}
