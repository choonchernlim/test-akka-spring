package com.github.choonchernlim.testAkkaSpring.akkaspring;

import akka.actor.Actor;
import akka.actor.Props;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Instead of manually passing `applicationContext` to `SpringActorProducer`, this service will automatically
 * pass this variable to it.
 */
@Component
public final class SpringProps {
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringProps(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props create(final Class<? extends Actor> actorClass) {
        return Props.create(SpringActorProducer.class, applicationContext, actorClass);
    }
}
