package org.thingsboard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.exception.ThingsboardException;
import org.thingsboard.server.dao.territory.TerritoryService;
import org.thingsboard.server.queue.util.TbCoreComponent;

@RestController
@TbCoreComponent
@RequestMapping("/api")
public class TerritoryController extends BaseController {

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/territory", method = RequestMethod.POST)
    @ResponseBody
    public Territory saveTerritory(@RequestBody Territory territory) throws ThingsboardException {
        try {
            territory.setTenantId(getCurrentUser().getTenantId());
            Territory savedTerritory = checkNotNull(territoryService.saveTerritory(territory));
            return savedTerritory;
        } catch (Exception e) {

            throw handleException(e);
        }
    }

}
