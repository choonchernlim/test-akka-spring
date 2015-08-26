package com.github.choonchernlim.testAkkaSpring;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.github.choonchernlim.testAkkaSpring.actor.Master;
import com.github.choonchernlim.testAkkaSpring.akkaspring.SpringProps;
import com.github.choonchernlim.testAkkaSpring.config.SpringConfig;
import com.github.choonchernlim.testAkkaSpring.message.ResultMessage;
import com.github.choonchernlim.testAkkaSpring.message.StartMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
public final class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private final ActorSystem actorSystem;
    private final SpringProps springProps;

    @Autowired
    public Main(final ActorSystem actorSystem, final SpringProps springProps) {
        this.actorSystem = actorSystem;
        this.springProps = springProps;
    }

    public static void main(String[] args) throws Exception {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        final Main main = context.getBean(Main.class);
        main.run();
    }

    public void run() {
        final ActorRef master = actorSystem.actorOf(springProps.create(Master.class), "master");
        final Timeout timeout = new Timeout(3, TimeUnit.SECONDS);
        final Future<Object> future = Patterns.ask(master, new StartMessage(), timeout);

        try {
            @SuppressWarnings("unchecked")
            final Collection<ResultMessage> results = (Collection<ResultMessage>) Await.result(future,
                                                                                               timeout.duration());
            for (ResultMessage result : results) {
                LOGGER.debug(result.getValue());
            }

            LOGGER.debug("DONE");
        }
        catch (Exception e) {
            actorSystem.shutdown();
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }
}
