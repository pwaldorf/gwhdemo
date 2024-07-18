package com.pw.api1;

import com.pw.api1.model.Entity;

public interface BusinessService {
    void process(Entity msg) throws Exception;
}
