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

import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.DeviceCredentials;
import org.thingsboard.server.common.data.security.TerritoryCredentials;
import org.thingsboard.server.dao.Dao;

import java.util.UUID;

/**
 * The Interface TerritoryCredentialsDao.
 */
public interface TerritoryCredentialsDao extends Dao<TerritoryCredentials> {

    /**
     * Save or update territory credentials object
     *
     * @param tenantId the territory tenant id
     * @param territoryCredentials the territory credentials object
     * @return saved territory credentials object
     */
    TerritoryCredentials save(TenantId tenantId, TerritoryCredentials territoryCredentials);

    /**
     * Find territory credentials by territory id.
     *
     * @param territoryId the territory id
     * @return the territory credentials object
     */
    TerritoryCredentials findByTerritoryId(TenantId tenantId, UUID territoryId);

    /**
     * Find territory credentials by credentials id.
     *
     * @param credentialsId the credentials id
     * @return the territory credentials object
     */
    TerritoryCredentials findByCredentialsId(TenantId tenantId, String credentialsId);

}
