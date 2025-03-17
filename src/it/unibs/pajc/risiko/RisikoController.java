package src.it.unibs.pajc.risiko;

public class RisikoController {
    private GameModel model;
    private RisikoWindow view;

    public RisikoController(GameModel model, RisikoWindow view) {
        this.model = model;
        this.view = view;
        initListeners();
    }

    private void initListeners() {
        // Ascolta quando viene selezionata un'azione dal RoundPnl
        view.getRoundPanel().addActionListenerToComboBox(e -> handleRoundAction((String) view.getRoundPanel().getSelectedAction()));
    }

    public void handleRoundAction(String selectedOption) {
    
        switch (selectedOption) {
            case "Attacca":
    
                //model.attack(); // Questo aggiornerà lo stato del gioco
                // Aggiungi l'effetto sul ChronoPnl
                view.getChronoPnl().addToChrono("Azione di attacco eseguita");
                // Cambia la mappa (es. evidenzia le truppe coinvolte nell'attacco)
                //view.getMapPanel().updateMapAfterAttack(); // Aggiungi un metodo nella mappa per aggiornarla
                //devo aggioranre tutto, anche il playerPnl, o si aggiorna se aggiorno tutta la window con un repaint?
                break;
            case "Muovi truppe":
                //model.move(); // Chiamata al modello per muovere le truppe
                view.getChronoPnl().addToChrono("Truppe mosse");
                //view.getMapPanel().updateMapAfterMove(); // Aggiungi un metodo per aggiornare la mappa
                break;
            case "Termina turno":
                //model.endTurn();
                view.getChronoPnl().addToChrono("Turno terminato");
                break;
            case "Rinforza":
                //model.reinforce();
                view.getChronoPnl().addToChrono("Rinforzo eseguito");
                break;
        }
    }
}
