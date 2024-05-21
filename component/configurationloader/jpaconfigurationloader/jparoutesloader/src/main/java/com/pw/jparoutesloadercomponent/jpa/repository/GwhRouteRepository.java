package com.pw.jparoutesloadercomponent.jpa.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pw.jparoutesloadercomponent.jpa.model.GwhRouteEntity;


@Repository
//@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhRouteRepository extends JpaRepository<GwhRouteEntity, Long> {

    List<GwhRouteEntity> findByProfile(String profile);

}
