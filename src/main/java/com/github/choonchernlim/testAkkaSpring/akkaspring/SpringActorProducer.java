package com.github.choonchernlim.testAkkaSpring.akkaspring;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

public final class SpringActorProducer implements IndirectActorProducer {
    private final ApplicationContext applicationContext;
    private final Class<? extends Actor> actorClass;

    public SpringActorProducer(final ApplicationContext applicationContext, final Class<? extends Actor> actorClass) {
        this.applicationContext = applicationContext;
        this.actorClass = actorClass;
    }

    @Override
    public Actor produce() {
        return applicationContext.getBean(actorClass);
    }

    @Override
    public Class<? extends Actor> actorClass() {
        return actorClass;
    }
}
