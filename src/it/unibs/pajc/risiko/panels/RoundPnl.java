package src.it.unibs.pajc.risiko.panels;

import java.awt.event.ActionListener;
import javax.swing.*;
import src.it.unibs.pajc.risiko.*;

public class RoundPnl extends JPanel {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> comboBox;
    private RisikoController cntrl;

    public RoundPnl(ChronoPnl chronoPnl, RisikoController cntrl) {
        this.cntrl = cntrl;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String[] options = { "Attacca", "Muovi truppe", "Termina turno", "Rinforza" };
        comboBox = new JComboBox<>(options);

        comboBox.addActionListener(e -> {
            // Invece di modificare direttamente il ChronoPnl, delega al controller
            String selectedOption = (String) comboBox.getSelectedItem();
            // Passa l'azione al controller
            // Il controller deciderà cosa fare con la mappa e con il modello
            cntrl.handleRoundAction(selectedOption);
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
