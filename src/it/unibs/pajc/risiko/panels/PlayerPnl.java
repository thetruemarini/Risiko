package src.it.unibs.pajc.risiko.panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPnl extends JPanel {

    private static final long serialVersionUID = 1L;
    private JButton button;
    private ChronoPnl chronoPnl;

    public void updateChronoPnl(String text) {
        chronoPnl.appendText(text);
    }

    public PlayerPnl(ChronoPnl chronoPnl) {
        this.chronoPnl = chronoPnl;

        button = new JButton("Click me");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chronoPnl.appendText("Button clicked!");
            }
        });

        add(button);
    }
}
