package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

public class Intermediaire extends UntypedActor {
    private final ActorRef erreurControleProvider;
    private final ActorRef recepteur;
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    public Intermediaire(ActorRef erreurControleProvider, ActorRef recepteur) {
        log.info("New Intermediaire created");
        this.erreurControleProvider = erreurControleProvider;
        this.recepteur = recepteur;

    }

    Procedure<Object> intermediaire = new Procedure<Object>() {
        public void apply(Object msg) throws Exception {
            if (msg instanceof Message givenMsg) {
                String msg1 = givenMsg.getMsg();
                Message message = new Message(msg1, recepteur);
                log.info("Intermediaire sending: " + msg1);
                erreurControleProvider.tell(message, null);
            } else {
                unhandled(msg);
            }
        }
    };

    @Override
    public void onReceive(Object o) throws Exception {
        intermediaire.apply(o);
    }
}
