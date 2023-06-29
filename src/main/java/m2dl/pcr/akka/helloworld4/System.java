package m2dl.pcr.akka.helloworld4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class System {

    public static final Logger log = LoggerFactory.getLogger(m2dl.pcr.akka.helloworld3.System.class);

    public static void main(String[] args) throws InterruptedException {
        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);

        final ActorRef actorRef = actorSystem.actorOf(Props.create(EmetterActor.class), "emetter-actor");

        actorRef.tell("Hello John", null);
        actorRef.tell("Good Bye Pauline", null);
        actorRef.tell("Good Bye Eva", null);
        actorRef.tell("Good Bye Bill", null);
        actorRef.tell("Hello Marc", null);

        Thread.sleep(1000);

        log.debug("Actor System Shutdown Starting...");

        actorSystem.terminate();
    }

}
