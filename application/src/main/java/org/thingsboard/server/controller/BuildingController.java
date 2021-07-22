package org.thingsboard.server.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.dao.model.sql.BuildingEntity;
import org.thingsboard.server.dao.model.sql.TerritoryEntity;
import org.thingsboard.server.queue.util.TbCoreComponent;

@RestController
@TbCoreComponent
@RequestMapping("/api")
public class BuildingController extends BaseController {

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/{id}/building", method = RequestMethod.POST)
    @ResponseBody
    public BuildingEntity saveBuilding(@RequestBody BuildingEntity entity, @PathVariable int id) throws ThingsboardException {
        try {
            entity.setTenantId(getCurrentUser().getTenantId().getId());
            entity.setCreatedTime(0L);
            BuildingEntity e = checkNotNull(buildingService.save(entity, id));
            return e;
        } catch (Exception e) {

            throw handleException(e);
        }
    }

}
