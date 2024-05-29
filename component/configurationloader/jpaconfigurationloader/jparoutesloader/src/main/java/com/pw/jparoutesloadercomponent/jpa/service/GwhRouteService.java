package com.pw.jparoutesloadercomponent.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.pw.jparoutesloadercomponent.jpa.model.GwhRouteEntity;
import com.pw.jparoutesloadercomponent.jpa.repository.GwhRouteRepository;


@Service
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
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
