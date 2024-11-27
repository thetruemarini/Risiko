package it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Territory {
    private String name;
    private int numberTanks;
    private Player owner;
    private ArrayList<Territory> linkedTerritories = new ArrayList<>(); 

    public Territory(String name) {
        this.name = name;
        this.numberTanks = 0;
        this.owner = null;
    }

    public ArrayList<Territory> getLinkedTerritories() {
        return linkedTerritories;
    }

    public void addLinkedTerritory(Territory territory) {
        linkedTerritories.add(territory);
    }

    public void setLinkedTerritories(ArrayList<Territory> linkedTerritories){
        this.linkedTerritories = linkedTerritories;
    }

    public void incrementsUnits(int nTanks){
        this.numberTanks += nTanks;
    }
    
    public void decrementsUnits(int nTanks){
        this.numberTanks -= nTanks;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setNumberTanks(int numberTanks){
        
    }

    public boolean isLinked(Territory t){//TODO cambiarla, sad
        for(Territory tx: linkedTerritories)
            if(tx.name.equals(t.name))
             return true;
        return false;
    }
}
