package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.svg.SVGDrawer;
import it.unibs.pajc.risiko.svg.SVGLoader;
import it.unibs.pajc.risiko.svg.SVGParser;
import it.unibs.pajc.risiko.xml.XmlReader;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.w3c.dom.Document;

public class RisikoApp {

    public static void main(String[] args) {
        // System.out.println("negri\t" + MyMath.diceRoll() + "\t" +
        // MyMath.generatedCard());

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

        // print della mappa con svg si spera:
        
         try {
            // Carica il file SVG
            Document svgDocument = SVGLoader.loadSVGDocument("it/unibs/pajc/risiko/resources/oceania.svg");

            // Estrai i percorsi dal documento SVG
            List<String> paths = SVGParser.extractPaths(svgDocument);

            // Crea e mostra il frame con il pannello di disegno
            JFrame frame = new JFrame("SVG Drawer");
            SVGDrawer svgDrawer = new SVGDrawer(paths);
            frame.add(svgDrawer);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        // creo un giocatore, mi prendo il target dei 24, gli do 27 territori e spero
        // che Dio mi voglia bene
        Game game = new Game();
        // TODO game.start();
        Player p = new Player("marini", Color.red);
        Player p1 = new Player("ugo", Color.yellow);
        Player p2 = new Player("prins", Color.black);
        Player p3 = new Player("redolfi", Color.green);
        game.addPlayer(p);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        // game.assignTerritories();
        // game.assignAchievements();

       /*  Territory t = game.getTerritories().get(0);
        Territory t1 = t.getLinkedTerritories().get(0);
        p.addTerritory(t);
        
        t.setNumberTanks(5);
        t1.setNumberTanks(1);
        System.out.println(t);
        
        p1.addTerritory(t1);
        System.out.println(t1);
        

        System.out.println(t.getNumberTanks() + "\t" + t1.getNumberTanks());

        game.attack(t, t1, 3);
        
 */
        
         /*  for (Player player : game.getPlayers()) {
          game.placeTanks(30, player);
          System.out.println(player.getName() + " has " +
          player.getTerritories().size() + " territories.");
          for (Territory territory : player.getTerritories()) {
          System.out.println(territory.getName() + " has " + territory.getNumberTanks()
          + " armies.");
          }
          System.out.println("OBIETTIVO:\t" + player.getAchievement().getName());
          
          } */
        

        /* int i = 1;
        for (Player player : game.startFirst()) {
            System.out.println(i + "\t" + player.getName());
            i++;
        }

        System.out.println(game.bonusTankPerTerritories(p));

        for (Territory t : game.getContinents().get(2).getTerritories()) {
            p.addTerritory(t);
            System.out.println(t.getName());
        }
        System.out.println(game.bonusTankPerContinent(p)); */

        /*
         * Achievement target = game.getAchievements().get(1);
         * p.setAchievements(target);
         * ArrayList<Territory> territories = game.getTerritories();
         * for(int i = 0; i < 28; i++){
         * p.addTerritory(territories.get(i));
         * }
         * // per funzionare funziona, però: prendo p, il suo achievement e per vedere se
         * // è raggiungo rimetto p?????
         * System.out.println(p.getAchievement().isAchived(p));
         */

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
                // System.out.println(" List Elements: ");
                for (String item : list) {
                    System.out.println("      " + item);
                }
            }
        }
    }

}
