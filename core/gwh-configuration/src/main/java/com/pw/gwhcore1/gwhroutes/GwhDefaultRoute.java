package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRoute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GwhDefaultRoute implements GwhRoute {

    private String routeId;
    private String routeType;
    private String route;

}
