package src.it.unibs.pajc.risiko;

import java.awt.*;
import javax.swing.*;
import src.it.unibs.pajc.risiko.panels.*;

public class RisikoWindow {

    public JFrame frame;
    private ChronoPnl chronoPnl;
    private MapPnl mapPnl;
    private RoundPnl roundPnl;
    private PlayerPnl playerPnl;
    private RisikoController cntrl;
    private GameModel model;

    public RisikoWindow() { //dovremo passare il cliente, cosi quando entra fa la sua window con il suo pleyerPnl
        model = new GameModel();
        initialize();
        cntrl = new RisikoController(model, this); 
        //TODO osservazione: per ora l'unico pannello regolato dal controller è il roundPnl
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(200, 200, 1200, 800); // Finestra più grande
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Layout principale

        // Pannello sinistro (Chrono)
        chronoPnl = new ChronoPnl();
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(chronoPnl, BorderLayout.CENTER);

        // Pannello centrale (Mappa)
        mapPnl = new MapPnl(chronoPnl);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(mapPnl, BorderLayout.CENTER);

        // Pannello destro (Player)
        playerPnl = new PlayerPnl(chronoPnl);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(playerPnl, BorderLayout.CENTER);

        // Pannello inferiore (Round)
        roundPnl = new RoundPnl(chronoPnl, cntrl);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(roundPnl, BorderLayout.CENTER);

        // Aggiunta dei pannelli al frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH); // Aggiunto il bottom panel

        // Imposta proporzioni dei pannelli
        leftPanel.setPreferredSize(new Dimension(200, 0));
        rightPanel.setPreferredSize(new Dimension(200, 0));
        bottomPanel.setPreferredSize(new Dimension(0, 150)); // Altezza per il round panel

        frame.setVisible(true);
    }

    public ChronoPnl getChronoPnl() {
        return chronoPnl;
    }

    public MapPnl getMapPanel() {
        return mapPnl;
    }

    public RoundPnl getRoundPanel() {
        return roundPnl;
    }

    public PlayerPnl getPlayerPanel() {
        return playerPnl;
    }
}
