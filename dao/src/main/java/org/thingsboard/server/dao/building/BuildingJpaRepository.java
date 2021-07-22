package org.thingsboard.server.dao.building;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thingsboard.server.dao.model.sql.BuildingEntity;

@Repository
public interface BuildingJpaRepository extends JpaRepository<BuildingEntity, Integer> {

}
