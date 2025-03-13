package it.unibs.pajc.risiko.server;

import it.unibs.pajc.risiko.GameModel;
import it.unibs.pajc.risiko.Player;
import java.util.ArrayList;
import java.util.List;

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

    public void startGame() {
        started = true;
        // qua dentro implemento penso
    }
}
