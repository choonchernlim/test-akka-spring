package com.github.choonchernlim.testAkkaSpring.config;

import akka.actor.ActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.github.choonchernlim.testAkkaSpring")
public class SpringConfig {
    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("System");
    }
}
