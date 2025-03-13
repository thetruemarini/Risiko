package it.unibs.pajc.risiko.server;

import it.unibs.pajc.risiko.Player;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class RisikoServer {

    public int playerCount = 0;
    public RisikoGame game = new RisikoGame();

    public static void main(String[] args) {
        RisikoServer risikoServer = new RisikoServer();
        try (ServerSocket server = new ServerSocket(1234)) {
            log("Risiko server started");

            while (true) {
                Socket playerSocket = server.accept();
                risikoServer.addPlayer(playerSocket);
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        log("Risiko server closed!");
    }

    public static void log(String message, Object... args) {
        System.out.println(String.format("[%s] %s", LocalDateTime.now(), String.format(message, args)));
    }  
    
    public synchronized void addPlayer(Socket playerSocket) {
        if (game.getPlayers().size() >= 6) {
            log("Cannot accept more players, game is full!");
            try {
                playerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        ++playerCount;
        Player player = new Player("Player " + playerCount);
        game.addPlayer(player);

        log("Player %s joined the game! (%d/6 players)", player.getName(), game.getPlayers().size());

        RisikoServerProtocol playerProtocol = new RisikoServerProtocol(game, player, playerSocket);
        new Thread(playerProtocol).start(); //sempre se serve?

    }
    //e startGame()?
    
}
