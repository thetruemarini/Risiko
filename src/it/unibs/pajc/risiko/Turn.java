package src.it.unibs.pajc.risiko;

public class Turn {

    private RisikoModel game;
    private Player currentPlayer;

    public Turn(RisikoModel game) {
        this.game = game;
        this.currentPlayer = game.getPlayers().get(0);
    }


    public void nextTurn() {
        int index = game.getPlayers().indexOf(currentPlayer);
        int nextIndex = (index + 1) % game.getPlayers().size(); // Ciclo dei giocatori
        currentPlayer = game.getPlayers().get(nextIndex);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    public void reinforcePhase(Player currentPlayer) {
        int tanksFromTerritories = game.bonusTankPerTerritories(currentPlayer);
        int tanksFromContinents = game.bonusTankPerContinent(currentPlayer);
        
        // Controlla le carte e assegna eventuali bonus
        game.checkCards(currentPlayer);
        
        // Il totale dei tank che il giocatore può piazzare
        int tanksToPlace = tanksFromTerritories + tanksFromContinents + currentPlayer.getBonusTanks();
        
        System.out.println("Posiziona i carri armati: " + tanksToPlace + " disponibili");
    
        // Gestione del clic sui territori
        // TODO
    }

    public void attackPhase(Player currentPlayer) {
        // TODO
    }   

    public void movementPhase(Player currentPlayer) {
        // TODO
    }   
    
    public void endTurn() {
        nextTurn();
    }

}
