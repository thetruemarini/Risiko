package it.unibs.pajc.risiko;

import java.awt.*;
import javax.swing.*;

import it.unibs.pajc.risiko.panels.*;

public class RisikoWindow {

    JFrame frame;
    ChronoPnl chronoPnl = new ChronoPnl();

    public RisikoWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(200, 200, 1200, 800); // Finestra più grande
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Layout principale

        // Pannello sinistro (Chrono)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(chronoPnl, BorderLayout.CENTER);

        // Pannello centrale (Mappa + Round)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new MapPnl(chronoPnl), BorderLayout.CENTER);

        // Pannello destro (Player)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new PlayerPnl(chronoPnl), BorderLayout.CENTER);

        // Aggiunta dei pannelli al frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);

        // Imposta proporzioni dei pannelli
        leftPanel.setPreferredSize(new Dimension(200, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));

        frame.setVisible(true);
    }

    public ChronoPnl getChronoPnl() {
        return chronoPnl;
    }
}
