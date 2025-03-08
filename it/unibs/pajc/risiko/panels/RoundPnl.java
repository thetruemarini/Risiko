package it.unibs.pajc.risiko.panels;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class RoundPnl extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public RoundPnl(ChronoPnl chronoPnl) {

		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				// System.out.println("Button " + source.getText()+ " clicked!");
				chronoPnl.appendText("Button " + source.getText() + " clicked!");
			}
		};

		JButton btnNewButton_1 = new JButton("1");
		btnNewButton_1.addActionListener(actionListener);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("2");
		btnNewButton_2.addActionListener(actionListener);
		add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("3");
		btnNewButton_3.addActionListener(actionListener);
		add(btnNewButton_3);

	}

}
