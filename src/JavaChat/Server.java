package JavaChat;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/*
 * @author Lorenzo Corazzi
 */
public class Server{

    public static void main(String[] args) {
        ServerOgg s = new ServerOgg();
        s.attendi();
        s.comunica();
    }
}
