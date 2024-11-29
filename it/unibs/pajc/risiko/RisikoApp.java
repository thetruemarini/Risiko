package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.achivement.Achievement;
import it.unibs.pajc.risiko.xml.XmlReader;
import java.awt.Color;
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

        //print della mappa con svg si spera:
        /* try {
            // Carica il file SVG
            Document svgDocument = SVGLoader.loadSVG("it/unibs/pajc/risiko/resources/oceania.svg");
    
            // Crea il pannello per visualizzare il file SVG
            SVGDrawer drawer = new SVGDrawer(svgDocument);
    
            // Crea il frame
            JFrame frame = new JFrame("SVG Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600); // Imposta una dimensione iniziale ragionevole
            frame.add(drawer);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        } */

        //creo un giocatore, mi prendo il target dei 24, gli do 27 territori e spero che Dio mi voglia bene
        Game game = new Game();
        //TODO game.start();
        Player p = new Player("marini", Color.RED);
        game.addPlayer(p);
        Achievement target = game.getAchievements().get(1);
        p.setAchievements(target);
        ArrayList<Territory> territories = game.getTerritories();
        for(int i = 0; i < 28; i++){
            p.addTerritory(territories.get(i));
        }
        //per funzionare funziona, però: prendo p, il suo achievement e per vedere se è raggiungo rimetto p?????
        System.out.println(p.getAchievement().isAchived(p));


        
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
