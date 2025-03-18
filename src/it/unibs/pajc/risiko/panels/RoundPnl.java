package src.it.unibs.pajc.risiko.panels;

import java.awt.*;
import javax.swing.*;
import src.it.unibs.pajc.risiko.RisikoLocalCntrl;

public class RoundPnl extends JPanel {
    private RisikoLocalCntrl cntrl;
    private ChronoPnl chronoPnl;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JPanel reinforcementPanel;
    private JPanel attackPanel;
    private JPanel movementPanel;

    private JLabel bonusTanksLabel;
    private JLabel selectAttackFromLabel;
    private JLabel selectAttackToLabel;
    private JLabel moveFromLabel;
    private JLabel moveToLabel;

    private JButton nextToAttackButton;
    private JButton nextToMovementButton;
    private JButton nextTurnButton;

    public RoundPnl(RisikoLocalCntrl cntrl, ChronoPnl chronoPnl) {
        this.cntrl = cntrl;
        this.chronoPnl = chronoPnl;

        setLayout(new BorderLayout());

        // Creiamo un CardLayout per gestire il cambio di pannelli
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Creiamo i pannelli delle fasi
        reinforcementPanel = createReinforcementPanel();
        attackPanel = createAttackPanel();
        movementPanel = createMovementPanel();

        // Aggiungiamo i pannelli alla cardPanel
        cardPanel.add(reinforcementPanel, "REINFORCEMENT");
        cardPanel.add(attackPanel, "ATTACK");
        cardPanel.add(movementPanel, "MOVEMENT");

        // Aggiungiamo tutto al RoundPnl
        add(cardPanel, BorderLayout.CENTER);

        // Mostra la fase iniziale
        updatePhase();
    }

    private JPanel createReinforcementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        bonusTanksLabel = new JLabel("Bonus Tanks: 0");
        JLabel instructionLabel = new JLabel("Clicca su uno dei tuoi stati per posizionare i tanks.");
        nextToAttackButton = new JButton("GO TO THE ATTACK PHASE");

        nextToAttackButton.addActionListener(e -> {
            cntrl.getStatus().nextPhase();
            updatePhase();
        });

        panel.add(bonusTanksLabel);
        panel.add(instructionLabel);
        panel.add(nextToAttackButton);

        return panel;
    }

    private JPanel createAttackPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        selectAttackFromLabel = new JLabel("SELEZIONA DA DOVE ATTACCARE");
        selectAttackToLabel = new JLabel("SELEZIONA IL BERSAGLIO");
        nextToMovementButton = new JButton("REINFORCE");

        nextToMovementButton.addActionListener(e -> {
            cntrl.getStatus().nextPhase();
            updatePhase();
        });

        panel.add(selectAttackFromLabel);
        panel.add(selectAttackToLabel);
        panel.add(nextToMovementButton);

        return panel;
    }

    private JPanel createMovementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        moveFromLabel = new JLabel("Seleziona lo stato da cui prelevare carri armati");
        moveToLabel = new JLabel("Seleziona lo stato in cui spostarli");
        nextTurnButton = new JButton("END TURN");

        nextTurnButton.addActionListener(e -> {
            cntrl.getStatus().nextPhase();
            updatePhase();
        });

        panel.add(moveFromLabel);
        panel.add(moveToLabel);
        panel.add(nextTurnButton);

        return panel;
    }

    public void updatePhase() {
        switch (cntrl.getStatus().getCurrentPhase()) {
            case REINFORCEMENT -> cardLayout.show(cardPanel, "REINFORCEMENT");
            case ATTACK -> cardLayout.show(cardPanel, "ATTACK");
            case MOVEMENT -> cardLayout.show(cardPanel, "MOVEMENT");
        }
    }

    public void updateBonusTanks(int tanks) {
        bonusTanksLabel.setText("Bonus Tanks: " + tanks);
    }

    public void updateAttackSelection(String from, String to) {
        selectAttackFromLabel.setText("Attacco da: " + (from == null ? "Seleziona" : from));
        selectAttackToLabel.setText("Attacco a: " + (to == null ? "Seleziona" : to));
    }

    public void updateMovementSelection(String from, String to) {
        moveFromLabel.setText("Sposta da: " + (from == null ? "Seleziona" : from));
        moveToLabel.setText("Sposta a: " + (to == null ? "Seleziona" : to));
    }
}
