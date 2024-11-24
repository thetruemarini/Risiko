package it.unibs.pajc.risiko;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class RisikoWindow {
    JFrame frame;

    public RisikoWindow() {
        initialize();
    }

    private void initialize() {
        // Crea il frame
        frame = new JFrame("Risiko");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Imposta il layout principale del frame
        frame.setLayout(new BorderLayout());

        // Pannello centrale
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(100, 100));
        centerPanel.setBackground(Color.LIGHT_GRAY);

        // Pannello sinistro con layout verticale
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1)); // Due pannelli verticali
        JPanel leftTopPanel = new JPanel();
        JPanel leftBottomPanel = new JPanel();
        leftTopPanel.setBackground(Color.RED);
        leftBottomPanel.setBackground(Color.ORANGE);
        leftPanel.add(leftTopPanel);
        leftPanel.add(leftBottomPanel);

        // Pannello destro con layout verticale
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 1)); // Due pannelli verticali
        JPanel rightTopPanel = new JPanel();
        JPanel rightBottomPanel = new JPanel();
        rightTopPanel.setBackground(Color.BLUE);
        rightBottomPanel.setBackground(Color.CYAN);
        rightPanel.add(rightTopPanel);
        rightPanel.add(rightBottomPanel);

        // Aggiungi i pannelli al frame
        frame.add(leftPanel, BorderLayout.WEST); // Pannelli verticali a sinistra
        frame.add(rightPanel, BorderLayout.EAST); // Pannelli verticali a destra
        frame.add(centerPanel, BorderLayout.CENTER); // Pannello centrale

        // Mostra il frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Avvia l'applicazione
        SwingUtilities.invokeLater(() -> new RisikoWindow());
    }
}
