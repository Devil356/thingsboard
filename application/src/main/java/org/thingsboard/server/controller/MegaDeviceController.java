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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.Megadevice;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.dao.model.sql.MegaDeviceEntity;
import org.thingsboard.server.dao.model.sql.RoomEntity;
import org.thingsboard.server.queue.util.TbCoreComponent;

@RestController
@TbCoreComponent
@RequestMapping("/api")
public class MegaDeviceController extends BaseController {

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/{id}/megadevices", method = RequestMethod.POST)
    @ResponseBody
    public Megadevice saveMegaDevice(@RequestBody Megadevice entity, @PathVariable int id) throws ThingsboardException {
        try {
            entity.setTenantId(getCurrentUser().getTenantId());
            entity.setCreatedTime(0L);
            Megadevice e = checkNotNull(megaDeviceService.save(entity, id));
            return e;
        } catch (Exception e) {

            throw handleException(e);
        }
    }

}
