package m2dl.pcr.akka.stringservices;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by franck on 23/05/2016.
 */
public class StringUtils {

    private static byte[] key = {26, 123, 72, 9, 17, 91, 48, 51, 97, 6, 24, 7, 33, 125, 109, 111};

    /**
     * Crypte la chaine s avec la clé par défaut sur 128 bits
     */
    public static String crypte(String s) {
        byte[] buffer = s.getBytes();
        for (int i = 0; i < s.length(); i++)
            buffer[i] = (byte) (buffer[i] ^ key[i % key.length]);
        return new String(buffer);

    }

    /**
     * Decrypte la chaine s avec la clé par défaut sur 128 bits
     */
    public static String decrypte(String s) {
        return crypte(s);
    }

    /**
     * Ajoute un caractère de controle à la fin de la chaine s
     */
    public static String ajouteCtrl(String s) {
        byte[] buffer = s.getBytes();
        byte x = 26;
        for (int i = 0; i < s.length(); i++)
            x = (byte) (buffer[i] ^ x);

        byte[] ctrl = new byte[1];
        ctrl[0] = (byte) x;
        return s + new String(ctrl);
    }

    /**
     * Verifie la conformite de la chaine s avec son code d'erreur (dernier caractère),
     * renvoie null en cas de pb, sinon la chaine sans la caractère de controle
     */
    public static String verifieCtrl(String s) {
        byte[] buffer = s.getBytes();
        byte x = 26;
        for (int i = 0; i < s.length() - 1; i++)
            x = (byte) (buffer[i] ^ x);
        if (x == buffer[s.length() - 1]) return s.substring(0, s.length() - 1);
        else return null;
    }

    /* juste pour le test */
    public static void main(String args[]) throws InterruptedException {
//        String s = args[0];
//        System.out.println(s);
//        System.out.println(crypte(s));
//        System.out.println(decrypte(crypte(s)));
//        System.out.println(ajouteCtrl(s));
//        System.out.println(verifieCtrl(ajouteCtrl(s)));

        final ActorSystem actorSystem = ActorSystem.create("actor-system");

        Thread.sleep(5000);

        final ActorRef cryptageProvider = actorSystem.actorOf(Props.create(CryptageProvider.class), "cryptage-actor");
        final ActorRef erreurControlProvider = actorSystem.actorOf(Props.create(ErreurControlProvider.class), "erreurControlProvider-actor");
        final ActorRef recepteur = actorSystem.actorOf(Props.create(Recepteur.class), "recepteur-actor");
        final ActorRef recepteur2 = actorSystem.actorOf(Props.create(Recepteur.class), "recepteur2-actor");
        final ActorRef intermediaire = actorSystem.actorOf(Props.create(Intermediaire.class, erreurControlProvider, recepteur2), "intermediaire-actor");

        Message message1 = new Message("test", recepteur);
        Message message2 = new Message("test", recepteur);
        Message message3 = new Message("test", intermediaire);

//        System.out.println("Cas 1");
//        cryptageProvider.tell(message1, null);

//        System.out.println("Cas 2");
//        erreurControlProvider.tell(message2, null);

        System.out.println("Cas 3");
        cryptageProvider.tell(message3, null);

    }
}
