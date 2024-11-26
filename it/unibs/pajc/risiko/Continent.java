package it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Continent {
    private String name;
    private ArrayList<Territory> states;
    private int bonusTanks;

    public Continent(String name, ArrayList<String> territoriesName, int bonusTanks) {
        this.name = name;
        this.bonusTanks = bonusTanks;
        inizializeStates(territoriesName);
    }

    private void inizializeStates(ArrayList<String> territoryNames) {
            for(String name:territoryNames){
                states.add(new Territory(name));
            }
    }

}
