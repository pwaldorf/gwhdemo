package com.pw.gwhcore.gwhroutetemplates;

import java.util.List;

import com.pw.gwhcore.model.GwhRouteTemplate;

public interface GwhRouteTemplates {

    List<GwhRouteTemplate> getAllTemplates();

    List<GwhRouteTemplate> getTemplatesByProfile();

    List<GwhRouteTemplate> getTemplatesByProfileAndRegion();
}
