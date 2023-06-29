package m2dl.pcr.akka.helloworld4;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class GoodbyeActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public GoodbyeActor() {
        log.info("Goodbye actor just appeared");
    }

    @Override
    public void onReceive(Object o) throws Exception {
        log.info("GoodBye actor saying : " + (String) o);
    }
}
