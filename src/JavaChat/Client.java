package JavaChat;

/*
 * @author Lorenzo Corazzi
 */
public class Client {

    public static void main(String[] args) {
        ClientOgg c = new ClientOgg();
        c.connetti();
        c.comunica();
    }
}
