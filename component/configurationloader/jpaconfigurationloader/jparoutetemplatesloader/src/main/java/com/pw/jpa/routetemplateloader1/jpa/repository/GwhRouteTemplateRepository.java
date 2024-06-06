package com.pw.jpa.routetemplateloader1.jpa.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pw.jpa.routetemplateloader1.jpa.model.GwhRouteTemplateEntity;

import java.util.List;


@Repository
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhRouteTemplateRepository extends JpaRepository<GwhRouteTemplateEntity, Long> {

    List<GwhRouteTemplateEntity> findByProfile(String profile);

}
