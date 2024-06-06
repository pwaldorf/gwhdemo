package com.pw.jpa.caffeinecacheloader1.jpa.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cache_configuration")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "profile")
    private String profile;

    @Column(name = "region")
    private String region;

    @Column(name = "cache_name")
    private String cacheName;

    @Column(name = "cache_initial_capacity")
    private Integer cacheInitialCapacity;

    @Column(name = "cache_maximum_size")
    private Integer cacheMaximumCapacity;

    @Column(name = "cache_eviction_type")
    private String cacheEvictionType;

    @Column(name = "cache_expire_after_access_time")
    private Integer cacheExpireAfterAccessTime;

    @Column(name = "cache_expire_after_write_time")
    private Integer cacheExpireAfterWriteTime;

    @Column(name = "cache_stats_enabled")
    private Boolean cacheStatsEnabled;

    @Column(name = "cache_stats_name")
    private String cacheStatsName;

    @Column(name = "cache_loader_name")
    private String cacheLoaderName;
}
