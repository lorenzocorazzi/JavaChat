package JavaChat;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/*
 * @author Lorenzo Corazzi
 */
public class Scrittura extends Thread {

    private Socket connection;
    private BufferedWriter bw;
    private OutputStream os;

    Scrittura(Socket c) {
        connection = c;
        try {
            os=connection.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
        } catch (IOException ex) {
            Logger.getLogger(Scrittura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void scrivi(String msg) {//invio un messaggio testuale
        try {
            bw.write(msg + "\n");
            bw.flush();
        } catch (SocketException e) {
            //se provo ad inviare un qualcosa ad una connessione chiusa 
            //genera eccezione
            System.out.println("Il destinatario ha chiuso la connessione!");
        } catch (IOException ex) {
            Logger.getLogger(Scrittura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void scrivi(ImageIcon msg) {//invio la source dell'icona
        try {
            bw.write(msg + "\n");
            bw.flush();
        } catch (SocketException e) {
            //se provo ad inviare un qualcosa ad una connessione chiusa 
            //genera eccezione
            System.out.println("Il destinatario ha chiuso la connessione!");
        } catch (IOException ex) {
            Logger.getLogger(Scrittura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inviaFile(File f) {//invio un file a sceltra
        try {
            InputStream in = new FileInputStream(f);
            int current;
            byte[] bytes = new byte[(int) f.length()];
            //Per inviare il file mi serve trasformarlo in byte 
            while ((current = in.read(bytes)) > 0) {
                os.write(bytes, 0, current);
            }
            
            os.close();
            in.close();
            // DA RIVEDERE, SE NON CHIUDO IL FILE NON VIENE RICEVUTO
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {}
    }
}
