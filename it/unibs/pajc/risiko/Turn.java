package it.unibs.pajc.risiko;

//è un'idea perche penso che cosi chiuderemmo il model (che è il game)
//e dopo basterebbe avere un controller a cui passare sia model che view

public class Turn {

    private GameModel game;
    private Player currentPlayer;

    public Turn(GameModel game) {
        this.game = game;
        this.currentPlayer = game.getPlayers().get(0);
    }

    public void nextTurn() {//TODO
        int index = game.getPlayers().indexOf(currentPlayer);
        currentPlayer = game.getPlayers().get(0);//metto 0 ma intendo pensare al prossimo giocatore.
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
