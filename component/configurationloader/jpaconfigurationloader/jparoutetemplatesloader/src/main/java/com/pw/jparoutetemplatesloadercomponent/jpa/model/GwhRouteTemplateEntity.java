package com.pw.jparoutetemplatesloadercomponent.jpa.model;

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
@Table(name = "route_template_params")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "profile")
    private String profile;

    @Column(name = "route_id")
    private String routeId;

    @Column(name = "template_param_name")
    private String templateParamName;

    @Column(name = "template_param_value")
    private String templateParamValue;

}
