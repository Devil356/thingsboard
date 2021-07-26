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
package org.thingsboard.server.controller;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.dao.model.sql.TerritoryEntity;
import org.thingsboard.server.queue.util.TbCoreComponent;

@RestController
@TbCoreComponent
@RequestMapping("/api")
public class TerritoryController extends BaseController {

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "old/territories", method = RequestMethod.POST)
    @ResponseBody
    public Territory saveTerritory(@RequestBody Territory territory) throws ThingsboardException {
        try {
            territory.setTenantId(getCurrentUser().getTenantId());
            territory.setCreatedTime(0L);
            TerritoryEntity savedTerritory = checkNotNull(territoryService.saveTerritory(territory));
            return savedTerritory.toData();
        } catch (Exception e) {

            throw handleException(e);
        }
    }

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/territories", method = RequestMethod.POST)
    @ResponseBody
    public Territory saveTerritoryWithAccessToken(
            @RequestBody Territory entity,
            @RequestParam(name = "accessToken", required = false) String accessToken
    ) throws ThingsboardException {
        try {
            entity.setTenantId(getCurrentUser().getTenantId());
            entity.setCreatedTime(0L);
            TerritoryEntity savedTerritory = checkNotNull(territoryService.saveTerritoryWithAccessToken(entity, accessToken));
            return savedTerritory.toData();
        } catch (Exception e) {

            throw handleException(e);
        }
    }

}
