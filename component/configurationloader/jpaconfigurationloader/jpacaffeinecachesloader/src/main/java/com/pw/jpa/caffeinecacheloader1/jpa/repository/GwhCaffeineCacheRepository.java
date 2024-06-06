package com.pw.jpa.caffeinecacheloader1.jpa.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pw.jpa.caffeinecacheloader1.jpa.model.GwhCaffeineCacheEntity;

import java.util.List;


@Repository
@ConditionalOnProperty(value = "gwh.framework.load.caches.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public interface GwhCaffeineCacheRepository extends JpaRepository<GwhCaffeineCacheEntity, Long> {

    List<GwhCaffeineCacheEntity> findByProfile(String profile);

}
