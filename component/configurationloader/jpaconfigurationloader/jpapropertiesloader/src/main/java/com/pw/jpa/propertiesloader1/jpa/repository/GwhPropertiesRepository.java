package com.pw.jpa.propertiesloader1.jpa.repository;

import com.pw.jpa.propertiesloader1.jpa.model.GwhPropertiesEntity;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "gwh.framework.load.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhPropertiesRepository extends JpaRepository<GwhPropertiesEntity, Long>{

    List<GwhPropertiesEntity> findByProfile(String profile);
    List<GwhPropertiesEntity> findByProfileAndRegion(String profile, String region);

}
