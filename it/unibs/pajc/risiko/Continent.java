package it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Continent {
    private String name;
    private ArrayList<Territory> states;
    private int bonusTanks;

    public Continent(String name, ArrayList<Territory> territories/* , int bonusTanks */) { // TODO pensare ai bonus
                                                                                            // TODO tank
        this.name = name;
        this.states = territories;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Territory> getTerritories() {
        return this.states;
    }
}
