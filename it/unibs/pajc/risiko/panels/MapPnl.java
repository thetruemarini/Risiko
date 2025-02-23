package it.unibs.pajc.risiko.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPnl extends JPanel {
    private Image mappa;

    public MapPnl() {
        // Carica l'immagine della mappa
        mappa = new ImageIcon("it/unibs/pajc/risiko/map/Mappa_Risiko.jpg").getImage();
    }

    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Disegna l'immagine della mappa
        g2d.drawImage(mappa, 0, 0, getWidth(), getHeight(), this);

        // Aggiungi altri elementi grafici sopra, se necessario
    }
}