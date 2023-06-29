package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;

import java.io.Serializable;

public class Message implements Serializable {

    private String msg;
    private ActorRef actor;

    public Message(String msg, ActorRef actor) {
        this.msg = msg;
        this.actor = actor;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ActorRef getActor() {
        return actor;
    }

    public void setActor(ActorRef actor) {
        this.actor = actor;
    }
}
