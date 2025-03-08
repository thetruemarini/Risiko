package it.unibs.pajc.risiko.svg;

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

import it.unibs.pajc.risiko.panels.ChronoPnl;

public class SVGDrawer extends JPanel {
    private List<String> paths;
    private List<Shape> shapes;
    private AffineTransform transform;
    private ChronoPnl chronoPnl;
    private Point movePoint = new Point(0, 0);

    public SVGDrawer(List<String> paths, ChronoPnl chronoPnl) {
        this.paths = paths;
        this.shapes = new ArrayList<>();
        this.chronoPnl = chronoPnl;
        addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMouseClick(e);
                    }
                });
        addMouseMotionListener(
                new MouseAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        movePoint = e.getPoint();
                        repaint();
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
            g2d.transform(transform);

            // Draw the shapes
            for (Shape shape : shapes) {
                g2d.draw(shape);
            }

            fillShape(g2d);
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

    private void fillShape(Graphics2D g2d) {
        try {
            if (transform != null) { // Ensure transform is initialized
                Point2D transformedPoint = transform.inverseTransform(movePoint, null);

                boolean found = false;
                for (Shape shape : shapes) {
                    if (shape.contains(transformedPoint)) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        g2d.setColor(Color.YELLOW);
                        g2d.fill(shape);
                        found = true;
                        break; // Exit loop once a shape is found
                    }
                }
                if (!found) {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleMouseClick(MouseEvent e) {
        try {
            if (transform != null) { // Ensure transform is initialized
                Point2D transformedPoint = transform.inverseTransform(e.getPoint(), null);

                for (Shape shape : shapes) {
                    if (shape.contains(transformedPoint)) {
                        chronoPnl.appendText("Shape clicked at: " + transformedPoint);
                        break; // Exit loop once a shape is found
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
