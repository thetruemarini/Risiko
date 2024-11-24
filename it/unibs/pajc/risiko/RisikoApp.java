package it.unibs.pajc.risiko;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class RisikoApp {
    
    public static void main(String[] args) {
        System.out.println("negri\t" + MyMath.diceRoll() + "\t" + MyMath.generatedCard());

        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RisikoWindow window = new RisikoWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
