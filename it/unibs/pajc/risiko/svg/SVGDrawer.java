package it.unibs.pajc.risiko.svg;

import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.w3c.dom.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;


    //TODO gestire meglio il pannello
    //TODO thread per velocizzare il disegno
public class SVGDrawer extends JPanel implements MouseMotionListener { //TODO AnnaMaria: gestire meglio il pannello
    private final GraphicsNode svgGraphics;
    private Rectangle2D highlightedBounds;  // Per tenere traccia della forma evidenziata
    private boolean isHighlighted;          // Per sapere se l'elemento è evidenziato

    //TODO implementare il fatto che l'elemento sia evidenziato
    //TODO è difficile zio di quel pera

    public SVGDrawer(Document svgDocument) {
        // Converte il documento SVG in un GraphicsNode
        UserAgent userAgent = new UserAgentAdapter();
        BridgeContext ctx = new BridgeContext(userAgent);
        GVTBuilder builder = new GVTBuilder();
        svgGraphics = builder.build(ctx, svgDocument);

        // Aggiungi il listener per il mouse
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Ottieni i limiti del contenuto SVG
        Rectangle2D bounds = svgGraphics.getBounds();

        // Calcola il fattore di scala per adattare il contenuto al pannello
        double scaleX = getWidth() / bounds.getWidth();
        double scaleY = getHeight() / bounds.getHeight();
        double scale = Math.min(scaleX, scaleY); // Mantiene le proporzioni

        // Calcola le traslazioni per centrare il contenuto SVG
        double translateX = (getWidth() - bounds.getWidth() * scale) / 2 - bounds.getX() * scale;
        double translateY = (getHeight() - bounds.getHeight() * scale) / 2 - bounds.getY() * scale;

        // Applica traslazione e scala
        g2d.translate(translateX, translateY);
        g2d.scale(scale, scale);

        // Disegna il contenuto SVG
        svgGraphics.paint(g2d);

        // Evidenzia l'area se il mouse è dentro
        if (isHighlighted && highlightedBounds != null) {
            g2d.setColor(Color.YELLOW);
            g2d.fill(highlightedBounds);  // Riempie l'interno dell'area evidenziata con giallo
        }
    }

    // Gestisce il movimento del mouse
    @Override
    public void mouseMoved(MouseEvent e) {
        // Ottieni la posizione del mouse
        Point mousePos = e.getPoint();

        // Calcola la posizione relativa del mouse in base alla traslazione e scala applicata
        double scaleX = getWidth() / svgGraphics.getBounds().getWidth();
        double scaleY = getHeight() / svgGraphics.getBounds().getHeight();
        double scale = Math.min(scaleX, scaleY);

        // Calcola le posizioni in base alla scala
        double mouseX = mousePos.getX() / scale;
        double mouseY = mousePos.getY() / scale;

        // Verifica se il mouse è all'interno dei confini di un elemento
        if (svgGraphics.getBounds().contains(mouseX, mouseY)) {
            // Se il mouse è all'interno, evidenziamo l'elemento
            highlightedBounds = svgGraphics.getBounds();
            isHighlighted = true;
        } else {
            // Se il mouse esce dai confini, rimuoviamo l'evidenziazione
            isHighlighted = false;
            highlightedBounds = null;
        }

        // Forza il repaint per aggiornare il disegno
        repaint();
    }

    // Metodi vuoti necessari per implementare MouseListener e MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) {}
}
