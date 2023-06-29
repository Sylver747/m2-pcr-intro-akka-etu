package m2dl.pcr.akka.stringservices;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class Recepteur extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Recepteur() {
        log.info("New Recepteur created");

    }

    Procedure<Object> recepteur = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Message givenMsg) {
                log.info("Recepteur received: " + givenMsg.getMsg());
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        recepteur.apply(o);
    }
}
