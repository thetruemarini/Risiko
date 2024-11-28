package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.achivement.*;
import it.unibs.pajc.risiko.xml.XmlReader;
import java.awt.Color;
import java.util.*;

public class Game {
    private HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>(); // TODO constructor
    private ArrayList<Card> cardDeck = new ArrayList<>(); // TODO constructor
    private ArrayList<Territory> territories = new ArrayList<>(); // che sarà =world.getTerritories(); questo dico
    private ArrayList<Continent> continents = new ArrayList<>();
    private ArrayList<Achievement> achievements = new ArrayList<>();
    private XmlReader reader;

    public Game() {
        this.reader = new XmlReader("it/unibs/pajc/risiko/xml/territories.xml");
        data = reader.getData();
        initializeWorld();
        setAchievements();

    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public ArrayList<Achievement> getAchievements(){
        return achievements;
    }
    
    public ArrayList<Territory> getTerritories(){
        return territories;
    }

    /*ArrayList<Territory> toAssign = new ArrayList<>(territories);

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
*/
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
    public void setAchievements() { //usa la findcontinent()  e hasConquiredContinent() che ho fatto
        //18 ter con 2 armate 
        this.achievements.add(new Achievement("Conquistare 18 territori presidiandoli con almeno due armate ciascuno",
                player -> player.getNumberTerritories() >= 18 && player.hasAtLeastTwoTanks()));  
        //24 ter
        this.achievements
                .add(new Achievement("Conquistare 24 territori", player -> player.getNumberTerritories() >= 24));
        //nordAmerica e africa
        this.achievements.add(new Achievement("Conquistare la totalità del Nord America e dell'Africa",
                player -> {
                    Continent nordAmerica = findContinent("Nord America");
                    Continent africa = findContinent("Africa");
                    return player.hasConqueredContinent(africa) && player.hasConqueredContinent(nordAmerica);
                }));
        //america e oceania
        this.achievements.add(new Achievement("Conquistare la totalità del Nord America e dell'Oceania",
        player -> {
            Continent oceania = findContinent("Oceania");
            Continent africa = findContinent("Africa");
            return player.hasConqueredContinent(africa) && player.hasConqueredContinent(oceania);
        }));
        //asia e sud america
        this.achievements.add(new Achievement("Conquistare la totalità dell'Asia e del Sud America",
        player -> {
            Continent asia = findContinent("Asia");
            Continent sudAmerica = findContinent("Sud America");
            return player.hasConqueredContinent(asia) && player.hasConqueredContinent(sudAmerica);
        }));
        //asia e africa
        this.achievements.add(new Achievement("Conquistare la totalità dell'Asia e dell'Africa",
                player -> {
            Continent asia = findContinent("Asia");
            Continent africa = findContinent("Africa");
            return player.hasConqueredContinent(africa) && player.hasConqueredContinent(asia);
        }));

        this.achievements.add(new Achievement(
                "Conquistare la totalità dell'Europa, del Sud America e di un terzo continente a scelta",
                player -> player.getNumberTerritories() >= 168));
        this.achievements.add(new Achievement(
                "Conquistare la totalità dell'Europa, dell'Oceania e di un terzo continente a scelta",
                        player -> player.getNumberTerritories() >= 192));
        
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate rosse. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> {
                    Color c = Color.RED;
                    Player p = findPlayerFromColor(c);
                    if(p != null)
                        return p.isEliminated();
                    else
                        return player.getNumberTerritories() >= 24;
                }));
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate nere. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> player.getNumberTerritories() >= 216));
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate blu. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> player.getNumberTerritories() >= 216));
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate verdi. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> player.getNumberTerritories() >= 216));
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate viola. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> player.getNumberTerritories() >= 216));
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate gialle. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> player.getNumberTerritories() >= 216));
    }

    public Continent findContinent(String nameContinent){
        for(Continent c: continents)
            if(c.getName().equals(nameContinent))
                return c;
        return null;
    }

    public boolean defeatArmy(Color c, Player player){
        Player p = findPlayerFromColor(c);
        if(p != null)
            return p.isEliminated();
        else
            return player.getNumberTerritories() >= 24;
    }

    public Player findPlayerFromColor(Color c){
        for(Player p: players)
            if(p.getColor().equals(c))
            return p;
        return null; 
    }

    

}
