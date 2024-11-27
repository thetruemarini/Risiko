package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.xml.XmlReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RisikoApp {

    public static void main(String[] args) {
        //System.out.println("negri\t" + MyMath.diceRoll() + "\t" + MyMath.generatedCard());

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
         XmlReader reader = new XmlReader("it/unibs/pajc/risiko/xml/territories.xml");

        // Chiama il metodo per leggere e stampare i nomi dei territori
        HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
        data = reader.getData(); 
        printMap(data);
        // Modifica il percorso con il tuo file
        // XML
    }

     public static void printMap(HashMap<String, HashMap<String, ArrayList<String>>> map) {
        // Itera sulla mappa esterna (che ha come chiavi String)
        for (Map.Entry<String, HashMap<String, ArrayList<String>>> entry : map.entrySet()) {
            System.out.println("Outer Key: " + entry.getKey());

            // Ottieni la mappa interna associata alla chiave esterna
            HashMap<String, ArrayList<String>> innerMap = entry.getValue();

            // Itera sulla mappa interna (che ha come chiavi String)
            for (Map.Entry<String, ArrayList<String>> innerEntry : innerMap.entrySet()) {
                System.out.println("  Inner Key: " + innerEntry.getKey());

                // Ottieni la lista associata alla chiave interna
                ArrayList<String> list = innerEntry.getValue();

                // Stampa gli elementi della lista
               // System.out.println("    List Elements: ");
                for (String item : list) {
                    System.out.println("      " + item);
                }
            }
        }
    }

    
}
