package com.pw.jpa.dataformatloader1.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pw.jpa.dataformatloader1.jpa.model.GwhDataFormatEntity;

@Repository
public interface GwhDataFormatRepository extends JpaRepository<GwhDataFormatEntity, Long> {

    List<GwhDataFormatEntity> findByProfile(String profile);

    List<GwhDataFormatEntity> findByProfileAndRegionAndVersion(String profile, String region, String version);

}
