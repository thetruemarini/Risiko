package it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Territory {
    private String name;
    private int numberTanks;
    private Player owner;
    private ArrayList<Territory> linkedTerritories = new ArrayList<>(); 

    public Territory(String name, int numberTanks, Player owner, ArrayList<Territory> linkedTerritories) {
        this.name = name;
        this.numberTanks = numberTanks;
        this.owner = owner;
        inizializeLinkedTerritories();
    }

    private void inizializeLinkedTerritories() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inizializeLinkedTerritories'");
    }

    public void incrementsUnits(int nTanks){
        this.numberTanks += nTanks;
    }
    
    public void decrementsUnits(int nTanks){
        this.numberTanks -= nTanks;
    }

}
