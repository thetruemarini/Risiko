package it.unibs.pajc.risiko;

import java.awt.*;
import javax.swing.*;

import it.unibs.pajc.risiko.panels.ChronoPnl;
import it.unibs.pajc.risiko.panels.MapPnl;
import it.unibs.pajc.risiko.panels.*;

public class RisikoWindow {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RisikoWindow window = new RisikoWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RisikoWindow() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400); // Finestra più grande
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Layout principale

        // Pannello sinistro (Chrono)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new ChronoPnl(), BorderLayout.CENTER);

        // Pannello centrale (Mappa + Round)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new MapPnl(), BorderLayout.CENTER);

        // Pannello destro (Player)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new PlayerPnl(), BorderLayout.CENTER);

        // Aggiunta dei pannelli al frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        
        // Imposta proporzioni dei pannelli
        leftPanel.setPreferredSize(new Dimension(100, 0));
        rightPanel.setPreferredSize(new Dimension(100, 0));

        frame.setVisible(true);
    }
}
