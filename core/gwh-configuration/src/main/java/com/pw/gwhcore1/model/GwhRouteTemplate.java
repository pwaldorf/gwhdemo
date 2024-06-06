package com.pw.gwhcore1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GwhRouteTemplate {

    private String routeId;

    private String templateParamName;

    private String templateParamValue;
}
