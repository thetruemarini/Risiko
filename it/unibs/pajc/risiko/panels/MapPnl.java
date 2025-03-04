package it.unibs.pajc.risiko.panels;

import javax.swing.*;
import org.w3c.dom.Document;
import it.unibs.pajc.risiko.svg.SVGDrawer;
import it.unibs.pajc.risiko.svg.SVGLoader;
import it.unibs.pajc.risiko.svg.SVGParser;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class MapPnl extends JPanel {

    private static final long serialVersionUID = 1L;
    private RoundPnl roundPnl;

    /**
     * Create the panel.
     */
    public MapPnl() {
        setLayout(new BorderLayout()); // Usa BorderLayout per gestire meglio i componenti

        // Creazione del RoundPnl
        roundPnl = new RoundPnl();
        add(roundPnl, BorderLayout.SOUTH); // Aggiungi il RoundPnl in basso

        // Aggiunge un listener per ridimensionare il RoundPnl quando il MapPnl cambia dimensione
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeRoundPnl();
            }
        });

        try {
            // Carica il file SVG
            Document svgDocument = SVGLoader.loadSVGDocument("it/unibs/pajc/risiko/resources/oceania.svg");

            // Estrai i percorsi dal documento SVG
            List<String> paths = SVGParser.extractPaths(svgDocument);

            // Crea e mostra il pannello di disegno
            SVGDrawer svgDrawer = new SVGDrawer(paths);
            svgDrawer.setPreferredSize(new Dimension(800, 600)); // Imposta dimensioni preferite
            add(svgDrawer, BorderLayout.CENTER); // Aggiungi il pannello SVGDrawer al centro

            // Forza il ridisegno del pannello
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resizeRoundPnl() {
        int width = getWidth();
        int height = getHeight();
        int roundHeight = 100; // Altezza fissa

        // Posiziona RoundPnl in basso e lo adatta in larghezza
        roundPnl.setPreferredSize(new Dimension(width, roundHeight));

        // Ridisegna il pannello
        revalidate();
        repaint();
    }
}