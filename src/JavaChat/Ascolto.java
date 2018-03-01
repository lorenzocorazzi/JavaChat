package JavaChat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * @author Lorenzo Corazzi
 */
public class Ascolto extends Thread {

    public static final String PURPLE = "\u001B[35m";
    public static final String RESET = "\u001B[0m";
    Socket connection;
    BufferedReader br;
    Gestore g;
    Finestra f;
    int fileCounter;

    Ascolto(Socket c, Gestore x, Finestra z) {
        try {
            connection = c;
            br = new BufferedReader(new InputStreamReader(c.getInputStream()));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        g = x;
        f = z;
        fileCounter=0;
    }

    public void run() {
        while (!g.end) {

            try {
                String letto = br.readLine();

                if (letto.equals("icone/SmileIcon.png")) {//leggo lo SMILE
                    f.logArea.insertIcon(new ImageIcon("icone/SmileIcon.png"));

                } else if (letto.equals("icone/LikeIcon.png")) {// leggo il LIKE
                    f.logArea.insertIcon(new ImageIcon("icone/LikeIcon.png"));

                } else if (letto.equals("File File File!!!")) {//leggo il FILE
                    long fileSize = Long.valueOf(br.readLine());
                    receiveFile(fileSize);

                } else {//leggo una normale STRINGA
                    f.logArea.setText(f.logArea.getText() + "\n" + letto);
                    System.out.println(PURPLE + g.autore + " CAZZO " + letto + RESET);
                }

            } catch(NullPointerException e){
                System.out.println("File inviato!");
            }catch (SocketException e) {
                System.out.println("Ho chiuso e non posso ricevere!");
            } catch (IOException ex) {
                Logger.getLogger(Ascolto.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//fine while
    }

    public void receiveFile(long x) {
        int bytesRead, current = 0;
        byte[] byteArray = new byte[((int) x)+1];
        InputStream is;
        BufferedOutputStream bos;
        File f;
        JFileChooser choice = new JFileChooser();
        choice.getCurrentDirectory();
        choice.setDialogTitle("Seleziona una cartella di destinazione");
        choice.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        choice.setAcceptAllFileFilterUsed(false);

        try {
            if (choice.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                do{
                    String nome= JOptionPane.showInputDialog("Inserisci il nome del file da ricevere");
                    f = new File(String.valueOf(choice.getSelectedFile()+File.separator+nome+".jpg"));
                    System.out.println(f.exists());
                }while(f.exists());// inserire un nome file non esistente
                
                
                f.getParentFile().mkdirs();
                f.createNewFile();

                bos = new BufferedOutputStream(new FileOutputStream(f)); //apro lo stream sulla cartelle scelta
                is = connection.getInputStream();

                do {
                    bytesRead = is.read(byteArray, current, (byteArray.length - current));
                    if (bytesRead >= 0) {
                        current += bytesRead;
                    }
                } while (bytesRead > -1);

                bos.write(byteArray, 0, current);
                bos.close();
                is.close();   //DA RIVEDERE, GENERA ECCEZIONE SOCKET EXCEPTION
            } else {
                System.out.println("Scegli una destinazione");
            }
        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println(e);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ascolto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {}
    }
}
