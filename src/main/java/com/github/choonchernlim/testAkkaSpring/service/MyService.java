package com.github.choonchernlim.testAkkaSpring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class MyService {
    private static Logger LOGGER = LoggerFactory.getLogger(MyService.class);

    public MyService() {
        LOGGER.debug("CREATING SERVICE...");
    }

    public String run(final String value) {
        return "RESULT FOR " + value;
    }
}
