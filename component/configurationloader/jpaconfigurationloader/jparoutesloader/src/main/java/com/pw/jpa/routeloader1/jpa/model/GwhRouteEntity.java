package com.pw.jpa.routeloader1.jpa.model;

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
@Table(name = "routes")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
//@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "profile")
    private String profile;

    @Column(name = "region")
    private String region;

    @Column(name = "version")
    private String version;

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "route_type")
    private String routeType;

    @Column(name = "route")
    private String route;

}
