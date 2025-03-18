package src.it.unibs.pajc.risiko;

public class GameStatus { //per ora si attacca una sola volta poi sistemiamo

    public enum Phase {
            REINFORCEMENT, ATTACK, MOVEMENT;
    }
    
    public enum State {
            IN_PROGRESS, VICTORY, GAME_OVER;
    }
    
    private int turnNumber;
    private Player currentPlayer;
    private Phase currentPhase;
    private State gameState;
    
    public GameStatus(Player startingPlayer) {
        this.turnNumber = 1;
        this.currentPlayer = startingPlayer;
        this.currentPhase = Phase.REINFORCEMENT;
        this.gameState = State.IN_PROGRESS;
    }
    
    public void nextPhase() {
        switch (currentPhase) {
            case REINFORCEMENT -> currentPhase = Phase.ATTACK;
            case ATTACK -> currentPhase = Phase.MOVEMENT;
            case MOVEMENT -> {
                currentPhase = Phase.REINFORCEMENT;
                turnNumber++;
            }
        }
    }
    
    public void setVictory(Player winner) {
        this.gameState = State.VICTORY;
        System.out.println("Il giocatore " + winner.getName() + " ha vinto!");
    }
    
    public void setGameOver() {
        this.gameState = State.GAME_OVER;
    }
    
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Phase getCurrentPhase() {
        return currentPhase;
    }
    
    public int getTurnNumber() {
        return turnNumber;
    }
    
    public State getGameState() {
        return gameState;
    }
}
    
    
