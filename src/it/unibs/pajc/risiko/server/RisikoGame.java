package src.it.unibs.pajc.risiko.server;

import java.util.ArrayList;
import java.util.List;

import src.it.unibs.pajc.risiko.GameModel;
import src.it.unibs.pajc.risiko.Player;

public class RisikoGame {
    private GameModel gameModel = new GameModel();
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
