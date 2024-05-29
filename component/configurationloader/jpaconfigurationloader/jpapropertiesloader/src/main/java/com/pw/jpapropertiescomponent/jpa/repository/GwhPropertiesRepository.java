package com.pw.jpapropertiescomponent.jpa.repository;

import com.pw.jpapropertiescomponent.jpa.model.GwhPropertiesEntity;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhPropertiesRepository extends JpaRepository<GwhPropertiesEntity, Long>{

    List<GwhPropertiesEntity> findByProfile(String profile);

    List<GwhPropertiesEntity> findByProfileAndRegion(String profile, String region);

}
