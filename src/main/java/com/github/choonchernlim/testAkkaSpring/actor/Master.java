package com.github.choonchernlim.testAkkaSpring.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import com.github.choonchernlim.testAkkaSpring.akkaspring.SpringProps;
import com.github.choonchernlim.testAkkaSpring.message.ResultMessage;
import com.github.choonchernlim.testAkkaSpring.message.StartMessage;
import com.github.choonchernlim.testAkkaSpring.message.WorkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Scope("prototype")
public final class Master extends UntypedActor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Master.class);
    private static final Integer TOTAL_WORK = 5;
    private final ActorRef workerRouter;
    private ActorRef originator;
    private Collection<ResultMessage> results = new ArrayList<ResultMessage>();

    @Autowired
    public Master(final SpringProps springProps) {
        LOGGER.debug("CREATING MASTER...");
        this.workerRouter = getContext().actorOf(springProps.create(Worker.class)
                                                         .withRouter(new RoundRobinPool(5)),
                                                 "workerRouter");
    }

    @Override
    public void onReceive(final Object message) {
        if (message instanceof StartMessage) {
            LOGGER.debug("STARTING...");
            originator = getSender();

            for (int i = 1; i <= TOTAL_WORK; ++i) {
                workerRouter.tell(new WorkMessage("WORK " + i), getSelf());
            }
        }
        else if (message instanceof ResultMessage) {
            ResultMessage resultMessage = (ResultMessage) message;

            LOGGER.debug("RECEIVING RESULT..." + resultMessage.getValue());

            results.add(resultMessage);

            if (results.size() == TOTAL_WORK) {
                originator.tell(results, getSelf());

                getContext().stop(getSelf());
                getContext().system().shutdown();
            }
        }
        else {
            unhandled(message);
        }
    }
}
