package com.pw.routemanagement1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GwhListRoute {

    private String routeId;
    private String routeGroup;
    private String routeType;
    private String routeService;
    private String routeEndpoint;
}
