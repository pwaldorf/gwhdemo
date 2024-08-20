package com.pw.utils1.keywordresolver.impl;

import org.springframework.stereotype.Component;

import com.pw.utils1.keywordresolver.Resolver;

@Component
public class MqResourceResolver implements Resolver {

    // get MQ Queue Name from GwhMqResource
    // key word pattern $(MQRESOURCE.RESOURCENAME)
    // private GwhDataResource<GwhMqResource> gwhResource;

    @Override
    public String getResolverKeyword() {
        return "MQRESOURCE";
    }

    // keyword is resource name to look up to get MQ Queue Name
    @Override
    public String resolveValue(String keyword) {
        return "TEST_RESOURCE";
    }

}
