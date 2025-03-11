package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.achivement.*;
import it.unibs.pajc.risiko.utility.MyMath;
import it.unibs.pajc.risiko.xml.XmlReader;
import java.awt.Color;
import java.util.*;

public class GameModel {
    private HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>(); // TODO constructor
    // private ArrayList<Card> cardDeck = new ArrayList<>(); // TODO constructor
    private ArrayList<Territory> territories = new ArrayList<>(); // che sarà =world.getTerritories(); questo dico
    private ArrayList<Continent> continents = new ArrayList<>();
    private ArrayList<Achievement> achievements = new ArrayList<>();
    private XmlReader reader;
    private boolean reachedCard = false;

    public GameModel() {
        this.reader = new XmlReader("it/unibs/pajc/risiko/xml/territories.xml");
        data = reader.getData();
        initializeWorld();
        initializeAchievements();

    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public ArrayList<Continent> getContinents() {
        return continents;
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

    public void initializeAchievements() { // usa la findcontinent() e hasConquiredContinent() che ho fatto
        // 18 ter con 2 armate
        this.achievements.add(new Achievement("Conquistare 18 territori presidiandoli con almeno due armate ciascuno",
                player -> player.getNumberTerritories() >= 18 && player.hasAtLeastTwoTanks()));
        // 24 ter
        this.achievements
                .add(new Achievement("Conquistare 24 territori", player -> player.getNumberTerritories() >= 24));
        // nordAmerica e africa
        this.achievements.add(new Achievement("Conquistare la totalità del Nord America e dell'Africa",
                player -> {
                    Continent nordAmerica = findContinent("America Del Nord");
                    Continent africa = findContinent("Africa");
                    return player.hasConqueredContinent(africa) && player.hasConqueredContinent(nordAmerica);
                }));
        // america e oceania
        this.achievements.add(new Achievement("Conquistare la totalità del Nord America e dell'Oceania",
                player -> {
                    Continent oceania = findContinent("Oceania");
                    Continent africa = findContinent("Africa");
                    return player.hasConqueredContinent(africa) && player.hasConqueredContinent(oceania);
                }));
        // asia e sud america
        this.achievements.add(new Achievement("Conquistare la totalità dell'Asia e del Sud America",
                player -> {
                    Continent asia = findContinent("Asia");
                    Continent sudAmerica = findContinent("America Del Sud");
                    return player.hasConqueredContinent(asia) && player.hasConqueredContinent(sudAmerica);
                }));
        // asia e africa
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
        // armata rossa
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate rosse. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> defeatArmy(Color.RED, player)));
        // armata nera
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate nere. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> defeatArmy(Color.BLACK, player)));
        // armata blu
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate blu. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> defeatArmy(Color.BLUE, player)));
        // armata verde
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate verdi. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> defeatArmy(Color.GREEN, player)));
        // il viola non esiste
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate viola. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> defeatArmy(Color.PINK, player)));
        // armate gialle
        this.achievements.add(new Achievement(
                "Distruggere completamente le armate gialle. Se le armate non sono presenti nel gioco, se le armate sono possedute dal giocatore che ha l'obiettivo di distruggerle o se l'ultima armata viene distrutta da un altro giocatore, l'obiettivo diventa conquistare 24 territori",
                player -> defeatArmy(Color.YELLOW, player)));
    } // TODO: gestire se io ho quel colore di armata

    public Continent findContinent(String nameContinent) {
        for (Continent c : continents)
            if (c.getName().equals(nameContinent))
                return c;
        return null;
    }

    public boolean defeatArmy(Color c, Player player) { // passo il player distruttente anche se è controintuitivo
        Player opponent = findPlayerFromColor(c);
        if (opponent != null)
            return opponent.isEliminated();
        else
            return player.getNumberTerritories() >= 24;
    }

    public Player findPlayerFromColor(Color c) {
        for (Player p : players)
            if (p.getColor().equals(c))
                return p;
        return null;
    }

    // TODO assegnazione colori

    // assegnazione territori
    public void assignTerritories() { // setOwner
        ArrayList<Territory> toAssign = new ArrayList<>(territories);
        int nPlayers = players.size();

        Collections.shuffle(territories); // sostanzialmente mischia casualmente gli elementi di una lista
        int territoriesPerPlayer = territories.size() / nPlayers;
        for (int i = 0; i < nPlayers; i++) {

            Player selectedPlayer = players.get(i);
            for (int j = 0; j < territoriesPerPlayer; j++) {
                Territory t = toAssign.remove(new Random().nextInt(toAssign.size())); // cosi di sicuro elimino sempre
                                                                                      // uno dopo averlo assegnato
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // assegna obbiettivi
    public void assignAchievements() {
        ArrayList<Achievement> toAssign = new ArrayList<>(achievements);
        for (Player p : players) {
            p.setAchievements(toAssign.remove(new Random().nextInt(toAssign.size())));
        }
    }

    // TODO disposizione tank
    public void placeTanks(Player p) {// TODO pensare alla classe turno
        int placebleTanks = p.getBonusTanks();
        while (placebleTanks > 0) {
            for (Territory t : p.getTerritories()) {
                if (placebleTanks > 0) {
                    t.incrementsUnits(1);
                    placebleTanks--;
                } else {
                    break;
                }
            }
        }

        // TODO implementare la logica di distribuzione dei tank
        p.setBonusTanks(0);
    }

    // inizia per primo
    public ArrayList<Player> startFirst() {
        ArrayList<Player> firstPlayers = new ArrayList<>();
        HashMap<Player, Integer> rolledNumbers = new HashMap<>();

        // Ogni giocatore tira il dado
        for (Player p : players) {
            rolledNumbers.put(p, MyMath.diceRoll());
            System.out.println(p.getName() + "\t" + rolledNumbers.get(p));
        }

        // Ordina i giocatori in base ai valori dei dadi tirati (decrescente)
        rolledNumbers.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // do il criterio
                .forEach(entry -> firstPlayers.add(entry.getKey())); // Aggiungi i giocatori ordinati

        return firstPlayers;
    }

    // conta territori
    public int bonusTankPerTerritories(Player p) {

        return p.getTerritories().size() / 3;
    }

    // hai continenti?-> aggiunta tank
    public int bonusTankPerContinent(Player p) { // TODO sommare tutti i bonus o altro attributo?
        ArrayList<Continent> conqueredContinents = new ArrayList<>();
        int bonus = 0;
        for (Continent c : continents) {
            if (p.hasConqueredContinent(c))
                conqueredContinents.add(c);
        }
        for (Continent c : conqueredContinents) {
            switch (c.getName()) {
                case "Africa" -> bonus += 3;
                case "Asia" -> bonus += 7;
                case "Europa" -> bonus += 5;
                case "America Del Nord" -> bonus += 5;
                case "America Del Sud" -> bonus += 2;
                case "Ocenia" -> bonus += 2;

                default -> {
                }
            }
        }
        return bonus;
    }

    // TODO disponi bonus

    // attacco
    public boolean attack(Territory att, Territory dif, int attTanks) { // TODO controlllare il check att tanks > 1 con
                                                                        // interfaccia utente
        int difTanks = 0;
        switch (dif.getNumberTanks()) {
            case 1:
                difTanks = 1;
                break;
            case 2:
                difTanks = 2;
                break;
            default:
                difTanks = 3;
                break;
        }

        if (att.isLinked(dif) && att.getNumberTanks() > 1 && !att.getOwner().equals(dif.getOwner())) {
            Integer[] difRolls = new Integer[difTanks]; // = {MyMath.diceRoll ...}
            Integer[] attRolls = new Integer[attTanks];
            // dipende con quanto attacchi e quanto difendi
            for (int i = 0; i < difTanks; i++) {
                difRolls[i] = MyMath.diceRoll();
            }
            for (int i = 0; i < attTanks; i++) {
                attRolls[i] = MyMath.diceRoll();
            }

            Arrays.sort(difRolls, (a, b) -> Integer.compare(b, a));
            Arrays.sort(attRolls, (a, b) -> Integer.compare(b, a));

            int nFight = Math.min(attTanks, difTanks);

            for (int i = 0; i < nFight; i++) { // ha senso. se è 3 v 1 gioca il maggior risultato
                                               // dell'attacco contro l'unico della difesa.
                                               // avendone lanciati 3 ha piu probabilità di fare
                                               // un numero piu alto ma si itera 1 sola volta
                if (attRolls[i] > difRolls[i]) // al pareggio vince la difesa, io ho sempre giocato cosi
                    dif.decrementsUnits(1);
                else {
                    att.decrementsUnits(1);
                    attTanks--;
                }
            }
            if (dif.getNumberTanks() == 0) {// TODO da qualche parte ma no qui, se conquista almeno un territorio dai la
                                            // carta
                att.decrementsUnits(attTanks);
                dif.getOwner().removeTerritory(dif);
                dif.setOwner(att.getOwner());
                dif.setNumberTanks(attTanks);
                att.getOwner().addTerritory(dif);
                this.reachedCard = true;
                pickCard(att.getOwner());
                return true;
            }
        } else
            System.out.println("ATTACCO NON FATTIBILE"); // TODO occhio al return
        return false;
    }

    public void pickCard(Player winner) {

        if (reachedCard) {
            winner.addCard(MyMath.generatedCard());
        }
        reachedCard = false;
    }

    public void checkCards(Player p) { // scorro le carte e se ne ha 3 di un tipo prende dei tanks bonus con un for, if
                                       // e switch

        for (Map.Entry<Card, Integer> entry : p.getCards().entrySet()) {
            if (entry.getValue() == 3) {
                switch (entry.getKey()) {
                    case gun -> p.incrementBonusTank(3);
                    case jack -> p.incrementBonusTank(5);
                    case knight -> p.incrementBonusTank(7);
                }
            }
        }
    }

    public boolean checkAchievent(Player p) {
        return p.getAchievement().isAchived(p);
    }

    public void checkVictory() {
        for (Player p : players) {
            if (checkAchievent(p)) {
                System.out.println("VITTORIA DI " + p.getName());
            }

        }
    }

    public boolean isGameOver(){
        for (Player p : players) {
            if (checkAchievent(p)) { // Se un giocatore ha completato l'obiettivo, il gioco finisce
                return true;
            }
        }
        
        // Controlla se c'è un solo giocatore rimasto
        int playersInGame = 0;
        for (Player p : players) {
            if (!p.getTerritories().isEmpty()) {
                playersInGame++;
            }
        }
        
        return playersInGame == 1; // Il gioco termina se rimane un solo giocatore
    }

    public void playGame() { 
        Turn turnManager = new Turn(this); //ricorda asteroid in un certo senso, ugo ti spiega
        
        while (!isGameOver()) {
            System.out.println("Turno di: " + turnManager.getCurrentPlayer().getName());
            
            turnManager.startTurn();
            
            // TODO fare tutte le cose di un turno?
            
            turnManager.nextTurn();
        }
        
        checkVictory();
    }

}
