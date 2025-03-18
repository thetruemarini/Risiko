package src.it.unibs.pajc.risiko;

import java.util.ArrayList;

public class RisikoLocalCntrl {

    private RisikoModel model;

    public RisikoLocalCntrl(RisikoModel model) {
        this.model = model;
    }  

    public boolean isMyTurn(Player p) {
        return model.getCurrentPlayer() != null && model.getCurrentPlayer() == p;
    }

    public void attack(Territory from, Territory to, int numArmies) {
        model.attack(from, to, numArmies);
    }

    public void moveArmies(Territory from, Territory to, int numArmies) {
        // TODO model.moveArmies(from, to, numArmies);
    }

    public ArrayList<Territory> getPlayerTerritories(Player p) {
        return p.getTerritories();
    }

    public GameStatus getStatus() {
        return model.getStatus();
    }

    public Player getCurrentPlayer() {
        return model.getCurrentPlayer();
    }


    
}
