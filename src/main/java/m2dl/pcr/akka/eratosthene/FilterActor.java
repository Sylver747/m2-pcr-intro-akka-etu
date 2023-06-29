package m2dl.pcr.akka.eratosthene;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

import java.util.ArrayList;
import java.util.List;


public class FilterActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    private int filterValue;
    List<ActorRef> filters;

    public FilterActor(int value) {
        log.info("New Cribble created");
        filterValue = value;
        filters = new ArrayList<ActorRef>();
    }

    Procedure<Object> filter = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Integer) {
                Integer givenValue = (Integer) msg;
                if (givenValue % filterValue != 0 && filters.size() == 0) {
                    ActorRef ref = getContext().actorOf(Props.create(FilterActor.class, givenValue), "filter-actor-" + givenValue);
                    filters.add(ref);
                    log.info("New prime number has been added : " + givenValue);
                } else if (givenValue % filterValue != 0 && filters.size() > 0) {
                    for (ActorRef ref : filters) {
                        ref.tell(givenValue, null);
                    }
                }
            } else {
                unhandled(msg);
            }
        }
    };


    @Override
    public void onReceive(Object o) throws Exception {
        filter.apply(o);
    }
}
