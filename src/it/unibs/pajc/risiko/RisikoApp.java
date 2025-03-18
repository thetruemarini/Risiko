package src.it.unibs.pajc.risiko;

// import it.unibs.pajc.risiko.xml.XmlReader;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RisikoApp {

    private static RisikoWindow window;

    public static void main(String[] args) {
       

        EventQueue.invokeLater(() -> {
            try {
                window = new RisikoWindow();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // creo un giocatore, mi prendo il target dei 24, gli do 27 territori e spero
        // che Dio mi voglia bene
        // TODO game.start();
        
        //Player p3 = new Player("redolfi", Color.green);
       
        //game.addPlayer(p3);

        //game.assignTerritories();
        //game.assignAchievements();

        /*for (Player player : model.getPlayers()) {
            //game.placeTanks(30, player);
            System.out.println(player.getName() + " has " +
                    player.getTerritories().size() + " territories.");
            for (Territory territory : player.getTerritories()) {
                System.out.println(territory.getName() + " has " + territory.getNumberTanks()
                        + " armies.");
            }
            System.out.println("OBIETTIVO:\t" + player.getAchievement().getName());

        }
        */

        

    }

    private RisikoModel model;
    private RisikoLocalCntrl cntrl;

    public RisikoApp() {

        Player p = new Player("marini", Color.red);
        Player p1 = new Player("ugo", Color.yellow);
        Player p2 = new Player("prins", Color.black);
        
        model.addPlayer(p);
        model.addPlayer(p1);
        model.addPlayer(p2);
        
        cntrl = new RisikoLocalCntrl(model);

        window.initialize();

        model.addChangeListener(e -> window.frame.repaint());
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
