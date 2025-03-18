package src.it.unibs.pajc.risiko.panels;

import java.awt.event.ActionListener;
import javax.swing.*;

public class RoundPnl extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBox;

    public RoundPnl(ChronoPnl chronoPnl) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] options = { "Attacca", "Muovi truppe", "Termina turno", "Rinforza" };
        comboBox = new JComboBox<>(options);

        comboBox.addActionListener(e -> {
            // Invece di modificare direttamente il ChronoPnl, delega al controller
            String selectedOption = (String) comboBox.getSelectedItem();
            //gestione
        });

        add(comboBox);
    }

    public void addActionListenerToComboBox(ActionListener listener) {
        comboBox.addActionListener(listener);
    }

    public String getSelectedAction() {
        return (String) comboBox.getSelectedItem();
    }
}
