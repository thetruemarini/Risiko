package src.it.unibs.pajc.risiko.server;

import java.util.ArrayList;
import java.util.List;
import src.it.unibs.pajc.risiko.Player;
import src.it.unibs.pajc.risiko.RisikoModel;

public class RisikoGame {
    private RisikoModel gameModel = new RisikoModel();
    private List<Player> players = new ArrayList<>();
    private boolean started = false;

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean isStarted() {
        return started;
    }

}
