package it.unibs.pajc.risiko.panels;

import javax.swing.JPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MapPnl extends JPanel {

    private static final long serialVersionUID = 1L;
    private RoundPnl roundPnl;

    /**
     * Create the panel.
     */
    public MapPnl() {
        setLayout(null);

        // Creazione del RoundPnl
        roundPnl = new RoundPnl();
        add(roundPnl);

        // Imposta le dimensioni iniziali
        resizeRoundPnl();

        // Aggiunge un listener per ridimensionare il RoundPnl quando il MapPnl cambia dimensione
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeRoundPnl();
            }
        });
    }

    private void resizeRoundPnl() {
        int width = getWidth();
        int height = getHeight();
        int roundHeight = 100; // Altezza fissa

        // Posiziona RoundPnl in basso e lo adatta in larghezza
        roundPnl.setBounds(0, height - roundHeight, width, roundHeight);

        // Ridisegna il pannello
        repaint();
    }
}