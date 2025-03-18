package src.it.unibs.pajc.risiko;

// import it.unibs.pajc.risiko.xml.XmlReader;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import src.it.unibs.pajc.risiko.panels.ChronoPnl;
import src.it.unibs.pajc.risiko.panels.MapPnl;
import src.it.unibs.pajc.risiko.panels.PlayerPnl;
import src.it.unibs.pajc.risiko.panels.RoundPnl;

public class RisikoApp {

    public JFrame frame;
    private ChronoPnl chronoPnl;
    private MapPnl mapPnl;
    private RoundPnl roundPnl;
    private PlayerPnl playerPnl;
 

    public static void main(String[] args) {
       

        EventQueue.invokeLater(() -> {
            try {
                RisikoApp window = new RisikoApp();
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

        model = new RisikoModel();

        Player p = new Player("marini", Color.red);
        Player p1 = new Player("ugo", Color.yellow);
        Player p2 = new Player("prins", Color.black);
        
        model.addPlayer(p);
        model.addPlayer(p1);
        model.addPlayer(p2);
        
        cntrl = new RisikoLocalCntrl(model);

        initialize();

        model.addChangeListener(e -> frame.repaint());
    }

    public void initialize() {
        frame = new JFrame();
        frame.setBounds(200, 200, 1200, 800); // Finestra più grande
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Layout principale

        // Pannello sinistro (Chrono)
        chronoPnl = new ChronoPnl();
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(chronoPnl, BorderLayout.CENTER);

        // Pannello centrale (Mappa)
        mapPnl = new MapPnl(chronoPnl);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(mapPnl, BorderLayout.CENTER);

        // Pannello destro (Player)
        playerPnl = new PlayerPnl(chronoPnl);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(playerPnl, BorderLayout.CENTER);

        // Pannello inferiore (Round)
        roundPnl = new RoundPnl(cntrl, chronoPnl);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(roundPnl, BorderLayout.CENTER);

        // Aggiunta dei pannelli al frame
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH); // Aggiunto il bottom panel

        // Imposta proporzioni dei pannelli
        leftPanel.setPreferredSize(new Dimension(200, 0));
        rightPanel.setPreferredSize(new Dimension(200, 0));
        bottomPanel.setPreferredSize(new Dimension(0, 150)); // Altezza per il round panel

        frame.setVisible(true);
    }

    public ChronoPnl getChronoPnl() {
        return chronoPnl;

    }

    public MapPnl getMapPanel() {
        return mapPnl;
    }

    public RoundPnl getRoundPanel() {
        return roundPnl;
    }

    public PlayerPnl getPlayerPanel() {
        return playerPnl;
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
