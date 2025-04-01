package src.it.unibs.pajc.risiko.panels;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.*;
import org.w3c.dom.Document;
import src.it.unibs.pajc.risiko.RisikoLocalCntrl;
import src.it.unibs.pajc.risiko.svg.SVGDrawer;
import src.it.unibs.pajc.risiko.svg.SVGLoader;
import src.it.unibs.pajc.risiko.svg.SVGParser;

public class MapPnl extends JPanel {

    private static final long serialVersionUID = 1L;
    private SVGDrawer svgDrawer;

    /**
     * Create the panel.
     */
    public MapPnl(ChronoPnl chronoPnl, RisikoLocalCntrl cntrl) {
        setLayout(new BorderLayout()); // Usa BorderLayout per gestire meglio i componenti

        try {
            // Carica il file SVG
            Document svgDocument = SVGLoader.loadSVGDocument("src/it/unibs/pajc/risiko/resources/Cartina_Risiko.svg");

            // Estrai i percorsi dal documento SVG
            List<String> paths = SVGParser.extractPaths(svgDocument);

            // Crea e mostra il pannello di disegno
            svgDrawer = new SVGDrawer(paths, chronoPnl, cntrl);
            add(svgDrawer, BorderLayout.CENTER); // Aggiungi il pannello SVGDrawer al centro

            // Aggiungi un listener per il ridimensionamento del pannello
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    svgDrawer.revalidate();
                    svgDrawer.repaint(); //TODO togliere sta merda
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}