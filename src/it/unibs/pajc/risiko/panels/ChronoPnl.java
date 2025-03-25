package src.it.unibs.pajc.risiko.panels;

import java.awt.*;
import javax.swing.*;

public class ChronoPnl extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea textArea;

    public ChronoPnl() {
        setLayout(new BorderLayout()); // Layout per organizzare i componenti

        // TextArea per visualizzare i messaggi
        textArea = new JTextArea(15, 30);
        textArea.setEditable(false); // Impedisce all'utente di scrivere direttamente
        textArea.setBackground(new Color(230, 230, 230)); // Colore di sfondo chiaro per contrastare

        // Aggiungi un messaggio di benvenuto
        textArea.append("Benvenuto in Risiko!\n");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); // Bordo per uniformità

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addToChrono(String text) {
        textArea.append(text + "\n"); // Aggiungi il testo e una nuova linea
        textArea.setCaretPosition(textArea.getDocument().getLength()); // Scrolla automaticamente
    }

    public JScrollPane getScrollPane() {
        return (JScrollPane) getComponent(0); // Restituisce lo JScrollPane
    }

    // Aggiunge un messaggio al pannello
    public void appendText(String text) {
        textArea.append(text + "\n");
    }
}
