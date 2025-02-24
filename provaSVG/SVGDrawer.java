package provaSVG;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;

public class SVGDrawer extends JPanel {
    private List<String> paths;
    private List<Shape> shapes;
    private AffineTransform transform;

    public SVGDrawer(List<String> paths) {
        this.paths = paths;
        this.shapes = new ArrayList<>();
        addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleMouseClick(e);
                }
            });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        shapes.clear();
        for (String path : paths) {
            Shape shape = createShape(path);
            if (shape != null) {
                shapes.add(shape);
            }
        }

        // Calculate the bounding box of all shapes
        Rectangle bounds = getShapesBounds();
        if (bounds != null) {
            // Calculate the translation to center the shapes
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int translateX = (panelWidth - bounds.width) / 2 - bounds.x;
            int translateY = (panelHeight - bounds.height) / 2 - bounds.y;

            // Apply the translation transformation
            transform = AffineTransform.getTranslateInstance(translateX, translateY);
            g2d.setTransform(transform);

            // Draw the shapes
            for (Shape shape : shapes) {
                g2d.draw(shape);
            }
        }
    }

    private Rectangle getShapesBounds() {
        if (shapes.isEmpty()) {
            return null;
        }
        Rectangle bounds = shapes.get(0).getBounds();
        for (Shape shape : shapes) {
            bounds = bounds.union(shape.getBounds());
        }
        return bounds;
    }

    private Shape createShape(String path) {
        try {
            AWTPathProducer pathProducer = new AWTPathProducer();
            PathParser pathParser = new PathParser();
            pathParser.setPathHandler(pathProducer);
            pathParser.parse(path);
            return pathProducer.getShape();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleMouseClick(MouseEvent e) {
        Point clickPoint = e.getPoint();
        try {
            Point2D transformedPoint = transform.inverseTransform(clickPoint, null);
            for (Shape shape : shapes) {
                if (shape.contains(transformedPoint)) {
                    System.out.println("Shape clicked: " + shape);
                    // Add your custom click handling logic here
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
