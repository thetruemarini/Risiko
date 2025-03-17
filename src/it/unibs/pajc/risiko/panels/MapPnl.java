package src.it.unibs.pajc.risiko.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.w3c.dom.Document;
import src.it.unibs.pajc.risiko.svg.SVGDrawer;
import src.it.unibs.pajc.risiko.svg.SVGLoader;
import src.it.unibs.pajc.risiko.svg.SVGParser;

public class MapPnl extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    public MapPnl(ChronoPnl chronoPnl) {
        setLayout(new BorderLayout()); // Usa BorderLayout per gestire meglio i componenti


        try {
            // Carica il file SVG
            Document svgDocument = SVGLoader.loadSVGDocument("src/it/unibs/pajc/risiko/resources/oceania.svg");

            // Estrai i percorsi dal documento SVG
            List<String> paths = SVGParser.extractPaths(svgDocument);

            // Crea e mostra il pannello di disegno
            SVGDrawer svgDrawer = new SVGDrawer(paths, chronoPnl);
            svgDrawer.setPreferredSize(new Dimension(800, 600)); // Imposta dimensioni preferite
            add(svgDrawer, BorderLayout.CENTER); // Aggiungi il pannello SVGDrawer al centro

            // Forza il ridisegno del pannello
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}