package it.unibs.pajc.risiko;

import javax.swing.JFrame;

import it.unibs.pajc.risiko.panels.*;


public class RisikoWindow {
    private JFrame frame;
    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Risikozzo window = new Risikozzo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Risikozzo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		MapPanel mapPanel = new MapPanel();
		mapPanel.setBounds(124, 69, 176, 110);
		frame.getContentPane().add(mapPanel);
		
		ChronoPanel chronoPnl = new ChronoPnl();
		chronoPnl.setBounds(10, 10, 47, 232);
		frame.getContentPane().add(chronoPnl);
		
		PlayerPnl playerPnl = new PlayerPnl();
		playerPnl.setBounds(67, 10, 47, 232);
		frame.getContentPane().add(playerPnl);
		
		TurnPnl turnPnl = new TurnPnl();
		turnPnl.setBounds(310, 131, 116, 103);
		frame.getContentPane().add(turnPnl);
		
		Pnl pnl = new Pnl();
		pnl.setBounds(309, 20, 117, 101);
		frame.getContentPane().add(pnl);
	}
}
