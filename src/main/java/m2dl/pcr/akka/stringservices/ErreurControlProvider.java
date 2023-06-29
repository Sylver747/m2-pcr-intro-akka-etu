package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

import static m2dl.pcr.akka.stringservices.StringUtils.ajouteCtrl;

public class ErreurControlProvider extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public ErreurControlProvider() {
        log.info("New ErreurControlProvider created");

    }

    Procedure<Object> erreurControlProvider = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Message givenMsg) {
                ActorRef actor = givenMsg.getActor();
                String msg1 = ajouteCtrl(givenMsg.getMsg());
                log.info("ErreurControlProvider sending: " + msg1);
                Message message = new Message(msg1, null);
                actor.tell(message, null);
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        erreurControlProvider.apply(o);
    }
}
