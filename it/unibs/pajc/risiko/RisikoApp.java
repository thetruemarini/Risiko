package it.unibs.pajc.risiko;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.unibs.pajc.risiko.map.Map;
import it.unibs.pajc.risiko.utility.MyMath;
import it.unibs.pajc.risiko.xml.XmlReader;

public class RisikoApp {

    public static void main(String[] args) {
        System.out.println("negri\t" + MyMath.diceRoll() + "\t" + MyMath.generatedCard());

        /*
         * EventQueue.invokeLater(new Runnable() {
         * public void run() {
         * try {
         * RisikoWindow window = new RisikoWindow();
         * window.frame.setVisible(true);
         * } catch (Exception e) {
         * e.printStackTrace();
         * }
         * }
         * });
         */

        // Crea un'istanza della classe TerritoryReader
        // XmlReader reader = new XmlReader();

        // Chiama il metodo per leggere e stampare i nomi dei territori
        // reader.printTerritoryNames("it/unibs/pajc/risiko/xml/territories.xml"); //
        // Modifica il percorso con il tuo file
        // XML

        SwingUtilities.invokeLater(Map::new);
    }
}
