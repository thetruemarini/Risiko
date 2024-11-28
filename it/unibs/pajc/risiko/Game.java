package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.xml.XmlReader;
import java.util.*;
public class Game {
    private HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
    private ArrayList<Player> players; // TODO constructor
    private ArrayList<Card> cardDeck = new ArrayList<>(); // TODO constructor
    private ArrayList<Territory> territories = new ArrayList<>(); //che sarà =world.getTerritories(); questo dico
    private ArrayList<Continent> continents = new ArrayList<>();
    private ArrayList<Achievement> targetDeck = new ArrayList<>(); // non bellissimo
    private XmlReader reader; 

    public Game() {
        this.reader = new XmlReader("it/unibs/pajc/risiko/xml/territories.xml"); 
        data = reader.getData();
        initializeWorld();

    }

    private void addPlayer(Player p) {
        players.add(p);
    }

    ArrayList<Territory> toAssign = new ArrayList<>(territories);

    int nPlayers = players.size();

    public void assignedTerritories() { // setOwner

        Collections.shuffle(territories); // sostanzialmente mischia casualmente gli elementi di una lista

        for (int i = 0; i < nPlayers; i++) {

            Player selectedPlayer = players.get(i);
            int territoriesPerPlayer = territories.size() / nPlayers;
            for (int j = 0; j < territoriesPerPlayer; j++) {
                Territory t = toAssign.remove(0); // cosi di sicuro elimino sempre uno dopo averlo assegnato
                // e il tutto avviene per il giusto numero di volte, territoriPerPlayer
                selectedPlayer.addTerritory(t);
                t.setOwner(selectedPlayer);
            }

        }

        int remainingTerritories = toAssign.size();
        for (int i = 0; i < remainingTerritories; i++) {
            Player selectedPlayer = players.get(i);
            Territory assignedTerritory = toAssign.remove(0);
            selectedPlayer.addTerritory(assignedTerritory);
            assignedTerritory.setOwner(selectedPlayer);
        }
    }

   private void initializeWorld() {
    // Mappa globale che collega i nomi dei territori alle loro istanze
    HashMap<String, Territory> territoryMap = new HashMap<>();

    // Primo passaggio: Creare tutti i territori e inserirli nella mappa
    for (String continentName : data.keySet()) {
        HashMap<String, ArrayList<String>> continentTerritoriesData = data.get(continentName);

        for (String territoryName : continentTerritoriesData.keySet()) {
            // Se il territorio non è già stato creato, lo creiamo
            if (!territoryMap.containsKey(territoryName)) {
                Territory territory = new Territory(territoryName);
                territoryMap.put(territoryName, territory);
                territories.add(territory); // Aggiungilo alla lista globale
            }
        }
    }

    // Secondo passaggio: Popolare i territori confinanti
    for (String continentName : data.keySet()) {
        HashMap<String, ArrayList<String>> continentTerritoriesData = data.get(continentName);

        for (String territoryName : continentTerritoriesData.keySet()) {
            Territory currentTerritory = territoryMap.get(territoryName);
            ArrayList<String> linkedTerritoriesNames = continentTerritoriesData.get(territoryName);

            // Aggiungi i territori confinanti alla lista del territorio corrente
            for (String linkedTerritoryName : linkedTerritoriesNames) {
                Territory linkedTerritory = territoryMap.get(linkedTerritoryName);
                currentTerritory.addLinkedTerritory(linkedTerritory);
            }
        }
    }

    // Terzo passaggio: Creare i continenti e popolare la lista `continents`
    for (String continentName : data.keySet()) {
        HashMap<String, ArrayList<String>> continentTerritoriesData = data.get(continentName);
        ArrayList<Territory> continentTerritories = new ArrayList<>();

        for (String territoryName : continentTerritoriesData.keySet()) {
            continentTerritories.add(territoryMap.get(territoryName));
        }

        Continent continent = new Continent(continentName, continentTerritories);
        continents.add(continent);
    }
    
}


}


