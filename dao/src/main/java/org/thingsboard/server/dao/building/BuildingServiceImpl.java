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
package org.thingsboard.server.dao.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.Building;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.sql.BuildingEntity;
import org.thingsboard.server.dao.territory.TerritoryJpaRepository;

import java.util.UUID;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingJpaRepository buildingJpaRepository;

    @Autowired
    private TerritoryJpaRepository territoryJpaRepository;

    @Override
    public Building save(Building building, UUID territoryId) {
        building.setTerritory(territoryJpaRepository.getOne(territoryId));
        return buildingJpaRepository.save(building);
    }
}
