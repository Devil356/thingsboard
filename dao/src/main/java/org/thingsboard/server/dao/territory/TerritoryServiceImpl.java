package org.thingsboard.server.dao.territory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.dao.entity.AbstractEntityService;

@Service
@Slf4j
public class TerritoryServiceImpl extends AbstractEntityService implements TerritoryService {
    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String INCORRECT_TERRITORY_ID = "Incorrect territoryId ";

    @Autowired
    private TerritoryJpaRepository territoryJpaRepository;

    @Override
    public Territory saveTerritory(Territory territory) {
        log.trace("Executing saveTerritory [{}]", territory);
        return territoryJpaRepository.save(territory);
    }
}
