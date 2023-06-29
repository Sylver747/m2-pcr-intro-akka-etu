package m2dl.pcr.akka.helloworld4;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class EmetterActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public ActorRef helloActor;
    public ActorRef goodbyeActor;

    public EmetterActor() {
        log.info("EmetterActor constructor");
        helloActor = getContext().actorOf(Props.create(HelloActor.class), "hello-actor");
        goodbyeActor = getContext().actorOf(Props.create(GoodbyeActor.class), "goodbye-actor");
    }

    Procedure<Object> send = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof String) {
                if (((String) msg).toLowerCase().trim().contains("hello")) {
                    helloActor.tell(msg, null);
                } else if (((String) msg).toLowerCase().trim().contains("good bye")) {
                    goodbyeActor.tell(msg, null);
                } else {
                    unhandled(msg);
                }
            } else {
                unhandled(msg);
            }
        }
    };


    @Override
    public void onReceive(Object o) throws Exception {
        send.apply(o);
    }
}
