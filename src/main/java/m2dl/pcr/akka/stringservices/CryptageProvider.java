package m2dl.pcr.akka.stringservices;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

import static m2dl.pcr.akka.stringservices.StringUtils.crypte;

public class CryptageProvider extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public CryptageProvider() {
        log.info("New CryptageProvider created");

    }

    Procedure<Object> crypte = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Message givenMsg) {
                String crypted = crypte(givenMsg.getMsg());
                log.info("CryptageProvider sending message: " + crypted);
                new Message(crypted, null);
                givenMsg.getActor().tell(crypted, null);
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        crypte.apply(o);
    }
}
