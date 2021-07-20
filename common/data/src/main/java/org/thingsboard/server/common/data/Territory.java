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
