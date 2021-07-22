package org.thingsboard.server.dao.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsboard.server.dao.model.sql.BuildingEntity;
import org.thingsboard.server.dao.territory.TerritoryJpaRepository;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingJpaRepository buildingJpaRepository;

    @Autowired
    private TerritoryJpaRepository territoryJpaRepository;

    @Override
    public BuildingEntity save(BuildingEntity buildingEntity, Integer territoryId) {
        buildingEntity.setTerritoryEntity(territoryJpaRepository.getOne(territoryId));
        return buildingJpaRepository.save(buildingEntity);
    }
}
