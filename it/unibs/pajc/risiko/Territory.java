package it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Territory {
    private String name;
    private int numberTanks;
    private Player owner;
    private ArrayList<Territory> linkedTerritories = new ArrayList<>(); 
    //TODO confine come attributo (->svg)

    public Territory(String name) {
        this.name = name;
        this.numberTanks = 0;
        this.owner = null;
    }

    //TODO illuminazione: va bene anche cosi senza il grafo, l'importante è gestire i linked
    //TODO con un array e inserire funzioni add e remove per aggiungere e rimuovere territori
    //TODO collegati cosi non serve inizializzare nella classe territorio la lista territori 
    //TODO che creerebbe loop strani e basta usare queste funzioni nella classe game o (se si 
    //TODO vuole) nella classe world che svolge il compito della classe grafo . oh yea


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

    public int getNumberTanks(){
        return this.numberTanks;
    }

    public boolean isLinked(Territory t){//TODO cambiarla, sad
        for(Territory tx: linkedTerritories)
            if(tx.name.equals(t.name))
             return true;
        return false;
    }
    //TODO canBeAttacked per vedere l'owner del territorio
}
