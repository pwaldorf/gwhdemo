package com.pw.jpapropertiescomponent.jpa.repository;

import com.pw.jpapropertiescomponent.jpa.model.GwhPropertiesEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GwhPropertiesRepository extends JpaRepository<GwhPropertiesEntity, Long>{

    List<GwhPropertiesEntity> findByProfile(String profile);

    List<GwhPropertiesEntity> findByProfileAndRegion(String profile, String region);

}
