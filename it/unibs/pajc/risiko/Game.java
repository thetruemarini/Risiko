package it.unibs.pajc.risiko;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private ArrayList<Player> players; // TODO constructor
    private ArrayList<Territory> territories; // per ors teniamos cosis
    private World world;
    private ArrayList<Card> cardDeck = new ArrayList<>(); // TODO constructor
    private ArrayList<Achievement> targetDeck = new ArrayList<>(); // non bellissimo

    public Game(World world) {
        this.world = world;
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

}
