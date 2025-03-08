package it.unibs.pajc.risiko.panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChronoPnl extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private JTextArea textArea;

	public ChronoPnl() {
		setLayout(new BorderLayout()); // Layout per organizzare i componenti

		textArea = new JTextArea(15, 30);
		textArea.setEditable(false); // Impedisce all'utente di scrivere direttamente

		textArea.append("Benvenuto in Risiko!\n");

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane, BorderLayout.CENTER);
	}

}
