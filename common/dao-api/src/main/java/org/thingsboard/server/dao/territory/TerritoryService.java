package org.thingsboard.server.dao.territory;

import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;

public interface TerritoryService {

    Territory saveTerritory(Territory territory);

}
