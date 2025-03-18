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

    public void startTurn() {
        int tanksFromTerritories = game.bonusTankPerTerritories(currentPlayer);
        int tanksFromContinents = game.bonusTankPerContinent(currentPlayer);
        
        // Controlla le carte e assegna eventuali bonus
        game.checkCards(currentPlayer);
        
        // Il totale dei tank che il giocatore può piazzare
        int totalTanks = tanksFromTerritories + tanksFromContinents + currentPlayer.getBonusTanks();
        currentPlayer.setBonusTanks(totalTanks);
        
        placeTanks();
    }

    public void placeTanks() {
        int placeableTanks = currentPlayer.getBonusTanks();
    
        while (placeableTanks > 0) {
            for (Territory t : currentPlayer.getTerritories()) {
                if (placeableTanks > 0) {
                    t.incrementsUnits(1);
                    placeableTanks--;
                } else {
                    break;
                }
            }
        }
        
        // Una volta piazzati i carri armati, azzeriamo il bonus
        currentPlayer.setBonusTanks(0);
    }

}
