package org.thingsboard.server.dao.territory;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.Dao;
import org.thingsboard.server.dao.TenantEntityDao;

public interface TerritoryJpaRepository extends JpaRepository<Territory, Long> {

    /**
     * Save or update territory object
     *
     * @param territory the territory object
     * @return saved territory object
     */
    @SuppressWarnings("unchecked")
    @NotNull
    Territory save(@NotNull Territory territory);
}
