package com.pw.jpa.routeloader1.jpa.repository;

import java.util.List;

import com.pw.jpa.routeloader1.jpa.model.GwhRouteEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@ConditionalOnProperty(value = "gwh.framework.load.routes.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhRouteRepository extends JpaRepository<GwhRouteEntity, Long> {

    List<GwhRouteEntity> findByProfile(String profile);

}
