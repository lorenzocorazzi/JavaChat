package JavaChat;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import javax.swing.*;
import java.io.*;

public class Finestra implements ActionListener {

    private Scrittura s;
    private JFrame frame;
    protected JTextPane msgArea;
    private JScrollPane msgScroller;
    protected JTextPane logArea;
    private JScrollPane logScroller;
    private JTextArea text;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private ImageIcon smile;
    private ImageIcon like;
    private JFileChooser choice;

    Finestra(Socket x,String nome) {

        s = new Scrittura(x);
        //FRAME E CONTAINER
        frame = new JFrame("JavaChat("+nome+")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(5, 5));

        //CENTERPANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 5, 5));

        logArea = new JTextPane();
        logArea.setEditable(false);
        

        logScroller = new JScrollPane();
        logScroller.setBorder(BorderFactory.createTitledBorder("Utente messages"));
        logScroller.setViewportView(logArea);

        msgArea = new JTextPane();
        msgArea.setEditable(false);
		SwingUtilities.getUnwrappedParent(msgArea);
		SwingUtilities.getUnwrappedParent(logArea);

        msgScroller = new JScrollPane();
        msgScroller.setBorder(BorderFactory.createTitledBorder("My messages"));
        msgScroller.setViewportView(msgArea);

        centerPanel.add(logScroller);
        centerPanel.add(msgScroller);

        //BOTTOMPANEL
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 0));
        text = new JTextArea(1, 1);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        text.getDocument().putProperty("lineLimit", 10);
        
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {//evento che si scatena quando viene premuto un tasto 
                if(text.getText().length()>30){// non fa scrivere una riga più grande di 30 caratteri
                    text.setText(text.getText().substring(0, 30));
                    e.consume();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER && text.getText() != "") {// controllo se il tasto premuto è INVIO
                    //Se si scrivo sul log dei messaggi locali e lo invio al destinatario
                    msgArea.setText(msgArea.getText() + "\n" + text.getText());
                    s.scrivi(text.getText());
                    text.setText(null);
                    e.consume();
                }
                
            }

        });

        //ENDPANEL
        choice=new JFileChooser();
        choice.getCurrentDirectory();
        choice.setDialogTitle("Seleziona un file da inviare");
        JPanel endPanel = new JPanel();
        endPanel.setLayout(new GridLayout(1, 1));
        smile=new ImageIcon("icone/SmileIcon.png");
        like=new ImageIcon("icone/LikeIcon.png");
        b1 = new JButton(new ImageIcon("icone/RenameIcon.png"));
        b2 = new JButton(like);
        b3 = new JButton(smile);
        b4 = new JButton(new ImageIcon("icone/SendIcon.png"));
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        endPanel.add(b1);
        endPanel.add(b2);
        endPanel.add(b3);
        endPanel.add(b4);

        bottomPanel.add(text);
        bottomPanel.add(endPanel);

        //CONTAINER E FRAME ADDS
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        frame.setContentPane(contentPane);
        frame.setSize(300,500);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {//ascoltatore dei pulsanti in basso 
        if (e.getSource() == b1) {
            logScroller.setBorder(BorderFactory.createTitledBorder(text.getText() + " "));
            text.setText(null); // primo pulsante, cambia il nome dell'utente
        }
        
        if(e.getSource() == b2){
            msgArea.insertIcon(new ImageIcon("icone/LikeIcon.png"));
            s.scrivi(like);// secondo pulnsante, invia il Like
        }
        
        if(e.getSource() == b3){
            msgArea.insertIcon(new ImageIcon("icone/SmileIcon.png"));
            s.scrivi(smile); // terzo pulsante, invia uno smile
        }
        
        if(e.getSource() == b4){// quarto pulsante invia un file
            int val = choice.showOpenDialog(frame); // apre la finestra di scelta
            if(val==choice.APPROVE_OPTION){// se l'utente selezione un file
                File selectedFile= choice.getSelectedFile();
                s.scrivi("File File File!!!");// invio dei messaggi predefiniti decisi dal programmatore per sincronizzarsi e
                s.scrivi(String.valueOf(selectedFile.length()));//per far capire al destinatario che il prossimo invio sarà un file
                s.scrivi(String.valueOf(selectedFile).substring(String.valueOf(selectedFile).lastIndexOf(".")+ 1)); // prendo l'estenzione del file
                s.inviaFile(selectedFile);
            }
        }
    }

    
    
}
