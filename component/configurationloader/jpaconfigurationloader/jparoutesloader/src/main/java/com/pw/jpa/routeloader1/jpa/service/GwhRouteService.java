package com.pw.jpa.routeloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.routeloader1.jpa.model.GwhRouteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.pw.jpa.routeloader1.jpa.repository.GwhRouteRepository;


@Service
@ConditionalOnProperty(value = "gwh.framework.load.routes.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteService {

    @Autowired
    GwhRouteRepository routeRepository;

    public List<GwhRouteEntity> getAllRoutes() {
        List<GwhRouteEntity> routes = routeRepository.findAll();

        if(routes.size() > 0) {
            return routes;
        } else {
            return new ArrayList<GwhRouteEntity>();
        }
    }

    public List<GwhRouteEntity> getByProfile(String profile) {
        List<GwhRouteEntity> routes = routeRepository.findByProfile(profile);

        if(routes.size() > 0) {
            return routes;
        } else {
            return new ArrayList<GwhRouteEntity>();
        }
    }
}
