package it.unibs.pajc.risiko;

import javax.swing.*;
import java.awt.*;
import processing.core.PApplet;
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
    }
}
