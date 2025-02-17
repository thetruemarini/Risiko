package it.unibs.pajc.risiko.svg;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.ArrayList;

public class SVGDrawer extends JPanel {
    private final List<Path2D> svgPaths;

    public SVGDrawer(List<String> paths) {
        svgPaths = new ArrayList<>();
        for (String path : paths) {
            svgPaths.add(createPath(path));
        }
    }

    private Path2D createPath(String svgPath) {
        Path2D path = new Path2D.Double();
        // Implementa la logica per convertire la stringa SVG in un oggetto Path2D
        // Questo richiede un parser per il formato SVG Path
        // Puoi usare librerie come Apache Batik per questo scopo
        return path;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Path2D path : svgPaths) {
            // Ottieni i limiti del path
            Rectangle2D bounds = path.getBounds2D();

            // Calcola il fattore di scala per adattare il contenuto al pannello
            double scaleX = getWidth() / bounds.getWidth();
            double scaleY = getHeight() / bounds.getHeight();
            double scale = Math.min(scaleX, scaleY); // Mantiene le proporzioni

            // Calcola le traslazioni per centrare il contenuto SVG
            double translateX = (getWidth() - bounds.getWidth() * scale) / 2 - bounds.getX() * scale;
            double translateY = (getHeight() - bounds.getHeight() * scale) / 2 - bounds.getY() * scale;

            // Applica la trasformazione
            AffineTransform transform = new AffineTransform();
            transform.translate(translateX, translateY);
            transform.scale(scale, scale);

            // Disegna il path trasformato
            g2d.setTransform(transform);
            g2d.draw(path);
        }
    }
}
