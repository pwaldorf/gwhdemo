package com.pw.gwhservice1.gwhposting;

import com.pw.gwhservice1.model.Entity;

public interface BuzService {
    Entity process(Entity msg) throws RuntimeException;
}
