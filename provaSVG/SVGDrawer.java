package provaSVG;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.List;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;

public class SVGDrawer extends JPanel {
    private List<String> paths;

    public SVGDrawer(List<String> paths) {
        this.paths = paths;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (String path : paths) {
            GeneralPath generalPath = createGeneralPath(path);
            if (generalPath != null) {
                g2d.draw(generalPath);
            }
        }
    }

    private GeneralPath createGeneralPath(String path) {
        try {
            AWTPathProducer pathProducer = new AWTPathProducer();
            PathParser pathParser = new PathParser();
            pathParser.setPathHandler(pathProducer);
            pathParser.parse(path);
            return (GeneralPath) pathProducer.getShape();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            // Carica il file SVG
            Document svgDocument = SVGLoader.loadSVGDocument("provaSVG/oceania.svg");

            // Estrai i percorsi dal documento SVG
            List<String> paths = SVGParser.extractPaths(svgDocument);

            // Crea e mostra il frame con il pannello di disegno
            JFrame frame = new JFrame("SVG Drawer");
            SVGDrawer svgDrawer = new SVGDrawer(paths);
            frame.add(svgDrawer);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
