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
package org.thingsboard.server.dao.territory;

import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.Dao;
import org.thingsboard.server.dao.TenantEntityDao;
import org.thingsboard.server.dao.model.sql.TerritoryEntity;

import java.util.UUID;

/**
 * The Interface TerritoryDao.
 *
 */
public interface TerritoryDao extends Dao<TerritoryEntity>, TenantEntityDao {
    /**
     * Save or update territory object
     *
     * @param territory the territory object
     * @return saved territory object
     */
    TerritoryEntity save(TenantId tenantId, Territory territory);

    TerritoryEntity findTerritoryByTenantIdAndId(TenantId tenantId, UUID id);
}
