package com.pw.gwhcore1.gwhroutetemplates;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pw.api1.configuration.GwhRouteTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Scope("prototype")
@Data
@AllArgsConstructor
public class GwhDefaultRouteTemplate implements GwhRouteTemplate {

    private String routeId;
    private String templateParamName;
    private String templateParamValue;

}
