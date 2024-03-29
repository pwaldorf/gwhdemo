package com.pw.gwhcore.jpa.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "routes")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "profile")
    private String profile;    

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "route")
    private String route;    
    
}
