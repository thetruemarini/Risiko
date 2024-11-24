package it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players; //TODO constructor
    private ArrayList<Territory> territories; // per ors teniamos cosis
    private World world;
    private ArrayList<Card> cardDeck = new ArrayList<>(); //TODO constructor
    private ArrayList<Achievement> targetDeck = new ArrayList<>(); //non bellissimo

    public Game(World world) {
        this.world = world;
    }

    private void addPlayer(Player p){
        players.add(p);
    }

    

}
