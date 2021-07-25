package org.thingsboard.server.dao.sql.territory;

import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thingsboard.server.common.data.Territory;
import org.thingsboard.server.common.data.id.TenantId;
import org.thingsboard.server.dao.DaoUtil;
import org.thingsboard.server.dao.sql.device.DeviceRepository;
import org.thingsboard.server.dao.territory.TerritoryDao;

import java.util.List;
import java.util.UUID;

@Component
public class JpaTerritoryDao implements TerritoryDao {

    @Autowired
    private TerritoryRepository territoryRepository;

    @Override
    public List<Territory> find(TenantId tenantId) {
        return null;
    }

    @Override
    public Territory findById(TenantId tenantId, UUID id) {
        return null;
    }

    @Override
    public ListenableFuture<Territory> findByIdAsync(TenantId tenantId, UUID id) {
        return null;
    }

    @Override
    public boolean removeById(TenantId tenantId, UUID id) {
        return false;
    }

    @Override
    public Long countByTenantId(TenantId tenantId) {
        return null;
    }

    @Override
    public Territory save(TenantId tenantId, Territory territory) {
        return null;
    }

    @Override
    public Territory findTerritoryByTenantIdAndId(TenantId tenantId, UUID id) {
        return DaoUtil.getData(territoryRepository.findByTenantIdAndId(tenantId.getId(), id));
    }
}
