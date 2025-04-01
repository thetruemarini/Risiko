package src.it.unibs.pajc.risiko;

import java.util.ArrayList;

public class Territory {
    private String name;
    private ArrayList<Integer> shapeIds = new ArrayList<>();
    private int numberTanks;
    private Player owner;
    private ArrayList<Territory> linkedTerritories = new ArrayList<>();
    // TODO confine come attributo (->svg)

    public Territory(String name) {
        this.name = name;
        this.numberTanks = 0;
        this.owner = null;
    }

    public ArrayList<Integer> getShapeIds() {
        return shapeIds;
    }

    public void setShapeIds(ArrayList<Integer> shapeIds) {
        this.shapeIds = shapeIds;
    }

    public void addShapeId(int shapeId) {
        this.shapeIds.add(shapeId);
    }

    public ArrayList<Territory> getLinkedTerritories() {
        return linkedTerritories;
    }

    public void addLinkedTerritory(Territory territory) {
        linkedTerritories.add(territory);
    }

    public void setLinkedTerritories(ArrayList<Territory> linkedTerritories) {
        this.linkedTerritories = linkedTerritories;
    }

    public void incrementsUnits(int nTanks) {
        this.numberTanks += nTanks;
    }

    public void decrementsUnits(int nTanks) {
        this.numberTanks -= nTanks;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return this.owner;
    }

    public void setNumberTanks(int numberTanks) {
        this.numberTanks = numberTanks;
    }

    public int getNumberTanks() {
        return this.numberTanks;
    }

    public boolean isLinked(Territory t) {// TODO cambiarla, sad
        for (Territory tx : linkedTerritories)
            if (tx.name.equals(t.name))
                return true;
        return false;
    }
    // TODO canBeAttacked per vedere l'owner del territorio

    public String getName() {
        return name;
    }
}
