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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.common.data.Tenant;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.common.data.id.TerritoryId;
import org.thingsboard.server.common.data.security.TerritoryCredentials;
import org.thingsboard.server.common.data.security.TerritoryCredentialsType;
import org.thingsboard.server.common.data.tenant.profile.DefaultTenantProfileConfiguration;
import org.thingsboard.server.dao.entity.AbstractEntityService;
import org.thingsboard.server.dao.exception.DataValidationException;
import org.thingsboard.server.dao.model.sql.TerritoryEntity;
import org.thingsboard.server.dao.service.DataValidator;
import org.thingsboard.server.dao.tenant.TbTenantProfileCache;
import org.thingsboard.server.dao.tenant.TenantDao;

import static org.thingsboard.server.dao.service.Validator.validateId;

@Service
@Slf4j
public class TerritoryServiceImpl extends AbstractEntityService implements TerritoryService {
    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String INCORRECT_TERRITORY_ID = "Incorrect territoryId ";

    @Autowired
    private TerritoryJpaRepository territoryJpaRepository;

    @Autowired
    @Lazy
    private TbTenantProfileCache tenantProfileCache;

    @Autowired
    private TerritoryDao territoryDao;

    @Autowired
    private TenantDao tenantDao;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TerritoryCredentialsService territoryCredentialsService;

    @Override
    public TerritoryEntity saveTerritory(Territory territory) {
        TerritoryEntity territoryEntity = new TerritoryEntity(territory);
        log.trace("Executing saveTerritory [{}]", territoryEntity);
        return territoryJpaRepository.save(territoryEntity);
    }

    @Override
    public TerritoryEntity saveTerritoryWithAccessToken(Territory entity, String accessToken) {
        return doSaveTerritory(entity, accessToken);
    }

    @Override
    public TerritoryEntity findTerritoryById(TenantId tenantId, TerritoryId territoryId) {
        log.trace("Executing findTerritoryById [{}]", territoryId);
        validateId(territoryId, INCORRECT_TERRITORY_ID + territoryId);
        if (TenantId.SYS_TENANT_ID.equals(tenantId)) {
            return territoryDao.findById(tenantId, territoryId.getId());
        } else {
            return territoryDao.findTerritoryByTenantIdAndId(tenantId, territoryId.getId());
        }
    }

    private TerritoryEntity doSaveTerritory(Territory territory, String accessToken) {
        log.trace("Executing saveTerritory [{}]", territory);
        territoryValidator.validate(territory, Territory::getTenantId);
        TerritoryEntity savedTerritory;
        try {
            savedTerritory = territoryDao.save(territory.getTenantId(), territory);
        } catch (Exception t) {
            ConstraintViolationException e = extractConstraintViolationException(t).orElse(null);
            if (e != null && e.getConstraintName() != null && e.getConstraintName().equalsIgnoreCase("territory_name_unq_key")) {
                throw new DataValidationException("Territory with such name already exists!");
            } else {
                throw t;
            }
        }
        if (territory.getId() == null) {
            TerritoryCredentials territoryCredentials = new TerritoryCredentials();
            territoryCredentials.setTerritoryId(new TerritoryId(savedTerritory.toData().getUuidId()));
            territoryCredentials.setCredentialsType(TerritoryCredentialsType.ACCESS_TOKEN);
            territoryCredentials.setCredentialsId(!StringUtils.isEmpty(accessToken) ? accessToken : RandomStringUtils.randomAlphanumeric(20));
            territoryCredentialsService.createTerritoryCredentials(territory.getTenantId(), territoryCredentials);
        }
        return savedTerritory;
    }

    private DataValidator<Territory> territoryValidator =
            new DataValidator<Territory>() {

                @Override
                protected void validateCreate(TenantId tenantId, Territory territory) {
                    DefaultTenantProfileConfiguration profileConfiguration =
                            (DefaultTenantProfileConfiguration)tenantProfileCache.get(tenantId).getProfileData().getConfiguration();
                    long maxTerritories = profileConfiguration.getMaxTerritories();
                    validateNumberOfEntitiesPerTenant(tenantId, territoryDao, maxTerritories, EntityType.TERRITORY);
                }

                @Override
                protected void validateUpdate(TenantId tenantId, Territory territory) {
                    TerritoryEntity old = territoryDao.findById(territory.getTenantId(), territory.getId().getId());
                    if (old == null) {
                        throw new DataValidationException("Can't update non existing territory!");
                    }
                }

                @Override
                protected void validateDataImpl(TenantId tenantId, Territory territory) {
                    if (StringUtils.isEmpty(territory.getName()) || territory.getName().trim().length() == 0) {
                        throw new DataValidationException("Territory name should be specified!");
                    }
                    if (territory.getTenantId() == null) {
                        throw new DataValidationException("Territory should be assigned to tenant!");
                    } else {
                        Tenant tenant = tenantDao.findById(territory.getTenantId(), territory.getTenantId().getId());
                        if (tenant == null) {
                            throw new DataValidationException("Territory is referencing to non-existent tenant!");
                        }
                    }
                }
            };
}
