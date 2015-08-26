package com.github.choonchernlim.testAkkaSpring.actor;

import akka.actor.UntypedActor;
import com.github.choonchernlim.testAkkaSpring.message.ResultMessage;
import com.github.choonchernlim.testAkkaSpring.message.WorkMessage;
import com.github.choonchernlim.testAkkaSpring.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public final class Worker extends UntypedActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    private final MyService myService;

    @Autowired
    public Worker(final MyService myService) {
        LOGGER.debug("CREATING WORKER...");
        this.myService = myService;
    }

    @Override
    public void onReceive(final Object message) throws Exception {
        if (message instanceof WorkMessage) {
            final String value = ((WorkMessage) message).getValue();

            LOGGER.debug("RECEIVING WORK... " + value);

            getSender().tell(new ResultMessage(myService.run(value)), getSelf());
        }
        else {
            unhandled(message);
        }
    }
}
