package org.thingsboard.server.dao.territory;

import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.model.sql.TerritoryEntity;

public interface TerritoryService {
    Territory saveTerritory(TerritoryEntity territory);
}
