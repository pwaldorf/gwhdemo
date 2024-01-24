package com.pw.gwhcore.jpa.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pw.gwhcore.jpa.model.RouteTemplateEntity;


@Repository
@ConditionalOnProperty(value = "gtw.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public interface RouteTemplateRepository extends JpaRepository<RouteTemplateEntity, Long> {    

}
