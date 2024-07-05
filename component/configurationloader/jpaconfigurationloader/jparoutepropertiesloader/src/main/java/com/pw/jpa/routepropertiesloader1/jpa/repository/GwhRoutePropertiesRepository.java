package com.pw.jpa.routepropertiesloader1.jpa.repository;

import com.pw.jpa.routepropertiesloader1.jpa.model.GwhRoutePropertiesEntity;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "gwh.framework.load.route.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhRoutePropertiesRepository extends JpaRepository<GwhRoutePropertiesEntity, Long>{

    List<GwhRoutePropertiesEntity> findByProfile(String profile);
    List<GwhRoutePropertiesEntity> findByProfileAndRegionAndVersion(String profile, String region, String version);

}
