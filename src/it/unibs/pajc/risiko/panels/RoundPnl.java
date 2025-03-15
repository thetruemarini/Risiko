package src.it.unibs.pajc.risiko.panels;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class RoundPnl extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    public RoundPnl(ChronoPnl chronoPnl) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        String[] options = { "Attacca", "Muovi truppe", "Termina turno", "Rinforza" };
        JComboBox<String> comboBox = new JComboBox<>(options);

        comboBox.addActionListener(e -> {
            String selectedOption = (String) comboBox.getSelectedItem();
            switch (selectedOption) {
                case "Attacca":
                    chronoPnl.addToChrono("Azione di attacco eseguita");
                    break;
                case "Muovi truppe":
                    chronoPnl.addToChrono("Movimento delle truppe eseguito");
                    break;
                case "Termina turno":
                    chronoPnl.addToChrono("Turno terminato");
                    break;
                case "Rinforza":
                    chronoPnl.addToChrono("Rinforzo delle truppe eseguito");
                    break;
            }
        });

        add(comboBox);
    }
}
