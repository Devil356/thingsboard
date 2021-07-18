package org.thingsboard.server.common.data;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.TerritoryId;

@EqualsAndHashCode(callSuper = true)
@Slf4j
public class Territory extends SearchTextBasedWithAdditionalInfo<TerritoryId> implements HasName, HasTenantId {

    private static final long serialVersionUID = 1L;

    private TenantId tenantId;
    private String name;

    public Territory() {
        super();
    }

    public Territory(TerritoryId id) {
        super(id);
    }

    public Territory(Territory territory) {
        super(territory);
        this.tenantId = territory.getTenantId();
        this.name = territory.getName();
    }

    public void setTenantId(TenantId tenantId) {
        this.tenantId = tenantId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TenantId getTenantId() {
        return tenantId;
    }

    @Override
    public String getSearchText() {
        return getName();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Device [tenantId=");
        builder.append(tenantId);
        builder.append(", name=");
        builder.append(name);
        builder.append(", createdTime=");
        builder.append(createdTime);
        builder.append(", id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }
}
