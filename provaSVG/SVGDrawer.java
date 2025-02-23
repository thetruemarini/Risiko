package provaSVG;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.PathParser;
import org.w3c.dom.Document;


public class SVGDrawer extends JPanel {
    private List<String> paths;
    private List<Shape> shapes;


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
          g2d.draw(shape);
        }
      }
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
        for (Shape shape : shapes) {
          if (shape.contains(clickPoint)) {
            System.out.println("Shape clicked: " + shape);
            // Add your custom click handling logic here
            break;
          }
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
