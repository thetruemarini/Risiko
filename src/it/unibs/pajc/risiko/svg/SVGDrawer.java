package src.it.unibs.pajc.risiko.svg;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;

import src.it.unibs.pajc.risiko.RisikoLocalCntrl;
import src.it.unibs.pajc.risiko.Territory;
import src.it.unibs.pajc.risiko.panels.ChronoPnl;

public class SVGDrawer extends JPanel {
    private ChronoPnl chronoPnl;
    private List<Shape> shapes;
    private AffineTransform transform;
    private Point movePoint = new Point(0, 0);
    private Rectangle bounds;
    private RisikoLocalCntrl cntrl;

    public SVGDrawer(List<String> paths, ChronoPnl chronoPnl, RisikoLocalCntrl cntrl) {
        this.cntrl = cntrl;
        this.chronoPnl = chronoPnl;
        this.shapes = new ArrayList<>();
        for (String path : paths) {
            Shape shape = createShape(path);
            if (shape != null) {
                shapes.add(shape);
            }
        }
        // Calcola il bounding box delle shape
        bounds = getShapesBounds();

        // Aggiungi il listener per il ridimensionamento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                repaint(); // Ricalcola la trasformazione quando il componente viene ridimensionato
            }
        });

        // Aggiungi il mouse listener per il movimento del mouse
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                movePoint = e.getPoint();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (bounds != null) {
            // Calcola la traslazione per centrare le shape
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Calcola la scala dinamica
            double scaleX = (double) panelWidth / bounds.width;
            double scaleY = (double) panelHeight / bounds.height;
            double scale = Math.min(scaleX, scaleY) * 0.9; // Mantieni il rapporto di aspetto
            // TODO sistemare il 0.9 qua sopra

            // Calcola la traslazione per centrare la mappa
            int translateX = (panelWidth - (int) (bounds.width * scale)) / 2;
            int translateY = (panelHeight - (int) (bounds.height * scale)) / 2;

            // Applica la trasformazione di traduzione e scala
            transform = AffineTransform.getTranslateInstance(translateX, translateY);
            transform.scale(scale, scale);

            // Disegna le shape
            g2d.transform(transform);
            for (Shape shape : shapes) {
                g2d.draw(shape);
            }

            // Gestisci il riempimento delle shape (se cliccate)
            fillShape(g2d);

            // disegno il rettangolo per capire casa non va
            g2d.setColor(Color.RED);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            //g2d.fill(bounds); // Disegna il bounding box (opzionale)
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
        System.out.println("Bounds: " + bounds);
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
            Point2D transformedPoint = transform.inverseTransform(movePoint, null);
            for (Shape shape : shapes) {
                if (shape.contains(transformedPoint)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    g2d.setColor(Color.YELLOW);
                    g2d.fill(shape);
                    return;
                }
            }
            setCursor(Cursor.getDefaultCursor());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleMouseClick(MouseEvent e) {
        if (transform != null) {
            try {
                Point2D transformedPoint = transform.inverseTransform(e.getPoint(), null);
                for (Shape shape : shapes) {
                    if (shape.contains(transformedPoint)) {
                        int shapeIndex = shapes.indexOf(shape);
    
                        // Trova il nome del territorio associato a questa shape
                        String territoryName = getTerritoryNameByShapeId(shapeIndex);
    
                        // Mostra il nome nel pannello
                        chronoPnl.appendText("Hai cliccato su: " + territoryName);
    
                        break;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getTerritoryNameByShapeId(int shapeId) {
        Integer shapeIdInteger = Integer.valueOf(shapeId);
    
        for (Territory territory : cntrl.getTerritories()) {    
            if (territory.getShapeIds().contains(shapeIdInteger)) {
                return territory.getName();
            }
        }
        return "Unknown Territory"; // Default return value
    }
    

    
}
