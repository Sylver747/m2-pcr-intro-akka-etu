package m2dl.pcr.akka.helloworld4;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HelloActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public HelloActor() {
        log.info("Hello actor just appeared");
    }

    @Override
    public void onReceive(Object o) throws Exception {
        log.info("Hello actor saying : " + (String) o);
    }

}
