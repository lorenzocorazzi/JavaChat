/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Lorenzo
 */
public class NewClass {

    JFrame frame = new JFrame("JavaChat");
    JPanel centro = new JPanel();
    JPanel sud = new JPanel();
    JPanel nord = new JPanel();
    JTextArea pane = new JTextArea();
    JTextArea text = new JTextArea();
    JPanel areaBottoni=new JPanel();
    JLabel nome=new JLabel("Utente",JLabel.CENTER);
    JScrollPane scroll;

    NewClass() {
        //COSE DEL CENTRO

        pane.setEditable(false);
        pane.setFont(new Font("Courier New", 2, 20));
        pane.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        pane.setLineWrap(true);
        pane.setWrapStyleWord(true);
        pane.setSize(280,280);
        scroll=new JScrollPane(pane,VERTICAL_SCROLLBAR_ALWAYS,HORIZONTAL_SCROLLBAR_NEVER);
        centro.add(scroll);
        centro.add(pane);
        javax.swing.text.Document doc = pane.getDocument();

        //COSE DEL SUD
        sud.setLayout(new GridLayout(1, 2));
        text.setFont(new Font("Courier New", 2, 20));
        text.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        text.setLineWrap(true);    
        text.setWrapStyleWord(true);
        text.addKeyListener(new KeyAdapter() {//alla pressione dello SPAZIO scatena l'evento
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && text.getText() != "") {
                    try {
                        //INVIARE DATI
                        doc.insertString(doc.getLength(),""+text.getText(), null);
                        text.setText(null);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(Finestra.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });  
        sud.add(text);
        
        
        
        //COSE DEL NORD
        nord.setLayout(new GridLayout(2, 2));
        nome.setBackground(Color.red);
        nome.setFont(new Font("Courier new", Font.BOLD, 30));
        areaBottoni.setBackground(Color.green);
        nord.add(nome);
        nord.add(areaBottoni);

        
        
        
        //COSE DEL FRAME
        frame.add(nord,BorderLayout.NORTH);
        frame.add(centro, BorderLayout.CENTER);
        frame.add(sud, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
    
    
    public void keyTyped(KeyEvent e) {
        
    }
}

