package it.unibs.pajc.risiko;

import it.unibs.pajc.risiko.achivement.Achievement;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String name;
    private Color color;
    private ArrayList<Territory> territoriesList = new ArrayList<>();
    private HashMap<Card, Integer> cards = new HashMap<>();; 
    private Achievement target; //TODO implement
    
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        initializeCards();
    }

    public void setAchievements(Achievement target){
        this.target = target;
    }

    public Achievement getAchievement(){
        return target;
    }

    private void initializeCards(){
        cards.put(Card.gun, 0);
        cards.put(Card.jack, 0);
        cards.put(Card.knight, 0);
    }

    public void addTerritory(Territory t){
        territoriesList.add(t);
    }

    public Color getColor(){
        return this.color;
    }

    public int getNumberTerritories(){
        return territoriesList.size();
    }

    public ArrayList<Territory> getTerritories(){
        return territoriesList;
    }

    public boolean hasAtLeastTwoTanks(){
        for(Territory t: territoriesList)
            if(t.getNumberTanks() < 2)
                return false;
        return true;
    }
    
    public boolean hasConqueredContinent(Continent continent){

        ArrayList<Territory> continentTerritories = continent.getTerritories();
        return territoriesList.containsAll(continentTerritories);
    }

    public boolean isEliminated(){
        return territoriesList.isEmpty();
    }

    public String getName() {
        return name;
    }
    
    
}
