package org.thingsboard.server.dao.building;

import org.thingsboard.server.dao.model.sql.BuildingEntity;

public interface BuildingService {
    BuildingEntity save(BuildingEntity buildingEntity, Integer territoryId);
}
