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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.security.TerritoryCredentials;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.model.sql.TerritoryCredentialsEntity;
import org.thingsboard.server.dao.sql.JpaAbstractDao;
import org.thingsboard.server.dao.territory.TerritoryCredentialsDao;

import java.util.UUID;

/**
 * Created by Valerii Sosliuk on 5/6/2017.
 */
@Component
public class JpaTerritoryCredentialsDao extends JpaAbstractDao<TerritoryCredentialsEntity, TerritoryCredentials> implements TerritoryCredentialsDao {

    @Autowired
    private TerritoryCredentialsRepository territoryCredentialsRepository;

    @Override
    public TerritoryCredentials findByTerritoryId(TenantId tenantId, UUID territoryId) {
        return DaoUtil.getData(territoryCredentialsRepository.findByDeviceId(territoryId));
    }

    @Override
    public TerritoryCredentials findByCredentialsId(TenantId tenantId, String credentialsId) {
        return DaoUtil.getData(territoryCredentialsRepository.findByCredentialsId(credentialsId));
    }

    @Override
    protected Class<TerritoryCredentialsEntity> getEntityClass() {
        return TerritoryCredentialsEntity.class;
    }

    @Override
    protected CrudRepository<TerritoryCredentialsEntity, UUID> getCrudRepository() {
        return territoryCredentialsRepository;
    }
}
