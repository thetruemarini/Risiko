package it.unibs.pajc.risiko;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.pajc.risiko.achivement.Achievement;

import java.awt.Color;

public class Player {
    private String name;
    private Color color;
    private ArrayList<Territory> territoriesList = new ArrayList<>();
    private HashMap<Card, Integer> cards = new HashMap<>();; 
    private Achievement target; //TODO implement
    
    public Player(String name, Color color, ArrayList<Territory> territoriesList) {
        this.name = name;
        this.color = color;
        initializeCards();
    }

    private void initializeCards(){
        cards.put(Card.gun, 0);
        cards.put(Card.jack, 0);
        cards.put(Card.knight, 0);
    }

    public void addTerritory(Territory t){
        territoriesList.add(t);
    }

    public int getNumberTerritories(){
        return territoriesList.size();
    }

    public boolean hasAtLeastTwoTanks(){
        for(Territory t: territoriesList)
            if(t.getNumberTanks() < 2)
                return false;
        return true;
    }
    
    
}
