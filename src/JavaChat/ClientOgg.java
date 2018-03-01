package JavaChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Lorenzo Corazzi
 */
public class ClientOgg {

    Socket connection;
    String serverAddress;
    int port;
    BufferedReader br;
    BufferedWriter bw;
    Gestore g;
    Ascolto a;
    Finestra f;

    ClientOgg() {
        connection = null;
        serverAddress = "localhost";
        port = 2000;
        br = null;
        bw = null;
        g=null;
        a=null;
    }

    public void connetti() {
        try {
            connection = new Socket(serverAddress, port);
            System.out.println("Client : connessione aperta");
        } catch (ConnectException e) {
            System.err.println("Server non disponibile!");
        } catch (UnknownHostException e1) {
            System.err.println("Errore DNS!");
        } catch (IOException e2) {
            System.err.println(e2);
        }
    }

    public void comunica() {
        g = new Gestore();
        f=new Finestra(connection);
        //Passo connection come parametro per averlo in comune tra i due thread
        a = new Ascolto(connection, g,f);
        a.start();
    }

}