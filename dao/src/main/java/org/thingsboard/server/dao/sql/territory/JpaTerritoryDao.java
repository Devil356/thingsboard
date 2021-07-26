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
package org.thingsboard.server.dao.sql.territory;

import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.model.sql.TerritoryEntity;
import org.thingsboard.server.dao.sql.device.DeviceRepository;
import org.thingsboard.server.dao.territory.TerritoryDao;

import java.util.List;
import java.util.UUID;

@Component
public class JpaTerritoryDao implements TerritoryDao {

    @Autowired
    private TerritoryRepository territoryRepository;

    @Override
    public List<TerritoryEntity> find(TenantId tenantId) {
        return null;
    }

    @Override
    public TerritoryEntity findById(TenantId tenantId, UUID id) {
        return null;
    }

    @Override
    public ListenableFuture<TerritoryEntity> findByIdAsync(TenantId tenantId, UUID id) {
        return null;
    }

    @Override
    public TerritoryEntity save(TenantId tenantId, TerritoryEntity entity) {
        return null;
    }

    @Override
    public boolean removeById(TenantId tenantId, UUID id) {
        return false;
    }

    @Override
    public Long countByTenantId(TenantId tenantId) {
        return null;
    }

    @Override
    public TerritoryEntity save(TenantId tenantId, Territory territory) {
        return null;
    }

    @Override
    public TerritoryEntity findTerritoryByTenantIdAndId(TenantId tenantId, UUID id) {
        return new TerritoryEntity(DaoUtil.getData(territoryRepository.findByTenantIdAndId(tenantId.getId(), id)));
    }
}
