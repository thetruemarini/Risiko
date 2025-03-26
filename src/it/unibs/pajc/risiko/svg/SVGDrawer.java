package src.it.unibs.pajc.risiko.svg;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import src.it.unibs.pajc.risiko.panels.ChronoPnl;

public class SVGDrawer extends JPanel {
    private List<Shape> shapes;
    private AffineTransform transform;
    private ChronoPnl chronoPnl;
    private Point movePoint = new Point(0, 0);
    private Rectangle bounds = null;

    public SVGDrawer(List<String> paths, ChronoPnl chronoPnl) {
        this.shapes = new ArrayList<>();
        this.chronoPnl = chronoPnl;

        for (String path : paths) {
            Shape shape = createShape(path);
            if (shape != null) {
                shapes.add(shape);
            }
        }

        bounds = getShapesBounds();
        updateTransform(); // Inizializza la trasformazione

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                movePoint = e.getPoint();
                repaint();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateTransform();
                repaint();
            }
        });
    }

    private void updateTransform() {
        if (bounds != null) {
            double scaleX = getWidth() / (double) bounds.width;
            double scaleY = getHeight() / (double) bounds.height;
            double scale = Math.min(scaleX, scaleY);
    
            double translateX = (getWidth() - bounds.width * scale) / 2;
            double translateY = (getHeight() - bounds.height * scale) / 2;
    
            transform = new AffineTransform();
            transform.translate(translateX, translateY);
            transform.scale(scale, scale);
    
            System.out.println("Updated Transform: " + transform);
        } else {
            System.out.println("Bounds is null, transformation skipped.");
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (transform != null) {
            g2d.setTransform(transform);
            drawShapes(g2d);
            fillShape(g2d);
        }
    }

    private Rectangle getShapesBounds() {
        if (shapes.isEmpty()) return null;
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
        if (transform != null) {
            try {
                Point2D transformedPoint = transform.inverseTransform(movePoint, null);
                boolean found = false;
                for (Shape shape : shapes) {
                    if (shape.contains(transformedPoint)) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        g2d.setColor(Color.YELLOW);
                        g2d.fill(shape);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    setCursor(Cursor.getDefaultCursor());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleMouseClick(MouseEvent e) {
        if (transform != null) {
            try {
                Point2D transformedPoint = transform.inverseTransform(e.getPoint(), null);
                for (Shape shape : shapes) {
                    if (shape.contains(transformedPoint)) {
                        chronoPnl.appendText("Shape clicked at: " + transformedPoint);
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void drawShapes(Graphics2D g2d) {
        for (Shape shape : shapes) {
            g2d.draw(shape);
        }
    }
}
