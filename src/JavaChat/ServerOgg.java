package JavaChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Lorenzo Corazzi
 */
public class ServerOgg {

    private int port;
    private ServerSocket sSocket;
    private Socket connection;
    private Ascolto a;
    private Finestra f;

    ServerOgg() {
        port = 2000;
        sSocket = null;
        connection = null;
        a = null;
    }

    public void attendi() {
        boolean sent = false;
        while (!sent) {
            try {
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                connection = sSocket.accept();
                System.out.println("Server : connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
                sent = true;
            } catch (IOException e) {
                System.err.println("Errore di I/O!");
            }
        }
    }
    
    public void comunica() {
        f=new Finestra(connection);
        //Passo connection come parametro per averlo in comune tra i due thread
        a = new Ascolto(connection, f);
        a.start();
    }

//    public void chiudi() {
//        try {
//            if (sSocket != null) {
//                sSocket.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(ServerOgg.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}