package src.it.unibs.pajc.risiko.panels;

import java.awt.*;
import javax.swing.*;

public class PlayerPnl extends JPanel {

    private static final long serialVersionUID = 1L;
    private ChronoPnl chronoPnl; //serve che siano collegati? alla fine è solo puramente informativo il playerPnl ed è personale

    private JLabel lblNome, lblColore, lblObiettivo, lblTankBonus;
    //private JList<String> listaCarte, listaTerritori;
    //private DefaultListModel<String> carteModel, territoriModel; 
    //me l'ha consigliato chatgpt a dire il vero ma non so se serve, potremmo inizializzarle normalmente inserendo i dati
    //e aggiungerli di volta in volta con add penso. comunque ci sarà sicuramente un metodo per ricavare tale lista
    private JScrollPane carteScroll, territoriScroll;


    public void updateChronoPnl(String text) {
        chronoPnl.appendText(text);
    }

    public PlayerPnl(ChronoPnl chronoPnl) {//piuttosto servirà avere il cliente, perche ognuno ha il proprio playerPnl
        this.chronoPnl = chronoPnl;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Informazioni Giocatore"));

        //parte sopra
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        lblNome = new JLabel("Nome: ---");
        lblColore = new JLabel("Colore: ---");
        topPanel.add(lblNome);
        topPanel.add(lblColore);
        add(topPanel, BorderLayout.NORTH);

        //oarte centrale carte e territori
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));

        //carteModel = new DefaultListModel<>();
        //listaCarte = new JList<>(carteModel);
        carteScroll = new JScrollPane();//listaCarte dentro a carteScroll
        centerPanel.add(new JLabel("Carte:"));
        centerPanel.add(carteScroll);

        //territoriModel = new DefaultListModel<>();
        //listaTerritori = new JList<>(territoriModel);
        territoriScroll = new JScrollPane(); //listaTerritori dentro a territoriScroll
        centerPanel.add(new JLabel("Territori:"));
        centerPanel.add(territoriScroll);

        add(centerPanel, BorderLayout.CENTER);

        // Pannello inferiore con obiettivo e tank bonus
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        lblObiettivo = new JLabel("Obiettivo: ---");
        lblTankBonus = new JLabel("Tank Bonus: 0");
        bottomPanel.add(lblObiettivo);
        bottomPanel.add(lblTankBonus);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

